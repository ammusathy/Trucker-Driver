package driver.com.driver.model.ResponseParams;

public class LoginResponseParams {

    private String Message;
    private String isActivated;
    private String isPasswordChanged;
    private String StatusCode;
    private String UserType;
    private String UserId;
    private String AuthToken;
    private String OrderStatus;
    private Login Login;


    LoginResponseParams() {
        Message = "";
        isActivated = "";
        isPasswordChanged = "";
        StatusCode = "";
        UserType = "";
        UserId = "";
        AuthToken = "";
        OrderStatus = "";
        Login = new Login();

    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(String isActivated) {
        this.isActivated = isActivated;
    }

    public String getIsPasswordChanged() {
        return isPasswordChanged;
    }

    public void setIsPasswordChanged(String isPasswordChanged) {
        this.isPasswordChanged = isPasswordChanged;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String StatusCode) {
        this.StatusCode = StatusCode;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String UserType) {
        this.UserType = UserType;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String AuthToken) {
        this.AuthToken = AuthToken;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String OrderStatus) {
        this.OrderStatus = OrderStatus;
    }

    public Login getLogin() {
        return Login;
    }

    public void setLogin(Login Login) {
        this.Login = Login;
    }


    public class Login {
        private String Firstname;
        private String Lastname;
        private String Email;
        private String MobileNumber;
        private String Address;
        private String TruckType;
        private String RouteId;
        private String UsDotNo;
        private String McNumber;
        private String CompanyId;
        private String Company;
        private String OfficeNumber;
        private String TaxId;
        private String LicenseNo;
        private String ProfilePicture;
        private String AcceptNewJob;
        private String InsuranceDoc;

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getTruckType() {
            return TruckType;
        }

        public void setTruckType(String truckType) {
            TruckType = truckType;
        }

        public String getRouteId() {
            return RouteId;
        }

        public void setRouteId(String routeId) {
            RouteId = routeId;
        }

        public String getUsDotNo() {
            return UsDotNo;
        }

        public void setUsDotNo(String usDotNo) {
            UsDotNo = usDotNo;
        }

        public String getMcNumber() {
            return McNumber;
        }

        public void setMcNumber(String mcNumber) {
            McNumber = mcNumber;
        }

        public String getCompanyId() {
            return CompanyId;
        }

        public void setCompanyId(String companyId) {
            CompanyId = companyId;
        }

        public String getCompany() {
            return Company;
        }

        public void setCompany(String company) {
            Company = company;
        }

        public String getOfficeNumber() {
            return OfficeNumber;
        }

        public void setOfficeNumber(String officeNumber) {
            OfficeNumber = officeNumber;
        }

        public String getTaxId() {
            return TaxId;
        }

        public void setTaxId(String taxId) {
            TaxId = taxId;
        }

        public String getLicenseNo() {
            return LicenseNo;
        }

        public void setLicenseNo(String licenseNo) {
            LicenseNo = licenseNo;
        }

        public Login() {
            Firstname = "";
            Lastname = "";
            Email = "";
            MobileNumber = "";
            Address="";


        }



        public String getFirstname() {
            return Firstname;
        }

        public void setFirstname(String firstname) {
            Firstname = firstname;
        }

        public String getInsuranceDoc() {
            return InsuranceDoc;
        }

        public void setInsuranceDoc(String insuranceDoc) {
            InsuranceDoc = insuranceDoc;
        }

        public String getAcceptNewJob() {
            return AcceptNewJob;
        }

        public void setAcceptNewJob(String acceptNewJob) {
            AcceptNewJob = acceptNewJob;
        }

        public String getProfilePicture() {
            return ProfilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            ProfilePicture = profilePicture;
        }

        public String getMobileNumber() {
            return MobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            MobileNumber = mobileNumber;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getLastname() {
            return Lastname;
        }

        public void setLastname(String lastname) {
            Lastname = lastname;
        }
    }
}