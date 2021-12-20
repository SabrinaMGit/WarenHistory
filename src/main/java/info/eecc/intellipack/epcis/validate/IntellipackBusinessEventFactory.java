package info.eecc.intellipack.epcis.validate;

import info.eecc.commons.epcis.business.BusinessEvent;
import info.eecc.commons.epcis.business.BusinessEventFactory;
import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.commons.epcis.utils.StringUtils;

import java.util.function.Function;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
public class IntellipackBusinessEventFactory<T extends BusinessEvent> extends BusinessEventFactory<T> {
    private BusinessEventValidator validator;
    public IntellipackBusinessEventFactory(BusinessEventValidator validator, Function<EpcisEvent, T> factoryMethod){
        super(validator.getAllPredicates(), factoryMethod);
        this.validator = validator;
    }

    @Override
    public boolean matches(EpcisEvent event) {
        String matchResult = this.matchWithErrorMessage(event);
        return !StringUtils.hasText(matchResult);
    }

    /**
     * Enhanced version of match, which gives back any possible error messages.
     * @return First met error message, or an empty string("") when matched successfully.
     */
    public String matchWithErrorMessage(EpcisEvent event){
        return this.validator.validate(event);
    }
}
