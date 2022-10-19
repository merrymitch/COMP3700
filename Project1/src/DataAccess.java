public interface DataAccess {
    void connect();

    void saveProduct(ProductModel product);

    ProductModel loadProduct(int productID);

    void saveOrder(OrderModel order);

    OrderModel loadOrder(int orderId);

    void saveCustomer(CustomerModel customer);

    CustomerModel loadCustomer(int customerID);
    // void loadUser(int uderId);
    // void saveUser(UserModel user);
}
