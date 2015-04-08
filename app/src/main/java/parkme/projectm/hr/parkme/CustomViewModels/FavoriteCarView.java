package parkme.projectm.hr.parkme.CustomViewModels;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 8.4.2015..
 */
public class FavoriteCarView extends LinearLayout {

    private Context context;

    private ImageView favoriteCarImage;
    private TextView carTablesText;

    public FavoriteCarView(Context context) {
        super(context);
        init(context);
    }

    public FavoriteCarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FavoriteCarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(getContext(), R.layout.layout_favorite_car, this);

        this.favoriteCarImage = (ImageView) findViewById(R.id.imgViewFavCar);
        this.carTablesText = (TextView) findViewById(R.id.txtCarTables);
    }

    public ImageView getFavoriteCarImage() {
        return favoriteCarImage;
    }

    public void setCarTablesText(String carTablesText) {
        this.carTablesText.setText(carTablesText);
    }
}
