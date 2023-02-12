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

## Relevant Articles

- [Guide to Java Packages](https://www.baeldung.com/java-packages)
- [If-Else Statement in Java](https://www.baeldung.com/java-if-else)
- [Control Structures in Java](https://www.baeldung.com/java-control-structures)
- [Java Double Brace Initialization](https://www.baeldung.com/java-double-brace-initialization)
- [The Java Native Keyword and Methods](https://www.baeldung.com/java-native)
- [Variable Scope in Java](https://www.baeldung.com/java-variable-scope)
- [x] [Introduction to Basic Syntax in Java](https://www.baeldung.com/java-syntax)
- [Java ‘protected’ Access Modifier](https://www.baeldung.com/java-protected-access-modifier)
- [Using the Not Operator in If Conditions in Java](https://www.baeldung.com/java-using-not-in-if-conditions)
- [The for-each Loop in Java](https://www.baeldung.com/java-for-each-loop)
- [[<-- Prev]](/core-java-modules/core-java-lang-syntax)
