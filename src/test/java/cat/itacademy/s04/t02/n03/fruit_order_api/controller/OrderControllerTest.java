package cat.itacademy.s04.t02.n03.fruit_order_api.controller;

import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderItemDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderRequestDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderResponseDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder_ShouldReturn201_WhenValidRequest() throws Exception {
        List<OrderItemDTO> itemsRequestDTOs = List.of(new OrderItemDTO("banana", 50));
        LocalDate deliveryDate = LocalDate.now().plusDays(1);
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO("Carlos Molina", deliveryDate, itemsRequestDTOs);

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO("abc123", "Carlos Molina", deliveryDate, itemsRequestDTOs);

        when(orderService.createOrder(any(OrderRequestDTO.class))).thenReturn(orderResponseDTO);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.clientName").value("Carlos Molina"))
                .andExpect(jsonPath("$.deliveryDate").value(LocalDate.now().plusDays(1).toString()))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].fruitName").value("banana"))
                .andExpect(jsonPath("$.items[0].quantityInKilos").value(50));
    }


}
