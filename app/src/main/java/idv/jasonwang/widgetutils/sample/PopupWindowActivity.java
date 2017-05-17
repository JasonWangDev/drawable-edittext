package idv.jasonwang.widgetutils.sample;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by jason on 2017/5/17.
 */

public class PopupWindowActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupwindow);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showPopWindow(v);
    }


    PopupWindow popupWindow;

    private void showPopWindow(View parent) {
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();

        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow, null);

        popupWindow = new PopupWindow(this);
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        Log.d("TAG", getPosition(parent, view).toString());

        popupWindow.showAtLocation(parent, Gravity.TOP|Gravity.LEFT, 37, 136);  // X,Y 是指 PopupWindow 的左上角位置
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

    private int getLeftOffsetX() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        switch (metrics.densityDpi)
        {
            default:
            case DisplayMetrics.DENSITY_MEDIUM:
                return 1;

            case DisplayMetrics.DENSITY_HIGH:
                return 3;

            case DisplayMetrics.DENSITY_XHIGH:
                return 4;

            case DisplayMetrics.DENSITY_XXHIGH:
                return 7;

            case DisplayMetrics.DENSITY_XXXHIGH:
                return 10;
        }
    }

    private int getCenterOffsetX() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        switch (metrics.densityDpi)
        {
            default:
            case DisplayMetrics.DENSITY_MEDIUM:
                return 0;

            case DisplayMetrics.DENSITY_HIGH:
                return 0;

            case DisplayMetrics.DENSITY_XHIGH:
                return 0;

            case DisplayMetrics.DENSITY_XXHIGH:
                return 0;

            case DisplayMetrics.DENSITY_XXXHIGH:
                return 0;
        }
    }









    // ************************************ 完成測試 ************************************ //

    public enum Position {
        TopCenter,
        BottomCenter,
        TopLeft,
        BottomLeft,
        TopRight,
        BottomRight
    }

    /**
     *
     * @param parent 父元件
     * @param content PopupWindow 內的 ContentView
     * @return
     */
    private Position getPosition(View parent, View content) {
        if (parent == null || content == null)
            return null;

        // 螢幕尺寸(px)
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        // 父元件所在區域(px)
        Rect parentRect = new Rect();
        parent.getGlobalVisibleRect(parentRect);

        // PopupWindow 尺寸(px)
        content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int contentWidth = content.getMeasuredWidth();
        int contentHeight = content.getMeasuredHeight();

        boolean isCenter, isBottom, isLeft;

        // 判斷是否能置中對齊
        // 父元件的中心點 X 絕對位置 + PopupWindow 元件寬度的一半必須小於螢幕的寬度，同時父元件的中
        // 心點 X 絕對位置 - PopupWindow 元件寬度的一半必須大於 0，則 PopupWindow 可以置中對齊父元件
        isCenter = (parentRect.centerX() + (contentWidth / 2) < screenWidth) && (parentRect.centerX() - (contentWidth / 2) > 0);

        // 判斷是否能靠底對齊
        // 父元件的底部絕對位置 + PopupWindow 元件的高度，如果小於螢幕高度，表示 PopupWindow 可以靠
        // 底對齊父元件
        isBottom = parentRect.bottom + contentHeight < screenHeight;

        // 判斷是否能靠左對齊
        // 父元件的左邊絕對位置 + PopupWindow 元件的寬度，如果小於螢幕寬度，表示 PopupWindow 可以靠
        // 左對齊父元件
        isLeft = parentRect.left + contentWidth < screenWidth;

        if (isCenter)
        {
            if (isBottom)
                return Position.BottomCenter;
            else
                return Position.TopCenter;
        }
        else
        {
            if (isLeft)
            {
                if (isBottom)
                    return Position.BottomLeft;
                else
                    return Position.TopLeft;
            }
            else
            {
                if (isBottom)
                    return Position.BottomRight;
                else
                    return Position.TopRight;
            }
        }
    }

}
