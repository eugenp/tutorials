
package com.baeldung.beaninjectiontypes.domian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Computer {

    private Processor processor;

    private GraphicsCard graphicsCard;

    @Autowired
    public Computer(Processor processor) {
        this.processor = processor;
    }

    @Autowired
    public void setGraphicsCard(GraphicsCard graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    @Override
    public String toString() {
        return "Computer [processor=" + processor + ", graphicsCard=" + graphicsCard + "]";
    }

}
