package ma.fsm.tp03_partie03_hammam_salaheddine.security.repository;

import ma.fsm.tp03_partie03_hammam_salaheddine.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
