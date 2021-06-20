package com.behsa.smsgw;

import UserAuth.UserService;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class Controller {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return userService.signIn(username, password);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody User user) {
        userService.signup(user);
        return "redirect:/login";
    }

    @GetMapping("/confirm")
    public String confirmMail(@RequestParam("token") String token) {
        userService.userconfirm(token);
        return "/signin";
    }

}