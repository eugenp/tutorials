package com.baeldung.application;

import com.baeldung.domain.ItemPriority;
import com.baeldung.domain.TodoItem;
import com.baeldung.domain.TodoItemService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConsoleApplication {
    public static void main(String[] args) {
        ApplicationContext context
                = new AnnotationConfigApplicationContext(AppConfiguration.class);
        TodoItemService todoItemServiceBean = context.getBean(TodoItemService.class);

        System.out.println("Show initial list of items");
        ConsoleAppService consoleAppService = new ConsoleAppServiceImpl(todoItemServiceBean);
        consoleAppService.showAllItems();
        System.out.println();

        System.out.println("Add new item");
        TodoItem item = new TodoItem("fourth item", "fourth details", ItemPriority.HIGH);
        consoleAppService.addItem(item);
        consoleAppService.showAllItems();
        System.out.println();

        System.out.println("Update Item Id = 4 to MED Priority");
        item = consoleAppService.getItem(4);
        item.setItemPriority(ItemPriority.MED);
        consoleAppService.updateItem(item);
        consoleAppService.showAllItems();
        System.out.println();

        System.out.println("Upgrade Priority of Item Id 4");
        item = consoleAppService.getItem(4);
        System.out.println(consoleAppService.upgradePriority(item));
        System.out.println();

        System.out.println("Degrade Priority of Item Id 1");
        item = consoleAppService.getItem(1);
        System.out.println(consoleAppService.degradePriority(item));
        System.out.println();

        // show all items again
        consoleAppService.showAllItems();
    }
}
