package com.baeldung.dtopattern.domain;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class User {

    private static final IvParameterSpec ivSpec;

    static  {
        byte[] iv = new byte[128/8];
        new Random().nextBytes(iv);
        ivSpec = new IvParameterSpec(iv);
    }

    private String id;
    private String name;
    private String password;
    private List<Role> roles;

    public User(String name, String password, List<Role> roles) {
        this.name = Objects.requireNonNull(name);
        this.password = this.encrypt(password);
        this.roles = Objects.requireNonNull(roles);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public List<Role> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String encrypt(String password) {
        Objects.requireNonNull(password);
        try {
            final byte[] secret = "secret".getBytes(StandardCharsets.UTF_8);
            SecretKeySpec key = new SecretKeySpec(secret, "DES");
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            final byte[] encriptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return new String(encriptedBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            // do nothing
            return "";
        }
    }
}
