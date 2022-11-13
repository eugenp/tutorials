# Spring MVC XML

This module contains articles about Spring MVC with XML configuration

## 探索SpringMVC的表单标签库

1. 概述
   在本系列的[第一篇](https://www.baeldung.com/spring-mvc-form-tutorial)文章，介绍了表单标签库的使用以及如何将数据绑定到控制器中。

   在这篇文章中，我们将介绍Spring MVC提供的各种标签，帮助我们创建和验证表单(create and validate forms)。
2. input标签
   我们将从输入标签开始。这个标签默认使用绑定的值和type='text'来渲染一个HTML输入标签。

   `<form:input path="name" />`

   从Spring 3.1开始，你可以使用其他HTML5特有的类型，如电子邮件、日期和其他。例如，如果我们想创建一个电子邮件字段，我们可以使用type='email'。

   `<form:input type="email" path="email" />`

   同样地，如果要创建一个日期字段，我们可以使用type='date'，这将在许多兼容HTML5的浏览器中呈现一个日期选择器。

   `<form:input type="date" path="dateOfBirth" />`

3. password标签

   这个标签使用绑定的值渲染了一个type='password'的HTML输入标签。这个HTML输入掩盖了打入字段的值。

   `<form:password path="password" />

4. textarea标签

   这个标签渲染了一个HTML文本区域。

   `<form:textarea path="notes" rows="3" cols="20" />`

   我们可以用与HTML textarea相同的方式指定行和列的数量。

5. checkbox和checkboxes标签

   checkbox标签渲染了一个type='checkbox'的HTML输入标签。Spring MVC的表单标签库为checkbox标签提供了不同的方法，应该可以满足我们所有的checkbox需求。

   `<form:checkbox path="receiveNewsletter" />`

   上面的例子生成了一个经典的单选框，有一个布尔值。如果我们将绑定的值设置为true，这个复选框将被默认选中。

   下面的例子生成了多个复选框。在这种情况下，复选框的值是在JSP页面里面硬编码的。

    ```html
    Bird watching: <form:checkbox path="hobbies" value="Bird watching"/>
    Astronomy: <form:checkbox path="hobbies" value="Astronomy"/>
    Snowboarding: <form:checkbox path="hobbies" value="Snowboarding"/>
    ```

   这里，绑定的值是数组或java.util.Collection的类型。

   `String[] hobbies;`

   checkboxes标签的目的是用来渲染多个复选框，其中的复选框值是在运行时生成的。

   `<form:checkboxes items="${favouriteLanguageItem}" path="favoriteLanguage" />`

   为了生成这些值，我们在items属性中传递一个数组、一个列表或一个包含可用选项的map。我们可以在控制器中初始化我们的值。

    ```java
    List<String> favouriteLanguageItem = new ArrayList<String>();
    favouriteLanguageItem.add("Java");
    favouriteLanguageItem.add("C++");
    favouriteLanguageItem.add("Perl");
    ```

   通常情况下，绑定的属性是一个集合，所以它可以容纳用户选择的多个值。

   `List<String> favouriteLanguage;`

6. radiobutton和radiobuttons标签

   这个标签渲染了一个type='radio'的HTML输入标签。

    ```html
    Male: <form:radiobutton path="sex" value="M"/>
    Female: <form:radiobutton path="sex" value="F"/>
    ```

   一个典型的使用模式会涉及到多个标签实例，它们的不同值被绑定到同一个属性。

   `private String sex;`

   就像checkboxes标签一样，radiobuttons标签也会渲染多个type='radio'的HTML输入标签。

   `<form:radiobuttons items="${jobItem}" path="job" />`

   在这种情况下，我们可能想把可用的选项作为一个数组、一个列表或一个包含 items 属性中可用选项的 Map 传入。

    ```java
    List<String> jobItem = new ArrayList<String>();
    jobItem.add("Full time");
    jobItem.add("Part time");
    ```

7. select标签

    这个标签渲染了一个HTML选择元素。

    `<form:select path="country" items="${countryItems}" />`

    为了生成数值，我们在 items 属性中传递一个数组、一个列表或一个包含可用选项的地图。再一次，我们可以在控制器中初始化我们的值。

    ```java
    Map<String, String> countryItems = new LinkedHashMap<String, String>();
    countryItems.put("US", "United States");
    countryItems.put("IT", "Italy");
    countryItems.put("UK", "United Kingdom");
    countryItems.put("FR", "France");
    ```

    select标签也支持使用嵌套的option和options标签。

    option标签渲染一个单一的HTML选项，而options标签则渲染一个HTML选项标签的列表。

    options标签和select标签一样，在items属性中接受一个数组、一个列表或一个包含可用选项的Map。

    ```html
    <form:select path="book">
        <form:option value="-" label="--请选择--"/>
        <form:options items="${books}" />
    </form:select>
    ```

    当我们有需要一次选择几个项目时，我们可以创建一个多重列表框。要呈现这种类型的列表，只需在选择标签中添加multiple="true"属性。

    `<form:select path="fruit" items="${fruit}" multiple="true"/>`

    这里的绑定属性是一个数组或一个java.util.Collection。

    `List<String> fruit`

8. 隐藏的标签

   此标记使用绑定值呈现type='hidden'的HTML输入标记：

   `<form:hidden path="id" value="12345" />`

9. Errors标签

   字段错误消息由与控制器关联的验证器生成。我们可以使用错误标记来呈现这些字段错误消息：

   `<form:errors path="name" cssClass="error" />`

   这将显示路径属性中指定的字段的错误。默认情况下，错误消息在span标记中呈现，路径值后面附加.errors作为id，还可以选择cssClass属性中的CSS类，该类可用于设置输出的样式：

   `<span id="name.errors" class="error">Name is required!</span>`

   要用不同的元素而不是默认的span标记来封装错误消息，我们可以在元素属性中指定首选元素：

   `<form:errors path="name" cssClass="error" element="div" />`

   这将在div元素中呈现错误消息：

   `<div id="name.errors" class="error">Name is required!</div>`

   除了能够显示特定输入元素的错误之外，我们还可以显示给定页面的整个错误列表（不考虑字段）。这是通过使用通配符*实现的：

   `<form:errors path="*" />`

### 验证器

为了显示一个给定字段的错误，我们需要定义一个验证器Validator。

```java
public class PersonValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Person.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.name");
    }
}
```

在这种情况下，如果字段名是空的，验证器会从资源包中返回由required.name标识的错误信息。

资源包在Spring的XML配置文件中定义如下。

```xml
<bean class="org.springframework.context.support.ResourceBundleMessageSource" id="messageSource">
     <property name="basename" value="messages" />
