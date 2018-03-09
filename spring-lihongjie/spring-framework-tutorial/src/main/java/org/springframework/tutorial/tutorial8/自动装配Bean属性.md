自动装配Bean属性 （autowire）


当涉及自动装配Bean的依赖关系时， Spring 有多种处理方式。因此， Spring 提供了4种各具特色的自动装配策略。
byName  把 与 Bean 的属性具有相同名字 或者 ID 的其他 Bean自动装配到Bean的对应属性中。如果没有跟属性的名字相匹配的Bean，则该属性不进行装配。

    byName 自动装配遵循一项约定： 为属性自动装配ID与该属性的名字相同的Bean. 通过设置 autowire属性为byName, Spring特殊对待 id为kenny的所有属性。


byType  把与 Bean的属性具有相同类型的其他Bean自动装配到Bean的对应属性中。如果没有跟属性的类型
相匹配的Bean，则该属性不被装配。
    byType 不再是匹配属性的名字而是检查属性的类型。 当我们尝试使用byType自动装配时，Spring会寻找哪一个Bean的类型与属性的类型相匹配。（多个相同类型的bean时抛出异常）

constructor 把与Bean的构造器入参具有相同类型的其他Bean自动装配到Bean构造器的对应入参中。

autodetect 首先尝试使用 constructor 进行自动装配。如果失败，再尝试使用byType进行自动装配。


---------------------------------------------------
使用注解装配

Spring容器默认禁用注解装配，所以，在使用基于注解的自动装配前，

<context:annotation-config> 

Spring3 支持几种不同的用于自动装配的注解：

1. 自带的 @Autowired
2. @Inject
3. @Resource