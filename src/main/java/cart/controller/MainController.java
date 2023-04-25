package cart.controller;

import cart.dto.ProductResponse;
import cart.service.ProductManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private final ProductManagementService managementService;

    public MainController(final ProductManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.addObject("products", ProductResponse.from(managementService.findAll()));
        modelAndView.setViewName("index");
        return modelAndView;
    }
}