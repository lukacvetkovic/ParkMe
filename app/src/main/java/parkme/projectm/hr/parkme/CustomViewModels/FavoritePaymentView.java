package parkme.projectm.hr.parkme.CustomViewModels;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavoritePayment;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 7.5.2015..
 */
public class FavoritePaymentView extends FrameLayout {

    private final String TAG = "FavoritePaymentView";
    private Context context;

    private TextView favoritePaymentCity;
    private TextView favoritePaymentZone;
    private ImageView favoritePaymentDeleteButton;

    public FavoritePaymentView(Context context) {
        super(context);
        init(context);
    }

    public FavoritePaymentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FavoritePaymentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        inflate(getContext(), R.layout.layout_favorite_payment, this);
        reference();
    }

    private void reference(){
        favoritePaymentCity = (TextView) findViewById(R.id.txtFavPaymentCity);
        favoritePaymentZone = (TextView) findViewById(R.id.txtZone);
        favoritePaymentDeleteButton = (ImageView) findViewById(R.id.imgDelete);
    }

    public void setCarTablesText(String favCity) {
        this.favoritePaymentCity.setText(favCity);
    }

    public void setPaymentZoneText(String zone){
        this.favoritePaymentZone.setText(zone);
    }
}
