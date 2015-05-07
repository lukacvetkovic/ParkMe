package parkme.projectm.hr.parkme.CustomViewModels;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 6.5.2015..
 */
public class ActiveTicketView extends FrameLayout {

    private final String TAG = "ActiveTicektView";
    private Context context;

    private ImageView activeTicketCarImage;
    private TextView activeTicketLicencePlatesText;
    private TextView activeTicketRemainingTime;

    private TextView doIsteka;
    private TextView txtMin;

    private LinearLayout lowerTextLinearLayout;

    public ActiveTicketView(Context context) {
        super(context);
        init(context);
    }

    public ActiveTicketView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ActiveTicketView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        inflate(getContext(), R.layout.layout_active_parking_ticket, this);
        referene();
    }

    private void referene(){
        activeTicketCarImage = (ImageView) findViewById(R.id.imgActiveTicketCar);
        activeTicketLicencePlatesText = (TextView) findViewById(R.id.txtActiveTicketCarTables);
        activeTicketRemainingTime = (TextView) findViewById(R.id.txtActiveTicketRemainingTimeMinutes);
        lowerTextLinearLayout = (LinearLayout) findViewById(R.id.lowText);

        doIsteka = (TextView) findViewById(R.id.txtActiveTicketRemainingTime);
        txtMin = (TextView) findViewById(R.id.txtActiveTicketRemainingTimeMins);

        //lowerTextLinearLayout.setVisibility(INVISIBLE);
    }

    public ImageView getActiveCarImage() {
        return activeTicketCarImage;
    }

    public void setCarTablesText(String carTablesText) {
        this.activeTicketLicencePlatesText.setText(carTablesText);
    }

    public String getCarTablesText() {
        return this.activeTicketLicencePlatesText.getText().toString();
    }

    public void showRemainingTime(boolean show){
        if (show){
            lowerTextLinearLayout.setVisibility(VISIBLE);
        }
        else{
            lowerTextLinearLayout.setVisibility(INVISIBLE);
        }
    }

    public void setRemainingTime(long time){
        activeTicketRemainingTime.setText(""+time);
    }

    public void hideStuff(){
        activeTicketCarImage.setVisibility(INVISIBLE);
        activeTicketLicencePlatesText.setVisibility(INVISIBLE);
        activeTicketRemainingTime.setVisibility(INVISIBLE);
        doIsteka.setVisibility(INVISIBLE);
        txtMin.setVisibility(INVISIBLE);
    }

    public void showStuff(){
        activeTicketCarImage.setVisibility(VISIBLE);
        activeTicketLicencePlatesText.setVisibility(VISIBLE);
        activeTicketRemainingTime.setVisibility(VISIBLE);
        doIsteka.setVisibility(VISIBLE);
        txtMin.setVisibility(VISIBLE);
    }
}
