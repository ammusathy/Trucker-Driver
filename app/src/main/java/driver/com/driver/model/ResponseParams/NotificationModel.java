package driver.com.driver.model.ResponseParams;

/**
 * Created by androidusr1 on 27/6/16.
 */
public class NotificationModel {
    String Id,message,Date;

    public NotificationModel() {
    }

    public NotificationModel(String id, String message, String date) {
        Id = id;
        this.message = message;
        Date = date;

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