</bean>
```

或者用纯Java的配置风格。

```java
@Bean
public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("messages");
    return messageSource;
}
```

错误信息是在messages.properties文件里面定义的。

`required.name = Name is required!`

为了应用这个验证，我们需要在控制器中包含一个对验证器的引用，并在控制器方法中调用验证方法，该方法在用户提交表单时被调用。

```java
@RequestMapping(value = "/addPerson", method = RequestMethod.POST)
public String submit(
  @ModelAttribute("person") Person person, 
  BindingResult result, 
  ModelMap modelMap) {

    validator.validate(person, result);

    if (result.hasErrors()) {
        return "personForm";
    }
    
    modelMap.addAttribute("person", person);
    return "personView";
}
```

### JSR 303 Bean验证

从Spring 3开始，我们可以使用JSR 303（通过 @Valid annotation 注解）进行bean验证。要做到这一点，我们需要在classpath上有一个JSR303验证器框架。我们将使用Hibernate验证器（参考实现）。以下是我们需要在POM中包含的依赖关系。

```xml
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>7.0.5.Final</version>
</dependency>
```

> <https://hibernate.org/validator/releases/7.0/> The main change is that all the dependencies using javax. packages are now using jakarta.* packages.

为了使Spring MVC通过@Valid注解支持JSR 303验证，我们需要在Spring配置文件中启用以下内容。

`<mvc:annotation-driven/>`

或者在Java配置中使用相应的注解@EnableWebMvc。

```java
@EnableWebMvc
@Configuration
public class ClientWebConfigJava implements WebMvcConfigurer {
    // All web configuration will go here
}
```

接下来，我们需要用@Valid注解来注解我们要验证的控制器方法。

```java
@RequestMapping(value = "/addPerson", method = RequestMethod.POST)
public String submit(
  @Valid @ModelAttribute("person") Person person, 
  BindingResult result, 
  ModelMap modelMap) {
    if(result.hasErrors()) {
        return "personForm";
    }    
    modelMap.addAttribute("person", person);
    return "personView";
}
```

现在我们可以用Hibernate验证器注解来验证该实体的属性。

```java
@NotEmpty
private String password;
```

默认情况下，如果我们让密码输入字段为空，这个注解将显示 "may not be empty"。

我们可以通过在验证器示例中定义的资源包中创建一个属性来覆盖默认的错误信息。消息的关键遵循规则AnnotationName.entity.fieldname:

`NotEmpty.person.password = Password is required!`

## 在Spring中验证RequestParams和PathVariables

1. 简介

    在本教程中，我们将学习如何在Spring MVC中验证HTTP请求参数和路径变量。

    具体来说，我们将用[JSR 303注解](https://beanvalidation.org/1.0/spec/)来验证字符串和数字参数。

    要探索其他类型的验证，我们可以参考我们关于[Java Bean验证](https://www.baeldung.com/javax-validation)和[方法约束](https://www.baeldung.com/javax-validation-method-constraints)的教程，或者我们可以学习[如何创建自己的验证器](https://www.baeldung.com/spring-mvc-custom-validator)。

2. 配置

    为了使用Java验证API，我们必须添加一个JSR 303实现，例如hibernate-validator。

    我们还必须通过添加@Validated注解来启用控制器中的请求参数和路径变量的验证。

    ```java
    @RestController
    @RequestMapping("/")
    @Validated
    public class Controller {
        // ...
    }
    ```

    值得注意的是，启用参数验证还需要一个**MethodValidationPostProcessor Bean**。如果我们使用的是Spring Boot应用程序，那么这个Bean是自动配置的，因为我们的classpath上有hibernate-validator依赖。

    否则，在标准的Spring应用程序中，我们必须明确添加这个Bean。

    ```java
    @EnableWebMvc
    @Configuration
    @ComponentScan("com.baeldung.spring")
    public class ClientWebConfigJava implements WebMvcConfigurer {
        @Bean
        public MethodValidationPostProcessor methodValidationPostProcessor() {
            return new MethodValidationPostProcessor();
        }
        // ...
    }
    ```

    默认情况下，Spring中路径或请求验证过程中的任何错误都会导致HTTP 500响应。在本教程中，我们将使用[ControllerAdvice](https://www.baeldung.com/exception-handling-for-rest-with-spring)的一个自定义实现，以更可读的方式处理这类错误，对任何不良请求返回HTTP 400。我们可以在GitHub上找到这个解决方案的[源代码](https://github.com/eugenp/tutorials/tree/master/spring-web-modules/spring-mvc-xml)。

3. 验证一个RequestParam

    让我们考虑一个例子，我们将一个数字的工作日作为请求参数传入控制器方法。

    ```java
    @GetMapping("/name-for-day")
    public String getNameOfDayByNumber(@RequestParam Integer dayOfWeek) {
        // ...
    }
    ```

    我们的目标是确保dayOfWeek的值在1到7之间。为了做到这一点，我们将使用@Min和@Max注解。

    ```java
    @GetMapping("/name-for-day")
    public String getNameOfDayByNumber(@RequestParam @Min(1) @Max(7) Integer dayOfWeek) {
        // ...
    }
    ```

    任何不符合这些条件的请求都会返回HTTP状态400，并带有一个默认的错误信息。

    例如，如果我们调用<http://localhost:8080/name-for-day?dayOfWeek=24>，响应信息将是。

    `getNameOfDayByNumber.dayOfWeek: must be less than or equal to 7`

    我们可以通过添加一个自定义的信息来改变默认的信息。

    `@Max(value = 1, message = “day number has to be less than or equal to 7”)`

4. 验证一个PathVariable

    就像@RequestParam一样，我们可以使用javax.validation.constraints包中的任何注解来验证一个@PathVariable。

    让我们考虑一个例子，我们验证一个字符串参数不是空白的，并且长度小于或等于10。

    ```java
    @GetMapping("/valid-name/{name}")
    public void createUsername(@PathVariable("name") @NotBlank @Size(max = 10) String username) {
        // ...
    }
    ```

    例如，任何带有超过10个字符的名字参数的请求，都会导致HTTP 400错误，并有一个消息。

    `createUser.name:size must be between 0 and 10`

    通过设置@Size注解中的消息参数，可以很容易地覆盖默认的消息。

## 调试Spring MVC 404 "没有找到HTTP请求的映射 "错误

1. 简介

    Spring MVC是使用Front Controller Pattern构建的传统应用程序。[DispatcherServlet](https://www.baeldung.com/spring-dispatcherservlet)充当前端控制器，负责路由和请求处理。

    与任何web应用程序或网站一样，当找不到请求的资源时，SpringMVC会返回HTTP 404响应代码。在本教程中，我们将研究SpringMVC中404错误的常见原因。

2. 404响应的可能原因-错误的URI

    假设我们有一个GreetingController，它映射到/greeting并呈现greeting.jsp：

    ```java
    @Controller
    public class GreetingController {

        @RequestMapping(value = "/greeting", method = RequestMethod.GET)
        public String get(ModelMap model) {
            model.addAttribute("message", "Hello, World!");
            return "greeting";
        }
    }
    ```

    相应的视图呈现消息变量的值：

    ```html
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
        <head>
            <title>Greeting</title>
        </head>
        <body>
            <h2>${message}</h2>
        </body>
    </html>
    ```

    正如预期的那样，向/问候发送GET请求是有效的：

    `curl http://localhost:8080/greeting`

    我们将看到一个带有“Hello World”消息的HTML页面。

    看到404的最常见原因之一是使用了错误的URI。例如，向/greetings而不是/greeting发出GET请求是错误的。

    在这种情况下，我们会在服务器日志中看到一条警告消息：

    ```log
    [http-nio-8080-exec-6] WARN  o.s.web.servlet.PageNotFound - 
    No mapping found for HTTP request with URI [/greetings] in DispatcherServlet with name 'mvc'
    ```

    客户端将看到一个错误页面：

    ```html
    <html>
        <head>
            <title>Home</title>
        </head>
        <body>
            <h1>Http Error Code : 404. Resource not found</h1>
        </body>
    </html>
    ```

    为了避免这种情况，我们需要确保正确输入了URI。

