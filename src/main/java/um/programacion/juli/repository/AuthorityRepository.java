package um.programacion.juli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.programacion.juli.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
