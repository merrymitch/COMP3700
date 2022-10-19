public class RequestModel {

    static public int EXIT_REQUEST = 0;
    static public int LOAD_PRODUCT_REQUEST = 1;
    static public int SAVE_PRODUCT_REQUEST = 11;
    static public int LOAD_ORDER_REQUEST = 2;
    static public int SAVE_ORDER_REQUEST = 22;
    static public int LOAD_CUSTOMER_REQUEST = 3;
    static public int SAVE_CUSTOMER_REQUEST = 33;

    public int code;
    public String body;
}
