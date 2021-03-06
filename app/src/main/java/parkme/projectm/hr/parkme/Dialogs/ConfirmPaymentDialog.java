package parkme.projectm.hr.parkme.Dialogs;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavoritePayment;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PastParkingPayment;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.Helpers.Rest.ApiConstants;
import parkme.projectm.hr.parkme.Helpers.Rest.PostRestService;
import parkme.projectm.hr.parkme.Helpers.SMSHelper;
import parkme.projectm.hr.parkme.R;
import parkme.projectm.hr.parkme.Receivers.IncomingSmsReceiver;
import parkme.projectm.hr.parkme.Services.ParkingServiceHelper;

/**
 * Created by Mihael on 5.5.2015..
 */
public class ConfirmPaymentDialog extends FrameLayout {

    private final String TAG = "ConfirmPaymnetDialog";
    private Context context;

    TextView tvTime;
    TextView tvCity;
    TextView tvParkingZone;
    TextView tvPrice;
    TextView tvDuration;
    TextView tvMaxDuration;
    TextView tvCarPlate;

    String city;
    String parkingZone;
    float price;
    String duration;
    String maxDuarton;
    String carPlate;
    String parkingZoneNumber;
    int parkingZoneId;
    int paymentModeId;
    int citiyId;
    boolean favs;
    boolean updateDb;
    double lat;
    double lng;

    private ImageButton cancelPaymentButton;
    private ImageButton confirmpaymentButton;

    DatabaseManager databaseManager;

    PrefsHelper prefsHelper;

    private ConfirmPaymentDialogCallback confirmPaymentDialogCallback;


    public interface ConfirmPaymentDialogCallback{
        void dismissDialog();
        void dismissBothDialogs();
    }

    public ConfirmPaymentDialog(Context context) {
        super(context);
        init(context);
    }

