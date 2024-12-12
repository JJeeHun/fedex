package com.example.fedex_json.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam("id") String id,
                        @RequestParam("password") String password) {
        if(!"safecnc".equals(id) || !"safe79001!".equals(password)) return "redirect:/login";
        request.getSession().setAttribute("loginOk", true);
        return "redirect:/";
    }
}
