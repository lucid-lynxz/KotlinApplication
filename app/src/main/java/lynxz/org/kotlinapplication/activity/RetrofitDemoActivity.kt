package lynxz.org.kotlinapplication.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_retrofit.*
import lynxz.org.kotlinapplication.R
import lynxz.org.kotlinapplication.Service
import lynxz.org.kotlinapplication.util.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
                .subscribe { bean ->
                    tv_msg.text = bean.access_token
                }

        val url = "http://soundbus-media.oss-cn-shenzhen.aliyuncs.com/super-g/prod/resources/3/resource.zip"
        val resource = service.getResource(url)
        resource.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { bean ->
                    Logger.d("finish ")
                }

        service.getResourceCall(url).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                Logger.d("callback.... ${response?.headers()}")
            }

        })

    }
}