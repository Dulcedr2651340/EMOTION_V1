package com.music.Emotion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class StaticController {
    @GetMapping("/")
    public String index() {
        return "index"; // Spring buscará 'index.html' en la carpeta 'static'
    }

}