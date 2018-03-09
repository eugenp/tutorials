package jcg.zheng.demo.jpademo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jcg.zheng.demo.jpademo.entity.Contact;
import jcg.zheng.demo.jpademo.type.PhoneType;

@Repository
@Transactional
public interface ContactRepository extends JpaRepository<Contact, Long> {

	@Query("SELECT con FROM Contact con  WHERE con.phoneType=(:pType) AND con.lastName= (:lName)")
	List<Contact> findByLastNameAndPhoneType(@Param("pType") PhoneType pType, @Param("lName") String lName);

}
