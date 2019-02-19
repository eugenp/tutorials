package com.baeldung.jpacriteria.repository;

import com.baeldung.jpacriteria.entity.MobilePhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class MobilePhoneRepositoryImpl {

    @Autowired
    EntityManager entityManager;

    /**
     *Find mobile phones having 4 gb ram and android version 7 or 8 gb ram and android version 8
     * @return
     */
    public List<MobilePhone> findByRamORAndroidVersion() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MobilePhone> criteriaQuery = criteriaBuilder.createQuery(MobilePhone.class);
        Root<MobilePhone> mobilePhoneRoot = criteriaQuery.from(MobilePhone.class);

        // ram=4 and android version=7
        Predicate predicateFor4gbRam = criteriaBuilder.equal(mobilePhoneRoot.get("ramInGb"), 4);
        Predicate predicateForAndroid7 = criteriaBuilder.equal(mobilePhoneRoot.get("androidVersion"), 7);
        Predicate firstAndCondition = criteriaBuilder.and(predicateFor4gbRam, predicateForAndroid7);

        // ram=8 and android version=8
        Predicate predicateFor8gbRam = criteriaBuilder.equal(mobilePhoneRoot.get("ramInGb"), 8);
        Predicate predicateForAndroid8 = criteriaBuilder.equal(mobilePhoneRoot.get("androidVersion"), 8);
        Predicate secondAndCondition = criteriaBuilder.and(predicateFor8gbRam, predicateForAndroid8);

        // final search filter
        Predicate finalPredicate = criteriaBuilder.or(firstAndCondition, secondAndCondition);

        criteriaQuery.where(finalPredicate);

        List<MobilePhone> phones = entityManager.createQuery(criteriaQuery).getResultList();

        return phones;
    }

    /**
     * Find mobile phones having ram 4 or 8 gb and having android version 7 or 8
     * @return list of mobile phones
     */
    public List<MobilePhone> findByRamAndAndroidVersion() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MobilePhone> criteriaQuery = criteriaBuilder.createQuery(MobilePhone.class);
        Root<MobilePhone> mobilePhoneRoot = criteriaQuery.from(MobilePhone.class);

        // ram=4 or ram=8
        Predicate predicateFor4gbRam = criteriaBuilder.equal(mobilePhoneRoot.get("ramInGb"), 4);
        Predicate predicateFor8gbRam = criteriaBuilder.equal(mobilePhoneRoot.get("ramInGb"), 8);
        Predicate firstORCondition = criteriaBuilder.or(predicateFor4gbRam, predicateFor8gbRam);

        // android version=7 or android version=8
        Predicate predicateForAndroid7 = criteriaBuilder.equal(mobilePhoneRoot.get("androidVersion"), 7);
        Predicate predicateForAndroid8 = criteriaBuilder.equal(mobilePhoneRoot.get("androidVersion"), 8);
        Predicate secondORCondition = criteriaBuilder.or(predicateForAndroid7, predicateForAndroid8);

        // final search filter
        Predicate finalPredicate = criteriaBuilder.and(firstORCondition, secondORCondition);

        criteriaQuery.where(finalPredicate);

        List<MobilePhone> phones = entityManager.createQuery(criteriaQuery).getResultList();
        return phones;
    }

}
