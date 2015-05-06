package parkme.projectm.hr.parkme.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Mihael on 14.12.2014..
 */
public class PrefsHelper {
    private final String TAG = "PrefsHelper";

    private final String prefsName = "hr.lumipex.parkMe";


    private Context context;

    public static String ActiveCarPlates = "ActiveCarPlates";
    public static String LastUpdate = "LastUpdate";
    public static String PhoneNumber = "PhoneNumber";
    public static String parkingZoneId = "parkingZoneId";
    public static String zonePriceId = "zonePriceId";
    public static String citiyId = "citiyId";
    public static String paymentModeId = "paymentModeId";
    public static String trajanje = "trajanje";
    public static String carLat="carLat";
    public static String carLng="carLng";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefsHelper(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // ### boiler code
    public String getString(String varName, String defaulrReturnValue) {
        return sharedPreferences.getString(varName, defaulrReturnValue);
    }

    public int getInt(String varName, int defaulrReturnValue) {
        return sharedPreferences.getInt(varName, defaulrReturnValue);
    }

    public Long getLong(String varName, long defaultReturnValue) {
        return sharedPreferences.getLong(varName, defaultReturnValue);
    }

    public boolean getBoolean(String varName, boolean defaulrReturnValue) {
        return sharedPreferences.getBoolean(varName, defaulrReturnValue);
    }

    public Set<String> getStringSet(String varName, Set<String> defaulrReturnValue) {
        return sharedPreferences.getStringSet(varName, defaulrReturnValue);
    }

    public void putString(String varName, String value) {
        editor.putString(varName, value);
        editor.commit();
    }

    public void putInt(String varName, int value) {
        editor.putInt(varName, value);
        editor.commit();
    }

    public void putLong(String varName, long value) {
        editor.putLong(varName, value);
        editor.commit();
    }

    public void putBoolean(String varName, boolean value) {
        editor.putBoolean(varName, value);
        editor.commit();
    }

    public void putStringSet(String varName, Set<String> value) {
        editor.remove(varName);
        editor.commit();
        editor.putStringSet(varName, value);
        editor.apply();
    }

    public boolean prefsContains(String varName) {
        return sharedPreferences.contains(varName);
    }

    public SharedPreferences.Editor remove(String varName) {
        editor.remove(varName);
        editor.commit();
        return editor;
    }

}
