package com.Estiam.Cours.J2E;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    
    private List<User> users = new ArrayList<>();
    
    public HomeController() {
        // Ajout de 3 utilisateurs par défaut
        User user1 = new User();
        user1.setNom("Jean Dupont");
        user1.setEmail("jean.dupont@example.com");
        user1.setTelephone("0612345678");
        user1.setAdresse("123 Rue de Paris, 75001 Paris");
        users.add(user1);
        
        User user2 = new User();
        user2.setNom("Marie Martin");
        user2.setEmail("marie.martin@example.com");
        user2.setTelephone("0623456789");
        user2.setAdresse("456 Avenue des Champs, 69002 Lyon");
        users.add(user2);
        
        User user3 = new User();
        user3.setNom("Pierre Durand");
        user3.setEmail("pierre.durand@example.com");
        user3.setTelephone("0634567890");
        user3.setAdresse("789 Boulevard de la Mer, 13008 Marseille");
        users.add(user3);
    }
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        return "home";
    }
    
    @GetMapping("/user/{id}")
    public String userDetail(@PathVariable int id, Model model) {
        User user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        model.addAttribute("user", user);
        return "user-detail";
    }
    
    @PostMapping("/addUser")
    public String addUser(@RequestParam String nom, 
                         @RequestParam String email,
                         @RequestParam(required = false) String telephone,
                         @RequestParam(required = false) String adresse) {
        User newUser = new User();
        newUser.setNom(nom);
        newUser.setEmail(email);
        if (telephone != null && !telephone.trim().isEmpty()) newUser.setTelephone(telephone);
        if (adresse != null && !adresse.trim().isEmpty()) newUser.setAdresse(adresse);
        users.add(newUser);
        return "redirect:/";
    }
    
    @PostMapping("/deleteUser/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        boolean removed = users.removeIf(user -> user.getId() == id);
        if (removed) {
            return ResponseEntity.ok("Utilisateur supprimé avec succès");
        } else {
            return ResponseEntity.status(404).body("Utilisateur non trouvé");
        }
    }
}
