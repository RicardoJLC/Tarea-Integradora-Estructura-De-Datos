package mx.edu.utez.bibliotecadigital.controller;
import mx.edu.utez.bibliotecadigital.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private List<User> users = new ArrayList<>();
    private int nextId = 1;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        user.setId(nextId++);
        users.add(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }
}
