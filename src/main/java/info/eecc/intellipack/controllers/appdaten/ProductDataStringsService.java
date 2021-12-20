package info.eecc.intellipack.controllers.appdaten;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import java.util.List;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 12.08.2021
 */

@Service
@Transactional
public class ProductDataStringsService {

    private final ProductDataStringsRepository productDataStringsRepository;
    private final XmlConverter xmlConverter;

    public ProductDataStringsService(ProductDataStringsRepository productDataStringsRepository, XmlConverter xmlConverter) {
        this.productDataStringsRepository = productDataStringsRepository;
        this.xmlConverter = xmlConverter;
    }

    public void insertProductData(ProductDataStrings productData) {
        this.productDataStringsRepository.save(productData);
    }

    public List<ProductDataStrings> findAll() {
        return this.productDataStringsRepository.findAll();
    }

    public List<ProductDataStrings> findAllByDigitallink(String digitallink) {
        return this.productDataStringsRepository.findAllByDigitallink(digitallink);
    }

    public String fillXmlDataInEvent1(
            String eventTime, String sgtin, String geoLocation,
            String mhd, String charge, String bactCount, String aufladewert, String datenquelle,
            String pbezeichnung, String zieltemp, String p1, String p2, String p3, String p4,
            String p5, String p6, String p7, String p8, String p9, String p10, String erwTemp ) {
        /*return this.xmlConverter.convertStringToXMLDocument(this.xmlConverter.fillXmlDataInEvent1(eventTime, sgtin, breite, laenge, mhd, charge, bactCount,
                aufladewert, datenquelle, pbezeichnung, zieltemp, p1, p2, p3, p4, p5, p6, p7, p8 ,p9, p10, erwTemp));
         */
        return this.xmlConverter.fillXmlDataInEvent1(eventTime, sgtin, geoLocation, mhd, charge, bactCount,
                aufladewert, datenquelle, pbezeichnung, zieltemp, p1, p2, p3, p4, p5, p6, p7, p8 ,p9, p10, erwTemp);
    }

    public String fillEvents(String eventTime, String digitalLink, String action, String bizStep, String disposition, String geoLocation, String remainingLive, String rot, String gruen, String blau) {
        return this.xmlConverter.fillXmlDataForEvents2until11(eventTime, digitalLink, action, bizStep, disposition, geoLocation, remainingLive, rot, gruen, blau);
    }

}
