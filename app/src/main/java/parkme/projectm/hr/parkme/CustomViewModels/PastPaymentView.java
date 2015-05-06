package parkme.projectm.hr.parkme.CustomViewModels;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import parkme.projectm.hr.parkme.R;

public class PastPaymentView extends FrameLayout {

    private final String TAG = "PastPaymentView";
    private Context context;

    private ImageView pastPaymentCarIcon;
    private TextView pastPaymentCarTables;
    private TextView pastpaymentDate;

    public PastPaymentView(Context context) {
        super(context);
        init(context);
    }

    public PastPaymentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PastPaymentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        inflate(getContext(), R.layout.layout_past_payment, this);
        reference();
    }

    private void reference(){
        pastPaymentCarIcon = (ImageView) findViewById(R.id.imgViewPastPaymentCarIcon);
        pastPaymentCarTables = (TextView) findViewById(R.id.txtCarTables);
        pastpaymentDate = (TextView) findViewById(R.id.txtDateOfPayment);
    }

    public ImageView getFavoriteCarImage() {
        return pastPaymentCarIcon;
    }

    public void setCarTablesText(String carTablesText) {
        this.pastPaymentCarTables.setText(carTablesText);
    }

    public void setDateOfPaymentText(String date){
        this.pastpaymentDate.setText(date);
    }
}
