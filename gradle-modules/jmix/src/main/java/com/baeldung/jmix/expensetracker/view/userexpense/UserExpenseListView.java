package com.baeldung.jmix.expensetracker.view.userexpense;

import com.baeldung.jmix.expensetracker.entity.UserExpense;
import com.baeldung.jmix.expensetracker.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.model.InstanceLoader;
import io.jmix.flowui.view.*;

@Route(value = "userExpenses", layout = MainView.class)
@ViewController("UserExpense.list")
@ViewDescriptor("user-expense-list-view.xml")
@LookupComponent("userExpensesDataGrid")
@DialogMode(width = "64em")
public class UserExpenseListView extends StandardListView<UserExpense> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<UserExpense> userExpensesDc;

    @ViewComponent
    private InstanceContainer<UserExpense> userExpenseDc;

    @ViewComponent
    private InstanceLoader<UserExpense> userExpenseDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        updateControls(false);
    }

    @Subscribe("userExpensesDataGrid.create")
    public void onUserExpensesDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        UserExpense entity = dataContext.create(UserExpense.class);
        userExpenseDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("userExpensesDataGrid.edit")
    public void onUserExpensesDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveBtn")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        UserExpense item = userExpenseDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        userExpensesDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelBtn")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        userExpenseDl.load();
        updateControls(false);
    }

    @Subscribe(id = "userExpensesDc", target = Target.DATA_CONTAINER)
    public void onUserExpensesDcItemChange(final InstanceContainer.ItemChangeEvent<UserExpense> event) {
        UserExpense entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            userExpenseDl.setEntityId(entity.getId());
            userExpenseDl.load();
        } else {
            userExpenseDl.setEntityId(null);
            userExpenseDc.setItem(null);
        }
    }

    protected ValidationErrors validateView(UserExpense entity) {
        ViewValidation viewValidation = getViewValidation();
        ValidationErrors validationErrors = viewValidation.validateUiComponents(form);
        if (!validationErrors.isEmpty()) {
            return validationErrors;
        }
        validationErrors.addAll(viewValidation.validateBeanGroup(UiCrossFieldChecks.class, entity));
        return validationErrors;
    }

    private void updateControls(boolean editing) {
        form.getChildren().forEach(component -> {
            if (component instanceof HasValueAndElement<?, ?> field) {
                field.setReadOnly(!editing);
            }
        });

        detailActions.setVisible(editing);
        listLayout.setEnabled(!editing);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}