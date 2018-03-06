package com.example.aop;

/**
 * This Aspect intercept methods annotated with {@link RabbitListener} and invoke {@link
 * AmqpMessagingSpanManager#beforeHandle(Message, String[])} with {@link Message} and queue names.
 *
 * @author André Ignacio
 */
@Aspect
public class RabbitListenerAspect {
    private final AmqpMessagingSpanManager spanManager;

    /**
     * Creates a new instance.
     *
     * @param spanManager Span manager for AMQP messaging
     */
    public RabbitListenerAspect(AmqpMessagingSpanManager spanManager) {
        this.spanManager = spanManager;
    }

    @Around("@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)")
    public Object executeAroundRabbitListenerAnnotation(ProceedingJoinPoint call) throws Throwable {
        final Object[] args = call.getArgs();
        final Message message = getMessageArgument(args);
        final String[] queueNames = getQueueNames(call);
        if (message != null) {
            spanManager.beforeHandle(message, queueNames);
        }
        try {
            Object result = call.proceed();
            spanManager.afterHandle(null);

            return result;
        } catch (Exception e) {
            spanManager.afterHandle(e);
            throw e;
        }
    }

    /**
     * Get queues names separated with comma from {@link RabbitListener} annotation.
     *
     * @param call Call
     * @return Queues names
     */
    private String[] getQueueNames(ProceedingJoinPoint call) {
        final MethodSignature signature = (MethodSignature) call.getSignature();
        final Method method = signature.getMethod();
        final RabbitListener rabbitListenerAnnotation = method.getAnnotation(RabbitListener.class);
        return rabbitListenerAnnotation.queues();
    }

    private Message getMessageArgument(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Message) {
                return (Message) arg;
            }
        }
        return null;
    }
}