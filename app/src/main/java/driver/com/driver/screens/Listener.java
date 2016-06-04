package driver.com.driver.screens;


import java.util.HashMap;
import java.util.List;

public interface Listener {

    public void onSuccessfullRouteFetch(List<List<HashMap<String, String>>> result);

    public void onFail();

}
