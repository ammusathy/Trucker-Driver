package driver.com.driver.model.ResponseParams;

/**
 * Created by kalaivani on 5/6/2016.
 */
public class AllReadShipmentResponse {
    String DeliveryTime, PickupTime, DeliveryDate, PickupDate, TotalPayable, FromAddress, ToAddress, Notes, TruckType;

    public AllReadShipmentResponse(String deliveryTime, String pickupTime, String deliveryDate, String pickupDate, String totalPayable, String fromAddress, String toAddress, String notes, String truckType) {
        DeliveryTime = deliveryTime;
        PickupTime = pickupTime;
        DeliveryDate = deliveryDate;
        PickupDate = pickupDate;
        TotalPayable = totalPayable;
        FromAddress = fromAddress;
        ToAddress = toAddress;
        Notes = notes;
        TruckType = truckType;
    }

    public String getDeliveryTime() {

        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public String getPickupTime() {
        return PickupTime;
    }

    public void setPickupTime(String pickupTime) {
        PickupTime = pickupTime;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getPickupDate() {
        return PickupDate;
    }

    public void setPickupDate(String pickupDate) {
        PickupDate = pickupDate;
    }

    public String getTotalPayable() {
        return TotalPayable;
    }

    public void setTotalPayable(String totalPayable) {
        TotalPayable = totalPayable;
    }

    public String getFromAddress() {
        return FromAddress;
    }

    public void setFromAddress(String fromAddress) {
        FromAddress = fromAddress;
    }

    public String getToAddress() {
        return ToAddress;
    }

    public void setToAddress(String toAddress) {
        ToAddress = toAddress;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getTruckType() {
        return TruckType;
    }

    public void setTruckType(String truckType) {
        TruckType = truckType;
    }
}