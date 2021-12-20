package info.eecc.intellipack.epcis.validate;

import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.commons.epcis.validation.ValidationException;
import info.eecc.commons.gs1.Gs1Exception;
import info.eecc.commons.gs1.epc.Epc;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
public final class ValidationUtils {

    private ValidationUtils(){
    }

    public static <T extends Epc> T toEpc(EpcisEvent event, String element, String value, Class<T> requiredEpcType) {
        Epc epc;
        try {
            epc = Epc.fromUri(value);
        } catch (Gs1Exception e){
            throw new ValidationException(event, "%s contains '%s' which is not a valid EPC", element, value);
        }
        if (requiredEpcType.isAssignableFrom(epc.getClass()) ){
            return requiredEpcType.cast(epc);
        } else {
            throw new ValidationException(event, "%s contains '%s' which is not of the required type %s", element, value, requiredEpcType.getSimpleName());
        }
    }

}
