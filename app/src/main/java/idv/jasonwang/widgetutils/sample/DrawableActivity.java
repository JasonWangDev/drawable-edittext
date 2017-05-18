package idv.jasonwang.widgetutils.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import idv.jasonwang.widgetutils.drawable.DrawableCheckBox;
import idv.jasonwang.widgetutils.drawable.DrawableEditText;

public class DrawableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawableEditText) findViewById(R.id.drawableEditText)).setDrawableBottomSize(100, 100);
                ((DrawableEditText) findViewById(R.id.drawableEditText)).setDrawableBottom(android.R.drawable.ic_menu_call);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawableCheckBox) findViewById(R.id.drawableCheckBox)).setDrawableLeftSize(15, 15);
                ((DrawableCheckBox) findViewById(R.id.drawableCheckBox)).setDrawableLeft(android.R.drawable.ic_menu_call);
            }
        });
    }

}
