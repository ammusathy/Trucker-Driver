package driver.com.driver.model.RequestParams;


public class LoginRequestParams {


        private String UserType;
        private LoginDetails LoginDetails;

    LoginRequestParams(){
            UserType    = "";
            LoginDetails= new LoginDetails();
        }

        public LoginRequestParams(String userType, String userName, String password, String deviceUDID, String deviceType, String regId){
            UserType    = userType;
            LoginDetails= new LoginDetails(userName,password,deviceUDID,deviceType,regId);
        }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String UserType) {
        this.UserType = UserType;
    }

    public LoginDetails getLoginDetails() {
        return LoginDetails;
    }

    public void setLoginDetails(LoginDetails LoginDetails) {
        this.LoginDetails = LoginDetails;
    }

    public class LoginDetails {

        private String Username;
        private String Password;
        private String DeviceUDID;
        private String DeviceType;
        private String RegId;

        LoginDetails(){
            Username    = "";
            Password    = "";
            DeviceUDID  = "";
            DeviceType  = "";
            RegId       = "";
        }

        LoginDetails(String username,String password,String deviceUDID,String deviceType, String regId){
            Username = username;
            Password = password;
            DeviceUDID = deviceUDID;
            DeviceType = deviceType;
            RegId = regId;
        }

        public String getUsername() {
            return Username;
        }

        public void setUsername(String Username) {
            this.Username = Username;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public String getDeviceUDID() {
            return DeviceUDID;
        }

        public void setDeviceUDID(String DeviceUDID) {
            this.DeviceUDID = DeviceUDID;
        }

        public String getDeviceType() {
            return DeviceType;
        }

        public void setDeviceType(String DeviceType) {
            this.DeviceType = DeviceType;
        }

        public String getRegId() {
            return RegId;
        }

        public void setRegId(String RegId) {
            this.RegId = RegId;
        }
    }
}
