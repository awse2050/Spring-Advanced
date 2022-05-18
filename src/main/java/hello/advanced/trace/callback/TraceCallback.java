package hello.advanced.trace.callback;

// 콜백 함수 전달
public interface TraceCallback<T> {
    public T call();
}
