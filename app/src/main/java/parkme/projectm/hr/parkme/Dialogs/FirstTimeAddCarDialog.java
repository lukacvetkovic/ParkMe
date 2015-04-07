package parkme.projectm.hr.parkme.Dialogs;

import android.app.DialogFragment;
import android.content.Context;
import android.text.InputFilter;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 7.4.2015..
 */
public class FirstTimeAddCarDialog extends FrameLayout{

    private final String TAG = "FirstTimeAddCarDialog";
    private Context context;

    private EditText carPlatesEditText;
    private TextView errorTextView;

    private Button saveCarButton;

    private ImageButton blueCarImageButton;
    private ImageView selectedBlueCar;
    private ImageButton greenCarImageButton;
    private ImageView selectedGreenCar;
    private ImageButton redCarImageButton;
    private ImageView selectedRedCar;
    private ImageButton yellowCarImageButton;
    private ImageView selectedYellowCar;

    private SelectedCar selectedCar = SelectedCar.BLUE;

    private PrefsHelper prefsHelper;

    private FirstTimeAddCarCallback dismissCallback;

    private enum SelectedCar {
        BLUE,
        RED,
        GREEN,
        YELLOW
    }

    public interface FirstTimeAddCarCallback {
        void dismissThisDialog();
    }

    public FirstTimeAddCarDialog(Context context) {
        super(context);
        init(context);
    }

    public FirstTimeAddCarDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FirstTimeAddCarDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(getContext(), R.layout.dialog_add_first_car, this);
        reference();
    }

    public void setDismissCallback(FirstTimeAddCarCallback dismissCallback) {
        this.dismissCallback = dismissCallback;
    }

    private void reference(){
        carPlatesEditText = (EditText) findViewById(R.id.edTxtCarPlate);
        carPlatesEditText.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        errorTextView = (TextView) findViewById(R.id.txtError);
        errorTextView.setVisibility(INVISIBLE);
        saveCarButton = (Button) findViewById(R.id.btnSaveCar);
        saveCarButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String carPlates = carPlatesEditText.getText().toString();
                if(carPlates.isEmpty()){
                    errorTextView.setText("Please enter car plates");
                    errorTextView.setVisibility(VISIBLE);
                }
                else {
                    prefsHelper = new PrefsHelper(context);
                    prefsHelper.putString(PrefsHelper.ActiveCarPlates, carPlatesEditText.getText().toString());
                    if (dismissCallback != null) {
                        dismissCallback.dismissThisDialog();
                    }
                }
            }
        });

        selectedBlueCar = (ImageView) findViewById(R.id.imgViewCheckedBlueCar);
        selectedGreenCar = (ImageView) findViewById(R.id.imgViewCheckedGreenCar);
        selectedRedCar = (ImageView) findViewById(R.id.imgViewCheckedRedCar);
        selectedYellowCar = (ImageView) findViewById(R.id.imgViewCheckedYellowCar);
        hideCheckedIcons();
        selectedBlueCar.setVisibility(VISIBLE);

        blueCarImageButton = (ImageButton) findViewById(R.id.imgBtnBlueCar);
        blueCarImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCar = SelectedCar.BLUE;
                hideCheckedIcons();
                selectedBlueCar.setVisibility(VISIBLE);
            }
        });
        greenCarImageButton = (ImageButton) findViewById(R.id.imgBtnGreenCar);
        greenCarImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCar = SelectedCar.GREEN;
                hideCheckedIcons();
                selectedGreenCar.setVisibility(VISIBLE);
            }
        });
        redCarImageButton = (ImageButton) findViewById(R.id.imgBtnRedCar);
        redCarImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCar = SelectedCar.RED;
                hideCheckedIcons();
                selectedRedCar.setVisibility(VISIBLE);
            }
        });
        yellowCarImageButton = (ImageButton) findViewById(R.id.imgBtnYellowCar);
        yellowCarImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCar = SelectedCar.YELLOW;
                hideCheckedIcons();
                selectedYellowCar.setVisibility(VISIBLE);
            }
        });
    }

    private void hideCheckedIcons(){
        selectedBlueCar.setVisibility(INVISIBLE);
        selectedGreenCar.setVisibility(INVISIBLE);
        selectedRedCar.setVisibility(INVISIBLE);
        selectedYellowCar.setVisibility(INVISIBLE);
    }
}
