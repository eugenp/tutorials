//package datajpa.domain.domain2;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.OneToOne;
//
///**
// * OneToOne
// * User Address Shipment
// */
//
//@Entity(name = "user_001")
//public class User {
//
//    private String firstname;
//
//    private String lastname;
//
//    private String username;
//
//    private String password;
//
//    private String email;
//
//    private int ranking;
//
//    private boolean admin;
//
//    @OneToOne(cascade = CascadeType.MERGE)
//    private Address shippingAddress;
//
//    public String getFirstname() {
//        return firstname;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public String getLastname() {
//        return lastname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public boolean isAdmin() {
//        return admin;
//    }
//
//    public void setAdmin(boolean admin) {
//        this.admin = admin;
//    }
//
//    public Address getShippingAddress() {
//        return shippingAddress;
//    }
//
//    public void setShippingAddress(Address shippingAddress) {
//        this.shippingAddress = shippingAddress;
//    }
//
//    public int getRanking() {
//        return ranking;
//    }
//
//    public void setRanking(int ranking) {
//        this.ranking = ranking;
//    }
//}
