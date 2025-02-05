package net.caimito.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.caimito.features.FeatureToggleService;

@Controller
@RequestMapping("/secret")
public class SecretController {

  @Autowired
  private FeatureToggleService featureToggleService;

  @GetMapping
  public String secret(Model model, Authentication auth) {
    model.addAttribute("username", auth.getName());
    model.addAttribute("roles", auth.getAuthorities());

    model.addAttribute("foo", featureToggleService.isFeatureEnabled("secret-foo", auth));
    return "secret";
  }

}
