import org.example.Address;
import org.example.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MainTest {

    @Test
    public void shallowCopyIsNotSameUser() {
        Address address = new Address("Quang Trung St 6", "Ha Noi", "Viet Nam");
        User user = new User("Nguyen", "Thanh", address);
        User shallowCopy = new User(user.getFirstName(), user.getLastName(), user.getAddress());
        assertThat(shallowCopy).isNotSameAs(user);
    }

    @Test
    public void addressShallowCopyIsSameAddress() {
        Address address = new Address("Quang Trung St 6", "Ha Noi", "Viet Nam");
        User user = new User("Nguyen", "Thanh", address);
        User shallowCopy = new User(user.getFirstName(), user.getLastName(), user.getAddress());
        assertThat(shallowCopy.getAddress()).isSameAs(address);
    }

    @Test
    public void countryAddressShallowCopyIsEqualToCountryAddress() {
        Address address = new Address("Quang Trung St 6", "Ha Noi", "Viet Nam");
        User user = new User("Nguyen", "Thanh", address);
        User shallowCopy = new User(user.getFirstName(), user.getLastName(), user.getAddress());
        address.setCountry("America");
        assertThat(shallowCopy.getAddress().getCountry()).isEqualTo(user.getAddress().getCountry());
    }

    @Test
    public void addressDeepCopyIsNotEqualToAddress() {
        Address address = new Address("Quang Trung St 6", "Ha Noi", "Viet Nam");
        User user = new User("Nguyen", "Thanh", address);
        User deepCopy = new User(user);
        assertThat(deepCopy.getAddress()).isNotSameAs(address);
        assertThat(deepCopy.getAddress()).isNotSameAs(user.getAddress());
    }

    @Test
    public void countryAddressDeepCopyIsNotEqualToCountryAddress() {
        Address address = new Address("Quang Trung St 6", "Ha Noi", "Viet Nam");
        User user = new User("Nguyen", "Thanh", address);
        User deepCopy = new User(user);
        address.setCountry("America");
        assertThat(user.getAddress().getCountry()).isNotEqualTo(deepCopy.getAddress().getCountry());
    }
}
