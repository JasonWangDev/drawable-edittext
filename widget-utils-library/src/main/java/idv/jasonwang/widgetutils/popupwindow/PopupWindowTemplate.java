package idv.jasonwang.widgetutils.popupwindow;

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

import static idv.jasonwang.widgetutils.popupwindow.PopupWindowTemplate.Position.BottomCenter;
import static idv.jasonwang.widgetutils.popupwindow.PopupWindowTemplate.Position.BottomLeft;
import static idv.jasonwang.widgetutils.popupwindow.PopupWindowTemplate.Position.BottomRight;
import static idv.jasonwang.widgetutils.popupwindow.PopupWindowTemplate.Position.TopCenter;
import static idv.jasonwang.widgetutils.popupwindow.PopupWindowTemplate.Position.TopLeft;
import static idv.jasonwang.widgetutils.popupwindow.PopupWindowTemplate.Position.TopRight;

/**
 * Created by jason on 2017/5/18.
 */
public abstract class PopupWindowTemplate {

    protected abstract View createView(LayoutInflater inflater);
    protected abstract Offset setOffset(Position position, Density density);


    protected enum Position {
        TopCenter,
        BottomCenter,
        TopLeft,
        BottomLeft,
        TopRight,
        BottomRight
    }

    protected enum Density {
        Medium,
        High,
        XHigh,
        XXHigh,
        XXXHigh
    }


    protected class Offset {
        int x, y;


        public Offset() { }

        public Offset(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }


    private Activity activity;
    private LayoutInflater inflater;

    private DisplayMetrics metrics;

    private View contentView;
    private PopupWindow popupWindow;


    public PopupWindowTemplate(Activity activity) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);

        metrics = new DisplayMetrics();
    }


    public void show(View parent) {
        buildContentView();
        buildPopupWindow();

        Position position = getPosition(activity, parent, contentView);
        Point location = getLocation(position, parent, contentView);
        Offset offset = getOffset(position);

        popupWindow.showAtLocation(parent,
                                    Gravity.TOP|Gravity.LEFT,
                                    location.x + (offset != null ? offset.getX() : 0),
                                    location.y + (offset != null ? offset.getY() : 0));
    }

    public void dissmiss() {
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
    }

    public Activity getActivity() {
        return activity;
    }


    private Offset getOffset(Position position) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        switch (metrics.densityDpi)
        {
            default:
            case DisplayMetrics.DENSITY_MEDIUM:
                return setOffset(position, Density.Medium);

            case DisplayMetrics.DENSITY_HIGH:
                return setOffset(position, Density.High);

            case DisplayMetrics.DENSITY_XHIGH:
                return setOffset(position, Density.XHigh);

            case DisplayMetrics.DENSITY_XXHIGH:
                return setOffset(position, Density.XXHigh);

            case DisplayMetrics.DENSITY_XXXHIGH:
                return setOffset(position, Density.XXXHigh);
        }
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

    /**
     *
     * @param activity 傳入啟動 PopupWindow 的 Activity
     * @param parent 傳入 PopupWindow 依附的父元件
     * @param content 傳入 PopupWindow 內的 ContentView
     * @return
     */
    private Position getPosition(Activity activity, View parent, View content) {
        if (activity == null || parent == null || content == null)
            return BottomCenter;

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
                return BottomCenter;
            else
                return TopCenter;
        }
        else
        {
            if (isLeft)
            {
                if (isBottom)
                    return BottomLeft;
                else
                    return TopLeft;
            }
            else
            {
                if (isBottom)
                    return BottomRight;
                else
                    return TopRight;
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
    private Point getLocation(Position position, View parent, View content) {
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
