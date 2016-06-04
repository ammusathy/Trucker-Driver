package driver.com.driver.model.ResponseParams;

/**
 * Created by kalaivani on 5/3/2016.
 */
public class AllShipmentResponse {

    private String OrderId;
    private String OrderStatus;
    private String OrderDate;
    private String OrderTime;
    private String FromAddress;
    private String TotalAmount;

//    public AllShipmentResponse(String orderId, String orderStatus, String orderDate, String orderTime, String fromAddress, String totalAmount) {
//        OrderId = orderId;
//        OrderStatus = orderStatus;
//        OrderDate = orderDate;
//        OrderTime = orderTime;
//        FromAddress = fromAddress;
//        TotalAmount = totalAmount;
//    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getFromAddress() {
        return FromAddress;
    }

    public void setFromAddress(String fromAddress) {
        FromAddress = fromAddress;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }
}
