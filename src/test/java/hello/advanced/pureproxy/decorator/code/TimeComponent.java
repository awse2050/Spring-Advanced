package hello.advanced.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeComponent implements Component {

    private Component component;

    public TimeComponent(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("timeCOmponent....");

        long startTime = System.currentTimeMillis();
        String result = component.operation();
        long endTime = System.currentTimeMillis();

        long times = endTime - startTime;
        log.info("times... : {}", times);
        return result;
    }
}
