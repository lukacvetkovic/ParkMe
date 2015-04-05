package parkme.projectm.hr.parkme.CustomViewModels;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 5.4.2015..
 */
public class HeaderView extends RelativeLayout{

    private TextView parkyTitleText;
    private Context context;

    public HeaderView(Context context) {
        super(context);
        this.context = context;
        this.init();
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.init();
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.init();
    }

    private void init(){
        inflate(getContext(), R.layout.layout_custom_header, this);

        this.parkyTitleText = (TextView) findViewById(R.id.txtParkyTitle);
        this.parkyTitleText.setTypeface(ParkyFont.getInstance().getParkyTypeface(this.context));

    }

    public void setTitleText(String text) {
        this.parkyTitleText.setText(text);
    }
}
