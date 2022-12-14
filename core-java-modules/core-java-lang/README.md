# Core Java Lang

## 用Eclipse生成equals()和hashCode()

1. 简介

    在这篇文章中，我们将探讨如何使用 Eclipse IDE 生成 equals() 和 hashCode() 方法。我们将说明Eclipse的代码自动生成功能是多么强大和方便，同时也强调对代码进行勤奋的测试仍然是必要的。

2. 规则

    Java中的equals()是用来检查2个对象是否相等的。测试的一个好方法是确保对象是对称的、反身的和传递的。也就是说，对于三个非空对象a、b和c。

    - 对称的 Symmetric - a.equals(b)当且仅当b.equals(a)。
    - 反射的 Reflexive - a.equals(a)
    - 传递性 Transitive - 如果a.equals(b)和b.equals(c)，那么a.equals(c)

    hashCode()必须遵守一条规则。

    - 2个被equals()的对象必须有相同的hashCode()值。

3. 带有原语的类

    让我们考虑一个只由原始成员变量组成的Java类。

    equalshashcode.entities/PrimitiveClass.java

    我们使用Eclipse IDE，用'Source->Generate hashCode() and equals()'来生成equals()和hashCode()。

    我们可以通过选择'Select All'来确保所有的成员变量都包括在内。

    请注意，在插入点下面列出的选项：影响生成的代码的风格。在这里，我们不选择任何一个选项，选择'OK'，hashCode()、equals(Object obj)方法就被添加到我们的类中。

    生成的hashCode()方法以一个质数（31）的声明开始，对原始对象进行各种操作，并根据对象的状态返回其结果。

    equals()首先检查两个对象是否是同一个实例（==），如果是则返回真。

    接下来，它检查比较对象是否为非空，两个对象是否属于同一类别，如果不是则返回false。

    最后，equals()检查每个成员变量是否相等，如果其中任何一个不相等，则返回false。

    所以我们可以编写简单的测试。

    参见 PrimitiveClassUnitTest.java

4. 带有集合和泛型的类

    现在，让我们考虑一个带有集合和泛型的更复杂的Java类。

    equalshashcode.entities/ComplexClass.java

    我们再次使用Eclipse'Source->Generate hashCode() and equals()'。注意hashCode()使用instanceOf来比较类对象，因为我们在对话框的Eclipse选项中选择了'使用'instanceof'来比较类型'。

    生成的hashCode()方法依赖于AbstractList.hashCode()和AbstractSet.hashCode()的核心Java方法。这些方法遍历一个集合，将每个项目的hashCode()值相加，并返回一个结果。

    同样，生成的equals()方法使用AbstractList.equals()和AbstractSet.equals()，它们通过比较集合的字段来比较它们是否相等。

    我们可以通过测试一些例子来验证其健壮性。

    参见 ComplexClassUnitTest.java

5. 继承

    让我们考虑一下使用继承的Java类。

    ```java
    public abstract class Shape {
        public abstract double area();
        public abstract double perimeter();
    }
    ```

    如果我们在 Square 类（extends Shape）上尝试 "Source->Generate hashCode() and equals() "，Eclipse 警告我们 ‘the superclass ‘Rectangle' does not redeclare equals() and hashCode() : the resulting code may not function correctly'。

    同样地，当我们试图在矩形类上生成hashCode()和equals()时，我们得到了一个关于超类'Shape'的警告。

    尽管有警告，Eclipse还是会允许我们继续前进。在 Rectangle 的情况下，它扩展了一个抽象的 Shape 类，它不能实现 hashCode() 或 equals() ，因为它没有具体的成员变量。对于这种情况，我们可以不理会 Eclipse。

    然而，Square 类从 Rectangle 继承了宽度和长度成员变量，以及它自己的颜色变量。在Square中创建hashCode()和equals()，而不先对Rectangle做同样的事情，意味着在equals()/hashCode()中只使用颜色。

    一个简单的测试告诉我们，如果只是宽度不同，Square的equals()/hashCode()是不够的，因为宽度不包括在equals()/hashCode()的计算中。

    让我们通过使用Eclipse为矩形类生成equals()/hashCode()来解决这个问题。

    我们必须在Square类中重新生成equals()/hashCode()，所以Rectangle的equals()/hashCode()被调用。在这次生成的代码中，我们选择了Eclipse对话框中的所有选项，所以我们看到了注释、instanceOf比较和if块。

    重新运行上面的测试，我们现在通过了，因为Square的hashCode()/equals()被正确计算。

    > With Java 8’s "default method" feature, any abstract class without direct or inherited field should be converted into an interface. However, this change may not be appropriate in libraries or other applications where the class is intended to be used as an API. 将 Shape 改为接口.

