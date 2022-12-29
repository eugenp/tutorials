# 在代理服务器后面使用Maven

在本教程中，我们将配置Maven，使其在代理服务器后工作--这是在不直接连接互联网的环境下常见的情况。

在我们的例子中，我们的代理在'proxy.baeldung.com'机器上运行，它通过HTTP'80'端口监听代理请求。我们还将在internal.baeldung.com上使用一些内部网站，我们不需要通过代理。

1. 代理配置

    首先，让我们设置一个基本的代理配置，不需要任何凭证。

    让我们编辑Maven settings.xml，通常在`<user_home>/.m2`目录下找到。如果还没有，我们可以从`'<maven_home>/conf'`目录下的全局设置中复制它。

    现在我们在`<proxies>`部分创建一个`<proxy>`条目。

    由于我们还使用了一个不需要通过代理的本地站点，让我们在`<nonProxyHosts>`中用'|'分隔的列表指定它与我们的本地主机。

2. 添加凭证

    如果我们的代理不安全，这就是我们所需要的；然而，我们的代理是安全的，所以让我们在代理定义中添加我们的证书。

    如果我们不需要，就不要添加用户名/密码项--甚至是空的--因为当代理不需要它们时，它们的存在会导致我们的请求被拒绝。

    我们最小的认证配置现在看起来应该是这样的。

    ```xml
    <proxies>
    <proxy>
            <host>proxy.baeldung.com</host>
            <port>80</port>
            <username>baeldung</username>
            <password>changeme</password>
            <nonProxyHosts>internal.baeldung.com|localhost|127.*|[::1]</nonProxyHosts>
        </proxy>
    </proxies>
    ```

    现在，当我们运行mvn命令时，我们将通过代理连接到我们想要的网站。

    1. 可选条目

        让我们给它一个可选的id'BaeldungProxy_Authenticated'，以使它更容易被引用，以防我们需要切换代理服务器。

        `<id>BaeldungProxy_Authenticated</id>`

        现在，如果我们有另一个代理，可以再添加一个代理定义，但只能有一个是活动的。默认情况下，Maven会使用它找到的第一个活跃的代理定义。

        代理定义默认是激活的，并获得隐含的定义。

        `<active>true</active>`

        如果我们想让另一个代理成为活跃的代理，那么我们就把`<active>`设为false，使原来的条目不再活跃。

        Maven对代理协议的默认值是HTTP，这适用于大多数情况。如果我们的代理使用不同的协议，我们可以在这里声明，然后用我们的代理需要的协议替换http。

        `<protocol>http</protocol>`

        注意，这是代理使用的协议--我们的请求（ftp://, http://, https://）的协议与此无关。

        这是我们扩展的代理定义的样子，包括可选元素。

        proxy/settings.xml

        那么，这就是我们的基本代理条目，但它对我们来说足够安全吗？

3. 确保我们的配置安全

    现在，假设我们的一个同事希望我们把代理配置发给他们。

    我们并不热衷于发送纯文本的密码，所以让我们看看Maven是如何轻松[加密密码](https://maven.apache.org/guides/mini/guide-encryption.html)的。

    1. 创建一个主密码

        首先，我们选择一个主密码，比如 "te!st!ma$ter"。

        现在让我们对主密码进行加密，在运行时的提示符下输入。

        ```bash
        mvn --encrypt-master-password
        Master Password:
        ```

        在我们按回车键后，我们看到我们的加密密码被括在大括号中。

        `{QFMlh/6WjF8H9po9UDo0Nv18e527jqWb6mUgIB798n4=}`

    2. 密码生成的故障排除

        如果我们看到的是{}而不是主密码：提示（这在使用bash时可能发生），那么我们就需要在命令行中指定密码。

        让我们用引号把密码包起来，以确保任何特殊字符如'!'不会产生不必要的影响。

        因此，如果我们使用bash，让我们使用单引号。

        `mvn --encrypt-master-password 'te!st!ma$ter'`

        或者如果使用Windows命令提示符，则使用双引号。

        `mvn --encrypt-master-password "te!st!ma$ter"`

        现在，有时我们生成的主密码包含大括号，比如这个例子，在'UD'后面有一个闭合的大括号，'}'。

        {QFMlh/6WjF8H9po9UD}0Nv18e527jqWb6mUgIB798n4=}

        在这种情况下，我们可以

        - 再次运行mvn -encrypt-master-password命令，生成另一个密码（希望不要有大括号）
        - 在密码中的"{"或"}"前面加一个反斜杠，以逃避大括号。

    3. 创建一个settings-security.xml文件

        现在，让我们把我们的加密密码，加上转义的`'\}'`，放到.m2目录下一个叫做settings-security.xml的文件中。

        ```xml
        <settingsSecurity>
            <master>{QFMlh/6WjF8H9po9UD\}0Nv18e527jqWb6mUgIB798n4=}</master>
        </settingsSecurity>
        ```

        最后，Maven让我们在主元素中添加注释。

        让我们在密码'{'分隔符前添加一些文本，注意不要在注释中使用{或}，因为Maven用它们来查找密码。

        `<master>We escaped the curly brace with '\' {QFMlh/6WjF8H9po9UD\}0Nv18e527jqWb6mUgIB798n4=}</master>`

    4. 使用一个可移动的驱动器

        假设我们需要更加安全，想把我们的主密码存储在一个单独的设备上。

        首先，我们要把settings-security.xml文件放在一个可移动驱动器 "R: "的配置目录中。

        R:\config\settings-security.xml

        现在，我们要更新.m2目录下的settings-security.xml文件，将Maven重定向到可移动驱动器上的真正的settings-security.xml。

        ```xml
        <settingsSecurity>
            <relocation>R:\config\settings-security.xml</relocation>
        </settingsSecurity>
        ```

        现在，Maven将从可移动驱动器上我们在relocation元素中指定的文件中读取我们的加密主密码。

