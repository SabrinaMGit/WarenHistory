package info.eecc.intellipack.logging;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
public class TraceContext {
    private static final Logger logger = LoggerFactory.getLogger(TraceContext.class);
    private static final ThreadLocal<TraceContext> contextHolder = new ThreadLocal<>();

    private static TraceContextListener traceContextListener = TraceContextListener.noop();

    public static void setTraceContextListener(@NonNull TraceContextListener listener) {
        traceContextListener = listener;
    }

    public static <R> R useWithId(TraceId traceId, Function<TraceContext, R> contextFunction) {
        TraceContext traceContext = contextHolder.get();
        if (traceContext != null) {
            return contextFunction.apply(traceContext);
        }

        if (traceId == null) {
            traceId = TraceId.random();
        }
        traceContext = new TraceContext(traceId);

        try {
            contextHolder.set(traceContext);

            traceContextListener.onStart(traceContext);

            return contextFunction.apply(traceContext);
        } finally {
            traceContext.stopTime = OffsetDateTime.now();
            traceContext.durationMillis = TimeUnit.MILLISECONDS.convert(System.nanoTime() - traceContext.startNanos, TimeUnit.NANOSECONDS);

            traceContextListener.onStop(traceContext);
            traceContext.resetContext();
            contextHolder.remove();
        }
    }

    public static void useWithId(TraceId traceId, Consumer<TraceContext> contextConsumer) {
        useWithId(traceId, ctx -> {
            contextConsumer.accept(ctx);
            return null;
        });
    }

    public static <R> R use(Function<TraceContext, R> contextFunction) {
        return useWithId(null, contextFunction);
    }

    public static void use(Consumer<TraceContext> contextConsumer) {
        useWithId(null, contextConsumer);
    }

    public static Optional<TraceContext> getContext() {
        TraceContext traceContext = contextHolder.get();
        if (traceContext == null) {
            logger.debug("no trace context set. Ensure to create context upfront via the use methods");
        }
        return Optional.ofNullable(traceContext);
    }

    private final Map<String, Object> contextEntries = new HashMap<>();
    private final TraceId traceId;
    private final long startNanos;
    private final OffsetDateTime startTime;

    private OffsetDateTime stopTime;
    private Long durationMillis;

    private TraceContext(TraceId traceId) {
        this.startTime = OffsetDateTime.now();
        this.startNanos = System.nanoTime();
        this.traceId = requireNonNull(traceId, "trace ID must not be null");
    }

    public void put(String key, Object value) {
        contextEntries.put(key, value);
    }

    public Optional<Object> get(String key) {
        return Optional.ofNullable(contextEntries.get(key));
    }

    public Map<String, Object> getAll() {
        return unmodifiableMap(contextEntries);
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public Optional<OffsetDateTime> getStopTime() {
        // only filled when stopped
        return Optional.ofNullable(stopTime);
    }

    public OffsetDateTime getStopTimeOrNull() {
        return this.stopTime;
    }

    public boolean hasFinished() {
        return this.durationMillis != null;
    }

    public Optional<Long> getDurationMs() {
        // only filled when stopped
        return Optional.ofNullable(this.durationMillis);
    }

    public Long getDurationMsOrNull() {
        return this.durationMillis;
    }

    private void resetContext() {
        contextEntries.clear();
    }
}
