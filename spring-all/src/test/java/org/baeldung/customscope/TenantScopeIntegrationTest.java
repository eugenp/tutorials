package org.baeldung.customscope;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TenantScopeIntegrationTest {

    @Test
    public final void whenRegisterScopeAndBeans_thenContextContainsFooAndBar() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        try {
            ctx.register(TenantScopeConfig.class);
            ctx.register(TenantBeansConfig.class);
            ctx.refresh();

            TenantBean foo = (TenantBean) ctx.getBean("foo", TenantBean.class);
            foo.sayHello();
            TenantBean bar = (TenantBean) ctx.getBean("bar", TenantBean.class);
            bar.sayHello();
            Map<String, TenantBean> foos = ctx.getBeansOfType(TenantBean.class);

            assertThat(foo, not(equalTo(bar)));
            assertThat(foos.size(), equalTo(2));
            assertTrue(foos.containsValue(foo));
            assertTrue(foos.containsValue(bar));

            BeanDefinition fooDefinition = ctx.getBeanDefinition("foo");
            BeanDefinition barDefinition = ctx.getBeanDefinition("bar");

            assertThat(fooDefinition.getScope(), equalTo("tenant"));
            assertThat(barDefinition.getScope(), equalTo("tenant"));
        } finally {
            ctx.close();
        }
    }

    @Test
    public final void whenComponentScan_thenContextContainsFooAndBar() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        try {
            ctx.scan("org.baeldung.customscope");
            ctx.refresh();

            TenantBean foo = (TenantBean) ctx.getBean("foo", TenantBean.class);
            foo.sayHello();
            TenantBean bar = (TenantBean) ctx.getBean("bar", TenantBean.class);
            bar.sayHello();
            Map<String, TenantBean> foos = ctx.getBeansOfType(TenantBean.class);

            assertThat(foo, not(equalTo(bar)));
            assertThat(foos.size(), equalTo(2));
            assertTrue(foos.containsValue(foo));
            assertTrue(foos.containsValue(bar));

            BeanDefinition fooDefinition = ctx.getBeanDefinition("foo");
            BeanDefinition barDefinition = ctx.getBeanDefinition("bar");

            assertThat(fooDefinition.getScope(), equalTo("tenant"));
            assertThat(barDefinition.getScope(), equalTo("tenant"));
        } finally {
            ctx.close();
        }
    }
}
