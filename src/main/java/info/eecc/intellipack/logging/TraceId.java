package info.eecc.intellipack.logging;

import com.fasterxml.jackson.annotation.JsonValue;
import net.logstash.logback.argument.StructuredArgument;

import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
public class TraceId {

    public static final String TRACE_ID = "TraceId";
    public static final String HEADER_FIELD = "X-Rcycle-Trace-Id";
    public static final String ATTRIBUTE_FIELD = TraceId.class.getName() + "traceId";

    public StructuredArgument getAsArgument() {
        return kv(TRACE_ID, value);
    }

    private final String value;

    private TraceId(String value) {
        this.value = value;
    }

    public static TraceId fromValue(String value) {
        return new TraceId(value);
    }

    public static TraceId random() {
        String value = UUID.randomUUID().toString().replace("-", "");
        return new TraceId(value);
    }

    public String getValue() {
        return value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }
}
