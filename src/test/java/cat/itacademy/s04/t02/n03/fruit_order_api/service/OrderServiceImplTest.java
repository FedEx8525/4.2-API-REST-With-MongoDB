package cat.itacademy.s04.t02.n03.fruit_order_api.service;

import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderItemDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderRequestDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.dto.OrderResponseDTO;
import cat.itacademy.s04.t02.n03.fruit_order_api.exception.OrderNotFoundException;
import cat.itacademy.s04.t02.n03.fruit_order_api.model.Order;
import cat.itacademy.s04.t02.n03.fruit_order_api.model.OrderItem;
import cat.itacademy.s04.t02.n03.fruit_order_api.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    private OrderItem item1;
    private OrderItem item2;
    private OrderItem item3;
    private List<OrderItem> items1;
    private List<OrderItem> items2;
    private Order order1;
    private Order order2;
    private LocalDate deliveryDate;

    @BeforeEach
    void setup() {
        items1 = new ArrayList<>();
        items2 = new ArrayList<>();

        deliveryDate = LocalDate.now().plusDays(1);

        item1 = new OrderItem("banana", 50);
        item2 = new OrderItem("apple", 45);
        item3 = new OrderItem("mango", 10);

        items1.add(item1);
        items2.add(item2);
        items2.add(item3);

        order1 = new Order("Carlos Molina", deliveryDate, items1);
        order1.setId("abc123");
        order2 = new Order("Nieves Rodriguez", deliveryDate, items2);
        order2.setId("def456");
    }

    @Test
    void createOrder_ShouldReturnOrderResponseDTO_WhenOrderIsSaved() {
        List<OrderItemDTO> itemsRequestDTOs = List.of(new OrderItemDTO("banana", 50));
        OrderRequestDTO orderRequest = new OrderRequestDTO("Carlos Molina", deliveryDate, itemsRequestDTOs);

        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        OrderResponseDTO result = orderService.createOrder(orderRequest);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals("abc123", result.id());
        assertEquals("Carlos Molina", result.clientName());
        assertEquals(LocalDate.now().plusDays(1), result.deliveryDate());
        assertEquals(1, result.items().size());
        assertEquals("banana", result.items().get(0).fruitName());
        assertEquals(50, result.items().get(0).quantityInKilos());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void listOrders_ShouldReturnListOfOrders_WhenOrdersExist() {

        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderResponseDTO> ordersDTO = orderService.listOrders();

        assertEquals(2, ordersDTO.size());
        assertEquals("abc123", ordersDTO.get(0).id());
        assertEquals("def456", ordersDTO.get(1).id());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void listOrder_ShouldReturnEmptyList_WhenNoOrdersExist() {

        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        List<OrderResponseDTO> ordersDTO = orderService.listOrders();

        assertNotNull(ordersDTO);
        assertTrue(ordersDTO.isEmpty());
        verify(orderRepository, times(1)).findAll();

    }

    @Test
    void getOrderById_ShouldReturnOrderResponseDTO_WhenIdExists() {
        String id = "abc123";
        when(orderRepository.findById(id)).thenReturn(Optional.of(order1));

        OrderResponseDTO orderDTO = orderService.getOrderById(id);

        assertNotNull(orderDTO);
        assertEquals(id, orderDTO.id());
        verify(orderRepository, times(1)).findById(id);

    }

    @Test
    void getOrderById_ThrowOrderNotFoundException_WhenIdDoesNotExist() {
        String wrongId = "zyx987";

        when(orderRepository.findById(wrongId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(wrongId));
        verify(orderRepository, times(1)).findById(wrongId);
    }


}
