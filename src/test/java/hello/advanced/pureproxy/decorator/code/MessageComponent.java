package hello.advanced.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageComponent implements Component {
    private Component component;

    public MessageComponent(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("Message COmponent....");

        String result = component.operation();
        String decoResult = "*****" + result + "*****";
        log.info("decoResult ... : {}", decoResult);
        return decoResult;
    }
}
