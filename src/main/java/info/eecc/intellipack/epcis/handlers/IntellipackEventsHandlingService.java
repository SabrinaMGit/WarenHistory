package info.eecc.intellipack.epcis.handlers;

import info.eecc.commons.epcis.business.BusinessEvent;
import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.commons.epcis.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@Component
public class IntellipackEventsHandlingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntellipackEventsHandlingService.class);

    private final Collection<EpcisEventHandler<? extends EpcisEvent>> eventHandlers = Arrays.asList(
    );

    public BusinessEvent<?> convertEvent(EpcisEvent epcisEvent) throws ValidationException {
        for (EpcisEventHandler<? extends EpcisEvent> handler : eventHandlers) {
            try {
                BusinessEvent<?> businessEvent = handler.tryHandleEvent(epcisEvent);
                if (businessEvent != null) {
                    return businessEvent;
                }
            } catch (ValidationException e) {
                LOGGER.debug("Invalid event. Error: {}", e.toString());
                throw e;
            }
        }
        LOGGER.error("Epcis event did not match any predicate.");
        return null;
    }
}
