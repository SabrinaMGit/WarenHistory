package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "verpackungsdatum", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class Verpackungsdatum extends IntellipackSimpleExtensionField {
    public Verpackungsdatum(String value){
        super(value);
    }
}
