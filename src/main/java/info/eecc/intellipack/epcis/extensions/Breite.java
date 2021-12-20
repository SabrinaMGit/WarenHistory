package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "breite", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class Breite extends IntellipackSimpleExtensionField{
    public Breite(String value) {
        super(value);
    }
}
