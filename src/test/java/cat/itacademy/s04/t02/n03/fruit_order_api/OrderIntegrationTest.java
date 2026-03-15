package cat.itacademy.s04.t02.n03.fruit_order_api;

import cat.itacademy.s04.t02.n03.fruit_order_api.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final LocalDate tomorrow = LocalDate.now().plusDays(1);

    @BeforeEach
    void cleanUp() {
        orderRepository.deleteAll();
    }

    private String validOrderBody() {
        return """
                {
                  "clientName": "Alice",
                  "deliveryDate": "%s",
                  "items": [{"fruitName": "Apple", "quantityInKilos": 5}]
                }
                """.formatted(tomorrow);
    }

    private String createOrderAndGetId() throws Exception {
        MvcResult result = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validOrderBody()))
                .andExpect(status().isCreated())
                .andReturn();
        return objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asText();
    }

    @Test
    void createOrder_ShouldReturn201_WhenValidRequest() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validOrderBody()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientName").value("Alice"))
                .andExpect(jsonPath("$.items[0].fruitName").value("Apple"));
    }
    @Test
    void createOrder_ShouldReturn400_WhenClientNameIsBlank() throws Exception {
        String body = """
                {
                  "clientName": "",
                  "deliveryDate": "%s",
                  "items": [{"fruitName": "Apple", "quantityInKilos": 5}]
                }
                """.formatted(tomorrow);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listOrders_ShouldReturnEmptyList_WhenNoOrders() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void listOrders_ShouldReturnAllOrders_WhenOrdersExist() throws Exception {
        createOrderAndGetId();

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientName").value("Alice"));
    }




}