3. 404响应的可能原因-错误的Servlet映射

    如前所述，DispatcherServlet是SpringMVC中的前端控制器。因此，就像在标准的基于servlet的应用程序中一样，我们需要使用web.xml文件为servlet创建映射。

    我们在servlet标记内定义servlet，并将其映射到servlet-mapping标记内的URI。我们需要确保 url-pattern 的值是正确的，因为在servlet映射到“/*”很常见-请注意后面的星号：

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app ...>
        <!-- Additional config omitted -->
        <servlet>
            <servlet-name>mvc</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <load-on-startup>1</load-on-startup>
        </servlet>
        <servlet-mapping>
            <servlet-name>mvc</servlet-name>
            <url-pattern>/*</url-pattern>
        </servlet-mapping>
        <!-- Additional config omitted -->
    </web-app>
    ```

    现在，如果我们请求/问候，我们会在服务器日志中看到一条警告：

    ```log
    WARN  o.s.web.servlet.PageNotFound - No mapping found for HTTP request with URI 
    [/WEB-INF/view/greeting.jsp] in DispatcherServlet with name 'mvc'
    ```

    这一次，错误陈述找不到greeting.jsp，用户会看到一个空白页面。

    要修复此错误，我们需要将DispatcherServlet映射到“/”（不带尾随星号）：修复映射后，一切都应该正常工作。请求/问候现在显示消息“你好，世界！”。

    问题背后的原因是，如果我们将DispatcherServlet映射到/*，那么我们会告诉应用程序，到达应用程序的每个请求都将由Dispatcher Servlet提供服务。然而，这不是一种正确的方法，因为DispatcherServlet无法做到这一点。相反，SpringMVC期望ViewResolver的实现为JSP文件等视图提供服务。

## code

我们探讨了Spring提供的用于处理表单的各种标签。

我们还看了用于显示验证错误的标签以及显示自定义错误信息所需的配置。

学习了如何在Spring应用程序中验证请求参数和路径变量。

当项目在本地运行时，可以通过以下地址访问表单示例。

<http://localhost:8080/spring-mvc-xml/person>

### TODO

spring MVC 与 springBoot 版本不匹配 导致运行 SpringContextTest.java 报错：

```log
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'requestMappingHandlerMapping' defined in class path resource [org/springframework/web/servlet/config/annotation/DelegatingWebMvcConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping]: Factory method 'requestMappingHandlerMapping' threw exception; nested exception is java.lang.ClassCastException: org.springframework.web.accept.ContentNegotiationManagerFactoryBean$$EnhancerBySpringCGLIB$$6dd798c1 cannot be cast to org.springframework.web.accept.ContentNegotiationManager
```

## Relevant Articles

- [x] [Exploring SpringMVC’s Form Tag Library](https://www.baeldung.com/spring-mvc-form-tags)
- [x] [Validating RequestParams and PathVariables in Spring](https://www.baeldung.com/spring-validate-requestparam-pathvariable)
- [x] [Debugging the Spring MVC 404 “No mapping found for HTTP request” Error](https://www.baeldung.com/spring-mvc-404-error)
- More articles: [[<-- prev]](../spring-mvc-xml)
