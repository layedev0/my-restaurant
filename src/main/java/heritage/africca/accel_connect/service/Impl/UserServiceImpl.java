package heritage.africca.accel_connect.service.Impl;

import heritage.africca.accel_connect.entity.User;
import heritage.africca.accel_connect.repository.UserRepository;
import heritage.africca.accel_connect.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Récupère ou crée un utilisateur à partir du JWT Keycloak
     * IMPORTANT: Doit être en écriture pour permettre la création
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User getOrCreateUserFromJwt(Jwt jwt) {
        String keycloakId = jwt.getSubject();

        return userRepository.findByKeycloakId(keycloakId)
                .orElseGet(() -> createUserFromJwt(jwt, keycloakId));
    }

    /**
     * Crée un nouvel utilisateur à partir des claims JWT
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User createUserFromJwt(Jwt jwt, String keycloakId) {
        User user = new User();
        user.setKeycloakId(keycloakId);
        user.setUsername(jwt.getClaim("preferred_username"));
        user.setEmail(jwt.getClaim("email"));
        user.setFirstName(jwt.getClaim("given_name"));
        user.setLastName(jwt.getClaim("family_name"));

        User savedUser = userRepository.save(user);
        log.info("✅ Nouvel utilisateur créé: {} (Keycloak ID: {})", savedUser.getUsername(), keycloakId);

        return savedUser;
    }

    /**
     * Récupère l'utilisateur actuellement authentifié
     * REMOVED readOnly to allow user creation
     */
    @Transactional
    public User getCurrentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        return getOrCreateUserFromJwt(jwt);
    }

    /**
     * Récupère un utilisateur par son ID Keycloak
     */
    @Transactional(readOnly = true)
    public Optional<User> findByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId);
    }

    /**
     * Récupère un utilisateur par son username
     */
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Met à jour les informations d'un utilisateur
     */
    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId)
                .map(user -> {
                    if (updatedUser.getFirstName() != null) {
                        user.setFirstName(updatedUser.getFirstName());
                    }
                    if (updatedUser.getLastName() != null) {
                        user.setLastName(updatedUser.getLastName());
                    }
                    if (updatedUser.getPhoneNumber() != null) {
                        user.setPhoneNumber(updatedUser.getPhoneNumber());
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));
    }

    /**
     * Récupère tous les utilisateurs (pour l'admin)
     */
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}