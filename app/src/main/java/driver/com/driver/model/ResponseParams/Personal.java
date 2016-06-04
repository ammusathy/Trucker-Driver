package driver.com.driver.model.ResponseParams;

/**
 * Created by kalaivani on 5/6/2016.
 */
public class Personal {

    private String Lastname;
    private String Firstname;
    private String Email;
    private String ProfilePicture;
    private String Insurance;
    private String MobileNumber;
    private String AcceptJobs;

    public Personal() {
    }

    public Personal(String lastname, String firstname, String email, String profilePicture, String insurance, String mobileNumber, String acceptJobs) {

        Lastname = lastname;
        Firstname = firstname;
        Email = email;
        ProfilePicture = profilePicture;
        Insurance = insurance;
        MobileNumber = mobileNumber;
        AcceptJobs = acceptJobs;
    }

    public String getLastname() {

        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getInsurance() {
        return Insurance;
    }

    public void setInsurance(String insurance) {
        Insurance = insurance;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getAcceptJobs() {
        return AcceptJobs;
    }

    public void setAcceptJobs(String acceptJobs) {
        AcceptJobs = acceptJobs;
    }
}