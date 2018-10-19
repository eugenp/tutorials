package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.core.BirthdayService;
import com.baeldung.hexagonal.architecture.core.EmployeeStore;

public class HexagonalArchitectureApplication {

    public static void main(String[] args) {
        HexagonalArchitectureApplication app = new HexagonalArchitectureApplication();

        EmployeeStore store = new FileEmployeeStore(app.getFile("files/hexagonal/employees.csv"));
        BirthdayService service = new BirthdayService(store);
        service.sendCoupons();

    }

    private String getFile(String fileName) {

        ClassLoader classLoader = this.getClass()
            .getClassLoader();
        return classLoader.getResource(fileName)
            .getPath();

    }
}
