package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "p5", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class P5 extends IntellipackSimpleExtensionField {
    public P5(String value){
        super(value);
    }
}
