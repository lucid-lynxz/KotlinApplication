package lynxz.org.kotlinapplication.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import lynxz.org.kotlinapplication.R;
import lynxz.org.kotlinapplication.util.Logger;

public class NavigationViewActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);

        BottomNavigationView bottomView = (BottomNavigationView) findViewById(R.id.navigation_view);
        bottomView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.recents:
                Logger.d("recent", "xxx");
                break;
            case R.id.favourites:
                Logger.d("favourites", "xxx");
                break;
            case R.id.nearby:
                Logger.d("nearby", "xxx");
                break;
            default:
                return false;
        }
        return true;
    }
}
