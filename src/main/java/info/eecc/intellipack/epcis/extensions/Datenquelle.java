package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "datenquelle", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class Datenquelle extends IntellipackSimpleExtensionField {
    public Datenquelle(String value){
        super(value);
    }
}
