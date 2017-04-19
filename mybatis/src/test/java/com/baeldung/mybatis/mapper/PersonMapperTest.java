package com.baeldung.mybatis.mapper;

import com.baeldung.mybatis.model.Address;
import com.baeldung.mybatis.model.Person;
import com.baeldung.mybatis.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class PersonMapperTest {

    SqlSession session;

    @Before
    public void setup() throws SQLException {

        session = MyBatisUtil.getSqlSessionFactory().openSession();
        createTables(session);

    }

    private void createTables(SqlSession session) throws SQLException {

        String createPersonTable = "create table person ("
                + "personId integer not null generated always as"
                + " identity (start with 1, increment by 1),   "
                + "name varchar(30) not null, "
                + "constraint primary_key_person primary key (personId))";

        String createAddressTable = "create table address ("
                + "addressId integer not null generated always as"
                + " identity (start with 1, increment by 1),   "
                + "streetAddress varchar(300), personId integer, "
                + "constraint primary_key_address primary key (addressId))";

        String alterTable="ALTER TABLE " +
                "    address ADD CONSTRAINT fk_person FOREIGN KEY (personId) REFERENCES person (personId)";


        session.getConnection().createStatement().execute(createPersonTable);
        session.getConnection().createStatement().execute(createAddressTable);
        session.getConnection().createStatement().execute(alterTable);

    }

    @Test
    public void whenPersonAdressSaved_ThenPersonAddressCanBeQueried(){
        Person person=new Person("Baljeet S");
        Address address = new Address("Pune");
        PersonMapper personMapper=session.getMapper(PersonMapper.class);
        Integer id =personMapper.save(person);
        address.setPersonId(id);
        AddressMapper addressMapper=session.getMapper(AddressMapper.class);
        addressMapper.saveAddress(address);

        Person returnedPerson= personMapper.getPersonById(id);
        assertEquals("Baljeet S", returnedPerson.getName());
        assertEquals("Pune", returnedPerson.getAddresses().get(0).getStreetAddress());
    }

    @Test
    public void whenPersonSaved_ThenPersonCanBeQueried(){
        Person person=new Person("Baljeet S");
        Address address = new Address("Pune");
        PersonMapper personMapper=session.getMapper(PersonMapper.class);
        Integer id =personMapper.save(person);
        address.setPersonId(id);
        AddressMapper addressMapper=session.getMapper(AddressMapper.class);
        addressMapper.saveAddress(address);

        Person returnedPerson= personMapper.getPerson(id);
        assertEquals("Baljeet S", returnedPerson.getName());
    }

    @Test
    public void whenPersonUpdated_ThenPersonIsChanged(){
        Person person=new Person("Baljeet S");
        Address address = new Address("Pune");
        PersonMapper personMapper=session.getMapper(PersonMapper.class);
        Integer id =personMapper.save(person);
        address.setPersonId(id);
        AddressMapper addressMapper=session.getMapper(AddressMapper.class);
        addressMapper.saveAddress(address);

        personMapper.updatePerson(new Person(id,"Baljeet1"));
        Person returnedPerson= personMapper.getPerson(id);
        assertEquals("Baljeet1", returnedPerson.getName());
    }
    @Test
    public void whenPersoSaved_ThenMapIsReturned(){
        Person person=new Person("Baljeet S");
        Address address = new Address("Pune");
        PersonMapper personMapper=session.getMapper(PersonMapper.class);
        Integer id =personMapper.save(person);
        address.setPersonId(id);
        AddressMapper addressMapper=session.getMapper(AddressMapper.class);
        addressMapper.saveAddress(address);

        Map<Integer, Person> returnedPerson= personMapper.getAllPerson();
        assertEquals(1, returnedPerson.size());
    }

    @Test
    public void whenPersonSearched_ThenResultIsReturned(){
        Person person=new Person("Baljeet S");
        Address address = new Address("Pune");
        PersonMapper personMapper=session.getMapper(PersonMapper.class);
        Integer id =personMapper.save(person);
        address.setPersonId(id);
        AddressMapper addressMapper=session.getMapper(AddressMapper.class);
        addressMapper.saveAddress(address);

        Person returnedPerson= personMapper.getPersonByName("Baljeet S");
        assertEquals("Baljeet S", returnedPerson.getName());
    }

    @Test
    public void whenAddressSearched_ThenResultIsReturned(){
        Person person=new Person("Baljeet S");
        Address address = new Address("Pune");
        PersonMapper personMapper=session.getMapper(PersonMapper.class);
        Integer id =personMapper.save(person);
        address.setPersonId(id);
        AddressMapper addressMapper=session.getMapper(AddressMapper.class);
        Integer addressId=addressMapper.saveAddress(address);
        Address returnedAddress=addressMapper.getAddresses(addressId);

        assertEquals("Pune", returnedAddress.getStreetAddress());
    }

    @After
    public void cleanup() throws SQLException {
        session.getConnection().createStatement().execute("drop table address");
        session.getConnection().createStatement().execute("drop table person");

        session.close();

    }

}
