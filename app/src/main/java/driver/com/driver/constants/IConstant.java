package driver.com.driver.constants;

import driver.com.driver.BuildConfig;

/**
 * Created by kalaivani on 1/21/2016.
 */

public interface IConstant {
    String title = "Trukr";
    String DeviceType = "1";
    String alert="Alert";
    String UserType = "2";
    int DialCount = 6;

    String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";

    String _EMAIL_PATTERN = "^[A-Za-z0-9,!#\\$%&'\\*\\+/=\\?\\^_`\\{\\|}~-]+(\\.[A-Za-z0-9,!#\\$%&'\\*\\+/=\\?\\^_`\\{\\|}~-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.([A-Za-z]{2,})$";

    String _ERR_NO_INTERNET_CONNECTION = "Internet Connection Required";
    String _ERR_SOMETHING_WENT_WRONG = "Something Went Wrong";
    String _ERR_INTERNAL_SERVER_ERROR = "Internal Server Error";
    String _ERR_UNABLE_TO_CONNECT_SERVER = "Unable To Connect Server";

    /* WEB API URL */
    String IP = BuildConfig.IP;
    String UserLogin = IP + "ws-login";
    String UserLogout = IP + "ws-logout";
    String MobileVerification = IP + "ws-mobile-verify";
    String ResendText = IP + "ws-resend-code";
    String ChangeMobileNumber = IP + "ws-change-mobile";
    String ChangePassword = IP + "ws-update-password";
    String ForgotPassword = IP + "ws-reset-password";
    String UpdateProfileInfo = IP + "ws-update-profile";
    String GetProfileInfo = IP + "ws-get-profile";
    String ProfileUpdate = IP + "ws-update-profile";
    String DeleteAccount = IP + "ws-delete-account";
    String TruckType = IP + "ws-truck-type";
    String BorderCrossing = IP;
    String TimeZone = IP;
    String GetDriverDetails = IP;
    String RemoveCard = IP;
    String ListCarsds = IP;
    String SetDefaultCard = IP;
    String GetDefaultCard = IP;
    String Chat = IP;
    String Notification = IP;

}
