package lynxz.org.kotlinapplication.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by lynxz on 31/05/2017.
 * 用于处理ble扫描和连接等功能
 */
public class BleService extends android.app.Service {

    private BleBinder mBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new BleBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
