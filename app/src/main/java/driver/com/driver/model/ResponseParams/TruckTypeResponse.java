package driver.com.driver.model.ResponseParams;

/**
 * Created by mansoor on 11/05/16.
 */
public class TruckTypeResponse {

    String Id,Name;

    public TruckTypeResponse(String response) {
    }

    public TruckTypeResponse(String id, String name) {

        Id = id;
        Name = name;
    }

    public String getId() {

        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
