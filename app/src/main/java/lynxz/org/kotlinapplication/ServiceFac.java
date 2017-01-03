package lynxz.org.kotlinapplication;

import lynxz.org.kotlinapplication.bean.UPlusGoGetTokenBean;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zxz on 2016/12/29.
 * sounbus app-developer
 */
public interface ServiceFac {
    @GET("uac/oauth/token?grant_type=client_credentials")
    Observable<UPlusGoGetTokenBean> getInfo();
}
