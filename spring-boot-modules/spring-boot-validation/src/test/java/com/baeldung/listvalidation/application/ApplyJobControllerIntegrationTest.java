package com.baeldung.listvalidation.application;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class ApplyJobControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private static final String[] CORRECT_PASSPORT_EXPIRY_DATES = {"2025-01-01", "2026-02-29", "2024-03-31"};
    private static final String[] INCORRECT_PASSPORT_EXPIRY_DATES = {"2020-01-01", "2021-02-29", "2022-03-31"};

    private static final Map<String,String> MAP_OF_JOB_LEVEL_TO_PASSPORT_ERRMSG = Map.of(
            "Junior", "Active passport is mandatory for Junior Level Job Application"
            , "MidSenior", "Active passport is mandatory for Mid-Senior Level Job Application"
            , "Senior", "Active passport is mandatory for Senior Level Job Application"
    );
    private static final Map<String, String> MAP_OF_JOB_LEVEL_TO_AGREEMENT_ERRMSG = Map.of(
            "Junior", "Terms and Conditions consent missing for Junior Level Job Application"
            , "MidSenior", "Terms and Conditions consent missing for Mid-Senior Level Job Application"
            , "Senior", "Terms and Conditions consent missing for Senior Level Job Application"
    );
    private static final String[] USERS_INCORRECT_NAMES = {"Bob", "bob Marley", "Bob John Federik Marley", "Bob M@rley"
            , " Bob Marley", "Bob Marley ", "Bob  Marley"};

    private static final String[] USERS_CORRECT_NAMES = {"Bob Marley", "Bob John Marley", "Bobby"};

    private static final Map<String, String[]> MAP_OF_JOBLEVEL_TO_INCORRECT_MIN_EXPERIENCES = Map.of(
            "Junior", new String[]{"1", "2", "3", "4"}
            , "MidSenior", new String[]{"6", "7", "8", "9"}
            , "Senior", new String[]{"11", "12", "13", "14"}
    );
    private static final Map<String, String> MAP_OF_JOBLEVEL_TO_MIN_ERRMSG = Map.of(
            "Junior", "Years of experience cannot be less than 5 Years"
            , "MidSenior", "Years of experience cannot be less than 10 Years"
            , "Senior", "Years of experience cannot be less than 15 Years"
    );
    private static final Map<String, String> MAP_OF_JOBLEVEL_TO_MAX_ERRMSG = Map.of(
            "Junior", "Years of experience cannot be more than 10 Years"
            , "MidSenior", "Years of experience cannot be more than 15 Years"
            , "Senior", "Years of experience cannot be more than 20 Years"
    );
    private static final Map<String, String[]> MAP_OF_JOBLEVEL_TO_INCORRECT_MAX_EXPERIENCES = Map.of(
            "Junior", new String[]{"11", "14", "20", "30"}
            , "MidSenior", new String[]{"16", "20", "25", "40"}
            , "Senior", new String[]{"21", "25", "30", "35"}
    );
    private static final Map<String, String> MAP_OF_INCORRECT_NAMES_TO_ERRMSG = Map.of(
            "Bob", "Name should have at least 5 characters"
            , "bob Marley", "Name should not start with a lower case character"
            , "Bob John Federik Marley", "Name should have at most 20 characters"
            , "Bob M@rley", "Name should contain only alphabets and space"
            , " Bob Marley", "Name should not start with space"
            , "Bob Marley ", "Name should not end with space"
            , "Bob  Marley", "Name should not contain consecutive spaces"
    );

    private static final Map<String, String[]> MAP_OF_JOBLEVEL_TO_CORRECT_EXPERIENCES = Map.of(
            "Junior", new String[]{"6", "7", "8", "9", "10"}
            , "MidSenior", new String[]{"11", "12", "13", "14", "15"}
            , "Senior", new String[]{"16", "17", "18", "19", "20"}
    );


    private static String[] USERS_VALID_NAMES_INVALID_MAXEXP_VALIDPSPT_VALIDAGMT_JSONS = null;
    private static String[] USERS_VALID_NAMES_INVALID_MINEXP_VALIDPSPT_VALIDAGMT_JSONS = null;
    private static String[] USERS_INVALID_NAMES_INVALID_MAXEXP_VALIDPSPT_VALIDAGMT_JSONS = null;
    private static String[] USERS_INVALID_NAMES_INVALID_MINEXP_VALIDPSPT_VALIDAGMT_JSONS = null;
    private static String[] USERS_ALL_FIELDS_VALID_EXCEPT_FOR_PSPT_AND_AGMT_JSONS = null;
    private static String[] USERS_ALL_FIELDS_VALID_JSONS = null;

    @Before
    public void prepareAllValidFieldsExceptForPsptAndAgmt() {
        USERS_ALL_FIELDS_VALID_EXCEPT_FOR_PSPT_AND_AGMT_JSONS = new String[USERS_CORRECT_NAMES.length
                * MAP_OF_JOBLEVEL_TO_CORRECT_EXPERIENCES.size() * 5 * INCORRECT_PASSPORT_EXPIRY_DATES.length];
        int i = 0;
        for (String user : USERS_CORRECT_NAMES) {
            for (String jobLevel : MAP_OF_JOBLEVEL_TO_CORRECT_EXPERIENCES.keySet()) {
                for (String experience : MAP_OF_JOBLEVEL_TO_CORRECT_EXPERIENCES.get(jobLevel)) {
                    for (String passportExpiryDate : INCORRECT_PASSPORT_EXPIRY_DATES) {
                        USERS_ALL_FIELDS_VALID_EXCEPT_FOR_PSPT_AND_AGMT_JSONS[i++] = "{\"name\": \"" + user
                                + "\", \"experience\": \"" + experience + "\", \"jobLevel\": \"" + jobLevel
                                + "\", \"passportExpiryDate\": \"" + passportExpiryDate
                                + "\", \"agreement\": \"" + false + "\"}";
                    }
                }
            }
        }

    }
    @Before
    public void prepareUserWithAllFieldsValid() {
        USERS_ALL_FIELDS_VALID_JSONS = new String[USERS_CORRECT_NAMES.length
                * MAP_OF_JOBLEVEL_TO_CORRECT_EXPERIENCES.size() * 5 * CORRECT_PASSPORT_EXPIRY_DATES.length];
        int i = 0;
        for (String user : USERS_CORRECT_NAMES) {
            for (String jobLevel : MAP_OF_JOBLEVEL_TO_CORRECT_EXPERIENCES.keySet()) {
                for (String experience : MAP_OF_JOBLEVEL_TO_CORRECT_EXPERIENCES.get(jobLevel)) {
                    for (String passportExpiryDate : CORRECT_PASSPORT_EXPIRY_DATES) {
                        USERS_ALL_FIELDS_VALID_JSONS[i++] = "{\"name\": \"" + user
                                + "\", \"experience\": \"" + experience + "\", \"jobLevel\": \"" + jobLevel
                                + "\", \"passportExpiryDate\": \"" + passportExpiryDate
                                + "\", \"agreement\": \"" + true + "\"}";
                    }
                }
            }
        }
    }
    @Before
    public void prepareUsersInvalidNamesInValidMaxExpValidPassportValidAgreementJsons() {
        USERS_INVALID_NAMES_INVALID_MAXEXP_VALIDPSPT_VALIDAGMT_JSONS = new String[USERS_INCORRECT_NAMES.length
                * MAP_OF_JOBLEVEL_TO_INCORRECT_MAX_EXPERIENCES.size() * 4 * CORRECT_PASSPORT_EXPIRY_DATES.length];
        int i = 0;
        for (String user : USERS_INCORRECT_NAMES) {
            for (String jobLevel : MAP_OF_JOBLEVEL_TO_INCORRECT_MAX_EXPERIENCES.keySet()) {
                for (String experience : MAP_OF_JOBLEVEL_TO_INCORRECT_MAX_EXPERIENCES.get(jobLevel)) {
                    for (String passportExpiryDate : CORRECT_PASSPORT_EXPIRY_DATES) {
                        USERS_INVALID_NAMES_INVALID_MAXEXP_VALIDPSPT_VALIDAGMT_JSONS[i++] = "{\"name\": \"" + user
                                + "\", \"experience\": \"" + experience + "\", \"jobLevel\": \"" + jobLevel
                                + "\", \"passportExpiryDate\": \"" + passportExpiryDate
                                + "\", \"agreement\": \"" + true + "\"}";
                    }
                }
            }
        }
    }
    @Before
    public void prepareUsersCorrectNamesIncorrectMinExpJsons() {
        USERS_VALID_NAMES_INVALID_MINEXP_VALIDPSPT_VALIDAGMT_JSONS = new String[USERS_CORRECT_NAMES.length
                * MAP_OF_JOBLEVEL_TO_INCORRECT_MIN_EXPERIENCES.size() * 4 * CORRECT_PASSPORT_EXPIRY_DATES.length];
        int i = 0;
        for (String user : USERS_CORRECT_NAMES) {
            for (String jobLevel : MAP_OF_JOBLEVEL_TO_INCORRECT_MIN_EXPERIENCES.keySet()) {
                for (String experience : MAP_OF_JOBLEVEL_TO_INCORRECT_MIN_EXPERIENCES.get(jobLevel)) {
                    for (String passportExpiryDate : CORRECT_PASSPORT_EXPIRY_DATES) {
                        USERS_VALID_NAMES_INVALID_MINEXP_VALIDPSPT_VALIDAGMT_JSONS[i++] = "{\"name\": \"" + user
                                + "\", \"experience\": \"" + experience + "\", \"jobLevel\": \"" + jobLevel
                                + "\", \"passportExpiryDate\": \"" + passportExpiryDate
                                + "\", \"agreement\": \"" + true + "\"}";
                    }
                }
            }
        }
    }
    @Before
    public void prepareUsersIncorrectNamesIncorrectMinExpJsons() {
        USERS_INVALID_NAMES_INVALID_MINEXP_VALIDPSPT_VALIDAGMT_JSONS = new String[USERS_INCORRECT_NAMES.length
                * MAP_OF_JOBLEVEL_TO_INCORRECT_MIN_EXPERIENCES.size() * 4 * CORRECT_PASSPORT_EXPIRY_DATES.length];
        int i = 0;
        for (String user : USERS_INCORRECT_NAMES) {
            for (String jobLevel : MAP_OF_JOBLEVEL_TO_INCORRECT_MIN_EXPERIENCES.keySet()) {
                for (String experience : MAP_OF_JOBLEVEL_TO_INCORRECT_MIN_EXPERIENCES.get(jobLevel)) {
                    for (String passportExpiryDate : CORRECT_PASSPORT_EXPIRY_DATES) {
                        USERS_INVALID_NAMES_INVALID_MINEXP_VALIDPSPT_VALIDAGMT_JSONS[i++] = "{\"name\": \"" + user
                                + "\", \"experience\": \"" + experience + "\", \"jobLevel\": \"" + jobLevel
                                + "\", \"passportExpiryDate\": \"" + passportExpiryDate
                                + "\", \"agreement\": \"" + true + "\"}";
                    }
                }
            }
        }
    }
    @Before
    public void prepareUsersCorrectNamesIncorrectMaxExpJsons() {
        USERS_VALID_NAMES_INVALID_MAXEXP_VALIDPSPT_VALIDAGMT_JSONS = new String[USERS_CORRECT_NAMES.length
                * MAP_OF_JOBLEVEL_TO_INCORRECT_MAX_EXPERIENCES.size() * 4 * CORRECT_PASSPORT_EXPIRY_DATES.length];
        int i = 0;
        for (String user : USERS_CORRECT_NAMES) {
            for (String jobLevel : MAP_OF_JOBLEVEL_TO_INCORRECT_MAX_EXPERIENCES.keySet()) {
                for (String experience : MAP_OF_JOBLEVEL_TO_INCORRECT_MAX_EXPERIENCES.get(jobLevel)) {
                    for (String passportExpiryDate : CORRECT_PASSPORT_EXPIRY_DATES) {
                        USERS_VALID_NAMES_INVALID_MAXEXP_VALIDPSPT_VALIDAGMT_JSONS[i++] = "{\"name\": \"" + user
                                + "\", \"experience\": \"" + experience + "\", \"jobLevel\": \"" + jobLevel
                                + "\", \"passportExpiryDate\": \"" + passportExpiryDate
                                + "\", \"agreement\": \"" + true + "\"}";
                    }
                }
            }
        }
    }
    @Test
    public void whenAllFieldsValidExceptForPsptAndAgmt_thenBadRequestResponse() throws Exception {
        String[] users = USERS_ALL_FIELDS_VALID_EXCEPT_FOR_PSPT_AND_AGMT_JSONS;
        for (String user : users) {
            JSONObject jsonObject = new JSONObject(user);
            String jobLevel = jsonObject.getString("jobLevel");
            mockMvc.perform(MockMvcRequestBuilders.post("/applyLevel" + jobLevel)
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content()
                            .json("{\"agreement\":\"" + MAP_OF_JOB_LEVEL_TO_AGREEMENT_ERRMSG.get(jobLevel)
                                    + "\",\"passportExpiryDate\":\"" + MAP_OF_JOB_LEVEL_TO_PASSPORT_ERRMSG.get(jobLevel) + "\"}"));
        }
    }
    @Test
    public void whenUserWithAllFieldsValid_thenCorrectResponse() throws Exception {
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        String[] users = USERS_ALL_FIELDS_VALID_JSONS;
        for (String user : users) {
            JSONObject jsonObject = new JSONObject(user);
            String jobLevel = jsonObject.getString("jobLevel");
            mockMvc.perform(MockMvcRequestBuilders.post("/applyLevel" + jobLevel)
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(textPlainUtf8))
                    .andExpect(MockMvcResultMatchers.content().string("Application submitted successfully"));
        }
    }
    @Test
    public void whenValidUserNamesInValidMaxExpValidPsptValidAgmt_thenBadRequestResponse() throws Exception {
        String[] users = USERS_VALID_NAMES_INVALID_MAXEXP_VALIDPSPT_VALIDAGMT_JSONS;
        for (String user : users) {
            JSONObject jsonObject = new JSONObject(user);
            String jobLevel = jsonObject.getString("jobLevel");
            mockMvc.perform(MockMvcRequestBuilders.post("/applyLevel" + jobLevel)
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content()
                            .json("{\"experience\":\"" + MAP_OF_JOBLEVEL_TO_MAX_ERRMSG.get(jobLevel)
                                    + "\"}"));
        }
    }
    @Test
    public void whenInvalidUserNamesInValidMaxExpValidPsptValidAgmt_thenBadRequestResponse() throws Exception {
        String[] users = USERS_INVALID_NAMES_INVALID_MAXEXP_VALIDPSPT_VALIDAGMT_JSONS;
        for (String user : users) {
            JSONObject jsonObject = new JSONObject(user);
            String jobLevel = jsonObject.getString("jobLevel");
            String name = jsonObject.getString("name");
            mockMvc.perform(MockMvcRequestBuilders.post("/applyLevel" + jobLevel)
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content()
                            .json("{\"name\":\"" + MAP_OF_INCORRECT_NAMES_TO_ERRMSG.get(name)
                                    + "\",\"experience\":\"" + MAP_OF_JOBLEVEL_TO_MAX_ERRMSG.get(jobLevel) + "\"}"));
        }
    }
    @Test
    public void whenValidUserNamesInValidMinExpValidPsptValidAgmt_thenBadRequestResponse() throws Exception {
        String[] users = USERS_VALID_NAMES_INVALID_MINEXP_VALIDPSPT_VALIDAGMT_JSONS;
        for (String user : users) {
            JSONObject jsonObject = new JSONObject(user);
            String jobLevel = jsonObject.getString("jobLevel");
            mockMvc.perform(MockMvcRequestBuilders.post("/applyLevel" + jobLevel)
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content()
                            .json("{\"experience\":\"" + MAP_OF_JOBLEVEL_TO_MIN_ERRMSG.get(jobLevel) + "\"}"));
        }
    }
    @Test
    public void whenInValidUserNamesInValidMinExpValidPsptValidAgmt_thenBadRequestResponse() throws Exception {
        String[] users = USERS_INVALID_NAMES_INVALID_MINEXP_VALIDPSPT_VALIDAGMT_JSONS;
        for (String user : users) {
            JSONObject jsonObject = new JSONObject(user);
            String jobLevel = jsonObject.getString("jobLevel");
            String name = jsonObject.getString("name");
            mockMvc.perform(MockMvcRequestBuilders.post("/applyLevel" + jobLevel)
                            .content(user)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content()
                            .json("{\"name\":\"" + MAP_OF_INCORRECT_NAMES_TO_ERRMSG.get(name)
                                    + "\",\"experience\":\"" + MAP_OF_JOBLEVEL_TO_MIN_ERRMSG.get(jobLevel) + "\"}"));
        }
    }
}
