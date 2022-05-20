package hello.advanced.pureproxy.proxy.code;

public class ProxyClient {

    private Subject subject;

    public ProxyClient(Subject subject) {
        this.subject = subject;
    }

    public void execute() {
        subject.operation();
    }
}
