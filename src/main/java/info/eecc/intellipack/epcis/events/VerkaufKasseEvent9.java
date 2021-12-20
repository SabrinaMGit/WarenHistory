package info.eecc.intellipack.epcis.events;

import epcglobal.epcis.ActionType;
import info.eecc.commons.epcis.business.BusinessEventFactory;
import info.eecc.commons.epcis.business.BusinessObjectEvent;
import info.eecc.commons.epcis.cbv.BizStep;
import info.eecc.commons.epcis.cbv.Disposition;
import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.commons.epcis.core.EventType;
import info.eecc.commons.epcis.predicates.EventPredicates;
import info.eecc.commons.epcis.validation.ValidationException;
import info.eecc.commons.gs1.epc.Sgln;
import info.eecc.intellipack.epcis.extensions.*;
import info.eecc.intellipack.epcis.validate.BusinessEventValidator;
import info.eecc.intellipack.epcis.validate.IntellipackBusinessEventFactory;
import info.eecc.intellipack.epcis.validate.ValidationRule;
import lombok.Getter;

import java.util.function.Predicate;

import static info.eecc.commons.epcis.predicates.EventPredicates.isEventType;
import static info.eecc.intellipack.epcis.validate.ValidationUtils.toEpc;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@Getter
public class VerkaufKasseEvent9 extends BusinessObjectEvent {

    private static final Predicate<EpcisEvent> PREDICATE = isEventType(EventType.OBJECT)
            .and(EventPredicates.hasAction(ActionType.ADD))
            .and(EventPredicates.hasBizStep(BizStep.RETAIL_SELLING))
            .and(EventPredicates.hasDisposition(Disposition.RETAIL_SOLD))
            .and(EventPredicates.hasReadPoint())
            .and(event -> event.standardEventId().isPresent())
            .and(event -> event.vendorExtensions(Rot.class).count() > 0)
            .and(event -> event.vendorExtensions(Gruen.class).count() > 0)
            .and(event -> event.vendorExtensions(Blau.class).count() > 0)
            .and(event -> event.vendorExtensions(RemainingLive.class).count() > 0);


    private static IntellipackBusinessEventFactory<ProduktionEvent1> FACTORY;

    private final Sgln readpoint;
    private final Sgln bizLocation;

    public VerkaufKasseEvent9(EpcisEvent event) {
        super(PREDICATE, event);

        //readpoint
        this.readpoint = event.readPoint()
                .map(rp -> toEpc(event, "readpoint", rp, Sgln.class))
                .orElse(null);
        if (this.readpoint == null) {
            throw new ValidationException(event, "%s requires a non empty readpoint", ProduktionEvent1.class);
        }

        //bizLocation
        this.bizLocation = event.bizLocation()
                .map(rp -> toEpc(event, "bizLocation", rp, Sgln.class))
                .orElse(null);
        if (this.bizLocation == null) {
            throw new ValidationException(event, "%s requires a non empty bizLocation", ProduktionEvent1.class);
        }
    }

    public static IntellipackBusinessEventFactory<ProduktionEvent1> getFactory() {
        if (FACTORY == null){
            BusinessEventValidator validator = new BusinessEventValidator();

            validator.addRule(new ValidationRule(EventPredicates.isEventType(EventType.OBJECT), "Event type should be OBJECT! "));
            validator.addRule(new ValidationRule(EventPredicates.hasAction(ActionType.ADD), "Event action should be ADD! "));
            validator.addRule(new ValidationRule(EventPredicates.hasBizStep(BizStep.RETAIL_SELLING), "Event bizStep should be RETAIL_SELLING! "));
            validator.addRule(new ValidationRule(EventPredicates.hasDisposition(Disposition.RETAIL_SOLD), "Event disposition should be RETAIL_SOLD! "));
            validator.addRule(new ValidationRule(EventPredicates.hasBizLocation(), "Event bizlocation is missing! "));
            validator.addRule(new ValidationRule(EventPredicates.hasReadPoint(), "Event readpoint is missing! "));
            validator.addRule(new ValidationRule(event -> event.standardEventId().isPresent(), "Standard event id is missing!  "));
            validator.addRule(new ValidationRule(event -> event.vendorExtensions(Rot.class).count() > 0, "Rot is missing!"));
            validator.addRule(new ValidationRule(event -> event.vendorExtensions(Gruen.class).count() > 0, "Gruen is missing!"));
            validator.addRule(new ValidationRule(event -> event.vendorExtensions(Blau.class).count() > 0, "Blau is missing!"));
            validator.addRule(new ValidationRule(event -> event.vendorExtensions(RemainingLive.class).count() > 0, "RemainingLive is missing!"));

            FACTORY = new IntellipackBusinessEventFactory<>(validator, ProduktionEvent1::new);
        }

        return FACTORY;
    }

    public Sgln getReadpoint() {
        return readpoint;
    }

    public Sgln getBizLocation() {
        return bizLocation;
    }

}
