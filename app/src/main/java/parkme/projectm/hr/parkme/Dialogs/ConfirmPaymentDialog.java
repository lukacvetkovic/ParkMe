package parkme.projectm.hr.parkme.Dialogs;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavoritePayment;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.R;
import parkme.projectm.hr.parkme.Receivers.IncomingSms;

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

                //SMSHelper.sendSMS(parkingZoneNumber, message);
                ParkingZone parkingZone = databaseManager.getParkingZoneFromId(parkingZoneId);

                parkingZoneNumber = parkingZone.getPhoneNumber();
                prefsHelper.putString(PrefsHelper.PhoneNumber,parkingZoneNumber);
                CharSequence text = "Broj : "+parkingZoneNumber+" Poruka:"+ message;
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                enableReciver();

                if(favs){
                    DatabaseManager.init(context);
                    DatabaseManager databaseManager = DatabaseManager.getInstance();
                    databaseManager.addFavoritePayment(new FavoritePayment(citiyId,parkingZoneId,paymentModeId));
                    Log.d("UPISANO U BAZU FAVS : ", "CityId :" + String.valueOf(citiyId) + " , " + " parkingZoneId :" + String.valueOf(parkingZoneId) + " paymentModeId :" + String.valueOf(paymentModeId));
                }

                if(confirmPaymentDialogCallback != null){
                    confirmPaymentDialogCallback.dismissBothDialogs();
                }
            }
        });
    }

    public void initWithData(String city, String zone, float price, String duration, String maxDuration,
                      int parkingZoneId, int paymentModeId, int cityId, boolean favs){
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

    public void enableReciver(){
        ComponentName receiver = new ComponentName(context, IncomingSms.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
}
