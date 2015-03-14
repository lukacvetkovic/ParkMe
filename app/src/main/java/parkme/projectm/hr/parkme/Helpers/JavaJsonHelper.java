package parkme.projectm.hr.parkme.Helpers;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import parkme.projectm.hr.parkme.Models.City;
import parkme.projectm.hr.parkme.Models.ParkingZone;



public class JavaJsonHelper {

    public static List<City> fromStringToCityList (String citiesJson) throws JSONException {

       Gson gson = new Gson();

        List<City> cityList= new ArrayList<>();

        JSONArray jsonCities = new JSONObject(citiesJson).getJSONArray("content");
        for (int i = 0; i < jsonCities.length(); i++) {
           cityList.add(gson.fromJson(String.valueOf(jsonCities.getJSONObject(i)),City.class));

        }

        return cityList;
    }

    public static List<ParkingZone> fromStringToZoneList(String zonesJson)throws JSONException{
        Gson gson = new Gson();
        List<ParkingZone> zoneList= new ArrayList<>();
        JSONArray jsonZones = new JSONObject(zonesJson).getJSONArray("content");
        for (int i = 0; i < jsonZones.length(); i++) {
            zoneList.add(gson.fromJson(String.valueOf(jsonZones.getJSONObject(i)), ParkingZone.class));

        }

        return zoneList;
    }

}
