package hello.advanced.pureproxy.proxy;


import hello.advanced.pureproxy.proxy.code.CacheProxy;
import hello.advanced.pureproxy.proxy.code.ProxyClient;
import hello.advanced.pureproxy.proxy.code.RealSubject;
import hello.advanced.pureproxy.proxy.code.Subject;
import org.junit.jupiter.api.Test;

public class ProxyTests {


    @Test
    public void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyClient proxyClient = new ProxyClient(realSubject);
        proxyClient.execute();
        proxyClient.execute();
        proxyClient.execute();
    }

    @Test
    public void cachingProxyTest() {
        Subject realSubject = new RealSubject();
        Subject cacheProxy = new CacheProxy(realSubject);
        ProxyClient proxyClient = new ProxyClient(cacheProxy);
        proxyClient.execute();
        proxyClient.execute();
        proxyClient.execute();
    }
}
