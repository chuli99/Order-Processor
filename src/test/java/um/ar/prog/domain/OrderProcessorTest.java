package um.ar.prog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import um.ar.prog.web.rest.TestUtil;

class OrderProcessorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderProcessor.class);
        OrderProcessor orderProcessor1 = new OrderProcessor();
        orderProcessor1.setId(1L);
        OrderProcessor orderProcessor2 = new OrderProcessor();
        orderProcessor2.setId(orderProcessor1.getId());
        assertThat(orderProcessor1).isEqualTo(orderProcessor2);
        orderProcessor2.setId(2L);
        assertThat(orderProcessor1).isNotEqualTo(orderProcessor2);
        orderProcessor1.setId(null);
        assertThat(orderProcessor1).isNotEqualTo(orderProcessor2);
    }
}
