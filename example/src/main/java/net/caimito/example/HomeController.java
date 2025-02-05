package net.caimito.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.caimito.features.FeatureToggleService;

@Controller
@RequestMapping("/")
public class HomeController {

  @Autowired
  private FeatureToggleService featureToggleService;

  @GetMapping
  public String home(Model model) {
    model.addAttribute("foo", featureToggleService.isFeatureEnabled("foo"));

    return "index";
  }

}
