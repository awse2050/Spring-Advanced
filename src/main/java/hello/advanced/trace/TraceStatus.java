package hello.advanced.trace;

/*
    로그의 상태 정보를 나타낸다.
 */
public class TraceStatus {
    // 로그Id , 메서드, 시간
    private TraceId traceId; // 트랜잭션 id , 레벨을 가짐.
    private Long startTimeMs; // 시작 - 종료의 수행시간
    private String message; // 시작시 사용한 메세지.

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartTimeMs() {
        return startTimeMs;
    }

    public String getMessage() {
        return message;
    }
}


