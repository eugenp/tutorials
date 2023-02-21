# Zookeeper入门教程

## Zookeeper ACL

如果我们对 Znode 操作失误，导致数据丢失，那么我们的应用也会受到影响，所以我们需要对 Znode 的操作加以控制。在 Zookeeper 中就是使用 Access Control Lists 访问控制列表的方式来避免这种情况的发生。本节我们就来介绍 Zookeeper ACL 的实现以及 ACL 的实现原理。

1. Zookeeper ACL 组成

    ACL 全称 Access Control Lists，访问控制列表。一个 Znode 的 ACL 可以分为 3 部分：Scheme:ID:Permission 

    - 授权模式 Scheme： 授权模式 Scheme 就是选择 Zookeeper 分配权限的方式，我们可以选择的授权方式有 6 种：
      - IP ： 我们可以使用 IP 或 IP 段来对授权对象进行授权；
      - HOST ： 我们可以使用主机名的后缀来对授权对象进行授权，如果我们设置的 host 为 imooc.com，就可以匹配 coding.imooc.com，class.imooc.com；
      - Auth ： 给所有的认证用户授权，需要使用 addauth digest username:password 命令添加认证用户，Zookeeper 会把密码使用 SHA-1 和 BASE64 算法进行加密；
      - Digest ： 使用用户名和密码的方式验证，不需要使用 addauth 添加认证用户，需要手动使用SHA-1 和 BASE64 算法对密码进行加密；
      - World ： 默认权限 world:anyone:cdrwa，任何人都可以对节点做任意操作。
      - Super ： 超级权限，授权对象可以对节点做任意操作；
    - 授权对象 ID： 授权对象的意思就是我们要把 Scheme 设置的权限授权给谁。
      - 如果我们设置的 Scheme 是 Auth ，那么授权对象就是所有使用 addauth digest username:password 命令添加认证用户，那么我们在设置 ACL 时就不需要写入授权对象了，例如：auth::crwda ；
      - 如果我们设置的 Scheme 是 Digest ，那么授权对象就是用户名和被加密后的密码，例如：digest:mooc:0xEep90zjLm6hbWMlQ2BGbaQWzI=:crwda ；
    - 权限信息 Permission： 权限信息是指对 Znode 操作的权限，一共有 5 种操作权限，分别是：
      - Create 节点创建权限： 授权对象可以在该节点下创建子节点；
      - Read 节点数据读取权限： 授权对象可以读取该节点的 data 以及其子节点的 stat 和 ACL；
      - Write 节点数据写入权限： 授权对象可以更新该节点的 data；
      - Delete 节点删除权限： 授权对象可以删除该节点的子节点；
      - Admin 节点 ACL修改权限： 授权对象可以修改节点的 ACL 权限信息。

    我们在设置权限信息时使用简写的方式，也就是使用权限信息的小写首字母 crwda 来表示每一种操作，需要哪种权限信息就写上哪种简写即可。

    介绍完 ACL 的组成，接下来我们来使用 Zookeeper 的客户端来实现 ACL 访问控制。

