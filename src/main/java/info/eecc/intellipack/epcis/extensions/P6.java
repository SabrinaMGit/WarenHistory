package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "p6", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class P6 extends IntellipackSimpleExtensionField {
    public P6(String value){
        super(value);
    }
}
