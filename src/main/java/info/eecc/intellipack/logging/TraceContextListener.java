package info.eecc.intellipack.logging;

import lombok.NonNull;

import java.util.Collection;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
public interface TraceContextListener {
    static TraceContextListener of(@NonNull Collection<TraceContextListener> listeners) {
        // return a new combined listener
        return new TraceContextListener() {
            @Override
            public void onStart(@NonNull TraceContext context) {
                listeners.forEach(listener -> listener.onStart(context));
            }

            @Override
            public void onStop(@NonNull TraceContext context) {
                listeners.forEach(listener -> listener.onStop(context));
            }
        };
    }

    static TraceContextListener noop() {
        return new TraceContextListener() {
            @Override
            public void onStart(@NonNull TraceContext context) {
                // No need to implement this
            }

            @Override
            public void onStop(@NonNull TraceContext context) {
                // No need to implement this
            }
        };
    }

    void onStart(@NonNull TraceContext context);

    void onStop(@NonNull TraceContext context);
}
