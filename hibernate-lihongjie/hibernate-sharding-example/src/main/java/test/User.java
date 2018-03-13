package test;

/**
 * Created by lihongjie on 5/19/17.
 */
public class User {

    private int usr_kid;
    private String name;
    private String gender;
    private String country;

    public int getUsr_kid() {
        return usr_kid;
    }

    public void setUsr_kid(int usr_kid) {
        this.usr_kid = usr_kid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "User Id: " + usr_kid + " , Username: " + name + " , Gender: " + gender + " , Country: " + country;
    }
}
