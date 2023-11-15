package um.ar.prog.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import um.ar.prog.domain.OrderProcessor;

/**
 * Spring Data JPA repository for the OrderProcessor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderProcessorRepository extends JpaRepository<OrderProcessor, Long> {}
