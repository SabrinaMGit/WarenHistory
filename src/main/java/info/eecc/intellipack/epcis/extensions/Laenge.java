package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "laenge", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class Laenge extends IntellipackSimpleExtensionField{
    public Laenge(String value){
        super(value);
    }
}
