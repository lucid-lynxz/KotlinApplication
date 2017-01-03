package lynxz.org.kotlinapplication

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_retrofit.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by zxz on 2016/12/29.
 * sounbus app-developer
 */
class RetrofitDemoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        initData();
    }

    fun initData() {
        var retrofit = Retrofit.Builder()
                .baseUrl("http://open.soundbus.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        var service = retrofit.create(Service::class.java)
        var observable = service.getInfo()
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { bean ->
                    tv_msg.text = bean.access_token
                }
    }
}