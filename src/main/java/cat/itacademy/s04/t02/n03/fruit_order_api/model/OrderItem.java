package cat.itacademy.s04.t02.n03.fruit_order_api.model;

public class OrderItem {
    private String fruitName;
    private int quantityInKilos;

    protected OrderItem() {
    }

    public OrderItem(String fruitName, int quantityInKilos) {
        this.fruitName = fruitName;
        this.quantityInKilos = quantityInKilos;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getQuantityInKilos() {
        return quantityInKilos;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public void setQuantityInKilos(int quantityInKilos) {
        this.quantityInKilos = quantityInKilos;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "fruitName='" + fruitName + '\'' +
                ", quantityInKilos=" + quantityInKilos +
                '}';
    }
}
