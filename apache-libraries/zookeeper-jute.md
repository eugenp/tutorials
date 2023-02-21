# zookeeper-jute



## 三方 zookeeper jute工具类

<https://github.com/wangfucai/zookeeper-jute>

ZooKeeper代码中包含rcc.jj和zookeeper.jute。

rcc.jj是Hadoop Record语法文件，通过使用JavaCC语法分析生成器生成相应的JAVA解析文件。 其中通过安装JavaCC Plug-in（一个用于辅助JavaCC应用程序开发的Eclipse）插件编译rcc.jj可以生成对应的语法分析JAVA文件。 具体生成的文件包含在src/org/apache/jute/compiler/generated中。 其中JavaCC类似于C语言的Lex/Flex词法、语法解析器。

具体JavaCC的使用可参考<http://blog.csdn.net/bhq2010/article/details/8763920>。

通过编译rcc.jj生成的语法分析文件放入zookeeper的jute工程中，执行rcc -l java zookeeper.jute会读取zookeeper当中的类定义和变量声明自动生成相应模块的序列化和反序列文件。具体生成目录文件见org/apache/zookeeper。
