package driver.com.driver.model.ResponseParams;

/**
 * Created by kalaivani on 5/2/2016.
 */
public class GeneralResponseParams {
    private String Message;

    public String getMessage() {
        return Message;
    }

    public GeneralResponseParams() {
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    private  int StatusCode;
}
