package info.eecc.intellipack.controllers.appdaten;

import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.intellipack.controllers.CaptureController;
import info.eecc.intellipack.epcat.EpcatQuerySender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class AppDatenController {

    Logger logger = LoggerFactory.getLogger(AppDatenController.class);

    @Autowired
    private EpcatQuerySender epcatQuerySender;

    private final ProductDataStringsService service;

    private final CaptureController captureController;

    private final PrepareCaptureController prepareCaptureController;

    public AppDatenController(ProductDataStringsService service, CaptureController captureController, PrepareCaptureController prepareCaptureController) {
        this.service = service;
        this.captureController = captureController;
        this.prepareCaptureController = prepareCaptureController;
    }

    @GetMapping("/access")
    public ResponseEntity<String> getAppDataFromFH(
            @RequestParam(value = "c") String eventType,
            @RequestParam(value = "g") String digitallink,
            @RequestParam(value = "i") String produktbezeichnung,
            @RequestParam(value = "k") String produktionscharge,
            @RequestParam(value = "m") String verpackungsdatum,
            @RequestParam(value = "n") String mhd,
            @RequestParam(value = "o") String aufladewertTTI,
            @RequestParam(value = "p") String zieltemperatur,
            @RequestParam(value = "e") String zeitstempel,
            @RequestParam(value = "q") String laenge,
            @RequestParam(value = "r") String breite,
            @RequestParam(value = "s") String rot,
            @RequestParam(value = "t") String gruen,
            @RequestParam(value = "u") String blau,
            @RequestParam(value = "v") String erwTemp,
            @RequestParam(value = "w") String resthaltbarkeit,
            @RequestParam(value = "x") String p1,
            @RequestParam(value = "y") String p2,
            @RequestParam(value = "z") String p3,
            @RequestParam(value = "b") String p4,
            @RequestParam(value = "ab") String p5,
            @RequestParam(value = "ac") String p6,
            @RequestParam(value = "ad") String p7
    ) throws ParseException {

        ProductDataStrings productData = new ProductDataStrings();
        productData.setDatenquelle(eventType);
        productData.setDigitallink("https://id.intelli-pack.de/01/"+digitallink);
        productData.setProduktbezeichnung(produktbezeichnung);
        productData.setProduktionscharge(produktionscharge);
        productData.setVerpackungsdatum(verpackungsdatum);
        productData.setMhd(mhd);
        productData.setAufladewertTTI(aufladewertTTI);
        productData.setZieltemperatur(zieltemperatur);
        productData.setZeitstempel(zeitstempel);
        productData.setLaenge(laenge);
        productData.setBreite(breite);
        productData.setRot(rot);
        productData.setGrün(gruen);
        productData.setBlau(blau);
        productData.setErwarteteTemperatur(erwTemp);
        productData.setResthaltbarkeit(resthaltbarkeit);

        productData.setP1(p1);
        productData.setP2(p2);
        productData.setP3(p3);
        productData.setP4(p4);
        productData.setP5(p5);
        productData.setP6(p6);
        productData.setP7(p7);

        service.insertProductData(productData);
        prepareCaptureController.startCaptureEpcisDocument(productData, eventType);

        return ResponseEntity.ok().body("Successfully receive data");
    }

    @GetMapping("/getEpcat")
    public ResponseEntity<List<EpcisEvent>> getAllDataFromEpcat() {
        List<EpcisEvent> intellipackEvents;

        intellipackEvents = new ArrayList<>(epcatQuerySender.queryEvents(null, true));
        return ResponseEntity.ok().body(intellipackEvents);
    }

    @GetMapping("/getDb")
    public ResponseEntity<List<ProductDataStrings>> getAllDataFromDb() {
        return ResponseEntity.ok().body(service.findAll());
    }

}