    public ConfirmPaymentDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ConfirmPaymentDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        inflate(getContext(), R.layout.dialog_confirm_payment_new, this);
        reference();
    }

    private void reference(){
        DatabaseManager.init(context);
        databaseManager= DatabaseManager.getInstance();
        prefsHelper= new PrefsHelper(context);

        tvTime=(TextView)findViewById(R.id.tvTime);
        tvCity=(TextView)findViewById(R.id.tvCity);
        tvParkingZone=(TextView)findViewById(R.id.tvParkingZone);
        tvPrice=(TextView)findViewById(R.id.tvPrice);
        tvDuration=(TextView)findViewById(R.id.tvDuration);
        tvMaxDuration=(TextView)findViewById(R.id.tvMaxDuration);
        tvCarPlate=(TextView)findViewById(R.id.tvCarPlate);

        cancelPaymentButton = (ImageButton) findViewById(R.id.btnCancelPayment);
        confirmpaymentButton = (ImageButton) findViewById(R.id.btnPayParking);

        cancelPaymentButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirmPaymentDialogCallback != null){
                    confirmPaymentDialogCallback.dismissDialog();
                }
            }
        });

        confirmpaymentButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                prefsHelper.putInt(PrefsHelper.citiyId, citiyId);
                prefsHelper.putInt(PrefsHelper.parkingZoneId, parkingZoneId);
                prefsHelper.putInt(PrefsHelper.paymentModeId, paymentModeId);
                prefsHelper.putString(PrefsHelper.trajanje, duration);

                PaymentMode paymentMode = databaseManager.getPaymentModeFromId(paymentModeId);

                String message = paymentMode.getSmsPrefix()+carPlate+paymentMode.getSmsSufix();

                ParkingServiceHelper parkingServiceHelper= new ParkingServiceHelper();
                parkingServiceHelper.startService(context);


                ParkingZone parkingZone = databaseManager.getParkingZoneFromId(parkingZoneId);

                parkingZoneNumber = parkingZone.getPhoneNumber();
                prefsHelper.putString(PrefsHelper.PhoneNumber,parkingZoneNumber);
                SMSHelper.sendSMS(parkingZoneNumber, message);



               /* // TODO _ MOCKANJE UPISA U BAZU KOJI SE DOVIJA U RECEIVERU ZAPRAVO

                DatabaseManager.init(context);
                DatabaseManager databaseManager = DatabaseManager.getInstance();

                FavouriteCar favouriteCar = databaseManager.getFavoriteCarFromPlates(carPlate);
                DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM");
                Date date = new Date();

                PastParkingPayment pastParkingPayment = new PastParkingPayment(carPlate, favouriteCar.getCarIcon(), dateFormat.format(date),citiyId,0,paymentModeId,dateFormat.format(date),parkingZoneId);
                databaseManager.addPastparkingPayment(pastParkingPayment);

                // TODO _ MOCKANJE UPISA U BAZU KOJI SE DOVIJA U RECEIVERU ZAPRAVO - maknut ovo
                */

                if(favs){
                    boolean contains = false;
                    FavoritePayment favPaymentToAdd = new FavoritePayment(citiyId,parkingZoneId,paymentModeId);
                    // Provjera da u favsima vec nemamo ovaj koji zelimo dodat
                    List<FavoritePayment> favoritePayments = databaseManager.getAllFavoritePayments();
                    for(FavoritePayment favoritePayment : favoritePayments){
                        if(favPaymentToAdd.getGradId() == favoritePayment.getGradId() &&
                                favPaymentToAdd.getZoneID() == favoritePayment.getZoneID()){
                            contains = true;
                            break;
                        }
                    }
                    if(!contains){
                        databaseManager.addFavoritePayment(favPaymentToAdd);
                        Log.d("UPISANO U BAZU FAVS : ", "CityId :" + String.valueOf(citiyId) + " , " + " parkingZoneId :"
                                + String.valueOf(parkingZoneId) + " paymentModeId :" + String.valueOf(paymentModeId));
                    }
                }

                if(updateDb){

                    final MyMarker myMarker = new MyMarker(parkingZoneId,lat,lng);
                    final Gson gson = new Gson();

                    Thread thread= new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("POSTANJE","MARKERA");
                            PostRestService postRestService= new PostRestService(ApiConstants.postNewMarker, gson.toJson(myMarker));
                            String response="";
                            try {
                                response=postRestService.execute();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Log.d("POSTANJE RESPONSE",response);
                        }
                    });

                    thread.start();

                }

                if(confirmPaymentDialogCallback != null){
                    confirmPaymentDialogCallback.dismissBothDialogs();
                }
            }
        });
    }

    public void initWithData(String city, String zone, float price, String duration, String maxDuration,
                      int parkingZoneId, int paymentModeId, int cityId, boolean favs,boolean updateDb, double lat, double lng){
        carPlate = prefsHelper.getString(PrefsHelper.ActiveCarPlates,"NULL");
        this.city = city;
        this.parkingZone = zone;
        this.price = price;
        this.duration = duration;
        this.maxDuarton = maxDuration;
        this.parkingZoneId = parkingZoneId;
        this.paymentModeId = paymentModeId;
        this.citiyId = cityId;
        this.favs = favs;
        this.updateDb=updateDb;
        this.lat=lat;
        this.lng=lng;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        String formatted = format1.format(cal.getTime());
        tvTime.setText(formatted);
        tvCity.setText(city);
        tvParkingZone.setText(parkingZone);
        tvPrice.setText(String.valueOf(price)+ " kn");
        tvDuration.setText(duration + " sati");
        tvMaxDuration.setText(maxDuarton + " sati");
        tvCarPlate.setText(carPlate);
    }


    public ConfirmPaymentDialogCallback getConfirmPaymentDialogCallback() {
        return confirmPaymentDialogCallback;
    }

    public void setConfirmPaymentDialogCallback(ConfirmPaymentDialogCallback confirmPaymentDialogCallback) {
        this.confirmPaymentDialogCallback = confirmPaymentDialogCallback;
    }

    private class MyMarker
    {

        private Integer id_zone;

        private double lng;

        private double lat;


        private MyMarker( Integer id_zone, double lat, double lng) {

            this.id_zone = id_zone;
            this.lng = lng;
            this.lat = lat;
        }

        @Override
        public String toString()
        {
            return "Marker [id_zone = "+id_zone+", lng = "+lng+", lat = "+lat+"]";
        }
    }


}
