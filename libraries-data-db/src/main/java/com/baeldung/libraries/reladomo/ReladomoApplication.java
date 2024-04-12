package com.baeldung.libraries.reladomo;

import com.gs.fw.common.mithra.MithraManager;
import com.gs.fw.common.mithra.MithraManagerProvider;

import java.io.InputStream;

public class ReladomoApplication {

    public static void main(String[] args) {

        try {
            ReladomoConnectionManager.getInstance().createTables();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        MithraManager mithraManager = MithraManagerProvider.getMithraManager();
        mithraManager.setTransactionTimeout(120);

        try (InputStream is = ReladomoApplication.class.getClassLoader().getResourceAsStream("ReladomoRuntimeConfig.xml")) {
            MithraManagerProvider.getMithraManager().readConfiguration(is);

            Department department = new Department(1, "IT");
            Employee employee = new Employee(1, "John");
            department.getEmployees().add(employee);
            department.cascadeInsert();

            Department depFound = DepartmentFinder.findByPrimaryKey(1);
            System.out.println("Department Name:" + department.getName());

            Employee empFound = EmployeeFinder.findOne(EmployeeFinder.name().eq("John"));
            System.out.println("Employee Id:" + empFound.getId());
            empFound.setName("Steven");
            empFound.delete();
            Department depDetached = DepartmentFinder.findByPrimaryKey(1).getDetachedCopy();

            mithraManager.executeTransactionalCommand(tx -> {
                Department dep = new Department(2, "HR");
                Employee emp = new Employee(2, "Jim");
                dep.getEmployees().add(emp);
                dep.cascadeInsert();
                return null;
            });
            
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

}
