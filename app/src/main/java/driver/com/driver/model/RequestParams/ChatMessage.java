package driver.com.driver.model.RequestParams;

import android.net.wifi.WifiConfiguration;

/**
 * Created by madhur on 17/01/15.
 */
public  class ChatMessage {

    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }



}