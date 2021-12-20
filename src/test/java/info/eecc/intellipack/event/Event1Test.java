package info.eecc.intellipack.event;

import epcglobal.epcis.ActionType;
import epcglobal.epcis.BusinessTransactionType;
import info.eecc.commons.epcis.cbv.BizStep;
import info.eecc.commons.epcis.cbv.Disposition;
import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.commons.epcis.core.ObjectEvent;
import info.eecc.commons.epcis.core.TransactionEvent;
import info.eecc.commons.epcis.parser.EpcisModelParser;
import info.eecc.commons.epcis.validation.ValidationException;
import info.eecc.commons.epcis.writer.EpcisModelXmlWriter;
import info.eecc.intellipack.epcis.events.ProduktionEvent1;
import info.eecc.intellipack.epcis.extensions.*;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Event1Test {
    String eventId = "urn:uuid:" + UUID.randomUUID();
    String epc = "urn:epc:id:sgtin:0606949.031242.31";
    String readPoint = "urn:epc:id:sgln:0614141.07346.1234";

    @Test
    public void shouldPassWithDispositionSellableNotAccessible() {
        ObjectEvent event = ObjectEvent.builder().action(ActionType.ADD)
                .addEpc(epc)
                .bizStep(BizStep.COMMISSIONING)
                .disposition(Disposition.ACTIVE)
                .readpoint(readPoint)
                .standardEventId(eventId)
                    //25 Ilmds
                .addIlmd(new bestBeforeDate("26.03.21"))
                .addIlmd(new lotNumber("4711"))
                .addIlmd(new BactCount("3425"))
                .addIlmd(new AufladewertTTI("58"))
                .addIlmd(new Datenquelle("Wolf Qualitätskontrolle"))
                .addIlmd(new Produktbezeichnung("Original Thueringer Rostbratwurst"))
                .addIlmd(new Zieltemperatur("7"))
                .addIlmd(new P1("0"))
                .addIlmd(new P2("0"))
                .addIlmd(new P3("0"))
                .addIlmd(new P4("0"))
                .addIlmd(new P5("0"))
                .addIlmd(new P6("0"))
                .addIlmd(new P7("0"))
                .addIlmd(new P8("0"))
                .addIlmd(new P9("0"))
                .addIlmd(new P10("0"))
                .addIlmd(new erwTemperatur("10"))
                .build();

        EpcisModelParser parser = EpcisModelParser.withExtensions(new IntellipackExtension());
        EpcisModelXmlWriter writer = EpcisModelXmlWriter.withExtensions(new IntellipackExtension());

        String xml = writer.writeToString(event);
        System.out.println(xml);
        EpcisEvent parsedEvent = EpcisEvent.parse(parser, xml);
        System.out.println(ProduktionEvent1.getFactory().matchWithErrorMessage(event));

        // create BusinessEvent
        ProduktionEvent1 encodingEvent = ProduktionEvent1.getFactory().tryConvert(event).orElseThrow(() -> new AssertionError("EncodingEvent not created although previously matched"));
        assertThat(encodingEvent.action()).contains(ActionType.ADD);
        assertThat(encodingEvent.bizStep()).contains(BizStep.COMMISSIONING.toUri());
        assertThat(encodingEvent.disposition()).contains(Disposition.ACTIVE.toUri());
        assertThat(encodingEvent.getReadpoint().toUri()).isEqualTo(readPoint);
        assertThat(encodingEvent.epcs().findFirst()).contains(epc);
        assertThat(encodingEvent.standardEventId()).contains(eventId);
    }

    @Test(expected = AssertionError.class)
    public void shouldFailWithInvalidDisopsition(){
        ObjectEvent event = ObjectEvent.builder().action(ActionType.ADD)
                .addEpc(epc)
                .bizStep(BizStep.COMMISSIONING)
                .disposition("fdgdf")
                .readpoint(readPoint)
                .standardEventId(eventId)
                //25 Ilmds
                .addIlmd(new bestBeforeDate("26.03.21"))
                .addIlmd(new lotNumber("4711"))
                .addIlmd(new BactCount("3425"))
                .addIlmd(new AufladewertTTI("58"))
                .addIlmd(new Datenquelle("Wolf Qualitätskontrolle"))
                .addIlmd(new Produktbezeichnung("Original Thueringer Rostbratwurst"))
                .addIlmd(new Zieltemperatur("7"))
                .addIlmd(new P1("0"))
                .addIlmd(new P2("0"))
                .addIlmd(new P3("0"))
                .addIlmd(new P4("0"))
                .addIlmd(new P5("0"))
                .addIlmd(new P6("0"))
                .addIlmd(new P7("0"))
                .addIlmd(new P8("0"))
                .addIlmd(new P9("0"))
                .addIlmd(new P10("0"))
                .addIlmd(new erwTemperatur("10"))
                .build();

        // create BusinessEvent
        ProduktionEvent1.getFactory().tryConvert(event).orElseThrow(() -> new AssertionError("EncodingEvent not created although previously matched"));
    }

    @Test(expected = AssertionError.class)
    public void shouldFailWithoutReadPoint(){
        ObjectEvent event = ObjectEvent.builder().action(ActionType.ADD)
                .addEpc(epc)
                .bizStep(BizStep.COMMISSIONING)
                .disposition(Disposition.ACTIVE)
                .standardEventId(eventId)
                //25 Ilmds
                .addIlmd(new bestBeforeDate("26.03.21"))
                .addIlmd(new lotNumber("4711"))
                .addIlmd(new BactCount("3425"))
                .addIlmd(new AufladewertTTI("58"))
                .addIlmd(new Datenquelle("Wolf Qualitätskontrolle"))
                .addIlmd(new Produktbezeichnung("Original Thueringer Rostbratwurst"))
                .addIlmd(new Zieltemperatur("7"))
                .addIlmd(new P1("0"))
                .addIlmd(new P2("0"))
                .addIlmd(new P3("0"))
                .addIlmd(new P4("0"))
                .addIlmd(new P5("0"))
                .addIlmd(new P6("0"))
                .addIlmd(new P7("0"))
                .addIlmd(new P8("0"))
                .addIlmd(new P9("0"))
                .addIlmd(new P10("0"))
                .addIlmd(new erwTemperatur("10"))
                .build();

        // create BusinessEvent
        ProduktionEvent1.getFactory().tryConvert(event).orElseThrow(() -> new AssertionError("EncodingEvent not created although previously matched"));
    }

    @Test(expected = ValidationException.class)
    public void shouldFailWithInvalidReadPoint(){
        ObjectEvent event = ObjectEvent.builder().action(ActionType.ADD)
                .addEpc(epc)
                .bizStep(BizStep.COMMISSIONING)
                .disposition(Disposition.ACTIVE)
                .readpoint("sfhgfjdfsh")
                .standardEventId(eventId)
                //25 Ilmds
                .addIlmd(new bestBeforeDate("26.03.21"))
                .addIlmd(new lotNumber("4711"))
                .addIlmd(new BactCount("3425"))
                .addIlmd(new AufladewertTTI("58"))
                .addIlmd(new Datenquelle("Wolf Qualitätskontrolle"))
                .addIlmd(new Produktbezeichnung("Original Thueringer Rostbratwurst"))
                .addIlmd(new Zieltemperatur("7"))
                .addIlmd(new P1("0"))
                .addIlmd(new P2("0"))
                .addIlmd(new P3("0"))
                .addIlmd(new P4("0"))
                .addIlmd(new P5("0"))
                .addIlmd(new P6("0"))
                .addIlmd(new P7("0"))
                .addIlmd(new P8("0"))
                .addIlmd(new P9("0"))
                .addIlmd(new P10("0"))
                .addIlmd(new erwTemperatur("10"))
                .build();

        // create BusinessEvent
        ProduktionEvent1.getFactory().tryConvert(event).orElseThrow(() -> new AssertionError("EncodingEvent not created although previously matched"));
    }

    @Test(expected = AssertionError.class)
    public void shouldFailWithoutBizStep(){
        ObjectEvent event = ObjectEvent.builder().action(ActionType.ADD)
                .addEpc(epc)
                .disposition(Disposition.ACTIVE)
                .readpoint(readPoint)
                .standardEventId(eventId)
                //25 Ilmds
                .addIlmd(new bestBeforeDate("26.03.21"))
                .addIlmd(new lotNumber("4711"))
                .addIlmd(new BactCount("3425"))
                .addIlmd(new AufladewertTTI("58"))
                .addIlmd(new Datenquelle("Wolf Qualitätskontrolle"))
                .addIlmd(new Produktbezeichnung("Original Thueringer Rostbratwurst"))
                .addIlmd(new Zieltemperatur("7"))
                .addIlmd(new P1("0"))
                .addIlmd(new P2("0"))
                .addIlmd(new P3("0"))
                .addIlmd(new P4("0"))
                .addIlmd(new P5("0"))
                .addIlmd(new P6("0"))
                .addIlmd(new P7("0"))
                .addIlmd(new P8("0"))
                .addIlmd(new P9("0"))
                .addIlmd(new P10("0"))
                .addIlmd(new erwTemperatur("10"))
                .build();

        // create BusinessEvent
        ProduktionEvent1.getFactory().tryConvert(event).orElseThrow(() -> new AssertionError("EncodingEvent not created although previously matched"));
    }

    @Test(expected = AssertionError.class)
    public void shouldFailWithWrongBizStep(){
        ObjectEvent event = ObjectEvent.builder().action(ActionType.ADD)
                .addEpc(epc)
                .bizStep(BizStep.INSPECTING)
                .disposition(Disposition.ACTIVE)
                .readpoint(readPoint)
                .standardEventId(eventId)
                //25 Ilmds
                .addIlmd(new bestBeforeDate("26.03.21"))
                .addIlmd(new lotNumber("4711"))
                .addIlmd(new BactCount("3425"))
                .addIlmd(new AufladewertTTI("58"))
                .addIlmd(new Datenquelle("Wolf Qualitätskontrolle"))
                .addIlmd(new Produktbezeichnung("Original Thueringer Rostbratwurst"))
                .addIlmd(new Zieltemperatur("7"))
                .addIlmd(new P1("0"))
                .addIlmd(new P2("0"))
                .addIlmd(new P3("0"))
                .addIlmd(new P4("0"))
                .addIlmd(new P5("0"))
                .addIlmd(new P6("0"))
                .addIlmd(new P7("0"))
                .addIlmd(new P8("0"))
                .addIlmd(new P9("0"))
                .addIlmd(new P10("0"))
                .addIlmd(new erwTemperatur("10"))
                .build();

        // create BusinessEvent
        ProduktionEvent1.getFactory().tryConvert(event).orElseThrow(() -> new AssertionError("EncodingEvent not created although previously matched"));
    }

    @Test(expected = AssertionError.class)
    public void shouldFailWithWrongAction(){
        ObjectEvent event = ObjectEvent.builder().action(ActionType.OBSERVE)
                .addEpc(epc)
                .bizStep(BizStep.COMMISSIONING)
                .disposition(Disposition.ACTIVE)
                .readpoint(readPoint)
                .standardEventId(eventId)
                .build();

        // create BusinessEvent
        ProduktionEvent1.getFactory().tryConvert(event).orElseThrow(() -> new AssertionError("EncodingEvent not created although previously matched"));
    }

    @Test(expected = AssertionError.class)
    public void shouldFailWithWrongEventType(){
        TransactionEvent event = TransactionEvent.builder().action(ActionType.OBSERVE)
                .bizStep(BizStep.CYCLE_COUNTING)
                .disposition(Disposition.ACTIVE)
                .addEpc(epc)
                .readpoint(readPoint)
                .standardEventId(eventId)
                .addBusinessTransaction(new BusinessTransactionType()) //Mandatory for creating TransactionEvent
                .build();

        // create BusinessEvent
        ProduktionEvent1.getFactory().tryConvert(event).orElseThrow(() -> new AssertionError("EncodingEvent not created although previously matched"));
    }

    @Test(expected = AssertionError.class)
    public void should_fail_without_eventId(){
        ObjectEvent event = ObjectEvent.builder().action(ActionType.ADD)
                .addEpc(epc)
                .bizStep(BizStep.ENCODING)
                .disposition(Disposition.ENCODED)
                .readpoint(readPoint)
                .build();

        // create BusinessEvent
        ProduktionEvent1.getFactory().tryConvert(event).orElseThrow(() -> new AssertionError("EncodingEvent not created although previously matched"));
    }
}
