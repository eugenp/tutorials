# Core Java Lang Syntax

This module contains articles about Java syntax

## Java中的基本语法介绍

1. 概述

    Java是一种静态类型的、面向对象的编程语言。它还具有平台独立性--Java程序可以在一种机器（如Windows系统）上编写和编译，也可以在另一种机器（如MacOS）上执行，而无需对源代码进行任何修改。

    在本教程中，我们要看一看并了解Java语法的基本知识。

2. 数据类型

    Java中的数据类型有两大类：[原始类型和对象/参考类型](https://www.baeldung.com/java-primitives-vs-objects)。

    原始类型([primitive types](https://www.baeldung.com/java-primitives))是存储简单数据的基本数据类型，是数据操作的基础。例如，Java有整数值（int、long、byte、short）、浮点值（float和double）、字符值（char）和逻辑值（boolean）的原始类型。

    另一方面，引用类型(reference types)是包含对值和/或其他对象的引用的对象，或者包含表示没有值的特殊值null。

    字符串类是引用类型的一个好例子。该类的一个实例被称为对象，代表一串字符，如 "Hello World"。

3. 在Java中声明变量

    要在Java中声明一个变量，我们必须指定其名称（也称为标识符）和类型。让我们看一个简单的例子。

    ```java
    int a;
    int b;
    double c;
    ```

    在上面的例子中，变量将根据其声明的类型获得默认的初始值。由于我们声明的变量是int和double，它们的默认值分别为0和0.0。

    另外，我们可以在声明时使用赋值运算符（=）来初始化变量。

    `int a = 10;`

    在上面的例子中，我们用标识符a声明一个int类型的变量，用赋值运算符（=）给它赋值为10，并用分号（;）来结束语句。在Java中，所有的语句都必须以分号结尾。

    标识符是一个任意长度的名称，由字母、数字、下划线和美元符号组成，并符合以下规则。

    - 以字母、下划线（_）或美元符号（$）开头
    - 不能是一个保留关键词
    - 不能是真、假、或空

    让我们扩展上面的代码片段，包括一个简单的算术操作。

    ```java
    int a = 10;
    int b = 5;
    double c = a + b;
    System.out.println( a + " + " + b + " = " + c);
    ```

    我们可以将上述代码片段的前三行理解为 "将10的值赋给a，将5的值赋给b，将a和b的值相加，将结果赋给c"。在最后一行，我们将操作的结果输出到控制台。

    `10 + 5 = 15.0`

    其他类型的变量的声明和初始化遵循我们上面展示的相同语法。例如，我们来声明String、char和boolean变量。

    ```java
    String name = "Baeldung Blog";
    char toggler = 'Y';
    boolean isVerified = true;
    ```

    为强调起见，表示char和String的字面价值的主要区别是环绕这些值的引号的数量。因此，'a'是一个char，而"a"是一个String。

4. 数组

    数组是一种引用类型，可以存储特定类型的值的集合。在Java中声明一个数组的一般语法是。

    `type[] identifier = new type[length];`

    该类型可以是任何原始类型或引用类型。

    例如，让我们看看如何声明一个最多可容纳100个整数的数组。

    `int[] numbers = new int[100];`

    为了引用一个数组中的特定元素，或者给一个元素赋值，我们使用变量名和它的索引。

    ```java
    numbers[0] = 1;
    numbers[1] = 2;
    numbers[2] = 3;
    int thirdElement = numbers[2];
    ```

    在Java中，数组的索引从零开始。一个数组的第一个元素的索引是0，第二个元素的索引是1，以此类推。

    此外，我们可以通过调用numbers.length来获得数组的长度。

    `int lengthOfNumbersArray = numbers.length;`

5. Java关键词

    关键词是在Java中具有特殊含义的保留词。

    例如，public、static、class、main、new、instanceof，都是Java中的[关键词](https://www.baeldung.com/tag/java-keyword/)，因此，我们不能用它们作为标识符（变量名）。

6. Java中的操作符

    现在我们已经看到了上面的赋值运算符（=），让我们来探讨一下[Java语言中的其他一些运算符类型](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/opsummary.html)。

    1. 算术运算符

        Java支持以下[算术运算符](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op1.html)，可用于编写数学、计算逻辑。

        - `+`（加号或加法；也用于字符串串联）
        - `-` (负数或减法)
        - `*` (乘法)
        - `/` (除法)
        - `%` (模数或余数)

        我们在之前的代码示例中使用了加号（+）运算符来执行两个变量的加法。其他算术运算符的使用方法与此类似。

        加号（+）的另一个用途是串联（连接）字符串，形成一个全新的字符串。

        `String output =  a + " + " + b + " = " + c;`

    2. 逻辑运算符

        除了算术运算符之外，Java还支持以下[逻辑运算符](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html)，用于评估布尔表达式。

        - && (AND)
        - || (OR)
        - ! (NOT)

        让我们考虑一下下面的代码片断，它们展示了逻辑运算符AND和OR。第一个例子显示了一个打印语句，当数字变量既能被2除以又能被3除以时执行。

        ```java
        int number = 6;
        if (number % 2 == 0 && number % 3 == 0) {
            System.out.println(number + " is divisible by 2 AND 3");
        }
        ```

        而当number能被2或5整除时，执行第二个。

        ```java
        if (number % 2 == 0 || number % 5 == 0) {
            System.out.println(number + " is divisible by 2 OR 5");
        }
        ```

    3. 比较运算符

        当我们需要将一个变量的值与另一个变量的值进行比较时，我们可以使用Java的比较运算符。

        - `<` (小于)
        - `<=` (小于或等于)
        - `>` (大于)
        - `>=` (大于或等于)
        - `==` (等同于)
        - `!=` (不等于)

        例如，我们可以使用比较运算符来确定一个选民的资格。

        ```java
        public boolean canVote(int age) {
            if(age < 18) {
                return false;
            }
            return true;
        }
        ```

7. Java程序结构

    现在我们已经了解了数据类型、变量和一些基本的运算符，让我们看看如何把这些元素放在一个简单的、可执行的程序中。

    Java程序的基本单元是一个类。一个类可以有一个或多个字段（有时称为属性）、方法，甚至还有其他称为内类的类成员。

    为了使一个类能够被执行，它必须有一个主方法。主方法标志着程序的进入点。

    让我们写一个简单的、可执行的类来练习我们前面考虑的一个代码片断。

    ```java
    public class SimpleAddition {
        public static void main(String[] args) {
            int a = 10;
            int b = 5;
            double c = a + b;
            System.out.println( a + " + " + b + " = " + c);
        }
    }
    ```

    这个类的名字是SimpleAddition，在这个类中，我们有一个主方法，用来容纳我们的逻辑。开头和结尾的大括号之间的代码段被称为代码块。

    Java程序的源代码存储在一个扩展名为.java的文件中。

8. 编译和执行一个程序

    为了执行我们的源代码，我们首先需要对其进行编译。这个过程将生成一个以.class为扩展名的二进制文件。我们可以在任何安装了Java运行环境（JRE）的机器上执行这个二进制文件。

    让我们把上面例子中的源代码保存到一个名为SimpleAddition.java的文件中，然后在保存文件的目录中运行这个命令。

    `javac SimpleAddition.java`

    要执行该程序，我们只需运行（前提是SimpleAddition中未定义package）

    `java SimpleAddition`

    这将在控制台产生与上面所示相同的输出。

    `10 + 5 = 15.0`

    - [x] 错误: 找不到或无法加载主类SimpleAddition
       由于SimpleAddition定义了package名，所以必须在包的上级目录下执行：java 包名的虚拟路径(以.分隔，不带.class)
       `cd ../main/java`
       `java com.baeldung.basicsyntax.SimpleAddition`

9. 总结

    在本教程中，我们已经了解了Java的一些基本语法。就像其他编程语言一样，通过不断的练习，它会变得更加简单。

    本教程的完整源代码可以在[Github](https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-lang-syntax-2)上找到。

## Java中的控制结构

1. 概述

    在最基本的意义上，一个程序是一个指令的列表。控制结构是可以改变我们通过这些指令的路径的编程块。

    在本教程中，我们将探讨Java中的控制结构。

    有三种控制结构。

    - 条件性分支，我们用它来在两条或多条路径中进行选择。在Java中有三种类型：if/else/else if，三元运算符和switch。
    - 循环，用于迭代多个值/对象并重复运行特定的代码块。Java中的基本循环类型是for、while和do while。
    - 分支语句，用于改变循环中的控制流。在Java中有两种类型：break 和 continue。

2. If/Else/Else If

    if/else语句是[最基本的控制结构](https://www.baeldung.com/java-if-else)，但也可以认为是编程中决策的基础。

    虽然if可以单独使用，但最常见的使用场景是用if/else在两条路径中进行选择。

    ```java
    if (count > 2) {
        System.out.println("Count is higher than 2");
    } else {
        System.out.println("Count is lower or equal than 2");
    }
    ```

    理论上说，我们可以无限地连锁或嵌套if/else块，但这将损害代码的可读性，这就是为什么不建议这样做。

    我们将在本文的其余部分探讨替代语句。

3. 三元运算符

    我们可以使用[三元运算符](https://www.baeldung.com/java-ternary-operator)作为一个速记表达式，它的作用类似于if/else语句。

    我们可以用三元组对if/else语句进行重构，如下。

    `System.out.println(count > 2 ? "Count is higher than 2" : "Count is lower or equal than 2");`

    虽然三元组可以使我们的代码更有可读性，但它并不总是if/else的好替代品。

4. 转换

    如果我们有多种情况可以选择，我们可以使用switch语句。

    让我们再看一个简单的例子。

    ```java
    int count = 3;
    switch (count) {
    case 0:
        System.out.println("Count is equal to 0");
        break;
    case 1:
        System.out.println("Count is equal to 1");
        break;
    default:
        System.out.println("Count is either negative, or higher than 1");
        break;
    }
    ```

    三个或更多的if/else语句会让人难以阅读。作为可能的变通方法之一，我们可以使用switch，如上所示。

    而且还要记住，[switch有范围和输入限制](https://www.baeldung.com/java-switch)，我们在使用它之前需要记住。

5. 循环

    当我们需要连续多次重复相同的代码时，我们会使用[loops](https://www.baeldung.com/java-loops)。

    让我们看一个简单的例子，比较for和while类型的循环。

    ```java
    for (int i = 1; i <= 50; i++) {
        methodToRepeat();
    }

    int whileCounter = 1;
    while (whileCounter <= 50) {
        methodToRepeat();
        whileCounter++;
    }
    ```

    上面的两个代码块将调用methodToRepeat 50次。

6. 中断

    我们需要使用[break](https://www.baeldung.com/java-continue-and-break)来提前退出一个循环。

    让我们看一个快速的例子。

    ```java
    List<String> names = getNameList();
    String name = "John Doe";
    int index = 0;
    for ( ; index < names.length; index++) {
        if (names[index].equals(name)) {
            break;
        }
    }
    ```

    在这里，我们在一个名字的列表中寻找一个名字，一旦找到它，我们就想停止寻找。

    循环通常会进行到结束，但我们在这里用break来绕过它，提前退出。

7. 继续

    简单地说，[continue](https://www.baeldung.com/java-continue-and-break)意味着跳过我们所处的循环的其余部分。

    ```java
    List<String> names = getNameList();
    String name = "John Doe";
    String list = "";
    for (int i = 0; i < names.length; i++) { 
        if (names[i].equals(name)) {
            continue;
        }
        list += names[i];
    }
    ```

    这里，我们跳过将重复的名字追加到列表中。

    正如我们在这里看到的，break和continue在迭代时可以很方便，尽管它们通常可以用return语句或其他逻辑重写。

8. 总结

    在这篇简短的文章中，我们了解了什么是控制结构，以及如何使用它们来管理我们的Java程序中的流控制。

    本文介绍的所有代码都可以在[GitHub](https://www.baeldung.com/java-continue-and-break)上找到。

## Java包指南

1. 简介

    在这个快速教程中，我们将介绍Java中包的基础知识。我们将看到如何创建包和访问我们放在包里的类型。

    我们还将讨论命名规则以及它与底层目录结构的关系。

    最后，我们将编译和运行我们打包的Java类。

2. Java包的概述

    在Java中，我们使用包来分组相关的类、接口和子包。

    这样做的主要好处是。

    - 使得相关的类型更容易找到--包通常包含逻辑上相关的类型
    - 避免命名冲突--包将帮助我们唯一地识别一个类；例如，我们可以有一个com.baeldung.Application，以及com.example.Application类
    - 控制访问--我们可以通过结合包和访问修饰符来控制类型的可见性和访问。

    接下来，让我们看看我们如何创建和使用Java包。

3. 创建一个包

    要创建一个包，我们必须使用包声明，把它作为文件中的第一行代码加入。

    让我们把一个类型放在一个名为com.baeldung.packages的包中。

    package com.baeldung.packages;

    强烈建议将每个新类型放在一个包中。如果我们定义了类型却不把它们放在一个包里，它们就会进入默认的或未命名的包里。使用默认包会有一些缺点。

    - 我们失去了拥有包结构的好处，我们不能拥有子包
    - 我们不能从其他包中导入默认包中的类型
    - [受保护的和包专用](https://www.baeldung.com/java-access-modifiers)的访问范围将毫无意义

    正如[Java语言规范](https://docs.oracle.com/javase/specs/jls/se14/html/jls-7.html#jls-7.4.2)所述，未命名包由Java SE平台提供，主要是为了在开发小型或临时应用程序或刚开始开发时提供方便。

    因此，我们应该避免在现实世界的应用中使用未命名的或默认的包。

    1. 命名规则

        为了避免同名的包，我们遵循一些命名约定。

        - 我们用所有的小写字母来定义我们的包名
        - 包的名字是以句号为界限的
        - 名称也由创建它们的公司或组织决定

        要根据一个组织来确定包的名称，我们通常会从颠倒公司的URL开始。之后，命名规则由公司定义，可能包括部门名称和项目名称。

        例如，要把www.baeldung.com，让我们反过来做一个包。

        com.baeldung

        然后我们可以进一步定义其中的子包，如com.baeldung.packages或com.baeldung.packages.domain。

    2. 目录结构

        Java中的包与目录结构相对应。

        每个包和子包都有自己的目录。因此，对于com.baeldung.packages这个包，我们应该有一个com -> baeldung -> packages的目录结构。

        大多数IDE会根据我们的包名来帮助创建这个目录结构，所以我们不必手工创建这些目录。

4. 使用包的成员

    让我们先在一个名为domain的子包中定义一个TodoItem类。

    com.baeldung.core.packages.domain/TodoItem.java

    1. 导入

        为了从另一个包的类中使用我们的TodoItem类，我们需要导入它。一旦它被导入，我们就可以通过名字来访问它。

        我们可以从一个包中导入一个单一的类型，或者使用星号来导入一个包中的所有类型。

        让我们来导入整个域子包。

        `import com.baeldung.packages.domain.*;`

        现在，让我们只导入TodoItem类。

        `import com.baeldung.packages.domain.TodoItem;`

        JDK和其他Java库也有自己的包。我们可以用同样的方式导入我们想在项目中使用的预先存在的类。

        例如，让我们导入Java核心List接口和ArrayList类。

        然后我们可以在我们的应用程序中使用这些类型，只需使用它们的名字。

        com.baeldung.core.packages/TodoList.java

        在这里，我们用我们的新类和Java核心类一起，创建了一个ToDoItems的列表。

    2. 完全合格的名称

        有时，我们可能会使用来自不同包的两个同名的类。例如，我们可能同时使用 java.sql.Date 和 java.util.Date。当我们遇到命名冲突时，我们需要至少为其中一个类使用一个完全合格的类名。

        让我们使用具有完全限定名称的TodoItem。

        ```java
        public class TodoList {
            private List<com.baeldung.packages.domain.TodoItem> todoItems;

            public void addTodoItem(com.baeldung.packages.domain.TodoItem todoItem) {
                if (todoItems == null) {
                    todoItems = new ArrayList<com.baeldung.packages.domain.TodoItem>();
                }todoItems.add(todoItem);
            }
            ...
        }
        ```

5. 用javac编译

    当我们要编译打包好的类时，我们需要记住我们的目录结构。从源文件夹开始，我们需要告诉javac在哪里可以找到我们的文件。

    我们需要先编译我们的TodoItem类，因为我们的TodoList类依赖于它。

    让我们先打开一个命令行或终端，导航到我们的源文件目录/src/main/java。

    现在，让我们编译我们的com.baeldung.packages.domain.TodoItem类。

    >javac com/baeldung/core/packages/domain/TodoItem.java

    如果我们的类编译得很干净，我们就不会看到错误信息，并且在com/baeldung/packages/domain目录下应该出现一个TodoItem.class文件。

    对于引用其他包中的类型，我们应该使用-classpath标志来告诉javac命令在哪里找到其他编译的类。

    现在我们的TodoItem类已经编译完成，我们可以编译TodoList和TodoApp类。

    >javac -classpath . com/baeldung/core/packages/*.java

    同样的，我们应该没有看到错误信息，我们应该在com/baeldung/packages目录下找到两个类文件。

    让我们使用TodoApp类的全称来运行我们的应用程序。

    >java com.baeldung.core.packages.TodoApp

    我们的输出应该看起来像这样。

    ```log
    TodoItem [id=0, description=Todo item 1, dueDate=2023-02-16]
    TodoItem [id=1, description=Todo item 2, dueDate=2023-02-17]
    TodoItem [id=2, description=Todo item 3, dueDate=2023-02-18]
    ```

6. 总结

    在这篇短文中，我们了解了什么是包以及为什么我们应该使用它们。

    我们讨论了命名规则以及包与目录结构的关系。我们还看到了如何创建和使用包。

    最后，我们讨论了如何使用javac和java命令来编译和运行一个带有包的应用程序。

    完整的例子代码可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-lang-syntax-2)上找到。

## Relevant Articles

- [x] [Guide to Java Packages](https://www.baeldung.com/java-packages)
- [If-Else Statement in Java](https://www.baeldung.com/java-if-else)
- [x] [Control Structures in Java](https://www.baeldung.com/java-control-structures)
- [Java Double Brace Initialization](https://www.baeldung.com/java-double-brace-initialization)
- [The Java Native Keyword and Methods](https://www.baeldung.com/java-native)
- [Variable Scope in Java](https://www.baeldung.com/java-variable-scope)
- [x] [Introduction to Basic Syntax in Java](https://www.baeldung.com/java-syntax)
- [Java ‘protected’ Access Modifier](https://www.baeldung.com/java-protected-access-modifier)
- [Using the Not Operator in If Conditions in Java](https://www.baeldung.com/java-using-not-in-if-conditions)
- [The for-each Loop in Java](https://www.baeldung.com/java-for-each-loop)
- [[<-- Prev]](/core-java-modules/core-java-lang-syntax)
