package com.example.aop;

/**
 * 日志处理
 * {@link Log} 注解的方法执行过程将会被记录到数据库
 *
 * 来自： https://www.programcreek.com/java-api-examples/?code=yujunhao8831/spring-boot-start-current/spring-boot-start-current-master/aidijing-manage-system/aidijing-manage-system-webapp/src/main/java/com/aidijing/manage/LogAspect.java
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SystemLogService systemLogService;

    @Around( value = "@annotation(com.aidijing.common.annotation.Log)" )
    public Object logHandle ( ProceedingJoinPoint joinPoint ) throws Throwable {
        Method method = currentMethod( joinPoint , joinPoint.getSignature().getName() );
        Log    log    = method.getAnnotation( Log.class );
        if ( Objects.isNull( log ) ) {
            return joinPoint.proceed();
        }
        return logHandle( joinPoint , method , log );
    }

    private Object logHandle ( ProceedingJoinPoint joinPoint , Method method , Log log ) throws Throwable {
        // 异常日志信息
        String actionLog = null;
        // 是否执行异常
        boolean isException = false;
        // 接收时间戳
        long endTime;
        // 开始时间戳
        long startTime = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } catch ( Throwable throwable ) {
            isException = true;
            actionLog = throwable.getMessage();
            throw throwable;
        } finally {
            endTime = System.currentTimeMillis();
            // 日志处理
            logHandle( joinPoint , method , log , actionLog , startTime , endTime , isException );
        }
    }

    private void logHandle ( ProceedingJoinPoint joinPoint ,
                             Method method ,
                             Log log ,
                             String actionLog ,
                             long startTime , long endTime ,
                             boolean isException ) {

        List< Map< String, Object > > args = Collections.emptyList();
        if ( ArrayUtils.isNotEmpty( joinPoint.getArgs() ) ) {
            args = new ArrayList<>( joinPoint.getArgs().length );
            for ( Object arg : joinPoint.getArgs() ) {
                args.add( CollectionProUtils.hashMapExpectedPuts( 2 ,
                        "parameterType" , arg.getClass().getCanonicalName() ,
                        "parameterValue" , arg
                ) );

            }
        }

        SystemLog systemLog = new SystemLog();

        if ( ContextUtils.isLogin() ) {
            systemLog
                    // 后台管理用户ID
                    .setUserId( ContextUtils.getUserId() )
                    // 后台管理用户真实姓名
                    .setUserRealName( ContextUtils.getUser().getRealName() );
        }

        systemLog
                // 操作日志(也用于可以存储异常栈信息,或者运行的sql)
                .setActionLog( actionLog )
                // 操作ip地址
                .setActionIpAddress( RequestUtils.getRequestIp() )
                // 操作描述
                .setActionDescription( log.description() )
                // 动作开始时间
                .setActionStartTime( new Date( startTime ) )
                // 动作结束时间
                .setActionEndTime( new Date( endTime ) )
                // 总执行时间
                .setActionTotalTime( endTime - startTime )
                // 操作类
                .setActionClass( joinPoint.getTarget().getClass().getName() )
                // 操作方法
                .setActionMethod( method.getName() )
                // 方法参数
                .setActionArgs( JsonUtils.toCustomizationJson( args ) )
                // 是否异常
                .setException( isException )
                // 通知类型(SMS:短信,MAIL:邮箱)
                .setNoticeType( NoticeType.valueOf( log.noticeType().getValue() ) )
                // 异常是否警报
                .setExceptionWarn( log.warn() );

        systemLogService.asyncSave( systemLog );

    }


    /**
     * 获取目标类的所有方法，找到当前要执行的方法
     */
    private Method currentMethod ( ProceedingJoinPoint joinPoint , String methodName ) {
        Method[] methods      = joinPoint.getTarget().getClass().getMethods();
        Method   resultMethod = null;
        for ( Method method : methods ) {
            if ( method.getName().equals( methodName ) ) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }


}
