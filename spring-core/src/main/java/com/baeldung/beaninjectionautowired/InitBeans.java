package com.baeldung.beaninjectionautowired;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class InitBeans {

    AnnotationConfigApplicationContext ctx;

    AttributeBean attr;
    ConstructorParamBean cons;
    SetterMethodBean setter;

    public InitBeans() {
        super();
        init();
    }

    private void init() {
        ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        attr = ctx.getBean("attributeBean", AttributeBean.class);
        cons = ctx.getBean("constructorParamBean", ConstructorParamBean.class);
        setter = ctx.getBean("setterMethodBean", SetterMethodBean.class);
    }
    
    public void close() {
        ctx.close(); 
    }

    public AnnotationConfigApplicationContext getCtx() {
        return ctx;
    }

    public AttributeBean getAttr() {
        return attr;
    }

    public ConstructorParamBean getCons() {
        return cons;
    }

    public SetterMethodBean getSetter() {
        return setter;
    }
    
    public static void main(String[] args) {
        InitBeans tb = new InitBeans();
        tb.init();
        System.out.println(tb.getAttr().tukTukBean());
        System.out.println(tb.getCons().tukTukBean());
        System.out.println(tb.getSetter().tukTukBean());
        tb.close();
    }
}
