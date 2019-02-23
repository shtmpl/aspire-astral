package aspire.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(YandexApiProperties.class)
public class YandexApiConfiguration {

}
