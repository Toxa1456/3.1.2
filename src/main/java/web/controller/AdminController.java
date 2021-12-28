package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Role;
import web.model.User;
import web.repositories.RoleRepository;
import web.repositories.UserRepository;
import web.service.RoleService;
import web.service.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userRepository;
    private final RoleService roleRepository;

    @Autowired
    public AdminController(UserService userRepository, RoleService roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String getUsers(@AuthenticationPrincipal User user, ModelMap model) {
        model.addAttribute("user1", user);
        model.addAttribute("Users", userRepository.findAllUsers());
        return "admin";
    }

    @RequestMapping("/adduser")
    public String addUser(@RequestParam Optional<String> role, User user) {
        setRoles(role, user);
        userRepository.save(user);
        return "redirect:/admin/";
    }

    @RequestMapping("/update/{id}")
    public String updateUser(@RequestParam Optional<String> role, User user, @PathVariable("id") long id) {
        User original = userRepository.findById(id);
        setRoles(role, user);
        if (user.getPassword().equals("")) {
            user.setPassword(original.getPassword());
        }
        userRepository.save(user);
        return "redirect:/admin/";
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/";
    }

    private void setRoles(@RequestParam Optional<String> role, User user) {
        if (role.get().equalsIgnoreCase("1,2")) {
            Set<Role> roles = roleRepository.findAllRoles();
            user.setRoles(roles);
        } else {
            if (role.get().equals("1")) {
                user.setRoles(Collections.singleton(roleRepository.findRoleById(2L)));
            } else {
                if (role.get().equals("2")) {
                    user.setRoles(Collections.singleton(roleRepository.findRoleById(1L)));
                }
            }
        }
    }
}
