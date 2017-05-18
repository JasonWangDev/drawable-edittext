package idv.jasonwang.widgetutils.sample;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import idv.jasonwang.widgetutils.popupwindow.PopupWindowTemplate;

/**
 * Created by jason on 2017/5/18.
 */

public class TestPopupWindow extends PopupWindowTemplate implements View.OnClickListener {

    public TestPopupWindow(Activity activity) {
        super(activity);
    }


    @Override
    protected View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.popupwindow, null);

        Button btn = (Button) view.findViewById(R.id.button9);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    protected Offset setOffset(Position position, Density density) {
        return null;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();
        dissmiss();
    }

}
