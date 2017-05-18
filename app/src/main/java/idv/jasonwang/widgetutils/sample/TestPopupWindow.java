package idv.jasonwang.widgetutils.sample;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by jason on 2017/5/18.
 */

public class TestPopupWindow extends PopupWindowTemplate {

    public TestPopupWindow(Activity activity) {
        super(activity);
    }


    @Override
    protected View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.popupwindow, null);
    }

}
