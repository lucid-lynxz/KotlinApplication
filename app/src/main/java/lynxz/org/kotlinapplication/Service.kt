package lynxz.org.kotlinapplication

import lynxz.org.kotlinapplication.bean.UPlusGoGetTokenBean
import retrofit2.Call
import retrofit2.http.*
import rx.Observable

/**
 * Created by zxz on 2016/12/29.
 * sounbus app-developer
 */
interface Service {

    /**
     * u+go获取登录token获取
     * */
    @Headers(
            "Authorization: Basic dXBsdXNnbzp1cGx1c2dvc2VjcmV0",
            "X-SOP-PHONE-PARAMS: {'IMEI ':'4bcecbe44dd1c942','appName':'com.soundbus.uplusgo','appVer':'1.0','phoneBrand':'Xiaomi','phoneModel':'HM 1S','systemVer':'4.4.4'}"
    )
    @POST("/api/v1/uac/oauth/token")
    fun getInfo(@Query("grant_type") type: String = "client_credentials"): Observable<UPlusGoGetTokenBean>


    @HEAD
    fun getResource(@Url url: String): Observable<Void>

    @HEAD
    fun getResourceCall(@Url url: String): Call<Void>
}