package idv.jasonwang.widgetutils.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by jason on 2017/5/17.
 */

public class PopupWindowActivity extends AppCompatActivity implements View.OnClickListener {

    TestPopupWindow window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupwindow);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);

        window = new TestPopupWindow(this);
    }

    @Override
    public void onClick(View v) {
        window.show(v);
    }




    private int getRightOffsetX() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        switch (metrics.densityDpi)
        {
            default:
            case DisplayMetrics.DENSITY_MEDIUM:
                return 10;

            case DisplayMetrics.DENSITY_HIGH:
                return 12;

            case DisplayMetrics.DENSITY_XHIGH:
                return 14;

            case DisplayMetrics.DENSITY_XXHIGH:
                return 23;

            case DisplayMetrics.DENSITY_XXXHIGH:
                return 30;
        }
    }

}
