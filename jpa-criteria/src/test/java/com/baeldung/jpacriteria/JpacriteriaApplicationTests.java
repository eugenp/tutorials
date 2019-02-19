package com.baeldung.jpacriteria;

import com.baeldung.jpacriteria.entity.MobilePhone;
import com.baeldung.jpacriteria.repository.MobilePhoneJpaRepository;
import com.baeldung.jpacriteria.repository.MobilePhoneRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpacriteriaApplicationTests {

    @Autowired
    MobilePhoneRepositoryImpl mobilePhoneRepository;

    @Autowired
    MobilePhoneJpaRepository mobilePhoneJpaRepository;

    @Before
    public void init() {

        mobilePhoneJpaRepository.deleteAll();

        MobilePhone firstMobile = new MobilePhone();
        firstMobile.setAndroidVersion(7);
        firstMobile.setRamInGb(4);
        firstMobile.setName("firstMobile");

        MobilePhone secondMobile = new MobilePhone();
        secondMobile.setAndroidVersion(8);
        secondMobile.setRamInGb(8);
        secondMobile.setName("secondMobile");

        MobilePhone thirdMobile = new MobilePhone();
        thirdMobile.setAndroidVersion(7);
        thirdMobile.setRamInGb(8);
        thirdMobile.setName("third");

        MobilePhone fourthMobile = new MobilePhone();
        fourthMobile.setAndroidVersion(7);
        fourthMobile.setRamInGb(8);
        fourthMobile.setName("fourthMobile");

        List<MobilePhone> mobilePhones = new ArrayList<>();
        mobilePhones.add(firstMobile);
        mobilePhones.add(secondMobile);
        mobilePhones.add(thirdMobile);
        mobilePhones.add(fourthMobile);

        mobilePhoneJpaRepository.saveAll(mobilePhones);
    }

    @Test
    public void testFindByRamAndAndroidVersion() {

        List<MobilePhone> mobilePhones = mobilePhoneRepository.findByRamAndAndroidVersion();

        Assert.assertFalse("List is empty", CollectionUtils.isEmpty(mobilePhones));
        Assert.assertEquals("ram size is not 4", 4, mobilePhones.get(0).getRamInGb());
        Assert.assertEquals("android version is not 7", 7, mobilePhones.get(0).getAndroidVersion());
    }

    @Test
    public void testFindByRamORAndroidVersion() {

        List<MobilePhone> mobilePhones = mobilePhoneRepository.findByRamORAndroidVersion();

        Assert.assertFalse("List is empty", CollectionUtils.isEmpty(mobilePhones));
        Assert.assertEquals("ram size is not 4", 4, mobilePhones.get(0).getRamInGb());
        Assert.assertEquals("android version is not 7", 7, mobilePhones.get(0).getAndroidVersion());
    }

}