## Java中的比较器和可比性

1. 简介

    在Java中，比较是很容易的，直到它们不那么容易。

    当使用自定义类型时，或者试图比较不能直接比较的对象时，我们需要使用一个比较策略。我们可以通过使用比较器或可比较接口来建立一个比较策略。

2. 设置实例

    让我们用一个足球队的例子来说明，我们想按照球员的排名来排队。

    我们将从创建一个简单的球员类开始。

    ```java
    public class Player {
        private int ranking;
        private String name;
        private int age;
        // constructor, getters, setters  
    }
    ```

    接下来，我们将创建一个PlayerSorter类来创建我们的集合，并尝试使用Collections.sort对其进行排序。

    comparable/PlayerSorter.java

    正如所料，这导致了一个编译时错误。

    ```log
    The method sort(List<T>) in the type Collections 
    is not applicable for the arguments (ArrayList<Player>)
    ```

    现在让我们试着理解我们在这里做错了什么。

3. 可比

    顾名思义，Comparable是一个接口，定义了一个对象与其他相同类型的对象进行比较的策略。这被称为该类的 "自然排序"。

    为了能够进行排序，我们必须通过实现可比较接口将我们的播放器对象定义为可比较对象。

    `public class Player implements Comparable<Player>`

    comparable/Player.java

    排序顺序是由compareTo()方法的返回值决定的。Integer.compare(x, y)如果x小于y，返回-1，如果它们相等，返回0，否则返回1。

    该方法返回一个数字，表明被比较的对象是否小于、等于或大于作为参数传递的对象。

    现在当我们运行我们的PlayerSorter时，我们可以看到我们的球员按照他们的排名进行排序。

    ```log
    Before Sorting : [John, Roger, Steven]
    After Sorting : [Steven, John, Roger]
    ```

    现在我们对使用Comparable的自然排序有了清楚的了解，让我们看看如何以比直接实现接口更灵活的方式使用其他类型的排序。

4. 比较器

    比较器接口定义了一个有两个参数的compare(arg1, arg2)方法，这两个参数代表被比较的对象，其工作原理与Comparable.compareTo()方法类似。

    1. 创建比较器

        为了创建一个比较器，我们必须实现比较器接口。

        对于我们的第一个例子，我们将创建一个比较器来使用Player的排名属性来对球员进行排序。

        comparator/PlayerRankingComparator.java

        同样地，我们可以创建一个比较器来使用播放器的年龄属性来对球员进行排序。

        comparator/PlayerAgeComparator.java

    2. 行动中的比较器

        为了演示这个概念，让我们修改我们的PlayerSorter，为Collections.sort方法引入第二个参数，这实际上是我们想要使用的比较器的实例。

        使用这种方法，我们可以覆盖自然排序。

        ```java
        // comparator/PlayerRankingSorter.java
        PlayerRankingComparator playerComparator = new PlayerRankingComparator();
        Collections.sort(footballTeam, playerComparator);
        ```

        现在让我们运行我们的 PlayerRankingSorter 来看看结果。

        ```log
        Before Sorting : [John, Roger, Steven]
        After Sorting by age : [Roger, John, Steven]
        ```

        如果我们想要一个不同的排序顺序，我们只需要改变我们使用的比较器。

        ```java
        // comparator/PlayerAgeSorter.java
        PlayerAgeComparator playerComparator = new PlayerAgeComparator();
        Collections.sort(footballTeam, playerComparator);
        ```

        现在当我们运行我们的PlayerAgeSorter时，我们可以看到不同的按年龄排序的顺序。

        ```log
        Before Sorting : [John, Roger, Steven]
        After Sorting by age : [Roger, John, Steven]
        ```

    3. Java 8的比较器

        Java 8提供了通过使用lambda表达式和comparing()静态工厂方法来定义比较器的新方法。

        参见 Java8ComparatorUnitTest.java

        让我们看看如何使用lambda表达式来创建比较器的一个快速例子。

        ```java
        Comparator byRanking = 
        (Player player1, Player player2) -> Integer.compare(player1.getRanking(), player2.getRanking());
        ```

        Comparator.comparing方法接收一个计算将用于比较项目的属性的方法，并返回一个匹配的比较器实例。

        ```java
        Comparator<Player> byRanking = Comparator
        .comparing(Player::getRanking);
        Comparator<Player> byAge = Comparator
        .comparing(Player::getAge);
        ```

