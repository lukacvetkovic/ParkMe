package parkme.projectm.hr.parkme.CustomViewModels;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

/**
 * Created by Mihael on 25.4.2015..
 */
@RemoteViews.RemoteView
public class CustomHeaderLayout extends ViewGroup {

    private final Rect tempChildRect = new Rect();

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
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {     // TODO
        final int childCount = getChildCount();
        final int leftPos = getPaddingLeft();
        final int rightPos = right - left - getPaddingRight();
        final int topPos = getPaddingTop();
        final int bottomPos = bottom - top - getPaddingBottom();

        for(int i=0; i<childCount; i++){
            final View child = getChildAt(i);
            if(child.getVisibility() != GONE) {
                if(i == 0){
                    tempChildRect.left = leftPos;
                    tempChildRect.bottom = bottomPos;
                    tempChildRect.top = topPos;
                    tempChildRect.right = leftPos + ( bottomPos - topPos );
                    child.layout(tempChildRect.left, tempChildRect.top, tempChildRect.right, tempChildRect.bottom);
                }
                else{
                    tempChildRect.left = leftPos + ( bottomPos - topPos );
                    tempChildRect.bottom = bottomPos;
                    tempChildRect.top = topPos;
                    tempChildRect.right = rightPos;
                    child.layout(tempChildRect.left, tempChildRect.top, tempChildRect.right, tempChildRect.bottom);
                }
            }

        }
    }


    @Override
    public boolean shouldDelayChildPressedState() {
        return false;       // Makes view unscrollable
    }
}
