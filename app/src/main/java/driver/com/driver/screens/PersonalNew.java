package driver.com.driver.screens;

/**
 * Created by androidusr1 on 26/5/16.
 */
public class PersonalNew {
    private String Lastname;
    private String Firstname;
    private String Email;
    private String Insurance;
    private String MobileNumber;
    private String AcceptJobs;

    public PersonalNew(String firstname) {
        Firstname = firstname;
    }

    public PersonalNew(String lastname, String acceptJobs, String mobileNumber, String insurance, String email, String firstname) {

        Lastname = lastname;
        AcceptJobs = acceptJobs;
        MobileNumber = mobileNumber;
        Insurance = insurance;
        Email = email;
        Firstname = firstname;
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