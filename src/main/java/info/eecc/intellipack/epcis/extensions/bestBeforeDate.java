package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "mhd", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class bestBeforeDate extends IntellipackSimpleExtensionField{
    public bestBeforeDate(String value) {
        super(value);
    }
}