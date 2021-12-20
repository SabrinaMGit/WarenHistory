package info.eecc.intellipack.epcis.validate;

import info.eecc.commons.epcis.core.EpcisEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
public class BusinessEventValidator {
    private List<ValidationRule> rules;

    public BusinessEventValidator(){
        this.rules = new ArrayList<>();
    }


    public void addRule(ValidationRule rule){
        this.rules.add(rule);
    }

    public String validate(EpcisEvent event){
        for (ValidationRule rule : this.rules) {
            if (!rule.getPredicate().test(event))
                return rule.getErrorMessage();
        }
        return "";
    }

    public Predicate<EpcisEvent> getAllPredicates(){
        Predicate<EpcisEvent> startingPredicate = (event) -> true;
        for (ValidationRule rule : this.rules) {
            startingPredicate = startingPredicate.and(rule.getPredicate());
        }
        return startingPredicate;
    }
}
