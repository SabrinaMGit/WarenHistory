package info.eecc.intellipack.controllers.appdaten;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Component
public class XmlConverter {

    public String fillXmlDataInEvent1(String eventTime, String sgtin, String geoLocation,
                                    String mhd, String charge, String bactCount, String aufladewert, String datenquelle,
                                    String pbezeichnung, String zieltemp, String p1, String p2, String p3, String p4,
                                    String p5, String p6, String p7, String p8, String p9, String p10, String erwTemp) {

        return "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<epcis:EPCISDocument schemaVersion=\"1.2\" creationDate=\"2021-07-16T12:16:35.034Z\" xmlns:epcis=\"urn:epcglobal:epcis:xsd:1\" xmlns:epcismd=\"urn:epcglobal:epcis-masterdata:xsd:1\" xmlns:ns8=\"https://intelli-pack-ui.eecc.de/\" xmlns:sbdh=\"http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader\" xmlns:epcisq=\"urn:epcglobal:epcis-query:xsd:1\" xmlns:epcglobal=\"urn:epcglobal:xsd:1\" xmlns:eecc=\"http://ns.eecc.info/epcis\">\n" +
                "    <EPCISBody>\n" +
                "        <EventList>\n" +
                "            <ObjectEvent>\n" +
                "               <eventTime>"+eventTime+"</eventTime>\n" +
                "                <eventTimeZoneOffset>+02:00</eventTimeZoneOffset>\n" +
                "                <baseExtension>\n" +
                "                    <eventID>urn:uuid:afa670cc-8618-42c2-bd7b-853e45831532</eventID>\n" +
                "                </baseExtension>\n" +
                "                <epcList>\n" +
                "                    <epc>"+sgtin+"</epc>\n" +
                "                </epcList>\n" +
                "                <action>ADD</action>\n" +
                "                <bizStep>urn:epcglobal:cbv:bizstep:commissioning</bizStep>\n" +
                "                <disposition>urn:epcglobal:cbv:disp:active</disposition>\n" +
                "                <readPoint>\n" +
                "                    <id>"+geoLocation+"</id>\n" +
                "                </readPoint>\n" +
                "                <extension>\n" +
                "                    <ilmd>\n" +
                "                        <ns8:mhd>" + mhd + "</ns8:mhd>\n" +
                "                        <ns8:produktionscharge>"+charge+"</ns8:produktionscharge>\n" +
                "                        <ns8:bactCount>"+bactCount+"</ns8:bactCount>\n" +
                "                        <ns8:aufladewertTTI>"+aufladewert+"</ns8:aufladewertTTI>\n" +
                "                        <ns8:datenquelle>"+datenquelle+"</ns8:datenquelle>\n" +
                "                        <ns8:produktbezeichnung>"+pbezeichnung+"</ns8:produktbezeichnung>\n" +
                "                        <ns8:zieltemperatur>"+zieltemp+"</ns8:zieltemperatur>\n" +
                "                        <ns8:p1>"+p1+"</ns8:p1>\n" +
                "                        <ns8:p2>"+p2+"</ns8:p2>\n" +
                "                        <ns8:p3>"+p3+"</ns8:p3>\n" +
                "                        <ns8:p4>"+p4+"</ns8:p4>\n" +
                "                        <ns8:p5>"+p5+"</ns8:p5>\n" +
                "                        <ns8:p6>"+p6+"</ns8:p6>\n" +
                "                        <ns8:p7>"+p7+"</ns8:p7>\n" +
                "                        <ns8:p8>"+p8+"</ns8:p8>\n" +
                "                        <ns8:p9>"+p9+"</ns8:p9>\n" +
                "                        <ns8:p10>"+p10+"</ns8:p10>\n" +
                "                        <ns8:erwTemperatur>"+erwTemp+"</ns8:erwTemperatur>\n" +
                "                    </ilmd>\n" +
                "                </extension>\n" +
                "            </ObjectEvent>\n" +
                "        </EventList>\n" +
                "    </EPCISBody>\n" +
                "</epcis:EPCISDocument>";
    }

    public String fillXmlDataForEvents2until11(String eventTime, String digitalLink, String action, String bizStep, String disposition,
                                               String geoLocation, String remainingLive, String rot, String gruen, String blau){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<epcis:EPCISDocument schemaVersion=\"1.2\" creationDate=\"2021-07-16T12:16:35.034Z\" xmlns:epcis=\"urn:epcglobal:epcis:xsd:1\" xmlns:epcismd=\"urn:epcglobal:epcis-masterdata:xsd:1\" xmlns:ns8=\"https://intelli-pack-ui.eecc.de/\" xmlns:sbdh=\"http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader\" xmlns:epcisq=\"urn:epcglobal:epcis-query:xsd:1\" xmlns:epcglobal=\"urn:epcglobal:xsd:1\" xmlns:eecc=\"http://ns.eecc.info/epcis\">\n" +
                "    <EPCISBody>\n" +
                "        <EventList>\n" +
                "            <ObjectEvent>\n" +
                "                <eventTime>"+eventTime+"</eventTime>\n" +
                "                <eventTimeZoneOffset>+02:00</eventTimeZoneOffset>\n" +
                "                <baseExtension>\n" +
                "                    <eventID>urn:uuid:bb84a485-b43e-48c5-9aed-8ec3a7ca6653</eventID>\n" +
                "                </baseExtension>\n" +
                "                <epcList>\n" +
                "                    <epc>"+digitalLink+"</epc>\n" +
                "                </epcList>\n" +
                "                <action>"+action+"</action>\n" +
                "                <bizStep>urn:epcglobal:cbv:bizstep:"+bizStep+"</bizStep>\n" +
                "                <disposition>urn:epcglobal:cbv:disp:"+disposition+"</disposition>\n" +
                "                <readPoint>\n" +
                "                    <id>"+geoLocation+"</id>\n" +
                "                </readPoint>\n" +
                "                <ns8:remainingLive>"+remainingLive+"</ns8:remainingLive>\n" +
                "                <ns8:rot>"+rot+"</ns8:rot>\n" +
                "                <ns8:gruen>"+gruen+"</ns8:gruen>\n" +
                "                <ns8:blau>"+blau+"</ns8:blau>\n" +
                "            </ObjectEvent>\n" +
                "        </EventList>\n" +
                "    </EPCISBody>\n" +
                "</epcis:EPCISDocument>";
    }

    public Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            return builder.parse(new InputSource(new StringReader(xmlString)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
