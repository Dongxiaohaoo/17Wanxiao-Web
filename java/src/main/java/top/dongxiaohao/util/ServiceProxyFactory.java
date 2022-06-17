package top.dongxiaohao.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ServiceProxyFactory {

    public static <T> T getProxy(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object obj = null;
                try {
                    obj = methodProxy.invokeSuper(o, objects);
                    MybatisUtil.getConn().commit();
                } catch (Exception e) {
                    System.out.println("回滚...");
                    throw e;
                } finally {
                    System.out.println("释放...");
                    MybatisUtil.closeConn(MybatisUtil.getConn());
                }
                return obj;
            }
        });
        return (T) enhancer.create();
    }
}
