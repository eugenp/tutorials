# Spring Cloud Contract

This module contains articles about Spring Cloud Contract

1. 简介

    [Spring Cloud Contract](https://cloud.spring.io/spring-cloud-contract/)是一个项目，简单地说，它可以帮助我们编写消费者驱动的合同 [Consumer-Driven Contracts（CDC）](https://martinfowler.com/articles/consumerDrivenContracts.html)。

    这确保了分布式系统中生产者和消费者之间的契约--基于HTTP和基于消息的交互。

    在这篇快速文章中，我们将探讨通过HTTP交互为Spring Cloud Contract编写生产者和消费者方面的测试案例。

2. 生产者--服务器端

    我们将以EvenOddController的形式编写一个生产者侧(producer side)的CDC--它只是告诉数字参数是偶数还是奇数。

    ```java
    @RestController
    public class EvenOddController {

        @GetMapping("/validate/prime-number")
        public String isNumberPrime(@RequestParam("number") Integer number) {
            return Integer.parseInt(number) % 2 == 0 ? "Even" : "Odd";
        }
    }
    ```

    1. Maven依赖性

        在生产者方面，我们需要[spring-cloud-starter-contract-verifier](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.springframework.cloud%22%20AND%20a%3A%22spring-cloud-starter-contract-verifier%22)这个依赖项。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-contract-verifier</artifactId>
            <version>2.1.1.RELEASE</version>
            <scope>test</scope>
        </dependency>
        ```

        我们还需要用基础测试类的名称配置[spring-cloud-contract-maven-plugin](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.springframework.cloud%22%20AND%20a%3A%22spring-cloud-contract-maven-plugin%22)，我们将在下一节介绍。

        ```xml
        <plugin>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-contract-maven-plugin</artifactId>
            <version>2.1.1.RELEASE</version>
            <extensions>true</extensions>
            <configuration>
                <baseClassForTests>
                    com.baeldung.spring.cloud.springcloudcontractproducer.BaseTestClass
                </baseClassForTests>
            </configuration>
        </plugin>
        ```

    2. 生产者方面的设置

        我们需要在测试包中添加一个基类，加载我们的Spring上下文。

        ```java
        @RunWith(SpringRunner.class)
        @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
        @DirtiesContext
        @AutoConfigureMessageVerifier
        public class BaseTestClass {

            @Autowired
            private EvenOddController evenOddController;

            @Before
            public void setup() {
                StandaloneMockMvcBuilder standaloneMockMvcBuilder 
                = MockMvcBuilders.standaloneSetup(evenOddController);
                RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
            }
        }
        ```

        在/src/test/resources/contracts/包中，我们将添加测试存根(stubs)，比如文件shouldReturnEvenWhenRequestParamIsEven.groovy中的这个。

        ```groovy
        import org.springframework.cloud.contract.spec.Contract
        Contract.make {
            description "should return even when number input is even"
            request{
                method GET()
                url("/validate/prime-number") {
                    queryParameters {
                        parameter("number", "2")
                    }
                }
            }
            response {
                body("Even")
                status 200
            }
        }
        ```

        当我们运行构建时，该插件会自动生成一个名为ContractVerifierTest的测试类，它扩展了我们的BaseTestClass，并将其放在/target/generated-test-sources/contracts/中。

        测试方法的名称是由前缀 "validate_"和我们的Groovy测试存根的名称连接而成的。对于上述Groovy文件，生成的方法名称将是 "validate_shouldReturnEvenWhenRequestParamIsEven"。

        让我们看一下这个自动生成的测试类。

        ```java

        public class ContractVerifierTest extends BaseTestClass {

            @Test
            public void validate_shouldReturnEvenWhenRequestParamIsEven() throws Exception {
                // given:
                MockMvcRequestSpecification request = given();

                // when:
                ResponseOptions response = given().spec(request)
                .queryParam("number","2")
                .get("/validate/prime-number");

                // then:
                assertThat(response.statusCode()).isEqualTo(200);
                
                // and:
                String responseBody = response.getBody().asString();
                assertThat(responseBody).isEqualTo("Even");
            }
        }
        ```

        构建过程中还会在我们的本地Maven仓库中添加存根jar，以便我们的消费者可以使用它。

        存根将出现在输出文件夹的stubs/mapping/下。

3. 消费者--客户端

    我们CDC的消费者端将通过HTTP交互消耗生产者端生成的存根，以维护合约，所以生产者端的任何改变都会破坏合约。

    我们将添加BasicMathController，它将发出HTTP请求，从生成的存根中获取响应。

    ```java
    @RestController
    public class BasicMathController {

        @Autowired
        private RestTemplate restTemplate;

        @GetMapping("/calculate")
        public String checkOddAndEven(@RequestParam("number") Integer number) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");

            ResponseEntity<String> responseEntity = restTemplate.exchange(
            "http://localhost:8090/validate/prime-number?number=" + number,
            HttpMethod.GET,
            new HttpEntity<>(httpHeaders),
            String.class);

            return responseEntity.getBody();
        }
    }
    ```

    1. Maven的依赖性

        对于我们的消费者，我们需要添加 [spring-cloud-contract-wiremock](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.springframework.cloud%22%20AND%20a%3A%22spring-cloud-contract-wiremock%22) 和 [spring-cloud-contract-stub-runner](https://search.maven.org/classic/#search%7Cgav%7C1%7Cg%3A%22org.springframework.cloud%22%20AND%20a%3A%22spring-cloud-contract-stub-runner%22) 依赖项。

        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-contract-wiremock</artifactId>
            <version>2.1.1.RELEASE</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-contract-stub-runner</artifactId>
            <version>2.1.1.RELEASE</version>
            <scope>test</scope>
        </dependency>
        ```

    2. 消费者端设置

        现在是时候配置我们的存根运行器了，它将告知我们的消费者本地Maven仓库中的可用存根。

        ```java
        @RunWith(SpringRunner.class)
        @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
        @AutoConfigureMockMvc
        @AutoConfigureJsonTesters
        @AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "com.baeldung.spring.cloud:spring-cloud-contract-producer:+:stubs:8090")
        public class BasicMathControllerIntegrationTest {

            @Autowired
            private MockMvc mockMvc;

            @Test
            public void given_WhenPassEvenNumberInQueryParam_ThenReturnEven()
            throws Exception {
        
                mockMvc.perform(MockMvcRequestBuilders.get("/calculate?number=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Even"));
            }
        }
        ```

        请注意，@AutoConfigureStubRunner 注解的 ids 属性指定。

        - com.baeldung.spring.cloud - 我们工件的groupId
        - spring-cloud-contract-producer - 生产者存根jar的artifactId
        - 8090 - 生成的存根将在哪个端口运行

4. 当合同被破坏时

    如果我们在生产者端做了任何直接影响合同的改变，而没有更新消费者端，这就会导致合同失败。

    例如，假设我们要在生产者端将EvenOddController的请求URI改为/validate/change/prime-number。

    如果我们没有通知消费者这一变化，消费者仍然会向/validate/prime-number URI发送请求，而消费者端测试案例会抛出org.springframework.web.client.HttpClientErrorException:404未找到。

5. 总结

    我们已经看到了Spring Cloud Contract是如何帮助我们维护服务消费者和生产者之间的合约的，这样我们就可以推送新的代码而不用担心破坏合约。

    像往常一样，本教程的完整实现可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-contract)上找到。

## Relevant Articles

- [x] [An Intro to Spring Cloud Contract](http://www.baeldung.com/spring-cloud-contract)
