package parkme.projectm.hr.parkme.Dialogs;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 8.4.2015..
 */
public class UpdateOrRemoveCarDialog extends FrameLayout {

    private final String TAG = "UpdateOrRemoveCarDIalog";
    private Context context;

    private boolean isDialogActive = false;

    private EditText carPlatesEditText;
    private TextView errorTextView;

    private Button updateCarButton;
    private Button deleteCar;

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

    private UpdateOrRemoveCarCallback dismissCallback;

    private FavouriteCar favoriteCarInProcess;

    private DatabaseManager dbManager;

    private enum SelectedCar {
        BLUE,
        RED,
        GREEN,
        YELLOW
    }

    public interface UpdateOrRemoveCarCallback {
        void dismissThisDialog();
    }

    public UpdateOrRemoveCarDialog(Context context) {
        super(context);
        init(context);
    }

    public UpdateOrRemoveCarDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UpdateOrRemoveCarDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        dbManager = DatabaseManager.getInstance();
        prefsHelper = new PrefsHelper(this.context);
        inflate(getContext(), R.layout.dialog_update_or_remove_car, this);
        reference();
    }

    public void setDismissCallback(UpdateOrRemoveCarCallback dismissCallback) {
        this.dismissCallback = dismissCallback;
    }

    public void setFavoriteCarToUpdate(FavouriteCar favoriteCar){
        this.favoriteCarInProcess = favoriteCar;
        hideCheckedIcons();
        carPlatesEditText.setText(this.favoriteCarInProcess.getCarRegistration());
        int carResId = this.favoriteCarInProcess.getCarIcon();
        switch (carResId){
            case R.drawable.car_icon_blue_s:
                selectedCar = SelectedCar.BLUE;
                selectedBlueCar.setVisibility(VISIBLE);
                break;
            case R.drawable.car_icon_green_s:
                selectedCar = SelectedCar.GREEN;
                selectedGreenCar.setVisibility(VISIBLE);
                break;
            case R.drawable.car_icon_red_s:
                selectedCar = SelectedCar.RED;
                selectedRedCar.setVisibility(VISIBLE);
                break;
            case R.drawable.car_icon_orange_s:
                selectedCar = SelectedCar.YELLOW;
                selectedYellowCar.setVisibility(VISIBLE);
                break;
        }
    }

    private void reference(){
        carPlatesEditText = (EditText) findViewById(R.id.edTxtCarPlate);
        carPlatesEditText.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        errorTextView = (TextView) findViewById(R.id.txtError);
        errorTextView.setVisibility(INVISIBLE);
        updateCarButton = (Button) findViewById(R.id.btnUpdateCar);
        updateCarButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String carPlates = carPlatesEditText.getText().toString();
                if (carPlates.isEmpty()) {
                    errorTextView.setText("Please enter car plates");
                    errorTextView.setVisibility(VISIBLE);
                } else {
                    // Setting up active
                    prefsHelper.putString(PrefsHelper.ActiveCarPlates, carPlatesEditText.getText().toString());

                    //Saving active to the database
                    int carIconResId;
                    switch (selectedCar) {
                        case BLUE:
                            carIconResId = R.drawable.car_icon_blue_s;
                            break;
                        case GREEN:
                            carIconResId = R.drawable.car_icon_green_s;
                            break;
                        case RED:
                            carIconResId = R.drawable.car_icon_red_s;
                            break;
                        case YELLOW:
                            carIconResId = R.drawable.car_icon_orange_s;
                            break;
                        default:
                            carIconResId = R.drawable.car_icon_green_s;
                    }
                    dbManager.removeFavouriteCar(favoriteCarInProcess);
                    dbManager.addFavouriteCar(new FavouriteCar(carPlates, carIconResId));
                    if (dismissCallback != null) {
                        dismissCallback.dismissThisDialog();
                    }
                }
            }
        });

        deleteCar = (Button) findViewById(R.id.btnDeleteCar);
        deleteCar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String removingFavCarplates = favoriteCarInProcess.getCarRegistration();

                Log.w("ERR", "" + removingFavCarplates);

                if(removingFavCarplates.equals(prefsHelper.getString(PrefsHelper.ActiveCarPlates, null))){
                    Log.w(TAG, "Active car has been removed !");
                    prefsHelper.putString(PrefsHelper.ActiveCarPlates, null);
                }

                dbManager.removeFavouriteCar(favoriteCarInProcess);
                if(dismissCallback != null) {
                    dismissCallback.dismissThisDialog();
                }
            }
        });

        selectedBlueCar = (ImageView) findViewById(R.id.imgViewCheckedBlueCar);
        selectedGreenCar = (ImageView) findViewById(R.id.imgViewCheckedGreenCar);
        selectedRedCar = (ImageView) findViewById(R.id.imgViewCheckedRedCar);
        selectedYellowCar = (ImageView) findViewById(R.id.imgViewCheckedYellowCar);
        hideCheckedIcons();

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

    public boolean isDialogActive() {
        return isDialogActive;
    }

    public void setDialogActive(boolean isDialogActive) {
        this.isDialogActive = isDialogActive;
    }

    public void disableDeleteButton(boolean disable){
        if(disable){
            deleteCar.setVisibility(INVISIBLE);
        }
        else{
            deleteCar.setVisibility(VISIBLE);
        }
    }
}
