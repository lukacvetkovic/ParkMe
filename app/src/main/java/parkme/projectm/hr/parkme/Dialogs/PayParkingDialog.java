package parkme.projectm.hr.parkme.Dialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavoritePayment;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.MaxDuration;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZonePrice;
import parkme.projectm.hr.parkme.Helpers.LocationHelper.FallbackLocationTracker;
import parkme.projectm.hr.parkme.Helpers.LocationHelper.GPSTracker;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.Helpers.Rest.ApiConstants;
import parkme.projectm.hr.parkme.Helpers.Rest.GetRestService;
import parkme.projectm.hr.parkme.Models.AutomaticZone;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 5.5.2015..
 */
public class PayParkingDialog extends FrameLayout{

    private final String TAG = "PayParkingDialog";
    private Context context;

    private PayParkingDialogCallback payParkingDialogCallback;

    private ConfirmPaymentDialog confirmPaymentDialog;

    GPSTracker gpsTracker;
    Location myLocation;

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

    ImageButton btnPay;
    CheckBox favs;
    CheckBox carPosition;

    boolean firstTime;
    boolean mjenjano;
    boolean imaGrada;

    ArrayAdapter<String> adapterZone;
    ArrayAdapter<String> adapterCity;
    ArrayAdapter<String> adapterOption;

    DatabaseManager databaseManager;

    public interface PayParkingDialogCallback {
        void dismissDialog();
        void showConfirmDialog(ConfirmPaymentDialog confirmPaymentDialog);
        void dismissConfirmDialog(ConfirmPaymentDialog confirmPaymentDialog);
    }

    public PayParkingDialog(Context context) {
        super(context);
        init(context);
    }

