package parkme.projectm.hr.parkme.CustomViewModels;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 25.4.2015..
 */
@RemoteViews.RemoteView
public class CustomHeaderLayout extends ViewGroup {

    private final Rect tempChildRect = new Rect();
    private final Rect tempLayoutRect = new Rect();

    public CustomHeaderLayout(Context context) {
        super(context);
    }

    public CustomHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (measuredHeight == 0 && measuredWidth == 0) { //Height and width set to wrap_content
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (measuredHeight == 0) { //Height set to wrap_content
            int width = measuredWidth;
            int height = width *  2;
        } else if (measuredWidth == 0) { //Width set to wrap_content
            int height = measuredHeight;
            int width = height * 2;
        }

        for(int i=0; i<getChildCount(); i++){
            View v = getChildAt(i);
            v.measure(measuredWidth, measuredHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {     // TODO
        final int childCount = getChildCount();
        final int leftPos = getPaddingLeft();
        final int rightPos = right - left - getPaddingRight();
        final int topPos = getPaddingTop();
        final int bottomPos = bottom - top - getPaddingBottom();

        tempLayoutRect.left = leftPos;
        tempLayoutRect.bottom = bottomPos;
        tempLayoutRect.top = topPos;
        tempLayoutRect.right = rightPos;

        for(int i=0; i<childCount; i++){
            final View child = getChildAt(i);
            if(child.getVisibility() != GONE) {
                final MyLayoutParams lp = new MyLayoutParams(child.getLayoutParams());

                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();

                if(i == 0){
                    tempChildRect.left = leftPos;
                    tempChildRect.bottom = bottomPos;
                    tempChildRect.top = topPos;
                    tempChildRect.right = leftPos + ( bottomPos - topPos );
                }
                else {
                    tempLayoutRect.left = leftPos + (bottomPos - topPos);
                    tempLayoutRect.bottom = bottomPos;
                    tempLayoutRect.top = topPos;
                    tempLayoutRect.right = rightPos;
                    lp.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
                    Gravity.apply(lp.gravity, width, height, tempLayoutRect, tempChildRect);

                    ((TextView)child).setTypeface(ParkyFont.getInstance().getParkyTypeface(getContext()));
                }

                child.layout(tempChildRect.left, tempChildRect.top, tempChildRect.right, tempChildRect.bottom);
            }

        }
    }


    @Override
    public boolean shouldDelayChildPressedState() {
        return false;       // Makes view unscrollable
    }

    public static class MyLayoutParams extends MarginLayoutParams {
        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         */
        public int gravity = Gravity.TOP | Gravity.START;

        public static int POSITION_MIDDLE = 0;
        public static int POSITION_LEFT = 1;
        public static int POSITION_RIGHT = 2;

        public int position = POSITION_MIDDLE;

        public MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public MyLayoutParams(int width, int height) {
            super(width, height);
        }

        public MyLayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
