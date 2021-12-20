package info.eecc.intellipack.epcis.events;

import epcglobal.epcis.ActionType;
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

import java.util.function.Predicate;

import static info.eecc.commons.epcis.predicates.EventPredicates.isEventType;
import static info.eecc.intellipack.epcis.validate.ValidationUtils.toEpc;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
public class ProduktionEvent1 extends BusinessObjectEvent {

    private static final Predicate<EpcisEvent> PREDICATE = isEventType(EventType.OBJECT)
            .and(EventPredicates.hasAction(ActionType.ADD))
            .and(EventPredicates.hasBizStep(BizStep.COMMISSIONING))
            .and(EventPredicates.hasDisposition(Disposition.ACTIVE))
            .and(EventPredicates.hasReadPoint())
            .and(event -> event.standardEventId().isPresent())
            .and(event -> event.ilmds(bestBeforeDate.class).count() > 0)
            .and(event -> event.ilmds(lotNumber.class).count() > 0)
            .and(event -> event.ilmds(BactCount.class).count() > 0)
            .and(event -> event.ilmds(AufladewertTTI.class).count() > 0)
            .and(event -> event.ilmds(Datenquelle.class).count() > 0)
            .and(event -> event.ilmds(Produktbezeichnung.class).count() > 0)
            .and(event -> event.ilmds(Zieltemperatur.class).count() > 0)
            .and(event -> event.ilmds(P1.class).count() > 0)
            .and(event -> event.ilmds(P2.class).count() > 0)
            .and(event -> event.ilmds(P3.class).count() > 0)
            .and(event -> event.ilmds(P4.class).count() > 0)
            .and(event -> event.ilmds(P5.class).count() > 0)
            .and(event -> event.ilmds(P6.class).count() > 0)
            .and(event -> event.ilmds(P7.class).count() > 0)
            .and(event -> event.ilmds(P8.class).count() > 0)
            .and(event -> event.ilmds(P9.class).count() > 0)
            .and(event -> event.ilmds(P10.class).count() > 0)
            .and(event -> event.ilmds(erwTemperatur.class).count() > 0);


    private static IntellipackBusinessEventFactory<ProduktionEvent1> FACTORY;

    private final Sgln readpoint;

    public ProduktionEvent1(EpcisEvent event) {
        super(PREDICATE, event);

        //readpoint
        this.readpoint = event.readPoint()
                .map(rp -> toEpc(event, "readpoint", rp, Sgln.class))
                .orElse(null);
        if (this.readpoint == null) {
            throw new ValidationException(event, "%s requires a non empty readpoint", ProduktionEvent1.class);
        }
    }

    public static IntellipackBusinessEventFactory<ProduktionEvent1> getFactory() {
        if (FACTORY == null){
            BusinessEventValidator validator = new BusinessEventValidator();

            validator.addRule(new ValidationRule(EventPredicates.isEventType(EventType.OBJECT), "Event type should be OBJECT! "));
            validator.addRule(new ValidationRule(EventPredicates.hasAction(ActionType.ADD), "Event action should be ADD! "));
            validator.addRule(new ValidationRule(EventPredicates.hasBizStep(BizStep.COMMISSIONING), "Event bizStep should be COMMISSIONING! "));
            validator.addRule(new ValidationRule(EventPredicates.hasDisposition(Disposition.ACTIVE), "Event disposition should be ACTIVE! "));
            validator.addRule(new ValidationRule(EventPredicates.hasReadPoint(), "Event readpoint is missing! "));
            validator.addRule(new ValidationRule(event -> event.standardEventId().isPresent(), "Standard event id is missing!  "));
            validator.addRule(new ValidationRule(event -> event.ilmds(Datenquelle.class).count() > 0, "Datenquelle is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(Produktbezeichnung.class).count() > 0, "Produktbezeichnung is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(lotNumber.class).count() > 0, "Produktionscharge is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(bestBeforeDate.class).count() > 0, "MHD is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(BactCount.class).count() > 0, "Bact count is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(AufladewertTTI.class).count() > 0, "AufladewertTTI is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(Zieltemperatur.class).count() > 0, "Zieltemperatur is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(P1.class).count() > 0, "P1 is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(P2.class).count() > 0, "P2 is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(P3.class).count() > 0, "P3 is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(P4.class).count() > 0, "P4 is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(P5.class).count() > 0, "P5 is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(P6.class).count() > 0, "P6 is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(P7.class).count() > 0, "P7 is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(P8.class).count() > 0, "P8 is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(P9.class).count() > 0, "P9 is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(P10.class).count() > 0, "P10 is missing!"));
            validator.addRule(new ValidationRule(event -> event.ilmds(erwTemperatur.class).count() > 0, "Erwartete Temperatur is missing!"));

            FACTORY = new IntellipackBusinessEventFactory<>(validator, ProduktionEvent1::new);
        }

        return FACTORY;
    }

    public Sgln getReadpoint() {
        return readpoint;
    }

}
