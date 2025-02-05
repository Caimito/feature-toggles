package net.caimito.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.caimito.features.FeatureToggleService;

@RestController
@RequestMapping("/api/features")
public class FeatureController {

  @Autowired
  private FeatureToggleService featureToggleService;

  @GetMapping("/{feature}")
  public boolean isFeatureEnabled(@PathVariable String feature, Authentication auth) {
    return featureToggleService.isFeatureEnabled(feature, auth);
  }
}
