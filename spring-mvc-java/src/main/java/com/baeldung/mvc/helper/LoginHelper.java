package com.baeldung.mvc.helper;

public class LoginHelper {
    
    public static final String validUserId = "baeldung";
    public static final String validPassword = "baeldung";
    
    /**
     * Return true if userId and password entered by used is same as already stored validUserId
     * and validPassword,
     * else;
     * return false
     * @param userId
     * @param password
     * @return
     */
    public static boolean authenticateUser(final String userId, final String password){
        if(userId.equals(validUserId) && password.equals(validPassword)){
            return true;
        }else{
            return false;
        }
    }

}
