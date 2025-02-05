package net.caimito.features;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(FeatureToggleConfig.class)
public class FeatureToggleAutoConfiguration {
  private final static Logger LOGGER = LoggerFactory.getLogger(FeatureToggleAutoConfiguration.class);

  @Bean
  @ConditionalOnMissingBean
  public FeatureToggleService featureToggleService(FeatureToggleConfig featureToggleConfig) {
    LOGGER.info("Creating FeatureToggleService with config: {}", featureToggleConfig);
    return new FeatureToggleService(featureToggleConfig);
  }
}