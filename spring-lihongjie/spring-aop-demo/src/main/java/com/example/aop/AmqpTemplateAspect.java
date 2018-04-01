package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * Aspect responsible for get the current {@link Message} before execution of {@link
 * org.springframework.amqp.core.AmqpTemplate} methods and invoke {@link
 * AmqpMessagingSpanManager#beforeSend(Message, String)} and {@link
 * AmqpMessagingSpanManager#afterSend(Exception)}.
 *
 * @see AmqpMessagingSpanManager
 * @see SpanManagerMessagePostProcessor
 * @author André Ignacio
 */
@Aspect
public class AmqpTemplateAspect {
    private static final String CONVERT_AND_SEND = "convertAndSend";
    private static final String UNKNOWN_EXCHANGE = "unknown-exchange";
    private static final String SPAN_NAME_PREFIX = "amqp://";
    private final AmqpMessagingSpanManager spanManager;

    /**
     * Creates a new instance.
     *
     * @param spanManager Span manager for AMQP messaging
     */
    public AmqpTemplateAspect(AmqpMessagingSpanManager spanManager) {
        this.spanManager = spanManager;
    }

    @Around("execution(* org.springframework.amqp.core.AmqpTemplate.send(..))")
    public void executeAroundSend(ProceedingJoinPoint call) throws Throwable {
        executeWithoutPostProcessor(call, ArgumentDiscover.from(call));
    }

    @Around("execution(* org.springframework.amqp.core.AmqpTemplate.sendAndReceive(..))")
    public Object executeAroundSendAndReceive(ProceedingJoinPoint call) throws Throwable {
        return executeWithoutPostProcessor(call, ArgumentDiscover.from(call));
    }

    @Around("execution(* org.springframework.amqp.core.AmqpTemplate.convertAndSend(Object))")
    public void executeAroundConvertAndSendOneArg(ProceedingJoinPoint call) throws Throwable {
        executeConvertAndSendWithoutPostProcessor(call, ArgumentDiscover.from(call));
    }

    @Around("execution(* org.springframework.amqp.core.AmqpTemplate.convertAndSend(String,Object))")
    public void executeAroundConvertAndSendTwoArgs(ProceedingJoinPoint call) throws Throwable {
        executeConvertAndSendWithoutPostProcessor(call, ArgumentDiscover.from(call));
    }

    @Around(
            "execution(* org.springframework.amqp.core.AmqpTemplate.convertAndSend(String,String,Object))")
    public void executeAroundConvertAndSendThreeArgs(ProceedingJoinPoint call) throws Throwable {
        executeConvertAndSendWithoutPostProcessor(call, ArgumentDiscover.from(call));
    }

    @Around(
            "execution(* org.springframework.amqp.core.AmqpTemplate.convertAndSend(Object,org.springframework.amqp.core.MessagePostProcessor))")
    public void executeAroundConvertAndSendOneArgWithProcessor(ProceedingJoinPoint call)
            throws Throwable {
        final ArgumentDiscover argumentDiscover = ArgumentDiscover.from(call);
        final MessagePostProcessor argPostProcessor = argumentDiscover.messagePostProcessor;
        final boolean byPass = argPostProcessor instanceof SpanManagerMessagePostProcessor;
        if (byPass) {
            call.proceed(call.getArgs());
        } else {
            executeConvertAndSendWithoutPostProcessor(call, argumentDiscover);
        }
    }

    @Around(
            "execution(* org.springframework.amqp.core.AmqpTemplate.convertAndSend(String,Object,org.springframework.amqp.core.MessagePostProcessor))")
    public void executeAroundConvertAndSendTwoArgsWithPostProcessor(ProceedingJoinPoint call)
            throws Throwable {
        final ArgumentDiscover argumentDiscover = ArgumentDiscover.from(call);
        final MessagePostProcessor argPostProcessor = argumentDiscover.messagePostProcessor;
        final boolean byPass = argPostProcessor instanceof SpanManagerMessagePostProcessor;
        if (byPass) {
            call.proceed(call.getArgs());
        } else {
            executeConvertAndSendWithoutPostProcessor(call, argumentDiscover);
        }
    }

    @Around(
            "execution(* org.springframework.amqp.core.AmqpTemplate.convertAndSend(String,String,Object,org.springframework.amqp.core.MessagePostProcessor))")
    public void executeAroundConvertAndSendThreeArgsWithPostProcessor(ProceedingJoinPoint call)
            throws Throwable {
        final ArgumentDiscover discoverArguments = ArgumentDiscover.from(call);
        final MessagePostProcessor argPostProcessor = discoverArguments.messagePostProcessor;
        final boolean byPass = argPostProcessor instanceof SpanManagerMessagePostProcessor;
        if (byPass) {
            call.proceed(call.getArgs());
        } else {
            executeConvertAndSendWithoutPostProcessor(call, discoverArguments);
        }
    }

    private void executeConvertAndSendWithoutPostProcessor(
            ProceedingJoinPoint call, ArgumentDiscover argumentDiscover) throws Throwable {
        final Object[] args = argumentDiscover.args;
        final MessagePostProcessor argPostProcessor = argumentDiscover.messagePostProcessor;

        boolean executed;

        try {
            if (argPostProcessor != null) {
                final String exchange = argumentDiscover.exchangeOfSender;
                final String routingKey = argumentDiscover.routingKey;
                final SpanManagerMessagePostProcessor postProcessor =
                        new SpanManagerMessagePostProcessor(spanManager, buildSpanName(exchange, routingKey));
                final MessagePostProcessor overwritePostProcessor =
                        new CompositeMessagePostProcessor(argPostProcessor, postProcessor);

                args[args.length - 1] = overwritePostProcessor;
                call.proceed(args);
                executed = true;
            } else {
                executed = changeExecutionOfMethodToUsePostProcessor(call, argumentDiscover);
            }
        } catch (Exception e) {
            spanManager.afterSend(e);
            throw e;
        }
        if (executed) {
            spanManager.afterSend(null);
        } else {
            executeWithoutPostProcessor(call, argumentDiscover);
        }
    }

    private Object executeWithoutPostProcessor(
            ProceedingJoinPoint call, ArgumentDiscover argumentDiscover) throws Throwable {
        final Object[] args = call.getArgs();
        final Message message = argumentDiscover.message;
        final String exchange = argumentDiscover.exchangeOfSender;
        final String routingKey = argumentDiscover.routingKey;
        before(message, exchange, routingKey);
        try {
            Object result = call.proceed(args);
            spanManager.afterSend(null);
            return result;
        } catch (Exception e) {
            spanManager.afterSend(e);
            throw e;
        }
    }

    private boolean changeExecutionOfMethodToUsePostProcessor(
            ProceedingJoinPoint call, ArgumentDiscover argumentDiscover) {
        final Object[] args = call.getArgs();
        final String exchange = argumentDiscover.exchangeOfSender;
        final String routingKey = argumentDiscover.routingKey;
        final SpanManagerMessagePostProcessor beforePublishPostProcessor =
                new SpanManagerMessagePostProcessor(spanManager, buildSpanName(exchange, routingKey));
        final AmqpTemplate amqpTemplate = (AmqpTemplate) call.getTarget();

        boolean executed = true;
        final int argsLength = args.length;
        switch (argsLength) {
            case 1:
                amqpTemplate.convertAndSend(args[0], beforePublishPostProcessor);
                break;
            case 2:
                amqpTemplate.convertAndSend((String) args[0], args[1], beforePublishPostProcessor);
                break;
            case 3:
                amqpTemplate.convertAndSend(
                        (String) args[0], (String) args[1], args[2], beforePublishPostProcessor);
                break;
            default:
                executed = false;
                break;
        }
        return executed;
    }

    private void before(Message message, String exchange, String routingKey) {
        if (message != null) {
            spanManager.beforeSend(message, buildSpanName(exchange, routingKey));
        }
    }

    private String buildSpanName(String exchange, String routingKey) {
        final StringBuilder sb = new StringBuilder();
        sb.append(SPAN_NAME_PREFIX);
        sb.append(exchange != null ? exchange : UNKNOWN_EXCHANGE);
        sb.append("/");
        sb.append(routingKey);
        return sb.toString();
    }

    private static class ArgumentDiscover {
        private Message message;
        private String exchangeOfSender;
        private String routingKey = "*";
        private Object[] args;
        private Object target;
        private MessagePostProcessor messagePostProcessor;

        private ArgumentDiscover(ProceedingJoinPoint call) {
            args = call.getArgs();
            target = call.getTarget();

            String exchangeFromArgs = null;
            final String methodName = call.getSignature().getName();
            final int argsLength = args.length;
            if (CONVERT_AND_SEND.equals(methodName)) {
                if (args[argsLength - 1] instanceof MessagePostProcessor) {
                    if (argsLength > 3) {
                        exchangeFromArgs = (String) args[0];
                        routingKey = (String) args[1];
                    } else if (argsLength > 2) {
                        routingKey = (String) args[0];
                    }
                } else {
                    if (argsLength > 2) {
                        exchangeFromArgs = (String) args[0];
                        routingKey = (String) args[1];
                    } else if (argsLength > 1) {
                        routingKey = (String) args[0];
                    }
                }
            } else {
                if (argsLength > 1) {
                    if (args[0] instanceof String && args[1] instanceof String) {
                        exchangeFromArgs = (String) args[0];
                        routingKey = (String) args[1];
                    } else {
                        routingKey = (String) args[0];
                    }
                }
            }

            if (exchangeFromArgs == null) {
                exchangeOfSender = getExchangeFromRabbitTemplate(target);
            } else {
                exchangeOfSender = exchangeFromArgs;
            }

            for (Object arg : args) {
                if (arg instanceof MessagePostProcessor) {
                    messagePostProcessor = (MessagePostProcessor) arg;
                } else if (arg instanceof Message) {
                    message = (Message) arg;
                }
            }
        }

        private String getExchangeFromRabbitTemplate(Object target) {
            String exchange = null;
            if (target instanceof RabbitTemplate) {
                exchange = ((RabbitTemplate) target).getExchange();
            }
            return exchange;
        }

        public static ArgumentDiscover from(ProceedingJoinPoint call) {
            return new ArgumentDiscover(call);
        }
    }
}