package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        // AInterface 타입의 AImpl
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);
        // 동적 프록시는 import java.lang.reflect.Proxy; 를 통해 생성할수 있다.
        // 클래스 로더 정보 , 인터페이스 , 핸들러 로직 을 넣어준다.

        proxy.call();
        // TimeProxy 실행
        // A 호출
        // TimeProxy 종료 , resultTime = 0

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        // target 은 AImpl
        // 프록시는 생성된 com.sun.proxy.$Proxy12
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);

        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }
}
