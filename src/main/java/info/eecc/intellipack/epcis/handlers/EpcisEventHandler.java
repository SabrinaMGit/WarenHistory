package info.eecc.intellipack.epcis.handlers;

import info.eecc.commons.epcis.business.BusinessEvent;
import info.eecc.commons.epcis.business.BusinessEventFactory;
import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.commons.epcis.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
public class EpcisEventHandler<T extends BusinessEvent<?>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EpcisEventHandler.class);

    private final Class<T> eventClass;

    private final BusinessEventFactory<T> factory;

    public EpcisEventHandler(Class<T> eventClass, BusinessEventFactory<T> factory) {
        this.eventClass = eventClass;
        this.factory = factory;
    }

    public Class<T> getEventClass() {
        return eventClass;
    }

    public T tryHandleEvent(EpcisEvent epcisEvent) throws ValidationException {
        // Try cast
        T casted = null;
        if (eventClass.isAssignableFrom(epcisEvent.getClass())) {
            casted = eventClass.cast(epcisEvent);
        } else {
            Optional<T> optionalCast = factory.tryConvert(epcisEvent);
            if (optionalCast.isPresent()) casted = optionalCast.get();
        }
        if (casted == null) {
            return null;
        }
        LOGGER.debug("Event (hash {}) class Recognised as {}. Validating.", casted.hashCode(), casted.getClass().getName());
        casted.validate(); // throws ValidationException if invalid
        LOGGER.debug("Event (hash {}) is valid.", casted.hashCode());
        return casted;
    }

}
