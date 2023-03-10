package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        // Hello.callA()
        log.info("result={}", result1);
        //공통 로직1 종료

        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        // Hello.callB()
        log.info("result={}", result2);
        //공통 로직2 종료

        // 일반적인 클래스의 메서드 호출
    }

    @Test
    void reflection1() throws Exception {
        // 클래스 정보
        // hello.proxy.jdkdynamic.ReflectionTest 의 내부클래스 Hello
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        System.out.println("classHello = " + classHello);

        Hello target = new Hello();
        //callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");

        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        //callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result2);
    }

    @Test
    void reflection2() throws Exception {
        //클래스 정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB,target);

        // invoke
        // 멀티스레드 환경에서 데이터 보호를 위해 사용

//        Method methodCallC = classHello.getMethod("callC");
//        System.out.println("methodCallC = " + methodCallC);
        // NoSuchMethodException
        // reflection 은 컴파일 시점에서 에러를 잡을수가 없어서 사용을 권장하지 않는다.
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }

    // dynamicCall 은 메서드와 target 을 받아 위의 두 로직을 공통 처리할수 있다.

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }
        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
