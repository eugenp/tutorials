Bean Life Cycle     Bean 的 生命周期  

传统的Java应用，Bean的生命周期简单。使用Java关键字new进行Bean实例化，然后该Bean就可以使用了。
一旦该Bean不再被使用，则由Java自动进行垃圾回收。  

相比之下， Spring 容器中的Bean的生命周期就细腻多了，理解Spring Bean 的生命周期后，可以利用提供的扩展点来自定义Bean的创建过程。

下图 展示了Bean装载到Spring应用上下文中的典型的生命周期过程。  

1. 实例化
   Spring 对 Bean 进行实例化.
2. 填充属性
   Spring 将值和Bean的引用注入进Bean对应的属性中.     
3. 调用 BeanNameAware 的 setBeanName()方法
   如果 Bean 实现了 BeanNameAware 接口， Spring 将 Bean 的 ID 传递给 setBeanName() 接口方法.
4. 调用 BeanFactoryAware 的 setBeanFactory()方法
   如果 Bean 实现了 BeanFactoryAware 接口， Spring 将调用 setBeanFactory() 接口方法， 将 BeanFactory 容器实例传入。
5. 调用 ApplicationContextAware 的 setApplicationContext() 方法
   如果Bean 实现了 ApplicationContextAware 接口， Spring 将调用 setApplicationContext() 接口方法， 将应用上下文的引用传入。
6. 调用 BeanPostProcessore 的预初始化方法
   如果 Bean 实现了 BeanPostProcessor 接口， Spring 将调用它们的 postProcessBeforeInitialization() 接口方法。
7. 调用 InitializingBean 的 afterPorpertiesSet()方法
   如果 Bean 实现了 InitializingBean 接口， Spring 将调用它们的 afterPropertiesSet() 接口方法。类似地， 如果Bean使用init-method声明了  
   初始化方法，该方法也会被调用。
8. 调用 定制的初始化方法
   如果 Bean 实现了 BeanPostProcessor 接口， Spring 将调用它们的 postPoressAfterInitialization() 方法。
9. 调用 BeanPostProcessors 的后初始化方法
   此时，Bean 已经准备就绪， 可以被应用程序使用， 它们将一直驻留在应用上下文中，直到该应用上下文被销毁。
---------------------------------------------
Bean 可以被使用了
--------------------------------------------
容器关闭 

10. 调用 DisposableBean 的destroy方法
    如果Bean 实现了 DisposableBean 接口， Spring 将调用它的 destroy()接口方法。同样， 如果 Bean 使用 destroy-method 声明了 销毁方法，该方法也会被调用。

11. 调用定制的销毁方法

这样就创建和加载一个Spring容器。

The life cycle of a Spring bean is easy to understand. When a bean is instantiated, it may be required to perform some initialization to get it into a usable state. Similarly, when the bean is no longer required and is removed from the container, some cleanup may be required.

Though, there is lists of the activities that take place behind the scenes between the time of bean Instantiation and its destruction, but this chapter will discuss only two important bean lifecycle callback methods which are required at the time of bean initialization and its destruction.

To define setup and teardown for a bean, we simply declare the <bean> with init-method and/or destroy-method parameters. The init-method attribute specifies a method that is to be called on the bean immediately upon instantiation. Similarly, destroy-method specifies a method that is called just before a bean is removed from the container.



