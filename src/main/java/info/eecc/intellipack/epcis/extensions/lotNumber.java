package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "produktionscharge", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class lotNumber extends IntellipackSimpleExtensionField {
    public lotNumber(String value){
        super(value);
    }
}
