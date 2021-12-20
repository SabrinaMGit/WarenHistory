package info.eecc.intellipack.epcis.validate;

import info.eecc.commons.epcis.core.EpcisEvent;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.function.Predicate;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@Value
@AllArgsConstructor
public class ValidationRule{
    private Predicate<EpcisEvent> predicate;
    private String errorMessage;
}
