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
public class ActiveCarView extends LinearLayout {

    private Context context;

    private ImageView activeCarImage;
    private TextView carTablesText;

    public ActiveCarView(Context context) {
        super(context);
        init(context);
    }

    public ActiveCarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ActiveCarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        inflate(getContext(), R.layout.layout_active_car, this);
        this.activeCarImage = (ImageView) findViewById(R.id.imgViewActiveCar);
        this.carTablesText = (TextView) findViewById(R.id.txtActiveCarTables);
    }

    public ImageView getActiveCarImage() {
        return activeCarImage;
    }

    public void setCarTablesText(String carTablesText) {
        this.carTablesText.setText(carTablesText);
    }

    public String getCarTablesText() {
        return this.carTablesText.getText().toString();
    }

}
