package driver.com.driver.screens;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kalaivani on 3/7/2016.
 */

/**
 * Created by kalaivani on 1/21/2016.
 */
public class PreferenceConnector {

    public static final String PREF_NAME = "driver.com.driver";
    public static final String PREF_NAME1 = "driver.com.driver";
    public static final int MODE = Context.MODE_PRIVATE;

    public static final String _PREF_USER_ID = "USERID";
    public static final String _PREF_AUTH_TOKEN = "AUTHTOKEN";
    public static final String _PREF_USER_INFO = "USERINFO";
    public static final String _PREF_EMAIL_ID = "EMAILID";
    public static final String _PREF_PASSWORD = "PASSWORD";
    public static final String _PREF_REMEMBER_ME = "REMEMBERME";
    public static final String _PREF_USER_TYPE = "USERTYPE";
    public static final String _PREF_USER_NAME = "USERNAME";
    public static final String _PREF_TEMP_PASSWORD = "TEMPPASSWORD";

    public static final String _PREF_CHANGE_STATUS = "CHANGESTATUS";
    /**
     * read = to get datas from shared preference
     * write = to store data in shared preference
     *
     * @param context
     * @return
     */
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static SharedPreferences getPreferencesRemember(Context context) {
        return context.getSharedPreferences(PREF_NAME1, MODE);
    }

    public static SharedPreferences.Editor getEditorRemember(Context context) {
        return getPreferencesRemember(context).edit();
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeStringRemember(Context context, String key, String value) {
        getEditorRemember(context).putString(key, value).commit();
    }

    public static String readStringRemember(Context context, String key, String defValue) {
        return getPreferencesRemember(context).getString(key, defValue);
    }

    public static void clearAllPreferences(Context context) {
        getPreferences(context).edit().clear().commit();
    }

    public static void clearAllPreferencesRemember(Context context) {
        getPreferencesRemember(context).edit().clear().commit();
    }
}


