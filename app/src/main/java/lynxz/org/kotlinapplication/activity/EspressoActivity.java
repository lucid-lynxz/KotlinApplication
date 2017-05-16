package lynxz.org.kotlinapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import lynxz.org.kotlinapplication.R;

public class EspressoActivity extends AppCompatActivity {

    public static final String KEY_TITLE = "key_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espresso);

        String title = getIntent().getStringExtra(KEY_TITLE);
        title = TextUtils.isEmpty(title) ? "empty intent" : title;
        TextView tv = (TextView) findViewById(R.id.tv_espresso);
        tv.setText(title);
    }
}
