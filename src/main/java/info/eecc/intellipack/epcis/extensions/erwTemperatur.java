package info.eecc.intellipack.epcis.extensions;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@XmlRootElement(name = "erwTemperatur", namespace = IntellipackNamespace.URI)
@NoArgsConstructor
public class erwTemperatur extends IntellipackSimpleExtensionField{
    public erwTemperatur(String value){
        super(value);
    }
}
