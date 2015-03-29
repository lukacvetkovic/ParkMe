package parkme.projectm.hr.parkme.Activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PostCode;
import parkme.projectm.hr.parkme.Dialogs.ConfirmPaymentDialog;
import parkme.projectm.hr.parkme.Helpers.Constants;
import parkme.projectm.hr.parkme.Helpers.GetRestService;
import parkme.projectm.hr.parkme.Helpers.JavaJsonHelper;
import parkme.projectm.hr.parkme.Helpers.LocationHelper.FallbackLocationTracker;
import parkme.projectm.hr.parkme.Helpers.LocationHelper.GPSTracker;
import parkme.projectm.hr.parkme.R;

import static android.widget.Toast.makeText;

public class ParkingPaymentActivity extends Activity {

    GPSTracker gpsTracker;

    GetRestService getRestService;
    String response;
    List<City> cityList;
    List<ParkingZone> parkingZoneList;
    List<PaymentMode> paymentModeList;
    Spinner citySpinner;
    Spinner zoneSpinner;
    Spinner paymentModeSpinner;


    String[] cityNames;
    Map<String, Integer> mapIdCity;

    String[] zoneNames;
    Map<String, Integer> mapIdZone;

    String[] options;
    Map<String, Integer> mapIdOption;

    String selectedZone;
    String selectedCity;
    String selectedOption;

    Button btnPay;
    CheckBox favs;

    ArrayAdapter<String> adapterZone;
    ArrayAdapter<String> adapterCity;
    ArrayAdapter<String> adapterOption;

