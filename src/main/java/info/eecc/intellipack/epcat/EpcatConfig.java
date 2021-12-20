package info.eecc.intellipack.epcat;

import info.eecc.intellipack.epcis.handlers.IntellipackEventsHandlingService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@Configuration
@EnableConfigurationProperties(EpcatProperties.class)
public class EpcatConfig {

    private final EpcatProperties epcatProperties;

    public EpcatConfig(EpcatProperties epcatProperties) {
        this.epcatProperties = epcatProperties;
    }

    @Bean
    public info.eecc.intellipack.epcat.EpcatEventSender eventSender() {
        return new info.eecc.intellipack.epcat.EpcatEventSender(epcatProperties);
    }

    @Bean
    public EpcatQuerySender epcatQuerySender() {
        Assert.hasText(epcatProperties.getUrl(), "EPCAT url may not be empty");
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setDefaultUri(epcatProperties.getUrl() + "/services/EpcisService");

        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setConnectionTimeout((int) (epcatProperties.getConnectionTimeoutInSeconds() * 1000));
        httpComponentsMessageSender.setReadTimeout((int) (epcatProperties.getReadTimeoutInSeconds() * 1000));
        webServiceTemplate.setMessageSender(httpComponentsMessageSender);
        return new EpcatQuerySender(epcatProperties, new IntellipackEventsHandlingService(), webServiceTemplate);
    }

}
