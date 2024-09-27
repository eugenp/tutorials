package com.baeldung.jmix.expensetracker.security;

import com.baeldung.jmix.expensetracker.entity.User;
import com.baeldung.jmix.expensetracker.entity.UserExpense;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "EmployeeRowLevelAccessRole", code = EmployeeRowLevelAccessRole.CODE)
public interface EmployeeRowLevelAccessRole {
    String CODE = "employee-row-level-access-role";

    @JpqlRowLevelPolicy(entityClass = UserExpense.class, where = "{E}.user.id = :current_user_id")
    void userExpense();

    @JpqlRowLevelPolicy(entityClass = User.class, where = "{E}.id = :current_user_id")
    void user();
}