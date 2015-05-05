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

import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.MaxDuration;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZonePrice;
import parkme.projectm.hr.parkme.Dialogs.ConfirmPaymentDialog;
import parkme.projectm.hr.parkme.Helpers.LocationHelper.FallbackLocationTracker;
import parkme.projectm.hr.parkme.Helpers.LocationHelper.GPSTracker;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.Helpers.Rest.ApiConstants;
import parkme.projectm.hr.parkme.Helpers.Rest.GetRestService;
import parkme.projectm.hr.parkme.Models.AutomaticZone;
import parkme.projectm.hr.parkme.R;


public class ParkingPaymentActivity extends Activity {

    GPSTracker gpsTracker;
    Location mylocation;

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

    String[] paymentModes;
    Map<String, Integer> mapIdOption;

    String selectedZone;
    String selectedCityPostCode;
    String selectedOption;

    Button btnPay;
    CheckBox favs;

    ArrayAdapter<String> adapterZone;
    ArrayAdapter<String> adapterCity;
    ArrayAdapter<String> adapterOption;

    boolean firstTime;

    DatabaseManager databaseManager;

    /**
     * On create method, starts with layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_payment);
        firstTime = true;
        citySpinner = (Spinner) findViewById(R.id.spinnerCity);
        zoneSpinner = (Spinner) findViewById(R.id.spinnerZone);
        paymentModeSpinner = (Spinner) findViewById(R.id.spinnerOption);
        btnPay = (Button) findViewById(R.id.btnPayParking);
        favs = (CheckBox) findViewById(R.id.cbFavorites);

        DatabaseManager.init(this);
        databaseManager = DatabaseManager.getInstance();

        mapIdCity = new HashMap<>();
        parkingZoneList = new ArrayList<>();

        //Gps
        gpsTracker = new GPSTracker(this);
        FallbackLocationTracker fallbackLocationTracker = new FallbackLocationTracker(this);
        fallbackLocationTracker.start();

        selectedCityPostCode = null;

        cityList = databaseManager.getAllCities(true);

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
        //Init adapterPaymentMode
        adapterOption = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        citySpinner.setAdapter(adapterCity);

        //Get location
        mylocation = null;

        mylocation = fallbackLocationTracker.getLocation();
        if (mylocation == null) {
            mylocation = gpsTracker.getLocation();
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
            if (addresses.size() > 0) {
                selectedCityPostCode = addresses.get(0).getPostalCode();

                Log.d("MOJ GRAD--->", selectedCityPostCode);
            }

            String cityName = databaseManager.getCityNameFromPostCode(selectedCityPostCode);
            if (cityName != null) {
                citySpinner.setSelection(Arrays.asList(cityNames).indexOf(cityName));
            }
        }


        //Citiy spinner on item selected lisener
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Get selected city
                String city=cityNames[position];
                Log.d("IZABRAN------>", city);

                parkingZoneList = databaseManager.getAllParkingZonesFromCity(mapIdCity.get(city));

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

                if (firstTime) {
                    findMyZoneIfPossible();
                    firstTime = false;
                }

                zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //Get selected city
                        selectedZone = zoneNames[position];
                        Log.d("IZABRAN------>", selectedZone);

                        paymentModeList = databaseManager.getAllPaymentModesFromParkingZone(mapIdZone.get(selectedZone));

                        paymentModes = new String[paymentModeList.size()];
                        mapIdOption = new HashMap<String, Integer>();

                        //add zonenames to map and arrray
                        for (int i = 0, z = paymentModeList.size(); i < z; ++i) {
                            Log.d("TIME ", paymentModeList.get(i).getDuration().toString());
                            Date date = new Date(paymentModeList.get(i).getDuration().getTime());
                            DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                            String dateFormatted = formatter.format(date);
                            paymentModes[i] = dateFormatted;
                            mapIdOption.put(paymentModes[i], paymentModeList.get(i).getId());
                        }

                        paymentModeSpinner.setEnabled(true);

                        //Add adapter
                        adapterOption.clear();
                        adapterOption.addAll(paymentModes);
                        adapterOption.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        paymentModeSpinner.setAdapter(adapterOption);

                        if (paymentModeList.size() == 1) {
                            paymentModeSpinner.setEnabled(false);
                        }

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
                int parkingZoneId = mapIdZone.get(zoneSpinner.getSelectedItem().toString());
                int paymentModeId = mapIdOption.get(paymentModeSpinner.getSelectedItem().toString());

                String city = citySpinner.getSelectedItem().toString();
                Log.d("Grad->", city);

                String zone = zoneSpinner.getSelectedItem().toString();
                Log.d("Zona->", zone);

                Date date = new Date();
                ZonePrice price = databaseManager.getPrice(date, paymentModeId);
                Log.d("Cijena->", String.valueOf(price.getPriceFloat()));

                String duration = paymentModeSpinner.getSelectedItem().toString();
                Log.d("Trajanje->", duration);

                MaxDuration maxDuration = databaseManager.getMaxDuration(new Date(), parkingZoneId);
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String maxDurationFormated = formatter.format(maxDuration.getMaxDuration().getTime());

                Log.d("Maksimalno trajanje->", String.valueOf(maxDuration.getMaxDuration().getTime()));
                Log.d("Maksimalno trajanje->", maxDurationFormated);


                DialogFragment pay = new ConfirmPaymentDialog();
                // Supply num input as an argument.
                Bundle args = new Bundle();

                args.putString("city", city);
                args.putString("zone", zone);
                args.putFloat("price", price.getPriceFloat());
                args.putString("duration", duration);
                if (maxDurationFormated.equals("00:00:00")) {
                    args.putString("maxDuration", "Neograničeno");
                } else {
                    args.putString("maxDuration", maxDurationFormated);
                }

                args.putInt("parkingZoneId", parkingZoneId);
                args.putInt("paymentModeId", paymentModeId);
                args.putBoolean("favs", favs.isChecked());

                //Spremanje parkingZoneId-a i tonePriceId da mogu update napraviti
                PrefsHelper prefsHelper = new PrefsHelper(getBaseContext());
                prefsHelper.putInt("parkingZoneId", parkingZoneId);
                prefsHelper.putInt("zonePriceId", price.getId());
                prefsHelper.putInt("citiyId", mapIdCity.get(city));
                prefsHelper.putString("priceString", String.valueOf(price.getPriceFloat()));

                pay.setArguments(args);
                pay.show(getFragmentManager(), "Plačanje");

            }
        });


    }

    public void findMyZoneIfPossible() {
        if (this.mylocation != null) {
            GetRestService get = new GetRestService(ApiConstants.automaticZone + databaseManager.getCityFromPostCode(this.selectedCityPostCode).getId() + "/" + mylocation.getLatitude() + "/" + mylocation.getLongitude());

            try {
                Gson gson = new Gson();
                response = get.execute();
                AutomaticZone automaticZone = gson.fromJson(response, AutomaticZone.class);
                if (automaticZone.getId_zone() != null) {
                    ParkingZone automatic = databaseManager.getParkingZoneFromId(automaticZone.getId_zone());
                    zoneSpinner.setSelection(Arrays.asList(zoneNames).indexOf(automatic.getName()));
                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}
