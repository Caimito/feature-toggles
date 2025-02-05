package net.caimito.features;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "feature-toggles")
public class FeatureToggleConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(FeatureToggleConfig.class);

  private Map<String, FeatureToggle> toggles = new HashMap<>();

  public Map<String, FeatureToggle> getToggles() {
    return toggles;
  }

  public void setToggles(Map<String, FeatureToggle> toggles) {
    this.toggles = toggles;
  }

  public static class FeatureToggle {
    private boolean enabled;
    private Set<String> roles;
    private Integer experimentPercentage;

    public boolean isEnabled() {
      return enabled;
    }

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }

    public Set<String> getRoles() {
      return roles;
    }

    public void setRoles(Set<String> roles) {
      this.roles = roles;
    }

    public Integer getExperimentPercentage() {
      return experimentPercentage;
    }

    public void setExperimentPercentage(Integer experimentPercentage) {
      this.experimentPercentage = experimentPercentage;
    }
  }

  public FeatureToggle getFeatureToggle(String feature) {
    LOGGER.info("Feature: " + feature);
    if (toggles == null) {
      LOGGER.info("No feature toggles defined");
      return new FeatureToggle();
    }
    return toggles.getOrDefault(feature, new FeatureToggle());
  }
}
