package hello.advanced.trace;

import hello.advanced.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    // 각 로직마다 반환타입이 다르기 떄문에, 제네릭을 활용해서 변환시킨다.
    public T execute(String message) {
        TraceStatus status = null;

        try {
            status = trace.begin(message);
            //로직 호출
            T result = call();
            trace.end(status);
            return result;

        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();
}