4. 加密代理密码

    现在我们有了一个加密的主密码，我们可以对代理密码进行加密。

    让我们运行以下命令，在提示符下输入我们的密码 "changeme"。

    ```bash
    mvn --encrypt-password
    Password:

    {U2iMf+7aJXQHRquuQq6MX+n7GOeh97zB9/4e7kkEQYs=}
    ```

    我们的最后一步是编辑settings.xml文件中的代理部分，并放入我们的加密密码。

    ```xml
    <proxies>
    <proxy>
            <id>BaeldungProxy_Encrypted</id>
            <host>proxy.baeldung.com</host>
            <port>80</port>
            <username>baeldung</username>
            <password>{U2iMf+7aJXQHRquuQq6MX+n7GOeh97zB9/4e7kkEQYs=}</password>
        </proxy>
    </proxies>
    ```

    保存这个，Maven现在应该能通过我们的代理连接到互联网，使用我们的加密密码。

5. 使用系统属性

    虽然通过设置文件配置Maven是推荐的方法，但我们可以[通过Java系统属性声明我们的代理配置](https://docs.oracle.com/javase/8/docs/technotes/guides/net/proxies.html)。

    如果我们的操作系统已经配置了一个代理，我们可以设置。

    `-Djava.net.useSystemProxies=true`

    另外，为了使其始终处于启用状态，如果我们有管理员权限，可以在`<JRE>/lib/net.properties`文件中设置。

    然而，我们要注意的是，虽然Maven本身会尊重这一设置，但并非所有插件都会这样做，所以我们使用这种方法仍可能出现连接失败。

    即使启用，我们也可以通过在http.proxyHost系统属性上设置我们的HTTP代理的详细信息来覆盖它。

    `-Dhttp.proxyHost=proxy.baeldung.com`

    我们的代理在默认的80端口上监听，但如果它在8080端口上监听，我们会配置http.proxyPort属性。

    `-Dhttp.proxyPort=8080`

    而对于我们不需要代理的网站。

    `-Dhttp.nonLocalHosts="internal.baeldung.com|localhost|127.*|[::1]"`

    所以，如果我们的代理在10.10.0.100，我们可以使用。

    `mvn compile -Dhttp.proxyHost=10.10.0.100 -Dhttp.proxyPort=8080 -Dhttp.nonProxyHosts=localhost|127.0.0.1`

    当然，如果我们的代理需要认证，我们也要添加。

    ```xml
    -Dhttp.proxyUser=baeldung
    -Dhttp.proxyPassword=changeme
    ```

    如果我们想让其中一些设置适用于所有的Maven调用，我们可以在MAVEN_OPTS环境变量中定义它们。

    `set MAVEN_OPTS= -Dhttp.proxyHost=10.10.0.100 -Dhttp.proxyPort=8080`

    现在，每当我们运行'mvn'时，这些设置将被自动应用--直到我们退出。

## Relevant Articles

- [x] [Using Maven Behind a Proxy](https://www.baeldung.com/maven-behind-proxy)
