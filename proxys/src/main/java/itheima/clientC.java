package itheima;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class clientC {
    public static void main(String[] args) {
        /**
         * 动态代理：
         *  特点：字节码随用随创建，随用随加载
         *  作用：不修改源码的基础上对方法增强
         *  分类：
         *      基于接口的动态代理
         *      基于子类的动态代理
         *  基于接口的动态代理：
         *      涉及的类：Enhancer
         *      提供者：第三方
         *  如何创建代理对象：
         *      使用Enhancer类中的create方法
         *  创建代理对象的要求：
         *
         *  newProxyInstance方法的参数：
         *      Class：字节码
         *          它是用于指定被代理对象的字节码。
         *    Callback：用于提供增强的代码
         *          它是让我们写如何代理。我们一般都是些一个该接口的实现类，通常情况下都是匿名内部类，但不是必须的。
         *          此接口的实现类都是谁用谁写。
         *      我们一般写的都是该接口的子接口实现类：MethodInterceptor
         */
        //创建被代理对象
        Producer producer = new ProducerImpl();
        //创建子类代理对象
        Producer cglibProducer = (Producer)Enhancer.create(producer.getClass(), new MethodInterceptor() {
            /**
             *
             * @param proxy
             * @param method
             * @param args
             * @param methodProxy
             * @return
             * @throws Throwable
             */
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                //提供增强的代码
                Object returnValue = null;
                //1.获取方法执行的参数
                Float money = (Float)args[0];
                //2.判断当前方法是不是销售
                //增强被代理对象的saleProduct方法
                if("saleProduct".equals(method.getName())) {
                    returnValue = method.invoke(producer, money*0.8f);
                }else if ("afterService".equals(method.getName())){
                    returnValue=method.invoke(producer,money*0.5f);
                }
                return returnValue;
            }
        });
        cglibProducer.saleProduct(10000f);
        cglibProducer.afterService(500f);
    }

}
