package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.imp.RoleService;
import ru.kata.spring.boot_security.demo.service.imp.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUserAll(Model model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles",roleService.findRoles());
        model.addAttribute("user",userService.findByUserName(principal.getName()));
        model.addAttribute("new", new User());
        return "admin/admin";
    }

    @PostMapping("/saveUser")
    public ModelAndView saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return new ModelAndView("redirect:/admin");
    }


    @PatchMapping("/updateUser/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
