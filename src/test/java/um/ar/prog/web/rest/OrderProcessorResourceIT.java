package um.ar.prog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static um.ar.prog.web.rest.TestUtil.sameInstant;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import um.ar.prog.IntegrationTest;
import um.ar.prog.domain.OrderProcessor;
import um.ar.prog.repository.OrderProcessorRepository;

/**
 * Integration tests for the {@link OrderProcessorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderProcessorResourceIT {

    private static final Integer DEFAULT_CLIENTE = 1;
    private static final Integer UPDATED_CLIENTE = 2;

    private static final String DEFAULT_ACCION = "AAAAAAAAAA";
    private static final String UPDATED_ACCION = "BBBBBBBBBB";

    private static final String DEFAULT_OPERACION = "AAAAAAAAAA";
    private static final String UPDATED_OPERACION = "BBBBBBBBBB";

    private static final Float DEFAULT_PRECIO = 1F;
    private static final Float UPDATED_PRECIO = 2F;

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final ZonedDateTime DEFAULT_FECHA_OPERACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_OPERACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODO = "AAAAAAAAAA";
    private static final String UPDATED_MODO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/order-processors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderProcessorRepository orderProcessorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderProcessorMockMvc;

    private OrderProcessor orderProcessor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderProcessor createEntity(EntityManager em) {
        OrderProcessor orderProcessor = new OrderProcessor()
            .cliente(DEFAULT_CLIENTE)
            .accion(DEFAULT_ACCION)
            .operacion(DEFAULT_OPERACION)
            .precio(DEFAULT_PRECIO)
            .cantidad(DEFAULT_CANTIDAD)
            .fechaOperacion(DEFAULT_FECHA_OPERACION)
            .modo(DEFAULT_MODO);
        return orderProcessor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderProcessor createUpdatedEntity(EntityManager em) {
        OrderProcessor orderProcessor = new OrderProcessor()
            .cliente(UPDATED_CLIENTE)
            .accion(UPDATED_ACCION)
            .operacion(UPDATED_OPERACION)
            .precio(UPDATED_PRECIO)
            .cantidad(UPDATED_CANTIDAD)
            .fechaOperacion(UPDATED_FECHA_OPERACION)
            .modo(UPDATED_MODO);
        return orderProcessor;
    }

    @BeforeEach
    public void initTest() {
        orderProcessor = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderProcessor() throws Exception {
        int databaseSizeBeforeCreate = orderProcessorRepository.findAll().size();
        // Create the OrderProcessor
        restOrderProcessorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderProcessor))
            )
            .andExpect(status().isCreated());

        // Validate the OrderProcessor in the database
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeCreate + 1);
        OrderProcessor testOrderProcessor = orderProcessorList.get(orderProcessorList.size() - 1);
        assertThat(testOrderProcessor.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testOrderProcessor.getAccion()).isEqualTo(DEFAULT_ACCION);
        assertThat(testOrderProcessor.getOperacion()).isEqualTo(DEFAULT_OPERACION);
        assertThat(testOrderProcessor.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testOrderProcessor.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testOrderProcessor.getFechaOperacion()).isEqualTo(DEFAULT_FECHA_OPERACION);
        assertThat(testOrderProcessor.getModo()).isEqualTo(DEFAULT_MODO);
    }

    @Test
    @Transactional
    void createOrderProcessorWithExistingId() throws Exception {
        // Create the OrderProcessor with an existing ID
        orderProcessor.setId(1L);

        int databaseSizeBeforeCreate = orderProcessorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderProcessorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderProcessor))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderProcessor in the database
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrderProcessors() throws Exception {
        // Initialize the database
        orderProcessorRepository.saveAndFlush(orderProcessor);

        // Get all the orderProcessorList
        restOrderProcessorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProcessor.getId().intValue())))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE)))
            .andExpect(jsonPath("$.[*].accion").value(hasItem(DEFAULT_ACCION)))
            .andExpect(jsonPath("$.[*].operacion").value(hasItem(DEFAULT_OPERACION)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].fechaOperacion").value(hasItem(sameInstant(DEFAULT_FECHA_OPERACION))))
            .andExpect(jsonPath("$.[*].modo").value(hasItem(DEFAULT_MODO)));
    }

    @Test
    @Transactional
    void getOrderProcessor() throws Exception {
        // Initialize the database
        orderProcessorRepository.saveAndFlush(orderProcessor);

        // Get the orderProcessor
        restOrderProcessorMockMvc
            .perform(get(ENTITY_API_URL_ID, orderProcessor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderProcessor.getId().intValue()))
            .andExpect(jsonPath("$.cliente").value(DEFAULT_CLIENTE))
            .andExpect(jsonPath("$.accion").value(DEFAULT_ACCION))
            .andExpect(jsonPath("$.operacion").value(DEFAULT_OPERACION))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.fechaOperacion").value(sameInstant(DEFAULT_FECHA_OPERACION)))
            .andExpect(jsonPath("$.modo").value(DEFAULT_MODO));
    }

    @Test
    @Transactional
    void getNonExistingOrderProcessor() throws Exception {
        // Get the orderProcessor
        restOrderProcessorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrderProcessor() throws Exception {
        // Initialize the database
        orderProcessorRepository.saveAndFlush(orderProcessor);

        int databaseSizeBeforeUpdate = orderProcessorRepository.findAll().size();

        // Update the orderProcessor
        OrderProcessor updatedOrderProcessor = orderProcessorRepository.findById(orderProcessor.getId()).get();
        // Disconnect from session so that the updates on updatedOrderProcessor are not directly saved in db
        em.detach(updatedOrderProcessor);
        updatedOrderProcessor
            .cliente(UPDATED_CLIENTE)
            .accion(UPDATED_ACCION)
            .operacion(UPDATED_OPERACION)
            .precio(UPDATED_PRECIO)
            .cantidad(UPDATED_CANTIDAD)
            .fechaOperacion(UPDATED_FECHA_OPERACION)
            .modo(UPDATED_MODO);

        restOrderProcessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrderProcessor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrderProcessor))
            )
            .andExpect(status().isOk());

        // Validate the OrderProcessor in the database
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeUpdate);
        OrderProcessor testOrderProcessor = orderProcessorList.get(orderProcessorList.size() - 1);
        assertThat(testOrderProcessor.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testOrderProcessor.getAccion()).isEqualTo(UPDATED_ACCION);
        assertThat(testOrderProcessor.getOperacion()).isEqualTo(UPDATED_OPERACION);
        assertThat(testOrderProcessor.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testOrderProcessor.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testOrderProcessor.getFechaOperacion()).isEqualTo(UPDATED_FECHA_OPERACION);
        assertThat(testOrderProcessor.getModo()).isEqualTo(UPDATED_MODO);
    }

    @Test
    @Transactional
    void putNonExistingOrderProcessor() throws Exception {
        int databaseSizeBeforeUpdate = orderProcessorRepository.findAll().size();
        orderProcessor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderProcessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderProcessor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderProcessor))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderProcessor in the database
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderProcessor() throws Exception {
        int databaseSizeBeforeUpdate = orderProcessorRepository.findAll().size();
        orderProcessor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderProcessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderProcessor))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderProcessor in the database
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderProcessor() throws Exception {
        int databaseSizeBeforeUpdate = orderProcessorRepository.findAll().size();
        orderProcessor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderProcessorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderProcessor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderProcessor in the database
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderProcessorWithPatch() throws Exception {
        // Initialize the database
        orderProcessorRepository.saveAndFlush(orderProcessor);

        int databaseSizeBeforeUpdate = orderProcessorRepository.findAll().size();

        // Update the orderProcessor using partial update
        OrderProcessor partialUpdatedOrderProcessor = new OrderProcessor();
        partialUpdatedOrderProcessor.setId(orderProcessor.getId());

        partialUpdatedOrderProcessor.accion(UPDATED_ACCION).operacion(UPDATED_OPERACION).precio(UPDATED_PRECIO);

        restOrderProcessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderProcessor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderProcessor))
            )
            .andExpect(status().isOk());

        // Validate the OrderProcessor in the database
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeUpdate);
        OrderProcessor testOrderProcessor = orderProcessorList.get(orderProcessorList.size() - 1);
        assertThat(testOrderProcessor.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testOrderProcessor.getAccion()).isEqualTo(UPDATED_ACCION);
        assertThat(testOrderProcessor.getOperacion()).isEqualTo(UPDATED_OPERACION);
        assertThat(testOrderProcessor.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testOrderProcessor.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testOrderProcessor.getFechaOperacion()).isEqualTo(DEFAULT_FECHA_OPERACION);
        assertThat(testOrderProcessor.getModo()).isEqualTo(DEFAULT_MODO);
    }

    @Test
    @Transactional
    void fullUpdateOrderProcessorWithPatch() throws Exception {
        // Initialize the database
        orderProcessorRepository.saveAndFlush(orderProcessor);

        int databaseSizeBeforeUpdate = orderProcessorRepository.findAll().size();

        // Update the orderProcessor using partial update
        OrderProcessor partialUpdatedOrderProcessor = new OrderProcessor();
        partialUpdatedOrderProcessor.setId(orderProcessor.getId());

        partialUpdatedOrderProcessor
            .cliente(UPDATED_CLIENTE)
            .accion(UPDATED_ACCION)
            .operacion(UPDATED_OPERACION)
            .precio(UPDATED_PRECIO)
            .cantidad(UPDATED_CANTIDAD)
            .fechaOperacion(UPDATED_FECHA_OPERACION)
            .modo(UPDATED_MODO);

        restOrderProcessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderProcessor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderProcessor))
            )
            .andExpect(status().isOk());

        // Validate the OrderProcessor in the database
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeUpdate);
        OrderProcessor testOrderProcessor = orderProcessorList.get(orderProcessorList.size() - 1);
        assertThat(testOrderProcessor.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testOrderProcessor.getAccion()).isEqualTo(UPDATED_ACCION);
        assertThat(testOrderProcessor.getOperacion()).isEqualTo(UPDATED_OPERACION);
        assertThat(testOrderProcessor.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testOrderProcessor.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testOrderProcessor.getFechaOperacion()).isEqualTo(UPDATED_FECHA_OPERACION);
        assertThat(testOrderProcessor.getModo()).isEqualTo(UPDATED_MODO);
    }

    @Test
    @Transactional
    void patchNonExistingOrderProcessor() throws Exception {
        int databaseSizeBeforeUpdate = orderProcessorRepository.findAll().size();
        orderProcessor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderProcessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderProcessor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderProcessor))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderProcessor in the database
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderProcessor() throws Exception {
        int databaseSizeBeforeUpdate = orderProcessorRepository.findAll().size();
        orderProcessor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderProcessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderProcessor))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderProcessor in the database
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderProcessor() throws Exception {
        int databaseSizeBeforeUpdate = orderProcessorRepository.findAll().size();
        orderProcessor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderProcessorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderProcessor))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderProcessor in the database
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderProcessor() throws Exception {
        // Initialize the database
        orderProcessorRepository.saveAndFlush(orderProcessor);

        int databaseSizeBeforeDelete = orderProcessorRepository.findAll().size();

        // Delete the orderProcessor
        restOrderProcessorMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderProcessor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderProcessor> orderProcessorList = orderProcessorRepository.findAll();
        assertThat(orderProcessorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
