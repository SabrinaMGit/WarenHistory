package info.eecc.intellipack.event;

import epcglobal.epcis.ActionType;
import info.eecc.commons.epcis.cbv.BizStep;
import info.eecc.commons.epcis.cbv.Disposition;
import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.commons.epcis.core.ObjectEvent;
import info.eecc.commons.epcis.parser.EpcisModelParser;
import info.eecc.commons.epcis.writer.EpcisModelXmlWriter;
import info.eecc.intellipack.epcis.events.ProduktionEvent1;
import info.eecc.intellipack.epcis.events.WarenEinlagernEvent2;
import info.eecc.intellipack.epcis.events.WarenausgangEvent3;
import info.eecc.intellipack.epcis.events.WareneingangEvent4;
import info.eecc.intellipack.epcis.extensions.*;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Event2Test {
    String eventId = "urn:uuid:" + UUID.randomUUID();
    String bizLocation = "urn:epc:id:sgln:0614141.07346.645634";
    String epc = "urn:epc:id:sgtin:0606949.031242.31";
    String readPoint = "urn:epc:id:sgln:0614141.07346.1234";

    @Test
    public void shouldPassWithDispositionSellableNotAccessible() {
        ObjectEvent event = ObjectEvent.builder().action(ActionType.OBSERVE)
                .addEpc(epc)
                .bizStep(BizStep.STORING)
                .disposition(Disposition.IN_PROGRESS)
                .readpoint(readPoint)
                .bizLocation(bizLocation)
                .standardEventId(eventId)
                .addVendorExtension(new RemainingLive("P0Y0M10DT2H30M"))
                .addVendorExtension(new Rot("25"))
                .addVendorExtension(new Gruen("45"))
                .addVendorExtension(new Blau("15"))
                .build();

        EpcisModelParser parser = EpcisModelParser.withExtensions(new IntellipackExtension());
        EpcisModelXmlWriter writer = EpcisModelXmlWriter.withExtensions(new IntellipackExtension());

        String xml = writer.writeToString(event);
        System.out.println(xml);
        EpcisEvent parsedEvent = EpcisEvent.parse(parser, xml);
        System.out.println(WarenEinlagernEvent2.getFactory().matchWithErrorMessage(event));
    }

    @Test
    public void event3() {
        ObjectEvent event = ObjectEvent.builder().action(ActionType.OBSERVE)
                .addEpc(epc)
                .bizStep(BizStep.SHIPPING)
                .disposition(Disposition.IN_TRANSIT)
                .readpoint(readPoint)
                .bizLocation(bizLocation)
                .standardEventId(eventId)
                .addVendorExtension(new RemainingLive("P0Y0M10DT2H30M"))
                .addVendorExtension(new Rot("25"))
                .addVendorExtension(new Gruen("45"))
                .addVendorExtension(new Blau("15"))
                .build();

        EpcisModelParser parser = EpcisModelParser.withExtensions(new IntellipackExtension());
        EpcisModelXmlWriter writer = EpcisModelXmlWriter.withExtensions(new IntellipackExtension());

        String xml = writer.writeToString(event);
        System.out.println(xml);
        EpcisEvent parsedEvent = EpcisEvent.parse(parser, xml);
        System.out.println(WarenausgangEvent3.getFactory().matchWithErrorMessage(event));
    }

    @Test
    public void event4() {
        ObjectEvent event = ObjectEvent.builder().action(ActionType.OBSERVE)
                .addEpc(epc)
                .bizStep(BizStep.RECEIVING)
                .disposition(Disposition.IN_PROGRESS)
                .readpoint(readPoint)
                .bizLocation(bizLocation)
                .standardEventId(eventId)
                .addVendorExtension(new RemainingLive("P0Y0M07DT1H20M"))
                .addVendorExtension(new Rot("11"))
                .addVendorExtension(new Gruen("22"))
                .addVendorExtension(new Blau("33"))
                .build();

        EpcisModelParser parser = EpcisModelParser.withExtensions(new IntellipackExtension());
        EpcisModelXmlWriter writer = EpcisModelXmlWriter.withExtensions(new IntellipackExtension());

        String xml = writer.writeToString(event);
        System.out.println(xml);
        EpcisEvent parsedEvent = EpcisEvent.parse(parser, xml);
        System.out.println(WareneingangEvent4.getFactory().matchWithErrorMessage(event));
    }

}
