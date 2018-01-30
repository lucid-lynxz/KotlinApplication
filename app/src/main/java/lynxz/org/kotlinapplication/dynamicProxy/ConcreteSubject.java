package lynxz.org.kotlinapplication.dynamicProxy;

import lynxz.org.kotlinapplication.util.Logger;

/**
 * Created by lynxz on 19/09/2017.
 */
public class ConcreteSubject implements Subject {
    @Override
    public void saySth(CharSequence msg) {
        Logger.d("msg is " + msg);
    }
}
