package com.baeldung.jmix.expensetracker.security;

import com.baeldung.jmix.expensetracker.entity.Expense;
import com.baeldung.jmix.expensetracker.entity.User;
import com.baeldung.jmix.expensetracker.entity.UserExpense;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "Employee Role", code = EmployeeRole.CODE, scope = "UI")
public interface EmployeeRole {
    String CODE = "employee-role";

    @EntityAttributePolicy(entityClass = Expense.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Expense.class, actions = EntityPolicyAction.READ)
    void expense();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void user();

    @EntityAttributePolicy(entityClass = UserExpense.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = UserExpense.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE, EntityPolicyAction.CREATE})
    void userExpense();

    @MenuPolicy(menuIds = "UserExpense.list")
    @ViewPolicy(viewIds = {"Expense.list", "UserExpense.list", "User.list"})
    void screens();
}