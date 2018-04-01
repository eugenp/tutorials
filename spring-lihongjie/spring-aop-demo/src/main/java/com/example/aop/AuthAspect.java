package com.example.aop;

public class AuthAspect {
    private static Logger log = LoggerFactory.getLogger(AuthAspect.class);
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        /**
         * 后面从redis中获取用户信息
         */
		/*if (UserInfoHolder.getUser() != null) {
			UserInfoHolder.resetUser();
		}*/
        long time1 = System.currentTimeMillis();
        Class<?> classTarget=pjp.getTarget().getClass();
        String className = classTarget.getName();
        String methodName = pjp.getSignature().getName();
        Class<?>[] par=((MethodSignature) pjp.getSignature()).getParameterTypes();
        Method objMethod=classTarget.getMethod(methodName, par);
        Object[] args = pjp.getArgs();
        args = ignoreHttpRequest(args);
        String inputParamInfo = getInputParamInfo(className, methodName, args);
        log.info(inputParamInfo);


        Login login = objMethod.getAnnotation(Login.class);
        Object o = null;
        if (login == null) {
            try {
                o = pjp.proceed();
            } catch (Exception e) {
                o = exceptionDeal(e, inputParamInfo, pjp);
            }
        } else {
            //验证登录
            CodeMeta code = validateLogin();
            if (code == null) {
                try {
                    o = pjp.proceed();
                } catch (Exception e) {
                    return exceptionDeal(e, inputParamInfo, pjp);
                }
            } else {
                o = getResultObj(code);
            }
        }
        long time2 = System.currentTimeMillis();
        loggerForOutPut(className, methodName, time2 - time1, o);
        return o;
    }

    private CodeMeta validateLogin() throws UnsupportedEncodingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //获取sessionId
        String userkey = getUserkey(request);
        if (userkey == null) {
            //未登录
            return ErrorCode.NOT_LOGIN;
        }

        String sessionId = URLDecoder.decode(userkey,"UTF-8");
        //调用缓存获取用户数据
        //TODO
		/*if (dto == null) {
			//登录失效
			return ErrorCode.LOGIN_EXPIRED;
		}*/

        //已登录
		/*if (dto != null) {
			setUser(dto);
		} else {
			setSubUser(subDto);
		}*/
        return null;
    }

    /**
     * 从cookie获取userkey
     * @Title: getUserkey
     * @return
     */
    private String getUserkey(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null;
        }
        String userkey = null;
        //主账号与子账号在cookie中的key不一样
        for (Cookie cookie : cookies) {
            if("userkey".equals(cookie.getName())){
                userkey = cookie.getValue();
                break;
            }
        }
        return userkey;
    }


    private Result exceptionDeal(Exception exception, String inputParamInfo, ProceedingJoinPoint pjp) {
        XiaLiuException retError;

        if (exception instanceof XiaLiuException) {
            retError = (XiaLiuException) exception;
        } else {
            retError = new XiaLiuException(ErrorCode.UNKNOWN_ERROR, ExceptionUtils.getExceptionProfile(exception));
            if (inputParamInfo == null) {
                String className = pjp.getTarget().getClass().getSimpleName();
                String methodName = pjp.getSignature().getName();
                Object[] args = pjp.getArgs();
                inputParamInfo = this.getInputParamInfo(className, methodName, args);
            }
            // 打印入参
            log.error(inputParamInfo, exception.getMessage());
        }

        return getResultObj(false, retError.getErrorCode().getCode(), retError.getMessage());
    }

    private Result getResultObj(CodeMeta code) {
        Result result = Result.create();
        result.setCode(code.getCode());
        result.setSuccess(false);
        result.setDescription(code.getMsgZhCN());
        return result;
    }

    private Result getResultObj(Boolean success, String code, String message) {
        Result result = Result.create();
        result.setSuccess(success);
        result.setCode(code);
        result.setDescription(message);
        return result;
    }

    private String getInputParamInfo(String className, String methodName, Object[] args) {
        String inputArgJson = "";
        try {
            inputArgJson = (args.length <= 0 ? "" : JSON.toJSONString(args));
        } catch (Exception e) {
            inputArgJson = "!!json解析error!!";
            log.warn("入参json解析error", e);
        }
        // 打印类名，方法名，传入参数
        return className + "." + methodName + ",入参:" + inputArgJson;
    }

    private void loggerForOutPut(String className, String methodName, long time, Object object) {
        String outputArgJson = "";
        try {
            outputArgJson = JSON.toJSONString(object);
        } catch (Exception e) {
            outputArgJson = "!!json解析error!!";
            log.warn("出参json解析error", e);
        }
        // 打印类名，方法名，方法调用时间，返回参数
        log.info(className + "." + methodName + ",time=" + time + "，出参:" + outputArgJson);
    }

    private Object[] ignoreHttpRequest(Object[] args) {
        if (args == null) return null;
        for (Object o : args) {
            if (o instanceof HttpServletRequest) {
                args = ArrayUtils.removeElement(args, o);
                return args;
            }
        }
        return args;
    }
}