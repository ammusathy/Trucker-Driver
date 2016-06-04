package driver.com.driver.model.ResponseParams;

/**
 * Created by kalaivani on 23-May-16.
 */
public class ShipperReasponse {
    public ShipperReasponse(String shipperContact) {
        ShipperContact = shipperContact;
    }

    public String getShipperContact() {

        return ShipperContact;
    }

    public void setShipperContact(String shipperContact) {
        ShipperContact = shipperContact;
    }

    public String ShipperContact;
}
