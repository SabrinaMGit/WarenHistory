package info.eecc.intellipack.controllers.appdaten;

import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.intellipack.controllers.CaptureController;
import info.eecc.intellipack.epcat.EpcatQuerySender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Component
public class PrepareCaptureController {

    Logger logger = LoggerFactory.getLogger(PrepareCaptureController.class);

    private final ProductDataStringsService service;

    private final CaptureController captureController;

    @Autowired
    private EpcatQuerySender epcatQuerySender;

    public PrepareCaptureController(ProductDataStringsService service, CaptureController captureController) {
        this.service = service;
        this.captureController = captureController;
    }

    public void startCaptureEpcisDocument(ProductDataStrings productData, String eventType) throws ParseException {
        List<EpcisEvent> intellipackEvents = new ArrayList<>(epcatQuerySender.queryEvents(productData.getDigitallink(), false));
        if(eventType == null || eventType.equals("")){
            String getEvenType = checkWhichEventType(productData, intellipackEvents);
            assert getEvenType != null;
            EventTypeModel ev = setEventType(getEvenType);
            System.out.println("ev = " + ev);
            productData.setDatenquelle(getEvenType);
            if(whichEventAndAddEventCapture(ev, productData, getEvenType, intellipackEvents)){
                captureController.addEventWithoutMapping(fillEvent1Until11(productData, Objects.requireNonNull(ev)));
                //service.insertProductData(productData);
            }

        } else {
            EventTypeModel ev = setEventType(Objects.requireNonNull(productData).getDatenquelle());
            System.out.println("ev = " + ev);
            if(whichEventAndAddEventCapture(Objects.requireNonNull(ev), productData, eventType, intellipackEvents)) {
                captureController.addEventWithoutMapping(fillEvent1Until11(productData, ev));
                //service.insertProductData(productData);
            }
        }
    }

    private boolean whichEventAndAddEventCapture(EventTypeModel ev, ProductDataStrings productData, String eventType, List<EpcisEvent> epcatData) throws ParseException {
        System.out.println("epcatData.size() = " + epcatData.size() + " ; EventType = " + eventType);
        EventNames eventNames = new EventNames();
        if(eventType.equals(eventNames.getEvent1())) {
            if(epcatData.size() == 0 ) {
                if(ev.getAction().equals("ADD") && ev.getBizStep().equals("commissioning") && ev.getDisposition().equals("active")) {
                    captureController.addEventWithoutMapping(fillEvent1Xml(productData, eventType));
                    //service.insertProductData(productData);
                    System.out.println("\"add Event1\" = " + "add Event1");
                }
            }
            return false;
        }
        return true;
    }

    private String checkWhichEventType(ProductDataStrings productData, List<EpcisEvent> epcatData){
        EventNames eventNames = new EventNames();
        int size = epcatData.size();
        if(size == 0) {
            return eventNames.getEvent1();

        } else if(size == 1) {
            return eventNames.getEvent2();

        } else if(size == 2) {
            return eventNames.getEvent3();

        } else if(size == 3) {
            return eventNames.getEvent4();

        } else if(size == 4) {
            return eventNames.getEvent5();

        } else if(size == 5) {
            return eventNames.getEvent6();

        } else if(size == 6) {
            return eventNames.getEvent7();

        } else if(size == 7) {
            return eventNames.getEvent8();

        } else if(size == 8) {
            return eventNames.getEvent9();

        } else if(size == 9) {
            return eventNames.getEvent10();

        } else if(size == 10) {
            return eventNames.getEvent11();

        } else {
            return null;
        }
    }

    private String convertZeitstempelToJodaTime(String zeitstempel) throws ParseException {
        String exp = "2021-08-05T10:07:44.167387Z";

        //Date date2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(exp);
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
        // Joda Time
        //System.out.println(DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss.SSS").print(new org.joda.time.DateTime())
    }

    private String fillEvent1Xml(ProductDataStrings productData, String eventType) throws ParseException {
        return service.fillXmlDataInEvent1(convertZeitstempelToJodaTime(productData.getZeitstempel())+"Z", productData.getDigitallink(),
                convertGeoLocation(productData.getBreite(), productData.getLaenge()), productData.getMhd(),
                productData.getProduktionscharge(), "0", productData.getAufladewertTTI(), eventType, productData.getProduktbezeichnung(),
                productData.getZieltemperatur(), productData.getP1(), productData.getP2(), productData.getP3(), productData.getP4(), productData.getP5(), productData.getP6(),
                productData.getP7(),productData.getP8(), productData.getP9(), productData.getP10(), productData.getErwarteteTemperatur());
    }

    private String fillEvent1Until11(ProductDataStrings productData, EventTypeModel eventTypeModel) throws ParseException {
        System.out.println("productData.getLaenge() = " + productData.getLaenge());
        return service.fillEvents(convertZeitstempelToJodaTime(productData.getZeitstempel())+"Z", productData.getDigitallink(),
                eventTypeModel.getAction(), eventTypeModel.getBizStep(), eventTypeModel.getDisposition(), convertGeoLocation(productData.getBreite(), productData.getLaenge()),
                productData.getResthaltbarkeit(), productData.getRot(), productData.getGrün(), productData.getBlau());
    }

    private String convertGeoLocation(String breite, String laenge) {
        return "geo:".concat(breite).concat(",").concat(laenge);
    }

    private EventTypeModel setEventType(String eventType){
        switch(eventType) {
            case "Produktion":
                return EventTypeModel.builder().action("ADD").bizStep("commissioning").disposition("active").build();
            case "Logistik: Einlagern":
                return EventTypeModel.builder().action("OBSERVE").bizStep("storing").disposition("in_progress").build();
            case "Logistik: Warenausgang":
                return EventTypeModel.builder().action("OBSERVE").bizStep("shipping").disposition("in_transit").build();
            case "Logistik: Wareneingang ":
                return EventTypeModel.builder().action("OBSERVE").bizStep("receiving").disposition("in_progress").build();
            case "Handel: Einlagern im Backroom":
                return EventTypeModel.builder().action("OBSERVE").bizStep("storing").disposition("sellable_accessible").build();
            case "Handel: In den Verkauf bringen":
                return EventTypeModel.builder().action("OBSERVE").bizStep("storing").disposition("sellable_not_accessible").build();
            case "Handel: Ware ist abgelaufen ":
                return EventTypeModel.builder().action("OBSERVE").bizStep("storing").disposition("non_sellable_expired").build();
            case "Handel: Prüfung der Restlaufzeit":
                return EventTypeModel.builder().action("OBSERVE").bizStep("inspecting").disposition("in_progress").build();
            case "Handel: Verkauf an der Kasse":
                return EventTypeModel.builder().action("ADD").bizStep("retail_selling").disposition("retail_sold").build();
            case "Konsument: Restlaufzeit bestimmen":
                return EventTypeModel.builder().action("OBSERVE").bizStep("inspecting").disposition("expired").build();
            case "Konsument:  Ware verbrauchen":
                return EventTypeModel.builder().action("DELETE").bizStep("decommissioning").disposition("inactive").build();
            default: return null;
        }
    }
}
