package um.ar.prog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.ar.prog.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
