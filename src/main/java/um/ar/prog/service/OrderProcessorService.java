package um.ar.prog.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.ar.prog.domain.OrderProcessor;
import um.ar.prog.repository.OrderProcessorRepository;

/**
 * Service Implementation for managing {@link OrderProcessor}.
 */
@Service
@Transactional
public class OrderProcessorService {

    private final Logger log = LoggerFactory.getLogger(OrderProcessorService.class);

    private final OrderProcessorRepository orderProcessorRepository;

    public OrderProcessorService(OrderProcessorRepository orderProcessorRepository) {
        this.orderProcessorRepository = orderProcessorRepository;
    }

    /**
     * Save a orderProcessor.
     *
     * @param orderProcessor the entity to save.
     * @return the persisted entity.
     */
    public OrderProcessor save(OrderProcessor orderProcessor) {
        log.debug("Request to save OrderProcessor : {}", orderProcessor);
        return orderProcessorRepository.save(orderProcessor);
    }

    /**
     * Update a orderProcessor.
     *
     * @param orderProcessor the entity to save.
     * @return the persisted entity.
     */
    public OrderProcessor update(OrderProcessor orderProcessor) {
        log.debug("Request to update OrderProcessor : {}", orderProcessor);
        return orderProcessorRepository.save(orderProcessor);
    }

    /**
     * Partially update a orderProcessor.
     *
     * @param orderProcessor the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrderProcessor> partialUpdate(OrderProcessor orderProcessor) {
        log.debug("Request to partially update OrderProcessor : {}", orderProcessor);

        return orderProcessorRepository
            .findById(orderProcessor.getId())
            .map(existingOrderProcessor -> {
                if (orderProcessor.getCliente() != null) {
                    existingOrderProcessor.setCliente(orderProcessor.getCliente());
                }
                if (orderProcessor.getAccion() != null) {
                    existingOrderProcessor.setAccion(orderProcessor.getAccion());
                }
                if (orderProcessor.getOperacion() != null) {
                    existingOrderProcessor.setOperacion(orderProcessor.getOperacion());
                }
                if (orderProcessor.getPrecio() != null) {
                    existingOrderProcessor.setPrecio(orderProcessor.getPrecio());
                }
                if (orderProcessor.getCantidad() != null) {
                    existingOrderProcessor.setCantidad(orderProcessor.getCantidad());
                }
                if (orderProcessor.getFechaOperacion() != null) {
                    existingOrderProcessor.setFechaOperacion(orderProcessor.getFechaOperacion());
                }
                if (orderProcessor.getModo() != null) {
                    existingOrderProcessor.setModo(orderProcessor.getModo());
                }

                return existingOrderProcessor;
            })
            .map(orderProcessorRepository::save);
    }

    /**
     * Get all the orderProcessors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderProcessor> findAll(Pageable pageable) {
        log.debug("Request to get all OrderProcessors");
        return orderProcessorRepository.findAll(pageable);
    }

    /**
     * Get one orderProcessor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderProcessor> findOne(Long id) {
        log.debug("Request to get OrderProcessor : {}", id);
        return orderProcessorRepository.findById(id);
    }

    /**
     * Delete the orderProcessor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderProcessor : {}", id);
        orderProcessorRepository.deleteById(id);
    }
}
