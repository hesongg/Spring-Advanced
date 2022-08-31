package hello.advanced.trace.callback;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    // <T> = ?
    // 제네릭 메서드를 정의할 때 중요한 것은 리턴 타입이 무엇인지와는 상관없이 해당 메서드가 제네릭 메서드라는 것을 컴파일러에게 알려주는 것
    // 그러기 위해서 리턴 타입을 정의하기 전에 제네릭 타입에 대한 정의가 반드시 필요
    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;

        try {
            status = trace.begin(message);

            //로직 호출
            T result = callback.call();

            trace.end(status);

            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
