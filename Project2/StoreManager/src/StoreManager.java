public class StoreManager {

    private static StoreManager instance = null;

    private RemoteDataAdapter dao;

    public RemoteDataAdapter getDataAccess() {
        return dao;
    }

    private MainAppViewController mainScreen = null;

    public MainAppViewController getMainScreen() {
        return mainScreen;
    }

    private ProductView productView = null;

    public ProductView getProductView() {
        return productView;
    }

    private ProductController productController = null;

    private OrderView orderView = null;

    public OrderView getOrderView() { return orderView; }

    private OrderController orderController = null;

    private CustomerView customerView = null;

    public CustomerView getCustomerView() { return customerView; }

    private CustomerController customerController = null;

    public static StoreManager getInstance() {
        if (instance == null)
            instance = new StoreManager("SQLite");
        return instance;
    }

    private StoreManager(String db) {
        dao = new RemoteDataAdapter();
        dao.connect();
        mainScreen = new MainAppViewController();
        productView = new ProductView();
        productController = new ProductController(productView, dao);
        orderView = new OrderView();
        orderController = new OrderController(orderView, dao);
        customerView = new CustomerView();
        customerController = new CustomerController(customerView, dao);
    }

}
