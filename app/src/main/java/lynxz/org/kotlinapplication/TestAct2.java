package lynxz.org.kotlinapplication;

import lynxz.org.kotlinapplication.bean.UPlusGoGetTokenBean;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zxz on 2016/12/29.
 * sounbus app-developer
 */
public class TestAct2 extends BaseActivity {
    public void test() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://open.soundbus.cn/api/v1")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Observable<UPlusGoGetTokenBean> obser = service.getInfo("client_credentials");
        obser.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UPlusGoGetTokenBean>() {
                    @Override
                    public void call(UPlusGoGetTokenBean uPlusGoGetTokenBean) {

                    }
                });
    }
}
