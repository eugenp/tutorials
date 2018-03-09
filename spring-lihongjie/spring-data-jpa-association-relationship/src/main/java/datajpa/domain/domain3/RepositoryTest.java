package datajpa.domain.domain3;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import datajpa.repository.*;
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
public class RepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void test_unidirectional() {
        Stock stock = new Stock();
        stock.setName("stock1");
        stock.setCode("code1");
        Category category = new Category();
        category.setName("category1");
        stock.getCategories().add(category);
        System.out.println("--------------------------------");
        stockRepository.save(stock);
    }

    @Test
    public void test_bidirectional() {
        Stock stock = new Stock();
        stock.setName("stock2");
        stock.setCode("code2");
        Category category = new Category();
        category.setName("category2");
//        stock.getCategories().add(category);
        stock.addCategory(category);
        System.out.println("--------------------------------");
        stockRepository.save(stock);
    }

    @Test
    public void test_bidirectional2() {
        Stock stock = new Stock();
        stock.setName("stock2");
        stock.setCode("code2");
        Category category = new Category();
        category.setName("category2");

        category.getStocks().add(stock);
        System.out.println("--------------------------------");
        categoryRepository.save(category);
    }

    @Test
    public void test_bidirectional3() {
        Stock stock = new Stock();
        stock.setName("stock2");
        stock.setCode("code2");
        Category category = new Category();
        category.setName("category2");

        category.addStock(stock);
        categoryRepository.save(category);
    }

    // lazily initialize
    @Test
    public void test_bidirectional4() {

        Stock stock = stockRepository.findOne(40L);

        Category category = categoryRepository.findOne(40L);

        stock.getCategories().add(category);
        stockRepository.save(stock);
    }

    @Test
    public void test_bidirectional5() {

        Stock stock = new Stock();
        Category category = new Category();
        stock.addCategory(category);
        Stock one = stockRepository.save(stock);
        Category newCategory = new Category();
        one.addCategory(newCategory);
        stockRepository.save(one);
    }

    @Test
    public void test_user_role_save() {
        User user = new User();
        Role role = new Role();
        user.addRole(role);
        userRepository.save(user);
    }

    @Test
    public void test_teacher_student_save() {
        Teacher teacher = new Teacher();
//        teacherRepository.save(teacher);
        Student student = new Student();
//        Student one = studentRepository.save(student);
        teacher.addStudent(student);
        teacherRepository.save(teacher);
    }

    @Test
    public void test_student_teacher_save() {
        Teacher teacher = new Teacher();
        Student student = new Student();
        student.addTeacher(teacher);
        Hobby hobby = new Hobby();
        student.addHobby(hobby);
        studentRepository.save(student);
    }

    @Test
    public void test_student_teacher_save2() {
        Teacher teacher = teacherRepository.findOne(53L);
        Hobby hobby = new Hobby();
        Student student = new Student();
        Student one = studentRepository.save(student);
        one.getHobbies().add(hobby);
//        teacher.addStudent(one);
        teacherRepository.save(teacher);
    }

}
