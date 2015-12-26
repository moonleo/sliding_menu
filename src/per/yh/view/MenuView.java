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
import com.nineoldandroids.view.ViewHelper;

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

    boolean open;

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
                    open();
                } else {
                    close();
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f /menuWidth;
        //实现抽屉式侧滑
        ViewHelper.setTranslationX(menu, menuWidth * scale * 0.7f);
        //实现内容区域的缩放和位移
        float contentScale = 0.6f + 0.4f*scale;
        ViewHelper.setPivotX(content, 0);
        ViewHelper.setPivotY(content, content.getHeight()>>1);
        ViewHelper.setScaleX(content, contentScale);
        ViewHelper.setScaleY(content, contentScale);
        //实现菜单的缩放
        float menuScale = 1.0f - 0.4f*scale;
        ViewHelper.setScaleX(menu, menuScale);
        ViewHelper.setScaleY(menu, menuScale);
        //菜单透明度
        float menuAlpha = 1.0f - 0.4f*scale;
        ViewHelper.setAlpha(menu, menuAlpha);
    }

    /**
     * 判断菜单是否打开
     * @return
     */
    public boolean isOpen() {
        return open;
    }

    public void open() {
        smoothScrollTo(menuWidth, 0);
        open = true;
    }

    public void close() {
        smoothScrollTo(0 ,0);
        open = false;
    }
}