    public PayParkingDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PayParkingDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        inflate(getContext(), R.layout.dialog_pay_parking, this);
        reference();
    }

    private void reference(){
        mjenjano=false;
        firstTime = true;
        imaGrada=true;
        citySpinner = (Spinner) findViewById(R.id.spinnerCity);
        zoneSpinner = (Spinner) findViewById(R.id.spinnerZone);
        paymentModeSpinner = (Spinner) findViewById(R.id.spinnerOption);
        btnPay = (ImageButton) findViewById(R.id.btnPayParking);
        favs = (CheckBox) findViewById(R.id.cbFavorites);
        carPosition=(CheckBox)findViewById(R.id.cbCurrentLocation);

        DatabaseManager.init(context);
        databaseManager = DatabaseManager.getInstance();

        mapIdCity = new HashMap<>();
        parkingZoneList = new ArrayList<>();

        //Gps
        gpsTracker = new GPSTracker(context);
        FallbackLocationTracker fallbackLocationTracker = new FallbackLocationTracker(context);
        fallbackLocationTracker.start();

        selectedCityPostCode = null;

        cityList = databaseManager.getAllCities(true);

        cityNames = new String[cityList.size()];

        //Add city names to array and mapp
        for (int i = 0, z = cityList.size(); i < z; ++i) {
            cityNames[i] = cityList.get(i).getName();
            mapIdCity.put(cityNames[i], cityList.get(i).getId());
        }

        //Init Set addapter to citySpinner
        adapterCity = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, cityNames);
        //Init adapterZone
        adapterZone = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        //Init adapterPaymentMode
        adapterOption = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        citySpinner.setAdapter(adapterCity);

        //Get location
        myLocation = null;

        myLocation = fallbackLocationTracker.getLocation();
        if (myLocation == null) {
            myLocation = gpsTracker.getLocation();
        }

        //If location not null set spinner to city
        if (myLocation != null) {
            if(isOnline()) {
                Geocoder gcd = new Geocoder(context, Locale.getDefault());
                List<Address> addresses = new ArrayList<>();
                try {
                    addresses = gcd.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses.size() > 0) {
                    selectedCityPostCode = addresses.get(0).getPostalCode();

                    Log.d("MOJ GRAD--->", selectedCityPostCode);

                    String cityName = databaseManager.getCityNameFromPostCode(selectedCityPostCode);
                    if (cityName != null) {
                        carPosition.setVisibility(VISIBLE);
                        citySpinner.setSelection(Arrays.asList(cityNames).indexOf(cityName));
                    }
                    else{
                        imaGrada =false;
                    }
                }

            }
        }


        //Citiy spinner on item selected lisener
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!firstTime){
                    mjenjano=true;
                }

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

                if (firstTime && imaGrada) {
                    findMyZoneIfPossible();
                    firstTime = false;
                }
                btnPay.setVisibility(VISIBLE);

                zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if(!firstTime){
                            mjenjano=true;
                        }

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

        btnPay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int parkingZoneId = mapIdZone.get(zoneSpinner.getSelectedItem().toString());
                final int paymentModeId = mapIdOption.get(paymentModeSpinner.getSelectedItem().toString());

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

                confirmPaymentDialog = new ConfirmPaymentDialog(context);

                String maxParkingDuration;
                if (maxDurationFormated.equals("00:00:00")) {
                    maxParkingDuration = "Neograniceno";
                } else {
                    maxParkingDuration = maxDurationFormated;
                }

                if(carPosition.isChecked() && mjenjano) {

                    confirmPaymentDialog.initWithData(city, zone, price.getPriceFloat(), duration, maxParkingDuration,
                            parkingZoneId, paymentModeId, mapIdCity.get(city), favs.isChecked(),carPosition.isChecked(),myLocation.getLatitude(),myLocation.getLongitude());
                }
                else{
                    confirmPaymentDialog.initWithData(city, zone, price.getPriceFloat(), duration, maxParkingDuration,
                            parkingZoneId, paymentModeId, mapIdCity.get(city), favs.isChecked(), false, 0, 0);

                }

                if(carPosition.isChecked()){
                    PrefsHelper prefsHelper= new PrefsHelper(context);
                    prefsHelper.putString(PrefsHelper.carLat,String.valueOf(myLocation.getLatitude()));
                    prefsHelper.putString(PrefsHelper.carLng,String.valueOf(myLocation.getLongitude()));
                }

                confirmPaymentDialog.setConfirmPaymentDialogCallback(new ConfirmPaymentDialog.ConfirmPaymentDialogCallback() {
                    @Override
                    public void dismissDialog() {
                        payParkingDialogCallback.dismissConfirmDialog(confirmPaymentDialog);
                    }

                    @Override
                    public void dismissBothDialogs() {
                        payParkingDialogCallback.dismissConfirmDialog(confirmPaymentDialog);
                        payParkingDialogCallback.dismissDialog();
                    }
                });

                if(payParkingDialogCallback != null){
                    payParkingDialogCallback.showConfirmDialog(confirmPaymentDialog);
                }

            }
        });
    }

    public void showConfirmPaymentDialogForFavoritepayment(FavoritePayment favoritePayment){
        confirmPaymentDialog = new ConfirmPaymentDialog(context);
        DatabaseManager dbManager = DatabaseManager.getInstance();
        /*String city, String zone, float price, String duration, String maxDuration,
        int parkingZoneId, int paymentModeId, int cityId, boolean favs,boolean updateDb, double lat, double lng*/
        ParkingZone parkingZone = dbManager.getParkingZoneFromId(favoritePayment.getZoneID());
        String cityName = dbManager.getCityNameFromId(favoritePayment.getGradId());
        PaymentMode paymentMode = dbManager.getPaymentModeFromId(favoritePayment.getPaymentMethodId());
        ZonePrice zonePrice = dbManager.getPrice(new Date(), paymentMode.getId());

        MaxDuration maxDuration = databaseManager.getMaxDuration(new Date(), parkingZone.getId());
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String maxDurationFormated = formatter.format(maxDuration.getMaxDuration().getTime());

        String duration = paymentMode.getDuration().toString();
        duration = duration.split(" ")[3];

        String maxParkingDuration;
        if (maxDurationFormated.equals("00:00:00")) {
            maxParkingDuration = "Neograniceno";
        } else {
            maxParkingDuration = maxDurationFormated;
        }

        confirmPaymentDialog.initWithData(cityName, parkingZone.getName(), zonePrice.getPriceFloat(), duration,
                maxParkingDuration, parkingZone.getId(), paymentMode.getId(), favoritePayment.getGradId(), false, false, 0, 0);

        confirmPaymentDialog.setConfirmPaymentDialogCallback(new ConfirmPaymentDialog.ConfirmPaymentDialogCallback() {
            @Override
            public void dismissDialog() {
                payParkingDialogCallback.dismissConfirmDialog(confirmPaymentDialog);
            }

            @Override
            public void dismissBothDialogs() {
                payParkingDialogCallback.dismissConfirmDialog(confirmPaymentDialog);
                payParkingDialogCallback.dismissDialog();
            }
        });

        if(payParkingDialogCallback != null){
            Log.w("TAAG", "payParkingDialog");
            payParkingDialogCallback.showConfirmDialog(confirmPaymentDialog);
        }
        else{
            Log.w("TAAG", "payParkingDialog je null");
        }

    }

    public void findMyZoneIfPossible() {
        if (this.myLocation != null) {

            try {
                if(this.selectedCityPostCode!=null) {
                    GetRestService get = new GetRestService(ApiConstants.automaticZone + databaseManager.getCityFromPostCode(this.selectedCityPostCode).getId() + "/" + myLocation.getLatitude() + "/" + myLocation.getLongitude());
                    Gson gson = new Gson();
                    response = get.execute();
                    AutomaticZone automaticZone = gson.fromJson(response, AutomaticZone.class);
                    if (automaticZone.getId_zone() != null) {
                        ParkingZone automatic = databaseManager.getParkingZoneFromId(automaticZone.getId_zone());
                        zoneSpinner.setSelection(Arrays.asList(zoneNames).indexOf(automatic.getName()));
                    }
                }

            } catch (ExecutionException e) {
                Log.d("NO CONNECTION","INTERNET");
            } catch (InterruptedException e) {
                Log.d("INTERRUPTED","INTERNET");
            }
        }


    }

    public PayParkingDialogCallback getPayParkingDialogCallback() {
        return payParkingDialogCallback;
    }

    public void setPayParkingDialogCallback(PayParkingDialogCallback payParkingDialogCallback) {
        this.payParkingDialogCallback = payParkingDialogCallback;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
