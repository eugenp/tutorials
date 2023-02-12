# Core Java Lang Syntax

## Java原语介绍

1. 概述

    Java编程语言有8种原始数据类型。

    在本教程中，我们将看看这些基元是什么，并对每个类型进行介绍。

2. 原始数据类型

    在Java中定义的八个基元是int、byte、short、long、float、double、boolean和char。这些并不被认为是对象，而是代表原始值。

    它们直接存储在堆栈中（查看这篇[文章](https://www.baeldung.com/java-initialization)以了解更多关于Java内存管理的信息）。

    我们将看一下存储大小、默认值和如何使用每种类型的例子。

    让我们从一个快速参考开始。

    类型 大小（比特） 最小 最大 示例
    | Type    | Size (bits) | Minimum | Maximum        | Example                       |
    |---------|-------------|---------|----------------|-------------------------------|
    | byte    | 8           | -2^7^     | 2^7^– 1         | byte b = 100;                 |
    | short   | 16          | -2^15^    | 2^15^– 1         | short s = 30_000;             |
    | int     | 32          | -2^31^    | 2^31^– 1         | int i = 100_000_000;          |
    | long    | 64          | -2^63^    | 2^63^– 1         | long l = 100_000_000_000_000; |
    | float   | 32          | -2^149^    | (2-2^-23^)·2^127^  | float f = 1.456f;             |
    | double  | 64          | -2^1074^   | (2-2^-52^)·2^1023^ | double f = 1.456789012345678; |
    | char    | 16          | 0       | 2^16^– 1         | char c = ‘c';                 |
    | boolean | 1           | –       | –              | boolean b = true;             |

    1. int

        我们要讨论的第一个原始数据类型是int。int类型也被称为整数，它可以容纳各种非分数的数字值。

        具体来说，Java使用32位的内存来存储它。换句话说，它可以表示从-2,147,483,648（-2^31^）到2,147,483,647（2^31^-1）的数值。

        在Java 8中，通过使用新的特殊辅助函数，可以将无符号整数值存储到4,294,967,295（2^32^-1）。

        我们可以简单地声明一个int。

        int x = 424_242;
        int y;

        没有赋值的int声明的默认值是0。

        如果该变量被定义在一个方法中，我们必须在使用它之前赋值。

        我们可以对int进行所有标准的算术运算。只是要注意，在对整数进行这些操作时，小数值会被砍掉。

    2. 字节

        byte是一种类似于int的原始数据类型，只是它只占用8位内存。这就是为什么我们称它为byte。因为内存大小太小，byte只能容纳从-128（-2^7^）到127（2^7^-1）的值。

        下面是我们如何创建byte。

        byte b = 100;
        byte empty;

        byte的默认值也是0。

    3. short

        如果我们想节省内存而byte又太小，我们可以使用介于byte和int之间的类型：short。

        在16比特的内存中，它是int的一半，是字节的两倍。它的可能取值范围是-32,768(-2^15^)到32,767(2^15^-1)。

        short是这样声明的。

        short s = 20_020;
        short s;

        与其他类型类似，其默认值为0，我们也可以对其使用所有标准的算术。

    4. long

        long是int的老大哥。它被存储在64位的内存中，所以它可以保存一个大得多的可能值。

        long的可能值在-9,223,372,036,854,775,808（-2^63^）到9,223,372,036,854,775,807（2^63^-1）之间。

        我们可以简单地声明一个。

        long l = 1_234_567_890;
        long l;

        和其他整数类型一样，默认值也是0，我们可以在long上使用所有对int有效的算术。

    5. float

        我们在Java中使用float类型来表示基本的小数。这是一个单精度的十进制数。这意味着如果我们超过了六个小数点，这个数字就会变得不那么精确，而更像是一种估计。

        在大多数情况下，我们并不关心精度的损失。但如果我们的计算需要绝对的精度（例如，金融操作、登陆月球等），我们需要使用为这项工作设计的特定类型。欲了解更多信息，请查看Java类[大十进制](https://www.baeldung.com/java-bigdecimal-biginteger)。

        这种类型就像int一样被存储在32位的内存中。然而，由于是浮动的小数点，它的范围有很大的不同。它既可以表示正数，也可以表示负数。最小的小数是1.40239846 x 10^-45^，最大的值是3.40282347 x 10^38^。

        我们声明浮点数与其他类型相同。

        float f = 3.145f;
        float f;

        而默认值是0.0而不是0。另外，注意我们在字面数字的末尾加上f的指定来定义浮点数。否则，Java会抛出一个错误，因为十进制值的默认类型是double。

        我们还可以对浮点数进行所有标准的算术运算。然而，需要注意的是，我们进行浮点运算的方式与整数运算非常不同。

    6. double

        接下来，我们看一下double。它的名字来自于它是一个双精度的十进制数字的事实。

        它被存储在64位的内存中。这意味着它代表的可能数字范围比float大得多。

        虽然，它确实受到与float一样的精度限制。其范围是4.9406564584124654 x 10^-324^到1.7976931348623157 x 10^308^。这个范围也可以是正数或负数。

        声明double与其他数字类型是一样的。

        double d = 3.134575999233847539348D;
        double d;

        和float一样，默认值也是0.0。与float类似，我们附加字母D来指定字面为双数。

    7. 布尔型

        最简单的原始数据类型是布尔型。它只能包含两个值：真或假。它的值存储在一个单一的位中。

        然而，为了方便起见，Java对该值进行填充，并将其存储在一个字节中。

        下面是我们如何声明布尔值。

        boolean b = true;
        boolean b;

        在没有值的情况下声明它，默认为false。 布尔值是控制我们程序流程的基石。我们可以对其使用布尔运算符（例如，和、或等）。

    8. char

        最后要看的原始数据类型是char。

        char也叫字符，是一个16位的整数，代表一个Unicode编码的字符。它的范围是0到65,535。在Unicode中，这代表'\u0000'到'\uffff'。

        关于所有可能的Unicode值的列表，请查看[Unicode表](https://unicode-table.com/en/)等网站。

        现在我们来声明一个char。

        char c = 'a';
        char c = 65;
        char c;

        在定义我们的变量时，我们可以使用任何字符字面，它们会自动为我们转换为Unicode的编码。一个字符的默认值是'/u0000'。

    9. Overflow溢出

        原始数据类型有大小限制。但如果我们试图存储一个大于最大值的值，会发生什么？

        我们会遇到一种叫做溢出的情况。

        当一个整数溢出时，它会滚到最小值，并从那里开始向上计数。

        浮点数的溢出是返回无穷大。

        int i = Integer.MAX_VALUE;
        int j = i + 1;
        // j will roll over to -2_147_483_648

        double d = Double.MAX_VALUE;
        double o = d + 1;
        // o will be Infinity

        下溢也是同样的问题，只是它涉及到存储一个比最小值小的值。当数字下溢时，它们会返回0.0。

    10. Autoboxing 自动排版

        每个原始数据类型也有一个完整的Java类实现，可以包裹它。例如，Integer类可以包装一个int。有时需要从原始类型转换到它的对象封装器（例如，将它们用于[泛型](https://www.baeldung.com/java-generics)）。

        幸运的是，Java可以自动为我们进行这种转换，这个过程被称为 "Autoboxing"。

        Character c = 'c';
        Integer i = 1;

3. 总结

    在这篇文章中，我们已经介绍了Java中支持的八种原始数据类型。

    这些是大多数（如果不是全部）Java程序所使用的构件，所以很值得了解它们的工作原理。

## Java的main()方法详解

1. 概述

    每个程序都需要一个地方来开始执行；说到Java程序，那就是main方法。
    我们在编写代码时习惯于写main方法，以至于我们甚至没有注意到它的细节。在这篇简短的文章中，我们将分析这个方法，并展示一些其他的编写方法。

2. 常见的签名

    最常见的main方法模板是。

    `public static void main(String[] args) { }`

    这就是我们学习的方式，这就是IDE为我们自动完成代码的方式。但这并不是这个方法的唯一形式，还有一些有效的变体我们可以使用，而不是每个开发者都会注意到这个事实。

    在我们深入研究这些方法的签名之前，让我们回顾一下普通签名中每个关键词的含义。

    - public--访问修改器，意味着全局可见
    - static--该方法可以直接从类中访问，我们不必实例化一个对象来获得一个引用并使用它
    - void - 意味着这个方法不会返回一个值
    - main - 方法的名称，这是JVM在执行Java程序时寻找的标识符。

    至于args参数，它表示方法所接收的值。这就是我们在第一次启动程序时向其传递参数的方式。

    参数args是一个字符串数组。在下面的例子中。

    `java CommonMainMethodSignature foo bar`

    我们正在执行一个名为CommonMainMethodSignature的Java程序，并传递两个参数：foo和bar。这些值可以在main方法中作为`args[0]`（以foo为值）和`args[1]`（以bar为值）被访问。

    在下一个例子中，我们要检查args来决定是加载测试还是生产参数。

    ```java
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("test")) {
                // load test parameters
            } else if (args[0].equals("production")) {
                // load production parameters
            }
        }
    }
    ```

    记住，IDE也可以向程序传递参数，这一点总是很好。

3. 写main()方法的不同方法

    让我们看看写main方法的一些不同方式。虽然它们不是很常见，但都是有效的签名。

    请注意，这些都不是专门针对main方法的，它们可以用于任何Java方法，但它们也是main方法的有效部分。

    方括号可以放在String附近，就像常见的模板一样，也可以放在两侧的args附近。

    `public static void main(String []args) { }`

    `public static void main(String args[]) { }`

    参数可以用varargs来表示。

    `public static void main(String...args) { }`

    我们甚至可以为main()方法添加strictfp，在处理浮点值时，它用于处理器之间的兼容。

    `public strictfp static void main(String[] args) { }`

    synchronized和final也是main方法的有效关键字，但它们在这里不会产生影响。

    另一方面，final可以应用于args，以防止数组被修改。

    `public static void main(final String[] args) { }`

    为了结束这些例子，我们也可以用上述所有的关键字来写main方法（当然，在实际应用中你可能永远不会用到这些关键字）。

    final static synchronized strictfp void main(final String[] args) { }

4. 拥有一个以上的main()方法

    我们也可以在我们的应用程序中定义一个以上的main方法。

    事实上，有些人把它作为一种原始的测试技术来验证单个的类（尽管像JUnit这样的测试框架更适合这种活动）。

    为了指定JVM应该执行哪个主方法作为我们应用程序的入口，我们使用MANIFEST.MF文件。在清单中，我们可以指明主类。

    主类： mypackage.ClassWithMainMethod

    这主要是在创建可执行的.jar文件时使用。我们通过位于META-INF/MANIFEST.MF的清单文件（以UTF-8编码），指出哪个类有主方法来启动执行。

5. 总结

    本教程描述了main方法的细节以及它可以采取的一些其他形式，甚至是那些对大多数开发者来说并不常见的形式。

    请记住，尽管我们所展示的所有例子在语法上都是有效的，但它们只是起到教育作用，大多数时候我们会坚持使用常见的签名来完成我们的工作。

## Relevant Articles

- [x] [Introduction to Java Primitives](https://www.baeldung.com/java-primitives)
- [x] [Java main() Method Explained](https://www.baeldung.com/java-main-method)
- [The Basics of Java Generics](https://www.baeldung.com/java-generics)
- [Java Primitive Conversions](https://www.baeldung.com/java-primitive-conversions)
- [A Guide to Creating Objects in Java](https://www.baeldung.com/java-initialization)
- [A Guide to Java Loops](https://www.baeldung.com/java-loops)
- [Varargs in Java](https://www.baeldung.com/java-varargs)
- [Java Switch Statement](https://www.baeldung.com/java-switch)
- [Breaking Out of Nested Loops](https://www.baeldung.com/java-breaking-out-nested-loop)
- [Java Do-While Loop](https://www.baeldung.com/java-do-while-loop)
- [Java While Loop](https://www.baeldung.com/java-while-loop)
- [Java For Loop](https://www.baeldung.com/java-for-loop)
- [[More -->]](/core-java-modules/core-java-lang-syntax-2)