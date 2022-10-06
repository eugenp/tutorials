# 控制反转

控制反转是您在扩展框架时遇到的一种常见现象。事实上，它常常被视为框架的一个定义特征。

让我们考虑一个简单的例子。想象一下，我正在编写一个程序来从用户那里获取一些信息，并且我正在使用命令行查询。我可能会这样做

```ruby
  puts 'What is your name?'
  name = gets
  process_name(name)
  puts 'What is your quest?'
  quest = gets
  process_quest(quest)
```

在这种交互中，我的代码处于控制之中：它决定何时提问，何时阅读响应，以及何时处理这些结果。

然而，如果我使用窗口系统来做这样的事情，我会通过配置窗口来做。

```ruby
  require 'tk'
  root = TkRoot.new()
  name_label = TkLabel.new() {text "What is Your Name?"}
  name_label.pack
  name = TkEntry.new(root).pack
  name.bind("FocusOut") {process_name(name)}
  quest_label = TkLabel.new() {text "What is Your Quest?"}
  quest_label.pack
  quest = TkEntry.new(root).pack
  quest.bind("FocusOut") {process_quest(quest)}
  Tk.mainloop()
```

现在，这些程序之间的控制流有了很大的不同，特别是在调用process_name和process_quest方法时的控制。在命令行形式中，我控制何时调用这些方法，但在窗口示例中，我不控制。相反，我将控制权移交给窗口系统（使用Tk.mainloop命令）。然后它根据创建表单时所做的绑定来决定何时调用我的方法。这种现象就是控制反转（也称为好莱坞原则——“不要给我们打电话，我们会给你打电话”）。

> 框架的一个重要特征是，用户为定制框架而定义的方法通常会从框架内部调用，而不是从用户的应用程序代码调用。框架通常在协调和排序应用程序活动中扮演主程序的角色。这种控制反转赋予框架作为可扩展框架的能力。用户提供的方法针对特定应用定制框架中定义的通用算法。
--[Ralph Johnson and Brian Foote](http://www.laputan.org/drc/drc.html)

控制反转是使框架不同于库的关键部分。库本质上是一组可以调用的函数，现在通常被组织成类。每个调用都会执行一些工作，并将控制权返回给客户端。

框架体现了一些抽象的设计，内置了更多的行为。为了使用它，您需要通过子类化或插入自己的类将您的行为插入框架中的各个位置。然后框架的代码在这些点调用您的代码。

有多种方法可以插入代码进行调用。在上面的ruby示例中，我们在文本输入字段上调用了一个bind方法，该方法传递一个事件名和一个 [Lambda](https://martinfowler.com/bliki/Lambda.html) 作为参数。每当文本输入框检测到事件时，它就会调用闭包中的代码。像这样使用闭包非常方便。

另一种方法是让框架定义事件，并让客户端代码订阅这些事件。NET是一个很好的平台示例，它具有允许人们在小部件上声明事件的语言功能。然后可以使用委托将方法绑定到事件。

上述方法（它们实际上是相同的）适用于单个情况，但有时您希望在单个扩展单元中组合几个必需的方法调用。在这种情况下，框架可以定义客户机代码必须为相关调用实现的接口。

EJB就是这种类型的控制反转的一个很好的例子。开发会话bean时，可以实现EJB容器在不同生命周期点调用的各种方法。例如，Session Bean接口定义了ejbRemove、ejbPassivate（存储到辅助存储）和ejbActivate（从被动状态恢复）。您无法控制这些方法的调用时间，只能控制它们的作用。容器叫我们，我们不叫它。

这些是控制反转的复杂情况，但在更简单的情况下会遇到这种效果。[模板方法](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=martinfowlerc-20)就是一个很好的例子：超类定义了控制流，子类扩展了这个覆盖方法，或者实现了抽象方法来进行扩展。因此，在JUnit中，框架代码调用setUp和tearDown方法来创建和清理测试夹具。它进行调用，代码做出反应，因此再次反转控制。

最近，由于IoC容器的增加，对控制反转的含义存在一些困惑；有些人将这里的一般原则与这些容器使用的特定类型的控制反转（例如[依赖注入](https://martinfowler.com/articles/injection.html)）混淆了。由于IoC容器通常被视为EJB的竞争对手，因此这个名称有点令人困惑（而且具有讽刺意味），但EJB也同样使用控制反转（如果不是更多的话）。

词源：据我所知，控制反转一词首次出现在Johnson和Foote的论文[《设计可重用类》](http://www.laputan.org/drc/drc.html)中，该论文由《面向对象编程杂志》于1988年出版。这篇论文是一篇经久不衰的论文，十五年后的今天非常值得一读。他们认为这个词来自其他地方，但不记得是什么。这个术语随后潜入面向对象社区，并出现在[四人帮](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=martinfowlerc-20)的书中。更丰富多彩的同义词“好莱坞原则”似乎起源于1983年理查德·斯威特（Richard Sweet）在梅萨（Mesa）发表的一篇[论文](http://www.digibarn.com/friends/curbow/star/XDEPaper.pdf)。在一份设计目标列表中，他写道：“不要给我们打电话，我们会给你打电话（好莱坞法则）：工具应该安排Tajo在用户希望向工具传达某个事件时通知它，而不是采用“向用户请求命令并执行”的模式。John Vlissides为C++报告撰写了一篇[专栏](http://www.research.ibm.com/designpatterns/pubs/ph-feb96.txt)，很好地解释了“好莱坞原则”这个绰号下的概念。
