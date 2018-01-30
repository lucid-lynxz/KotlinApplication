package lynxz.org.kotlinapplication.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import lynxz.org.kotlinapplication.util.Logger;

/**
 * Created by lynxz on 19/09/2017.
 */
public class DynamicProxy implements InvocationHandler {
    private Object target;

    public DynamicProxy(Object obj) {
        target = obj;
    }

    public <T> T getProxy(String msg) {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger.d("invoke " + method.getName());
        Object invoke = method.invoke(target, args);
        return invoke;
    }
}
