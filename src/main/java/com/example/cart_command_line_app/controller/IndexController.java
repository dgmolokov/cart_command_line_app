package com.example.cart_command_line_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  @GetMapping("/")
  public String showIndexPage(Model model) {
    return "index";
  }
}
