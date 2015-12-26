package per.yh.sliding_menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import per.yh.view.MenuView;

public class MyActivity extends Activity implements View.OnClickListener{

    private MenuView slidingMenu;
    private Button btn;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        slidingMenu = (MenuView) findViewById(R.id.slidingmenu);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(slidingMenu.isOpen()) {
            slidingMenu.close();
        } else {
            slidingMenu.open();
        }
    }
}
