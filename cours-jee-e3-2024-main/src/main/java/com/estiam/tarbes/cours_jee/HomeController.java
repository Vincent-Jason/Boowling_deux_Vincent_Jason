package com.estiam.tarbes.cours_jee;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;


@Controller
@SessionScope
@Transactional
public class HomeController {
    
    // private List<User> users = new ArrayList<>();

    @Autowired
    private UserService userService;

    @Autowired
    private TaxCalculator taxCalculator;

    @GetMapping("/")
    public String home(Model model) {
        
        Page<User> users = userService.findAll(PageRequest.of(0, 10,Sort.by("id")));
        model.addAttribute("tax",taxCalculator.computeTax(0));
        model.addAttribute("users", users);
        model.addAttribute("newUser", new User());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        // System.out.println("Getting user with id: " + id);
        // System.out.println("users: " + users);
        // for(User user : users) {
        //     System.out.println("User: " + user);
        //     System.out.println("egal? " + ((long)user.getId() == (long)id));
        //     if(user.getId().equals(id)) {
        //         System.out.println("Found user: " + user);
        //         model.addAttribute("user", user);
        //         return "user";
        //     }
        // }
        Optional<User> user = userService.findById(id);
        if(user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user";
        }
        return "redirect:/";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute User newUser) {
        userService.createUser(newUser);
        return "redirect:/";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam Long id) {
        System.out.println("Deleting user with id: " + id);
        userService.deleteUser(id);
        return "redirect:/";
    }
}
