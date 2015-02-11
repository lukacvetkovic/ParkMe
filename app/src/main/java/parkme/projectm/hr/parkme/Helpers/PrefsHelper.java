package parkme.projectm.hr.parkme.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mihael on 14.12.2014..
 */
public class PrefsHelper {
    private final String TAG = "PrefsHelper";

    private final String prefsName = "hr.cvim.hzzo.pamZdravlje";

    private Context context;

    public static String keys = "KEYS";         // String formated like 1,2,3 -> keys to Strings like institutionCode%timeOfMeeting%napomena

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefsHelper(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void addNewMeeting(int key, String institutionSifra, String dateOfMeeting){
        putString(String.valueOf(key), institutionSifra + "%" + dateOfMeeting);
    }

    public void removeMeeting(int key){        // tested
        if(prefsContains(String.valueOf(key))){
            String intKey = String.valueOf(key);
            remove(intKey);
            String keyString = getString(PrefsHelper.keys, null);
            List<String> keys = new ArrayList<String>();
            keys.addAll(Arrays.asList(keyString.split(",")));
            keyString = "";
            for(String key1 : keys){
                if(key1.equals(intKey)){
                    continue;
                }
                keyString = keyString + key1 + ",";
            }
            if(!keyString.equals("")) {
                keyString = keyString.substring(0, keyString.length() - 1);     // removing last ','
            }
            putString(PrefsHelper.keys, keyString);
        }
    }

    public String getInstitucionSifraAndMeetingTime(int key){         // tested
        if(prefsContains(String.valueOf(key))){
            return getString(String.valueOf(key), null);
        }
        else{
            return null;
        }
    }

    public String getInstitutionSifra(int key){                     // tested
        if(prefsContains(String.valueOf(key))){
            String values = getString(String.valueOf(key), "");
            return values.split("%")[0];
        }
        return null;
    }

    public void printOut(){             // tested
        String keyString = getString(PrefsHelper.keys, "");
        Log.i(TAG, "." + keyString + ".");
        List<String> keys = Arrays.asList(keyString.split(","));
        for(String key : keys) {
            Log.i(TAG, "" + key + " <-> " + getString(String.valueOf(key), ""));
        }
    }

    public int getNewKey(){             // tested
        String keyString;
        if(prefsContains(PrefsHelper.keys)){
            keyString = getString(PrefsHelper.keys, null);
            List<String> keys = new ArrayList<String>();
            keys.addAll(Arrays.asList(keyString.split(",")));
            int i = 0;
            while(keys.contains(String.valueOf(++i)));
            keys.add(String.valueOf(i));
            keyString = "";
            for(String key : keys){
                keyString = keyString + key + ",";
            }
            keyString = keyString.substring(0, keyString.length() - 1);     // removing last ','
            putString(PrefsHelper.keys, keyString);
            return i;
        }
        else{
            keyString = "1";
            putString(PrefsHelper.keys, keyString);
            return 1;
        }
    }

    public void clearTimedOutEntries(){         // tested
        long now = System.currentTimeMillis();
        long timeMettingWas;
        String valuesString;
        String[] values;
        String keyString = getString(PrefsHelper.keys, "");
        List<String> keys = new ArrayList<String>();
        List<String> keysForRemoval = new ArrayList<String>();
        keys = Arrays.asList(keyString.split(","));
        for(String key : keys){
            valuesString = getString(String.valueOf(key), "");
            if(valuesString.isEmpty()) {
                continue;
            }
            values = valuesString.split("%");
            if(values.length == 2){
                timeMettingWas = Long.parseLong(values[1]);
                if(now > timeMettingWas){
                    remove(String.valueOf(key));
                    keysForRemoval.add(key);
                    Log.i(TAG, "Removing outTimed key - " + key);
                }
            }
            else{
                Log.e(TAG, "Error reading properties value, deleting entry : " + key + " -> " + getString(String.valueOf(key), null));
                remove(String.valueOf(key));
                keysForRemoval.add(key);
            }
        }
        keyString = "";
        for(String key : keys){
            if(keysForRemoval.contains(key)){
                continue;
            }
            keyString = keyString + key + ",";
        }
        if(!keyString.equals("")) {
            keyString = keyString.substring(0, keyString.length() - 1);     // removing last ','
        }
        putString(PrefsHelper.keys, keyString);
    }





    // ### boiler code
    public String getString(String varName, String defaulrReturnValue){
        return sharedPreferences.getString(varName, defaulrReturnValue);
    }

    public int getInt(String varName, int defaulrReturnValue){
        return sharedPreferences.getInt(varName, defaulrReturnValue);
    }

    public Long getLong(String varName, long defaultReturnValue){
        return sharedPreferences.getLong(varName, defaultReturnValue);
    }

    public boolean getBoolean(String varName, boolean defaulrReturnValue){
        return sharedPreferences.getBoolean(varName, defaulrReturnValue);
    }

    public void putString(String varName, String value){
        editor.putString(varName, value);
        editor.commit();
    }

    public void putInt(String varName, int value){
        editor.putInt(varName, value);
        editor.commit();
    }

    public void putLong(String varName, long value){
        editor.putLong(varName, value);
        editor.commit();
    }

    public void putBoolean(String varName, boolean value){
        editor.putBoolean(varName, value);
        editor.commit();
    }

    public boolean prefsContains(String varName){
        return sharedPreferences.contains(varName);
    }

    public SharedPreferences.Editor remove (String varName){
        editor.remove(varName);
        editor.commit();
        return editor;
    }

}
