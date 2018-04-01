package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 数据源切面配置
 *
 * @author LuPindong
 * @time 2017-04-27 20:29
 */
@Aspect
@Order(Integer.MIN_VALUE)// 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Autowired
    private DynamicDataSourceHolder dynamicDataSourceHolder;

    @Pointcut("execution(public * net.lovexq.seckill.background.*.service.*.*(..))")
    public void dsPointcut() {
    }

    //@Around("dsPointcut()")
    public Object doAroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object response;
        boolean hasBinding = false;
        String method = pjp.getSignature().getName();
        try {
            hasBinding = dynamicDataSourceHolder.hasBindingDataSource();    // 是否绑定数据源
            if (!hasBinding) {
                if (isReadOnlyMethod(method)) {
                    dynamicDataSourceHolder.markSlave();
                } else {
                    dynamicDataSourceHolder.markMaster();
                }
            }
            response = pjp.proceed();
        } finally {
            if (!hasBinding) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.info("移除自动选取数据源");
                }
                dynamicDataSourceHolder.markRemove();
            }
        }
        return response;
    }

    @Around("dsPointcut()")
    public Object doAroundReadOnlyMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object response;
        boolean hasBinding = false;
        String method = pjp.getSignature().getName();
        String dataSourceKey = "";
        try {
            dataSourceKey = dynamicDataSourceHolder.getDataSourceKey();
            if (dataSourceKey != null) hasBinding = true;
            if (!hasBinding) {
                if (isReadOnlyMethod(method)) {
                    dynamicDataSourceHolder.markSlave();
                } else {
                    dynamicDataSourceHolder.markMaster();
                }
            }
            if (dataSourceKey == null) {
                dataSourceKey = dynamicDataSourceHolder.getDataSourceKey();
            }
            response = pjp.proceed();
        } finally {
            if (!hasBinding && !DynamicDataSourceHolder.getMasterDSKey().equals(dataSourceKey)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.info("移除自动选取数据源:{}", dataSourceKey);
                }
                dynamicDataSourceHolder.markRemove();
            }
        }
        return response;
    }

    /**
     * 是否只读方法
     *
     * @param method
     * @return
     */
    private boolean isReadOnlyMethod(String method) {
        return method.startsWith("query") || method.startsWith("select") || method.startsWith("find") || method.startsWith("get") || method.startsWith("list") || method.startsWith("search");
    }

}
