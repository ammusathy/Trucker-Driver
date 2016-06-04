package driver.com.driver.model.ResponseParams;

/**
 * Created by rajaganapathi on 5/6/2016.
 */
public class ProfileResponse  {

    String Message,UserId,AuthToken;
    int StatusCode,UserType;
    Personal Personal;

    public ProfileResponse() {
    }

    public ProfileResponse(String message, String userId, String authToken, int statusCode, int userType, driver.com.driver.model.ResponseParams.Personal personal) {

        Message = message;
        UserId = userId;
        AuthToken = authToken;
        StatusCode = statusCode;
        UserType = userType;
        Personal = personal;
    }

    public String getMessage() {

        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public driver.com.driver.model.ResponseParams.Personal getPersonal() {
        return Personal;
    }

    public void setPersonal(driver.com.driver.model.ResponseParams.Personal personal) {
        Personal = personal;
    }
}
