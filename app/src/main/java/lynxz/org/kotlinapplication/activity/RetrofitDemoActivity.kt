package lynxz.org.kotlinapplication.activity

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_retrofit.*
import lynxz.org.kotlinapplication.R
import lynxz.org.kotlinapplication.Service
import lynxz.org.kotlinapplication.util.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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