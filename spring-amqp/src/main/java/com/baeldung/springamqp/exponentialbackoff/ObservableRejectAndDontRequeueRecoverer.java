package com.baeldung.springamqp.exponentialbackoff;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;

public class ObservableRejectAndDontRequeueRecoverer extends RejectAndDontRequeueRecoverer {
    private Runnable observer;

    @Override
    public void recover(Message message, Throwable cause) {
        if(observer != null) {
            observer.run();
        }
        
        super.recover(message, cause);
    }
    
    void setObserver(Runnable observer){
        this.observer = observer;
    }
}
