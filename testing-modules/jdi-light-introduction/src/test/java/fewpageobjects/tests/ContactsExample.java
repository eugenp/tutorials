package fewpageobjects.tests;

import fewpageobjects.TestsInit;
import org.testng.annotations.Test;
import pageobject.uiobjects.example.baeldung.entities.ContactInfo;

import static pageobject.uiobjects.example.baeldung.BaeldungSite.contactPage;

public class ContactsExample implements TestsInit {
    ContactInfo MY_CONTACT = new ContactInfo().set(c -> {
        c.firstName = "Roman";
        c.email = "roman.iovlev.jdi@gmail.com";
        c.message = "Hi Baeldung!";} );

    @Test
    public void fillContactTest() {
        contactPage.open();
        contactPage.asForm().send(MY_CONTACT);
        contactPage.asForm().check(MY_CONTACT);
    }
}
