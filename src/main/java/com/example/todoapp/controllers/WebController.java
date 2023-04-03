package com.example.todoapp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = {"/", "/home"})
    public String homepage() {
        return "home"; // Trả về home.html
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello"; // Trả về hello.html
    }

}