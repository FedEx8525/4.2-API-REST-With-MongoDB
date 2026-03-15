package cat.itacademy.s04.t02.n03.fruit_order_api.controller;

import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderItemDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderRequestDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderResponseDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.exception.OrderNotFoundException;
import cat.itacademy.s04.t02.n03.fruit_order_api.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private LocalDate deliveryDate;
    private OrderResponseDTO response1;
    private OrderResponseDTO response2;

    @BeforeEach
    void setup() {
        deliveryDate = LocalDate.now().plusDays(1);
        response1 = new OrderResponseDTO("abc123", "Carlos Molina", deliveryDate,
                List.of(new OrderItemDTO("banana", 50)));
        response2 = new OrderResponseDTO("def456", "Nieves Rodriguez", deliveryDate,
                List.of(new OrderItemDTO("apple", 45), new OrderItemDTO("mango", 10)));
    }

    @Test
    void createOrder_ShouldReturn201_WhenValidRequest() throws Exception {
        List<OrderItemDTO> itemsRequestDTOs = List.of(new OrderItemDTO("banana", 50));
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO("Carlos Molina", deliveryDate, itemsRequestDTOs);

        when(orderService.createOrder(any(OrderRequestDTO.class))).thenReturn(response1);

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

    @Test
    void createOrder_ShouldReturn400_WhenClientNameIsBlank() throws Exception {
        String body = """
            {
              "clientName": "",
              "deliveryDate": "%s",
              "items": [{"fruitName": "banana", "quantityInKilos": 50}]
            }
            """.formatted(deliveryDate);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrder_ShouldReturn400_WhenItemsIsEmpty() throws Exception {
        String body = """
            {
              "clientName": "Carlos Molina",
              "deliveryDate": "%s",
              "items": []
            }
            """.formatted(deliveryDate);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrder_ShouldReturn400_WhenDeliveryDateIsInThePast() throws Exception {
        String body = """
            {
              "clientName": "Carlos Molina",
              "deliveryDate": "2020-01-01",
              "items": [{"fruitName": "banana", "quantityInKilos": 50}]
            }
            """;

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listOrders_ShouldReturn200_WhenOrdersExist() throws Exception{
        List<OrderResponseDTO> orders = List.of(response1, response2);

        when(orderService.listOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("abc123"))
                .andExpect(jsonPath("$[1].id").value("def456"));
    }

    @Test
    void listOrders_ShouldReturn200WithEmptyList_WhenNoOrdersExist () throws Exception{
        when(orderService.listOrders()).thenReturn(List.of());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getOrderById_ShouldReturn200_WhenIdExists() throws Exception{
        String id = "abc123";
        when(orderService.getOrderById(id)).thenReturn(response1);

        mockMvc.perform(get("/orders/abc123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getOrderById_ShouldReturn404_WhenIdDoesNotExist() throws Exception{
        String id = "zyx987";
        when(orderService.getOrderById(id))
                .thenThrow(new OrderNotFoundException(id));

        mockMvc.perform(get("/orders/zyx987")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
