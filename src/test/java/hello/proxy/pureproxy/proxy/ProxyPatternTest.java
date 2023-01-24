package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);

        // client    -- >    realSubject
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

        // client    -->   cacheProxy      -->     RealSubject
        // 1. 프록시 호출 -> 데이터 없음
        // 2. 실제 객체 호출
        //
        // 3. 프록시 호출 -> 데이터 있음
        // 4. 프록시 호출 -> 데이터 있음
        client.execute();
        client.execute();
        client.execute();
    }
}
