# zookeeper-jute

<https://www.cnblogs.com/jing99/p/12737135.html>

对于一个网络通信，首先需要解决的就是对数据的序列化和反序列化处理，在ZooKeeper中，使用了Jute这一序列化组件来进行数据的序列化和反序列化操作。同时，为了实现一个高效的网络通信程序，良好的通信协议设计也是至关重要的。

Zookeeper团队曾想过将Jute替换成Apache的Avro或是Google的protobuf但是考虑到新老版本序列化组件的兼容性和当前Jute的序列化能力并不是ZooKeeper的性能瓶颈。

使用Jute进行序列化：

```java
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import lombok.Getter;
import lombok.Setter;
import org.apache.jute.BinaryInputArchive;
import org.apache.jute.BinaryOutputArchive;
import org.apache.jute.InputArchive;
import org.apache.jute.OutputArchive;
import org.apache.jute.Record;
import org.apache.zookeeper.server.ByteBufferInputStream;

@Getter
@Setter
public class MocHeader implements Record {
 
    private long sessionId;
    private String type;
 
    public MocHeader(){}
    public MocHeader(long sessionId, String type){
        this.sessionId = sessionId;
        this.type = type;
    }
 
    @Override
    public void serialize(OutputArchive outputArchive, String tag) throws IOException {
        outputArchive.startRecord(this, tag);
        outputArchive.writeLong(sessionId,"sessionId");
        outputArchive.writeString(type, "type");
        outputArchive.endRecord(this, tag);
    }
 
    @Override
    public void deserialize(InputArchive inputArchive, String tag) throws IOException {
        inputArchive.startRecord(tag);
        sessionId = inputArchive.readLong("sessionId");
        type = inputArchive.readString("type");
        inputArchive.endRecord(tag);
    }
 
    public static void main(String[] args) throws IOException {
 
        final String tag = "header";
        // 序列化
        ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
        BinaryOutputArchive boa = BinaryOutputArchive.getArchive(baos);
        new MocHeader(100001L, "MyHeader").serialize(boa, tag);
        // 包装成ByteBuffer 用于网络传输
        ByteBuffer bb = ByteBuffer.wrap(baos.toByteArray());
        // 反序列化
        ByteBufferInputStream bbis = new ByteBufferInputStream(bb);
        BinaryInputArchive bbia = BinaryInputArchive.getArchive(bbis);
        MocHeader header = new MocHeader();
        header.deserialize(bbia, tag);
        baos.close();
        bbis.close();
    }
}
```

## 三方 zookeeper jute工具类

<https://github.com/wangfucai/zookeeper-jute>

ZooKeeper代码中包含rcc.jj和zookeeper.jute。

rcc.jj是Hadoop Record语法文件，通过使用JavaCC语法分析生成器生成相应的JAVA解析文件。 其中通过安装JavaCC Plug-in（一个用于辅助JavaCC应用程序开发的Eclipse）插件编译rcc.jj可以生成对应的语法分析JAVA文件。 具体生成的文件包含在src/org/apache/jute/compiler/generated中。 其中JavaCC类似于C语言的Lex/Flex词法、语法解析器。

具体JavaCC的使用可参考<http://blog.csdn.net/bhq2010/article/details/8763920>。

通过编译rcc.jj生成的语法分析文件放入zookeeper的jute工程中，执行rcc -l java zookeeper.jute会读取zookeeper当中的类定义和变量声明自动生成相应模块的序列化和反序列文件。具体生成目录文件见org/apache/zookeeper。
