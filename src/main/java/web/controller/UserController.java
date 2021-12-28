package web.controller;



import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;


@Controller
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/")
    public String helloUser (@AuthenticationPrincipal User user, ModelMap model) {
        model.addAttribute("user", user);
        return "/user";
    }
}
