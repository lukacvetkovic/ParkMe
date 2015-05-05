package parkme.projectm.hr.parkme.Dialogs;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 5.5.2015..
 */
public class ConfirmPaymentDialog extends FrameLayout {

    private final String TAG = "ConfirmPaymnetDialog";
    private Context context;

    private ConfirmPaymentDialogCallback confirmPaymentDialogCallback;


    public interface ConfirmPaymentDialogCallback{
        void dismissDialog();
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

    }


    public ConfirmPaymentDialogCallback getConfirmPaymentDialogCallback() {
        return confirmPaymentDialogCallback;
    }

    public void setConfirmPaymentDialogCallback(ConfirmPaymentDialogCallback confirmPaymentDialogCallback) {
        this.confirmPaymentDialogCallback = confirmPaymentDialogCallback;
    }
}
