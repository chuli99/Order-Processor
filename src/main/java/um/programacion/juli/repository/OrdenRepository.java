package um.programacion.juli.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import um.programacion.juli.domain.Orden;

/**
 * Spring Data JPA repository for the Orden entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {}
