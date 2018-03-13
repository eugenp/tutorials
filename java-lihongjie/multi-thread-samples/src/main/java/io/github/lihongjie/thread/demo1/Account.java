package io.github.lihongjie.thread.demo1;

/**
 * Created by lihongjie on 8/2/17.
 * <p>
 * synchronized 关键字
 * 到目前为止，我们看到的示例都只是以非常简单的方式来利用线程。只有最小的数据流，而且不会出现两个线程访问同一个对象的情况。但是，在大多数有用的程序中，线程之间通常有信息流。
 * 试考虑一个金融应用程序，它有一个 Account 对象，如下例中所示：
 * 一个银行中的多项活动
 */
public class Account {
    String holderName;
    float amount;

    public Account(String name, float amt) {
        holderName = name;
        amount = amt;
    }

    public synchronized void deposit(float amt) {
        amount += amt;
    }

    public synchronized void withdraw(float amt) {
        amount -= amt;
    }

    /**
     * deposit() 和 withdraw() 函数都需要这个锁来进行操作，所以当一个函数运行时，另一个函数就被阻塞。
     * 请注意， checkBalance() 未作更改，它严格是一个读函数。因为 checkBalance() 未作同步处理，
     * 所以任何其他方法都不会阻塞它，它也不会阻塞任何其他方法，不管那些方法是否进行了同步处理。
     * @return
     */

//    public void deposit(float amt) {
//        amount += amt;
//    }
//
//    public void withdraw(float amt) {
//        amount -= amt;
//    }

    public float checkBalance() {
        return amount;
    }
}
