package aspire.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HeadHunterProperties.class)
public class HeadHunterConfiguration {

}
