package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "produktbezeichnung", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class Produktbezeichnung extends IntellipackSimpleExtensionField {
    public Produktbezeichnung(String value){
        super(value);
    }
}
