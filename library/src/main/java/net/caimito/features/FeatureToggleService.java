package net.caimito.features;

import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class FeatureToggleService {

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
      return false;
    }

    // Role-based access
    if (auth != null && toggle.getRoles() != null && !toggle.getRoles().isEmpty()) {
      Set<String> userRoles = auth.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .collect(java.util.stream.Collectors.toSet());
      return !userRoles.isEmpty() && toggle.getRoles().stream().anyMatch(userRoles::contains);
    }

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
