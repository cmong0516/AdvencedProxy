package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));

        // CGLIB 는 MethodInterceptor 를 구현해서 사용하며
        // 프록시 생성은 enhancer 에 타겟과 프록시를 등록하고
        // create() 해서 생성한다.

        // JDK 동적 프록시는 InvocationHandler 를 구현하며
        // 프록시 생성은 Proxy.newProxyInstance() 로 생성.

        ConcreteService proxy = (ConcreteService) enhancer.create();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

    }
}

// CGLIB 는 자식 클래스를 동적으로 생성하기 때문에 기본 생성자가 필요함.
// 클래스에 final 키워드가 붙으면 상속이 불가능하다.
// 메서드에 final 키워드가 붙으면 해당 메서드 오버라이딩이 불가능하다.