package api.orders;

public class OrdersGenerator {

    public static Orders getDefaultOrders() {
        return new Orders(new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"});
    }

    public static Orders getWrongHashOrders() {
        return new Orders(new String[]{"61c0c5a71d1f82001bdaaa6", "61c0c5a71d1f82001bdaaa6f"});
    }

    public static Orders getEmptyOrders() {
        Orders orders = getDefaultOrders();
        orders.setIngredients(null);
        return orders;
    }
}
