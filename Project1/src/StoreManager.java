public class StoreManager {

    private static StoreManager instance = null;

    private SQLiteDataAdapter dao;

    private ProductView productView = null;

    private OrderView orderView = null;

    private CustomerView customerView = null;

    private MainScreen mainScreen;

    public ProductView getProductView() {
        return productView;
    }

    public OrderView getOrderView() { return orderView; }

    public CustomerView getCustomerView() { return customerView; }

    private ProductController productController = null;

    private OrderController orderController = null;

    private CustomerController customerController = null;

    private StoreManager() { }

    public static StoreManager getInstance() {
        if (instance == null)
            instance = new StoreManager("SQLite");
        return instance;
    }

    public SQLiteDataAdapter getDataAccess() {
        return dao;
    }

    private StoreManager(String db) {
        // do some initialization here!!!
        if (db.equals("SQLite"))
            dao = new SQLiteDataAdapter();

        dao.connect();
        productView = new ProductView();
        orderView = new OrderView();
        customerView = new CustomerView();
        mainScreen = new MainScreen();
        productController = new ProductController(productView, dao);
        orderController = new OrderController(orderView, dao);
        customerController = new CustomerController(customerView, dao);

    }


    public MainScreen getMainScreen() {
        return mainScreen;
    }

}