5. 比较器与可比较接口

    对于定义默认排序，或者换句话说，如果它是比较对象的主要方式，那么使用Comparable接口是个不错的选择。

    那么如果我们已经有了Comparable，为什么还要使用 Comparator 呢？

    有几个原因。

    - 有时我们无法修改我们想要排序的对象的类的源代码，因此不可能使用Comparable
    - 使用Comparator可以让我们避免在我们的领域类中添加额外的代码
    - 我们可以定义多种不同的比较策略，这在使用Comparable是不可能的

6. 避开减法技巧

    在本教程的过程中，我们已经使用Integer.compare()方法来比较两个整数。然而，有人可能会说，我们应该用这个聪明的 one-liner 方法来代替。

    `Comparator<Player> comparator = (p1, p2) -> p1.getRanking() - p2.getRanking();`

    虽然它比其他解决方案要简洁得多，但在Java中它可能是整数溢出的受害者。

    Comparator/AvoidingSubtractionUnitTest.java

    由于-1远远小于Integer.MAX_VALUE，在排序后的集合中，"Roger"应该排在 "John"之前。然而，由于整数溢出，"Integer.MAX_VALUE - (-1)"将小于零。所以根据比较器/可比较契约，Integer.MAX_VALUE小于-1，这显然是不正确的。

    因此，尽管我们预期，在排序的集合中 "John "排在 "Roger "之前。

7. 总结

    在这篇文章中，我们探讨了Comparable和Comparator接口，并讨论了它们之间的区别。

    要了解更多关于排序的高级话题，请查看我们的其他文章，如[Java 8 Comparator.comparing](https://www.baeldung.com/java-8-comparator-comparing)，以及[Java 8与Lambdas的比较](https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-lambdas)。

## Java中的无限循环

无限循环是指在不满足终止条件的情况下无休止地循环的指令序列。创建无限循环可能是一个编程错误，但也可能是基于应用程序的行为而有意为之。

1. 使用while

    让我们从while循环开始。在这里，我们将使用布尔字面的true来编写while循环的条件。

    ```java
    public void infiniteLoopUsingWhile() {
        while (true) {
            // do something
        }
    }
    ```

2. 使用for

    现在，让我们使用for循环来创建一个无限循环。

    ```java
    public void infiniteLoopUsingFor() {
        for (;;) {
            // do something
        }
    }
    ```

3. 使用do-while

    一个无限循环也可以使用Java中不太常见的do-while循环来创建。这里，循环条件在第一次执行后被评估。

    ```java
    public void infiniteLoopUsingDoWhile() {
        do {
            // do something
        } while (true);
    }
    ```

## Relevant Articles

- [x] [Generate equals() and hashCode() with Eclipse](https://www.baeldung.com/java-eclipse-equals-and-hashcode)
- [x] [Comparator and Comparable in Java](https://www.baeldung.com/java-comparator-comparable)
- [Recursion In Java](https://www.baeldung.com/java-recursion)
- [A Guide to the finalize Method in Java](https://www.baeldung.com/java-finalize)
- [Quick Guide to java.lang.System](https://www.baeldung.com/java-lang-system)
- [Using Java Assertions](https://www.baeldung.com/java-assert)
- [Synthetic Constructs in Java](https://www.baeldung.com/java-synthetic)
- [Retrieving a Class Name in Java](https://www.baeldung.com/java-class-name)
- [The Java continue and break Keywords](https://www.baeldung.com/java-continue-and-break)
- [x] [Infinite Loops in Java](https://www.baeldung.com/infinite-loops-java)

[[Next --> ]](/core-java-modules/core-java-lang-2)
