package idv.jasonwang.drawableedittext.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import idv.jasonwang.drawableedittext.DrawableEditText;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawableEditText) findViewById(R.id.drawableEditText)).setDrawableBottomSize(100, 100);
                ((DrawableEditText) findViewById(R.id.drawableEditText)).setDrawableBottom(android.R.drawable.ic_menu_call);
            }
        });
    }

}
