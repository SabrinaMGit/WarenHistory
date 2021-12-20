package info.eecc.intellipack;

import info.eecc.commons.gs1.Gtin;
import info.eecc.commons.gs1.epc.Sgtin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DigitalLinkConverterTest {

    @Test
    void convertToDigitalLink() {
        String input = "00606949031244/21/12345";
        String digitalLinkPrefix = "https://id.gs1.org/01/";

        String digitalLink = digitalLinkPrefix + input;
        System.out.println("new Gtin(\"606949031244\") = " + new Gtin("606949031244"));
        Sgtin sgtin = (Sgtin) Sgtin.fromDigitalLink(digitalLink);

        Sgtin expectedSgtin = new Sgtin("urn:epc:id:sgtin:0606949.003124.12345");

        Assertions.assertEquals(expectedSgtin, sgtin);
    }
}
