package info.eecc.intellipack.controllers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.eecc.intellipack.controllers.model.GenericApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 21.06.2021
 */

@RestController
@RequestMapping(path = "/api")
public class StatusAbfrageController {

    Logger logger = LoggerFactory.getLogger(StatusAbfrageController.class);

    @Autowired
    private ProductDataService service;

    //Status-Abfrage (APP)
    /**
     *
     * @param response
     * @param digitalLink
     * @throws IOException
     */
    @GetMapping("/status")
    public void exportToCSV(HttpServletResponse response, @RequestParam (value = "digitalLink")String digitalLink) throws IOException {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        if(service.useRegex(digitalLink)) {
            String[] parts = digitalLink.split("de/");
            String sgtin = parts[1];
            logger.info(""+sgtin);

            List<ProductData> listProductData = service.listProductDataBySgtin(sgtin);
            if (listProductData.isEmpty()) {
                //Not found - 404
                GenericApiResponse responseObj = GenericApiResponse.builder().message("No data found for this digitalLink.").path("/access").reason("Not found").status(HttpStatus.NOT_FOUND).timestamp(timestamp.toLocalDateTime()).build();
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                String json = new ObjectMapper().writeValueAsString(responseObj);
                response.getWriter().write(json);
                response.flushBuffer();
            } else {
                // OK - 200
                response.setContentType("text/csv");
                String[] part = digitalLink.split("/");
                String gtin = part[4];
                String ser = part[6];

                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=" + ser + ".csv";
                response.setStatus(200);
                response.setHeader(headerKey, headerValue);

                ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
                String[] csvHeader = {"Datenquelle", "SGTIN", "Produkt- \nbezeichnung", "Produktions- \ncharge", "Verpackungs- \ndatum", "MHD",
                        "Aufladewert TTI", "Ziel- \ntemperatur", "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9", "P10",
                        "Zeitstempel \n" +
                                "(jjjjmmdd_hhmmss)", "Laenge", "Breite", "Rot", "Gruen", "Blau", "erw. \n" +
                        "Temperatur", "Resthalt-\n" +
                        "barkeit (d)"};
                String[] nameMapping = {"datenquelle", "sgtin", "produktbezeichnung", "produktionscharge", "verpackungsdatum", "mhd",
                        "aufladewertTTI", "zieltemperatur", "p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8", "p9", "p10", "zeitstempel",
                        "laenge", "breite", "rot", "grün", "blau", "erwarteteTemperatur", "resthaltbarkeit"};

                csvWriter.writeHeader(csvHeader);

                for (ProductData data : listProductData) {
                    csvWriter.write(data, nameMapping);
                }

                csvWriter.close();
            }
        } else {
            //Bad Request - 400
            GenericApiResponse responseObj = GenericApiResponse.builder().message("DigitalLink format is not equal with https://id.intelli-pack.de/01/04016623487360/21/123456. GTIN length must be 14")
                    .path("/access").reason("Bad request").status(HttpStatus.BAD_REQUEST).timestamp(timestamp.toLocalDateTime()).build();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            String json = new ObjectMapper().writeValueAsString(responseObj);
            response.getWriter().write(json);
            response.flushBuffer();
        }
    }

    /**
     *
     * @param digitalLink
     * @return
     */
    @GetMapping(path = "ui")
    public ResponseEntity<List<ProductData>> exportToUI(@RequestParam (value = "digitalLink")String digitalLink){
        String[] parts = digitalLink.split("de/");
        String sgtin = parts[1];
        logger.info(""+sgtin);
        return ResponseEntity.ok().body(service.listProductDataBySgtin(sgtin));
    }
}
