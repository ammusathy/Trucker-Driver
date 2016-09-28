package driver.com.driver.constants;

import java.io.File;

import driver.com.driver.BuildConfig;

/**
 * Created by kalaivani on 1/21/2016.
 */

public interface IConstant {
    String title = "Trukr";
    String DeviceType = "2";
    String alert = "Alert";
    String signout = "Signout";
    String UserType = "2";
    int DialCount = 6;

    String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    String TEMP_PHOTO_FILE_NAME1 = "temp_photo.jpg";

    String _EMAIL_PATTERN = "^[A-Za-z0-9,!#\\$%&'\\*\\+/=\\?\\^_`\\{\\|}~-]+(\\.[A-Za-z0-9,!#\\$%&'\\*\\+/=\\?\\^_`\\{\\|}~-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.([A-Za-z]{2,})$";

    String _ERR_NO_INTERNET_CONNECTION = "Please check your internet connection";
    String ADD_DOCUMENTS = "Please check your internet connection";
    String _ERR_SOMETHING_WENT_WRONG = "Something Went Wrong";
    String _ERR_INTERNAL_SERVER_ERROR = "Internal Server Error";
    String _ERR_UNABLE_TO_CONNECT_SERVER = "Unable To Connect Server";
    String chatUri = "http://www.etrukr.com/webservice/public/chatimages/";
    String RootDir = "Trucker";
    String SharedImage_Path = RootDir + File.separator + "Images";
    String ImageFormat = ".png";

    /* WEB API URL truckertest4@yopmail.com 12345678*/
    String Loading = "Loading...";
    String IP = BuildConfig.IP;
    String UserLogin = IP + "ws-login";
    String ChangeJobStatus = IP + "ws-driver-change-job-status";
    String ChangeMobileNumber = IP + "ws-change-mobile";
    String ChangePassword = IP + "ws-update-password";
    String ForgotPassword = IP + "ws-reset-password";
    String ListAllShipment = IP + "ws-all-shippment";
    String Cancelorder = IP + "ws-cancel-order";
    String ReadShipment = IP + "ws-read-order";
    String EditShipment = IP + "ws-driver-edit-shipment";
    String AcceptJob = IP + "ws-accept-job";
    String Notification = IP + "ws-notification";
    String ProfileUpdate = IP + "ws-update-profile";
    String Mobile = IP + "ws-mobile-verify";
    String ResendText = IP + "ws-resend-code";
    String DriverEnroute = IP + "ws-driver-enroute";
    String SendChat = IP + "ws-send-chat";
    String ReceiveChat = IP + "ws-receive-chat";
    String GetProfileInfo = IP + "ws-get-profile";
    String UserLogout = IP + "ws-logout";
    String ViewDetails = IP + "ws-driver-view-shipment";
    String InTransit = IP + "/ws-list-intransit";
    String Driverdocments = IP + "/ws-delete-driver-orderdoc";
    String Pending = IP + "/ws-list-pending";
    String uri = "http://www.etrukr.com/webservice/public/driver_order_documents/";

}
























