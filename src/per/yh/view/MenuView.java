package per.yh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by MoonLeo on 2015/12/25.
 */
public class MenuView extends HorizontalScrollView {
    private static final String TAG = MenuView.class.getSimpleName();

    private LinearLayout wrapper;
    private LinearLayout menu;
    private LinearLayout content;

    private int menuWidth, screenWidth;
    private int menuRightPadding;

    private int menuRightPaddingDIP = 50;

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        //dip转pix
        menuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, menuRightPaddingDIP, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        wrapper = (LinearLayout) this.getChildAt(0);
        menu = (LinearLayout)wrapper.getChildAt(0);
        content = (LinearLayout)wrapper.getChildAt(1);
        menuWidth = menu.getLayoutParams().width = screenWidth - menuRightPadding;
        content.getLayoutParams().width = screenWidth;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //必须放在super.onLayout()之后
        if(changed)
            scrollTo(menuWidth, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                Log.d(TAG, "scrollX:"+scrollX);
                if(scrollX > (menuWidth >> 1)) {
                    smoothScrollTo(menuWidth, 0);
                } else {
                    smoothScrollTo(0 ,0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }
}

