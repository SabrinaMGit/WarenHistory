package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "bactCount", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class BactCount extends IntellipackSimpleExtensionField {
    public BactCount(String value) {
        super(value);
    }
}
