package idv.jasonwang.widgetutils.sample;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by jason on 2017/5/18.
 */
public abstract class PopupWindowTemplate {

    protected abstract View createView(LayoutInflater inflater);


    private Activity activity;
    private LayoutInflater inflater;

    private View contentView;
    private PopupWindow popupWindow;


    public PopupWindowTemplate(Activity activity) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }


    public void show(View parent) {
        show(parent, 0, 0);
    }

    public void show(View parent, int offsetX, int offsetY) {
        buildContentView();
        buildPopupWindow();

        Position position = getPosition(activity, parent, contentView);
        Point point = getPoint(position, parent, contentView);

        popupWindow.showAtLocation(parent, Gravity.TOP|Gravity.LEFT, point.x + offsetX, point.y + offsetY);
    }


    private void buildContentView() {
        if (contentView == null)
            contentView = createView(inflater);
    }

    private void buildPopupWindow() {
        if (popupWindow == null)
        {
            popupWindow = new PopupWindow(activity);
            popupWindow.setContentView(contentView);
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
        }
    }


    private enum Position {
        TopCenter,
        BottomCenter,
        TopLeft,
        BottomLeft,
        TopRight,
        BottomRight
    }


    /**
     *
     * @param activity 傳入啟動 PopupWindow 的 Activity
     * @param parent 傳入 PopupWindow 依附的父元件
     * @param content 傳入 PopupWindow 內的 ContentView
     * @return
     */
    private Position getPosition(Activity activity, View parent, View content) {
        if (activity == null || parent == null || content == null)
            return Position.BottomCenter;

        // 螢幕尺寸(px)
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
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

    /**
     *
     * @param position 傳入 PopupWindow 顯示的位置
     * @param parent 傳入 PopupWindow 要依附要依附的父元件
     * @param content 傳入 PopupWindow 內的 ContentView
     * @return
     */
    private Point getPoint(Position position, View parent, View content) {
        if (position == null || parent == null || content == null)
            return new Point(0, 0);

        Rect parentRect = new Rect();
        parent.getGlobalVisibleRect(parentRect);

        content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int contentWidth = content.getMeasuredWidth();
        int contentHeight = content.getMeasuredHeight();

        switch (position)
        {
            default:
            case TopCenter:
                return new Point(parentRect.centerX() - contentWidth / 2,
                                    parentRect.top - contentHeight);

            case BottomCenter:
                return new Point(parentRect.centerX() - contentWidth / 2,
                                    parentRect.bottom);

            case TopLeft:
                return new Point(parentRect.left,
                                    parentRect.top - contentHeight);

            case BottomLeft:
                return new Point(parentRect.left,
                                    parentRect.bottom);

            case TopRight:
                return new Point(parentRect.right - contentWidth,
                                    parentRect.top - contentHeight);

            case BottomRight:
                return new Point(parentRect.right - contentWidth,
                                    parentRect.bottom);
        }
    }

}
