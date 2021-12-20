package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "p8", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class P8 extends IntellipackSimpleExtensionField {
    public P8(String value){
        super(value);
    }
}
