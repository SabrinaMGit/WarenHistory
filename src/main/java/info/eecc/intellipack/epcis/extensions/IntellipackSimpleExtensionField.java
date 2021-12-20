package info.eecc.intellipack.epcis.extensions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import info.eecc.commons.epcis.extensions.SimpleExtensionField;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"localName", "namespaceUri"})
@NoArgsConstructor
@Setter
public class IntellipackSimpleExtensionField extends SimpleExtensionField<String> {
    private String value;

    @XmlValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
