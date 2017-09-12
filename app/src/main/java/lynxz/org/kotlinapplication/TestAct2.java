package lynxz.org.kotlinapplication;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lynxz.org.kotlinapplication.activity.BaseActivity;
import lynxz.org.kotlinapplication.bean.UPlusGoGetTokenBean;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zxz on 2016/12/29.
 * sounbus app-developer
 */
public class TestAct2 extends BaseActivity {
    boolean isAccess = false;

    public void test() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://open.soundbus.cn/api/v1")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Observable<UPlusGoGetTokenBean> obser = service.getInfo("client_credentials");
        obser.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UPlusGoGetTokenBean>() {
                    @Override
                    public void accept(UPlusGoGetTokenBean uPlusGoGetTokenBean) throws Exception {

                    }
                });
    }

    class Temp {
        public void test() {
            isAccess = true;
            TestAct2.this.test();
        }

    }
}
