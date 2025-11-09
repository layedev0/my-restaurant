package heritage.africca.accel_connect.controller;

import heritage.africca.accel_connect.entity.User;
import heritage.africca.accel_connect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Récupère les informations de l'utilisateur connecté
     */
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(user);
    }

    /**
     * Met à jour le profil de l'utilisateur connecté
     */
    @PutMapping("/me")
    public ResponseEntity<User> updateCurrentUser(
            Authentication authentication,
            @RequestBody User updatedUser) {

        User currentUser = userService.getCurrentUser(authentication);
        User updated = userService.updateUser(currentUser.getId(), updatedUser);
        return ResponseEntity.ok(updated);
    }

    /**
     * Récupère un utilisateur par son username
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}