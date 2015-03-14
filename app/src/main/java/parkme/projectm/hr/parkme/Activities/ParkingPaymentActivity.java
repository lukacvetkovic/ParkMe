package parkme.projectm.hr.parkme.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import parkme.projectm.hr.parkme.Helpers.Constants;
import parkme.projectm.hr.parkme.Helpers.GetRestService;
import parkme.projectm.hr.parkme.Helpers.JavaJsonHelper;
import parkme.projectm.hr.parkme.Models.City;
import parkme.projectm.hr.parkme.Models.ParkingZone;
import parkme.projectm.hr.parkme.R;

public class ParkingPaymentActivity extends Activity {

    GetRestService getRestService;
    String response;
    List<City> cityList;
    List<ParkingZone> parkingZoneList;
    Spinner citySpinner;
    Spinner zoneSpinner;
    String[] cityNames;
    Map<String, Integer> mapIdCity;
    String selectedCity;
    String[] zoneNames;
    Map<String, Integer> mapIdZone;
    String selectedZone;


    ArrayAdapter<String> adapterZone;
    ArrayAdapter<String> adapterCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_payment);
        citySpinner = (Spinner) findViewById(R.id.spinnerCity);
        zoneSpinner = (Spinner) findViewById(R.id.spinnerZone);
        mapIdCity = new HashMap<>();
        getRestService = new GetRestService(Constants.dohvatiSveGradove + ".json");
        parkingZoneList = new ArrayList<>();


        try {
            response = getRestService.execute();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response != null) {
            try {
                cityList = JavaJsonHelper.fromStringToCityList(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("NIJE USPJELO", "!!!!!!!");
            Toast toast = Toast.makeText(this, "Neuspjelo dohvačanje podataka", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }

        cityNames = new String[cityList.size()];

        for (int i = 0, z = cityList.size(); i < z; ++i) {
            cityNames[i] = cityList.get(i).getName();
            mapIdCity.put(cityNames[i], cityList.get(i).getId());
        }


        adapterCity = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityNames);
        adapterZone = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        citySpinner.setAdapter(adapterCity);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cityNames[position];
                Log.d("IZABRAN------>", selectedCity);

                getRestService.setUrl(Constants.dohvatiParkingZonuZaGrad + mapIdCity.get(selectedCity) + ".json");

                Log.d("RESP------>","PRIJE");
                try {
                    response = getRestService.execute();
                    Log.d("RESP------>",response);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                Log.d("RESP------>","NAKON");

                if (response != null) {
                    try {
                        parkingZoneList = JavaJsonHelper.fromStringToZoneList(response);
                        Log.d("ParkingZoneList....->",parkingZoneList.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        finish();
                    }
                }

                zoneNames=new String[parkingZoneList.size()];

                for (int i = 0, z = parkingZoneList.size(); i < z; ++i) {
                    zoneNames[i] = parkingZoneList.get(i).getName()+" "+parkingZoneList.get(i).getPhone_number();
                    mapIdZone.put(zoneNames[i], parkingZoneList.get(i).getId());
                }

                adapterZone.clear();
                adapterZone.addAll(zoneNames);
                adapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                zoneSpinner.setAdapter(adapterZone);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
