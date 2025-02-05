package net.caimito.features;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class FeatureToggleService {
  private static final Logger LOGGER = LoggerFactory.getLogger(FeatureToggleService.class);

  private final FeatureToggleConfig featureToggleConfig;

  public FeatureToggleService(FeatureToggleConfig featureToggleConfig) {
    this.featureToggleConfig = featureToggleConfig;
  }

  public boolean isFeatureEnabled(String feature) {
    return isFeatureEnabled(feature, null);
  }

  public boolean isFeatureEnabled(String feature, Authentication auth) {
    FeatureToggleConfig.FeatureToggle toggle = featureToggleConfig.getFeatureToggle(feature);
    if (!toggle.isEnabled()) {
      LOGGER.debug("Feature {} is disabled", feature);
      return false;
    }

    // Role-based access
    if (auth != null && toggle.getRoles() != null && !toggle.getRoles().isEmpty()) {
      Set<String> userRoles = auth.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .collect(java.util.stream.Collectors.toSet());
      boolean enabled = !userRoles.isEmpty() && toggle.getRoles().stream().anyMatch(userRoles::contains);
      LOGGER.debug("Feature {} is {} for user roles {}", feature, enabled ? "enabled" : "disabled", userRoles);
      return enabled;
    }

    LOGGER.debug("Feature {} is enabled", feature);
    return true;
  }

  public boolean isFeatureEnabledForUser(String feature, String userId) {
    FeatureToggleConfig.FeatureToggle toggle = featureToggleConfig.getFeatureToggle(feature);

    if (!toggle.isEnabled()) {
      return false;
    }

    // A/B testing
    if (toggle.getExperimentPercentage() != null) {
      int hash = Math.abs(userId.hashCode() % 100);
      return hash < toggle.getExperimentPercentage();
    }

    return true;
  }
}
