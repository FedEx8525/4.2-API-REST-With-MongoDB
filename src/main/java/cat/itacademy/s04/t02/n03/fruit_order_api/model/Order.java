package cat.itacademy.s04.t02.n03.fruit_order_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String clientName;
    private LocalDate deliveryDate;
    private List<OrderItem> items;

    protected Order() {
    }

    public Order(String clientName, LocalDate deliveryDate, List<OrderItem> items) {
        this.clientName = clientName;
        this.deliveryDate = deliveryDate;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", clientName='" + clientName + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", items=" + items +
                '}';
    }
}
