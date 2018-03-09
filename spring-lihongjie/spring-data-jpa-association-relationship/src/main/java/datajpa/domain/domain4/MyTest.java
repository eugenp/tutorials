package datajpa.domain.domain4;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import datajpa.repository.CategoriedItemRepository;
import datajpa.repository.ItemCategoryRepository;
import datajpa.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:exampleApplicationContext-persistence.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class MyTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCategoryRepository categoryRepository;

    @Autowired
    private CategoriedItemRepository categoriedItemRepository;


    @Test
    public void test1() {
        Item item = new Item();
        item.setName("item1");

        Bid bid1 = new Bid();

        Bid bid2 = new Bid();

        item.addBid(bid1);
        item.addBid(bid2);


        itemRepository.save(item);
    }

    @Test
    public void test2() {
        Item item = new Item();
        item.setName("buyer");

        User user = new User();
        user.setUsername("user1");

        user.getBoughtItems().add(item);
        item.setBuyer(user);

        itemRepository.save(item);
    }

    @Test
    public void test3() {
        Item item = new Item();
        item.setName("manytomany");

        Category category = new Category();
//        category.getItems().add(item);

        categoryRepository.save(category);
    }

    @Test
    public void test4() {
        Item item = new Item();
        item.setName("bid-manytomany");

        Category category = new Category();

//        category.getItems().add(item);
//
//        item.getCategories().add(category);

        categoryRepository.save(category);
    }

    @Test
    public void test5() {
        Item item = new Item();
        item.setName("categorizedItem");

        Category category = new Category();
        category.setName("categorizedCategory");
//        categoryRepository.save(category);
        CategorizedItem newLink = new CategorizedItem("test", category, item);


        categoriedItemRepository.save(newLink);

        category.setName("categorizedCategory");

        CategorizedItem newLink1 = new CategorizedItem("categorized", category, item);

//        categorizedItemRepository.save(newLink);

    }

}