    /**
     * On create method, starts with layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_payment);
        citySpinner = (Spinner) findViewById(R.id.spinnerCity);
        zoneSpinner = (Spinner) findViewById(R.id.spinnerZone);
        paymentModeSpinner =(Spinner) findViewById(R.id.spinnerOption);
        btnPay=(Button)findViewById(R.id.btnPayParking);
        favs=(CheckBox)findViewById(R.id.cbFavorites);

        mapIdCity = new HashMap<>();
        getRestService = new GetRestService(Constants.dohvatiSveGradove + ".json");
        parkingZoneList = new ArrayList<>();

        //Gps
        gpsTracker = new GPSTracker(this);
        FallbackLocationTracker fallbackLocationTracker = new FallbackLocationTracker(this);
        fallbackLocationTracker.start();

        selectedCity = null;

        //Get all cities
        try {
            response = getRestService.execute();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //If there was not error
        if (response != null) {
            try {
                cityList = JavaJsonHelper.fromStringToCityList(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //If there was error
        } else {
            Log.d("NIJE USPJELO", "!!!!!!!");
            Toast toast = makeText(this, "Neuspjelo dohvačanje podataka", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }

        cityNames = new String[cityList.size()];

        //Add city names to array and map
        for (int i = 0, z = cityList.size(); i < z; ++i) {
            cityNames[i] = cityList.get(i).getName();
            mapIdCity.put(cityNames[i], cityList.get(i).getId());
        }

        //Init Set addapter to citySpinner
        adapterCity = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityNames);
        //Init adapterZone
        adapterZone = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        //Init adapterOption
        adapterOption= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        citySpinner.setAdapter(adapterCity);

        //Get location
        Location mylocation=null;
        mylocation = fallbackLocationTracker.getLocation();
        if(mylocation==null){
            mylocation=gpsTracker.getLocation();
        }

        //If location not null set spinner to city
        if (mylocation != null) {
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = new ArrayList<>();
            try {
                addresses = gcd.getFromLocation(mylocation.getLatitude(), mylocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            response = null;
            if (addresses.size() > 0) {
                selectedCity = addresses.get(0).getPostalCode();
                try {
                    getRestService.setUrl(Constants.dohvatiGradPrekoPostanskogBroja + selectedCity + ".json");
                    response = getRestService.execute();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                Log.d("MOJ GRAD--->", selectedCity);
            }

            if (response != null) {
                try {
                    String cityName = null;
                    PostCode postCode = JavaJsonHelper.fromJsonToIdPostCode(response);
                    for (Map.Entry<String, Integer> entry : mapIdCity.entrySet()) {
                        if (postCode.getId_city() == (entry.getValue())) {
                            cityName = entry.getKey();
                        }
                    }

                    assert cityName != null;
                    citySpinner.setSelection(Arrays.asList(cityNames).indexOf(cityName));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("NIJE USPJELO", "!!!!!!!");
                Toast toast = makeText(this, "Neuspjelo dohvačanje podataka", Toast.LENGTH_LONG);
                toast.show();
            }

        }


        //Citiy spinner on item selected lisener
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Get selected city
                selectedCity = cityNames[position];
                Log.d("IZABRAN------>", selectedCity);

                //Get parkingZone for city
                getRestService.setUrl(Constants.dohvatiParkingZonuZaGrad + mapIdCity.get(selectedCity) + ".json");

                Log.d("REST------>", "PRIJE");
                try {
                    response = getRestService.execute();
                    Log.d("RESP------>", response);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                Log.d("REST------>", "NAKON");


                //If resault is OK
                if (response != null) {
                    try {
                        parkingZoneList = JavaJsonHelper.fromStringToZoneList(response);
                        Log.d("ParkingZoneList....->", parkingZoneList.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        finish();
                    }
                }

                zoneNames = new String[parkingZoneList.size()];
                mapIdZone = new HashMap<String, Integer>();

                //add zonenames to map and arrray
                for (int i = 0, z = parkingZoneList.size(); i < z; ++i) {
                    zoneNames[i] = parkingZoneList.get(i).getName();
                    mapIdZone.put(zoneNames[i], parkingZoneList.get(i).getId());
                }

                //Add adapter
                adapterZone.clear();
                adapterZone.addAll(zoneNames);
                adapterZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                zoneSpinner.setAdapter(adapterZone);

                zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //Get selected city
                        selectedZone = zoneNames[position];
                        Log.d("IZABRAN------>", selectedZone);

                        //Get parkingZone for city
                        getRestService.setUrl(Constants.dohvatiOpcijePrekoZone + mapIdZone.get(selectedZone) + ".json");

                        Log.d("REST------>", "PRIJE");
                        try {
                            response = getRestService.execute();
                            Log.d("RESP------>", response);
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        Log.d("REST------>", "NAKON");


                        //If resault is OK
                        if (response != null) {
                            try {
                                paymentModeList = JavaJsonHelper.fromStringToOptionList(response);
                                Log.d("ParkingZoneList....->", paymentModeList.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                                finish();
                            }
                        }

                        options = new String[paymentModeList.size()];
                        mapIdOption = new HashMap<String, Integer>();

                        //add zonenames to map and arrray
                        for (int i = 0, z = paymentModeList.size(); i < z; ++i) {
                            options[i] = paymentModeList.get(i).getDuration().toString();
                            mapIdOption.put(options[i], paymentModeList.get(i).getId());
                        }

                        //Add adapter
                        adapterOption.clear();
                        adapterOption.addAll(options);
                        adapterOption.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        paymentModeSpinner.setAdapter(adapterOption);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Radi
                //SMSHelper.sendSMS("0913020800","BOK, ti si najpametniji, covjece =)");


                String city=citySpinner.getSelectedItem().toString();
                Log.d("Grad->",city);

                String zone=zoneSpinner.getSelectedItem().toString();
                Log.d("Zona->",zone);

                float price= 71.15f;//TODO treba koristit API
                Log.d("Cijena->",String.valueOf(price));

                String duration=paymentModeSpinner.getSelectedItem().toString();
                Log.d("Trajanje->",duration);

                String maxDuration=new Date().toLocaleString();
                Log.d("Maksimalno trajanje->",maxDuration);


                int parkingZoneId=mapIdZone.get(zoneSpinner.getSelectedItem().toString());
                int parkingMethodId=mapIdOption.get(paymentModeSpinner.getSelectedItem().toString());

                DialogFragment pay= new ConfirmPaymentDialog();
                // Supply num input as an argument.
                Bundle args = new Bundle();

                args.putString("city", city);
                args.putString("zone", zone);
                args.putFloat("price", price);
                args.putString("duration", duration);
                args.putString("maxDuration", maxDuration);
                args.putInt("parkingZoneId",parkingZoneId);
                args.putInt("parkingMethodId",parkingMethodId);

                pay.setArguments(args);
                pay.show(getFragmentManager(),"Plačanje");

            }
        });

    }

}
