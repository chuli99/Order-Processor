package um.ar.prog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import um.ar.prog.domain.OrderProcessor;
import um.ar.prog.repository.OrderProcessorRepository;
import um.ar.prog.service.OrderProcessorService;
import um.ar.prog.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link um.ar.prog.domain.OrderProcessor}.
 */
@RestController
@RequestMapping("/api")
public class OrderProcessorResource {

    private final Logger log = LoggerFactory.getLogger(OrderProcessorResource.class);

    private static final String ENTITY_NAME = "orderProcessor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderProcessorService orderProcessorService;

    private final OrderProcessorRepository orderProcessorRepository;

    public OrderProcessorResource(OrderProcessorService orderProcessorService, OrderProcessorRepository orderProcessorRepository) {
        this.orderProcessorService = orderProcessorService;
        this.orderProcessorRepository = orderProcessorRepository;
    }

    /**
     * {@code POST  /order-processors} : Create a new orderProcessor.
     *
     * @param orderProcessor the orderProcessor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderProcessor, or with status {@code 400 (Bad Request)} if the orderProcessor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-processors")
    public ResponseEntity<OrderProcessor> createOrderProcessor(@RequestBody OrderProcessor orderProcessor) throws URISyntaxException {
        log.debug("REST request to save OrderProcessor : {}", orderProcessor);
        if (orderProcessor.getId() != null) {
            throw new BadRequestAlertException("A new orderProcessor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderProcessor result = orderProcessorService.save(orderProcessor);
        return ResponseEntity
            .created(new URI("/api/order-processors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-processors/:id} : Updates an existing orderProcessor.
     *
     * @param id the id of the orderProcessor to save.
     * @param orderProcessor the orderProcessor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderProcessor,
     * or with status {@code 400 (Bad Request)} if the orderProcessor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderProcessor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-processors/{id}")
    public ResponseEntity<OrderProcessor> updateOrderProcessor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderProcessor orderProcessor
    ) throws URISyntaxException {
        log.debug("REST request to update OrderProcessor : {}, {}", id, orderProcessor);
        if (orderProcessor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderProcessor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderProcessorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderProcessor result = orderProcessorService.update(orderProcessor);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderProcessor.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-processors/:id} : Partial updates given fields of an existing orderProcessor, field will ignore if it is null
     *
     * @param id the id of the orderProcessor to save.
     * @param orderProcessor the orderProcessor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderProcessor,
     * or with status {@code 400 (Bad Request)} if the orderProcessor is not valid,
     * or with status {@code 404 (Not Found)} if the orderProcessor is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderProcessor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-processors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderProcessor> partialUpdateOrderProcessor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderProcessor orderProcessor
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderProcessor partially : {}, {}", id, orderProcessor);
        if (orderProcessor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderProcessor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderProcessorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderProcessor> result = orderProcessorService.partialUpdate(orderProcessor);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderProcessor.getId().toString())
        );
    }

    /**
     * {@code GET  /order-processors} : get all the orderProcessors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderProcessors in body.
     */
    @GetMapping("/order-processors")
    public ResponseEntity<List<OrderProcessor>> getAllOrderProcessors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of OrderProcessors");
        Page<OrderProcessor> page = orderProcessorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-processors/:id} : get the "id" orderProcessor.
     *
     * @param id the id of the orderProcessor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderProcessor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-processors/{id}")
    public ResponseEntity<OrderProcessor> getOrderProcessor(@PathVariable Long id) {
        log.debug("REST request to get OrderProcessor : {}", id);
        Optional<OrderProcessor> orderProcessor = orderProcessorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderProcessor);
    }

    /**
     * {@code DELETE  /order-processors/:id} : delete the "id" orderProcessor.
     *
     * @param id the id of the orderProcessor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-processors/{id}")
    public ResponseEntity<Void> deleteOrderProcessor(@PathVariable Long id) {
        log.debug("REST request to delete OrderProcessor : {}", id);
        orderProcessorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
