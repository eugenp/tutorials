package com.baledung.billpugh;

public class BillPughSingleton {
    private BillPughSingleton() {

    }

    private static class SingletonHelper {
        private static final BillPughSingleton BILL_PUGH_SINGLETON_INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.BILL_PUGH_SINGLETON_INSTANCE;
    }
}
