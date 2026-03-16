package cat.itacademy.s04.t02.n03.fruit_order_api.exception;

public class OrderNotFoundException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Order not found with id: ";
    public OrderNotFoundException(String id) {
        super(DEFAULT_MESSAGE + id);
    }
}