2. Zookeeper ACL 实现

    在这里我们使用 [zkCli.sh](http://zkcli.sh/) 命令行客户端的方式来设置节点的 ACL 信息。

    1. 命令行客户端设置 ACL

        我们使用 Zookeeper 的客户端 zkCli 命令行工具来设置节点的 ACL ，使用 zkCli.sh 连接 Zookeeper 服务端后，然后查看节点的 ACL 信息：

        ```bash
        # 创建 /imooc
        [zk: localhost:2181(CONNECTED) 1] create /imooc
        # 创建成功
        Created /imooc
        # 查询 /imooc 节点的 ACL 信息
        [zk: localhost:2181(CONNECTED) 2] getAcl /imooc
        # 输出 ACL
        'world,'anyone
        : cdrwa
        ```

        我们可以发现，imooc 节点的 ACL 信息为默认的 world:anyone:cdrwa.
        接下来我们使用 Auth 的方式改变该节点的 ACL 设置：

        ```bash
        # 添加认证用户 mooc，密码为 mooc
        [zk: localhost:2181(CONNECTED) 3] addauth digest mooc:mooc
        # 给 /imooc 节点设置 ACL，验证模式为 auth，授权对象为所有认证用户，所以可以不用填写参数，权限信息为所有操作
        [zk: localhost:2181(CONNECTED) 4] setAcl /imooc auth::cdrwa
        # 查看 /imooc 节点的 ACL 信息
        [zk: localhost:2181(CONNECTED) 5] getAcl /imooc
        # 输出 ACL
        'digest,'mooc:0xEep90zjLm6hbWMlQ2BGbaQWzI=
        : cdrwa
        ```

        我们可以看到 imooc 节点的 ACL 信息已经修改成功，密码也被加密了。接下来我们使用 Digest 的方式来设置节点的 ACL ：

        ```bash
        # 给 /imooc 节点设置 ACL，验证模式为 digest
        # 用户名为 mooc，密码是被加密后的 mooc，加密方式为 BASE64(SHA1(mooc))，权限信息为所有操作
        [zk: localhost:2181(CONNECTED) 6] [zk: localhost:2181(CONNECTED) 6] setAcl /imooc digest:mooc:0xEep90zjLm6hbWMlQ2BGbaQWzI=:cdrwa
        # 查看 /imooc 节点的 ACL 信息
        [zk: localhost:2181(CONNECTED) 7]  getAcl /imooc
        # 输出 ACL
        'digest,'mooc:0xEep90zjLm6hbWMlQ2BGbaQWzI=
        : cdrwa
        ```

        我们可以发现，使用 Auth 和 Digest 设置的 ACL 信息是相同的，都是基于 Digest 的授权模式，它们的区别是：

        - Auth 需要在设置 ACL 之前添加认证用户，并且无需手动加密，它的授权范围是所有的使用 addauth digest username:password 命令添加的用户。
        - Digest 不需要提前添加认证用户，但是需要自己完成密码的加密，而且授权范围只有当前设置的用户。

        > Tips： 我们需要注意，当前客户端添加的认证用户只针对当前客户端有效，客户端断开连接后也需要重新进行认证。

        如果此时客户端断开连接，再重新连接，或者新开一个客户端进行操作，访问节点可能会出现以下错误：

        ```bash
        [zk: localhost:2181(CONNECTED) 1] get /imooc
        # 错误 1
        org.apache.zookeeper.KeeperException$NoAuthException: KeeperErrorCode = NoAuth for /imooc
        [zk: localhost:2181(CONNECTED) 2] getAcl /imooc
        # 错误 2
        Authentication is not valid : /imooc
        ```

        此时我们需要重新使用 addauth digest username:password 来对新的客户端添加认证用户，才可以访问有 ACL 权限的节点。

        ```bash
        # 添加认证用户，用户名密码和设置 ACL 时的一致
        [zk: localhost:2181(CONNECTED) 3] addauth digest mooc:mooc
        [zk: localhost:2181(CONNECTED) 4] get /imooc
        null
        [zk: localhost:2181(CONNECTED) 5] getAcl /imooc
        'digest,'mooc:0xEep90zjLm6hbWMlQ2BGbaQWzI=
        : cdrwa
        ```

        > Tips： 如果是密码设置错误导致对节点的访问无权限，我们可以在 zoo.cfg 配置文件中加入 skipACL=yes，跳过 ACL 验证，重启服务后再修改 ACL 信息或者删除错误节点。本设置不建议在生产环境下使用！

        上面的设置方式都是针对当前节点的 ACL 的设置，一个一个的对每个节点去设置它们的 ACL 信息是一件非常繁琐的事情，所以我们可以使用 -R 参数来递归设置子节点的 ACL 信息：

        ```bash
        # 创建子节点 /imooc/mooc
        [zk: localhost:2181(CONNECTED) 8] create /imooc/mooc
        # 创建子节点 /imooc/mooc 成功
        Created /imooc/mooc
        # 创建子节点 /imooc/mooc/wiki
        [zk: localhost:2181(CONNECTED) 9] create /imooc/mooc/wiki
        # 创建子节点 /imooc/mooc/wiki 成功
        Created /imooc/mooc/wiki
        # 使用 digest 设置 /imooc 节点的 ACL，使用 —R 来递归设置子节点的 ACL 信息
        [zk: localhost:2181(CONNECTED) 10] setAcl -R /imooc digest:mooc:0xEep90zjLm6hbWMlQ2BGbaQWzI=:cdrwa
        # 查看 /imooc 节点的 ACL 信息
        [zk: localhost:2181(CONNECTED) 11] getAcl /imooc 
        'digest,'mooc:0xEep90zjLm6hbWMlQ2BGbaQWzI=
        : cdrwa
        # 查看 /imooc/mooc 节点的 ACL 信息
        [zk: localhost:2181(CONNECTED) 12] getAcl /imooc/mooc
        'digest,'mooc:0xEep90zjLm6hbWMlQ2BGbaQWzI=
        : cdrwa
        # 查看 /imooc/mooc/wiki 节点的 ACL 信息
        [zk: localhost:2181(CONNECTED) 13] getAcl /imooc/mooc/wiki
        'digest,'mooc:0xEep90zjLm6hbWMlQ2BGbaQWzI=
        : cdrwa
        ```

        我们发现 imooc 的 ACL 信息和它的子节点的 ACL 信息都是相同的，说明我们使用 -R 递归设置子节点的 ACL 信息成功了。
        成功的实现了节点 ACL 的设置之后，接下来我们来讲解 Zookeeper ACL 实现的原理。

3. Zookeeper ACL 原理

    由于 Zookeeper 是 C/S 架构，所以 Zookeeper ACL 的实现原理也分为两部分，Zookeeper 客户端和 Zookeeper 服务端。我们首先从 Zookeeper 客户端开始介绍。

    1. Zookeeper 客户端处理 ACL 请求

        在 Zookeeper 客户端中，当我们使用 addauth digest mooc:mooc 命令来添加认证用户信息时，Zookeeper 客户端会使用 ClientCnxn 客户端类的 addAuthInfo 方法来封装请求信息，把请求类型包装成权限类请求，封装完毕后发送给服务端。

        ```java
        // 添加认证信息，参数一：授权类型，参数二：认证信息的字节数组
        public void addAuthInfo(String scheme, byte[] auth) {
            // 判断当前客户端状态
            if (this.state.isAlive()) {
                // 封装认证信息 scheme:digest 
                this.authInfo.add(new ClientCnxn.AuthData(scheme, auth));
                // 封装请求头 -4为请求头的xid，100为请求类型：auth
                this.queuePacket(new RequestHeader(-4, 100), (ReplyHeader)null, new AuthPacket(0, scheme, auth), (Record)null, (AsyncCallback)null, (String)null, (String)null, (Object)null, (WatchRegistration)null);
            }
        }
        ```

        简单地介绍了 Zookeeper 客户端处理权限类请求的过程，接下来就是 Zookeeper 服务端的处理过程了。

    2. Zookeeper 服务端处理 ACL 请求

        在 Zookeeper 服务端接收到 Zookeeper 客户端发送过来的请求后，首先解析请求头，识别请求的类型，发现客户端的请求类型是 auth 时，也就是权限类请求时，Zookeeper 会使用 scheme 来判断授权类型，找具体处理授权的实现类，在实现类中来进行权限验证，然后把认证用户保存到认证信息的集合中。
        在 Zookeeper 客户端使用刚才添加的认证用户信息来进行节点操作时，向 Zookeeper 服务端发送权限请求，服务端解析完成请求类型和授权类型后，再去具体的认证信息集合中去匹配请求中携带的授权信息，匹配成功则执行具体操作，匹配不到则说明该请求无授权，返回 NoAuthException 异常信息。
