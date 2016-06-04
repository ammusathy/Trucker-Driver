package driver.com.driver.model.ResponseParams;

/**
 * Created by androidusr1 on 1/6/16.
 */
public class AdditionalResponse {
    String title,value;

    public AdditionalResponse(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
