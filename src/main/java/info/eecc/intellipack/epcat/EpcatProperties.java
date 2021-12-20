package info.eecc.intellipack.epcat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "epcat")
public class EpcatProperties {

    private String url;
    private float connectionTimeoutInSeconds;
    private float readTimeoutInSeconds;
    private int maxNumberOfCacheUses;
    private float cacheTimeoutSeconds;
}
