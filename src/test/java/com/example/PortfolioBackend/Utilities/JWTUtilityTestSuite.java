package com.example.PortfolioBackend.Utilities;
import com.example.PortfolioBackend.Exceptions.BadCredentialsException;
import com.example.PortfolioBackend.Exceptions.BadTokenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class JWTUtilityTestSuite {

    @Autowired
    JWTUtility sut;
    @Value("${admin.username}")
    String correctAdminUsername;
    String incorrectAdminUsername = "not_the_admin";

    @Value("${admin.password}")
    String correctAdminPassword;
    String incorrectAdminPassword = "the wrong password 55%%%";
    @Value("${jwt.secret}")
    String secret;


    /** Confirm test setup is valid by checking that the admin username and password
     * are being */
    @Test
    void test_loadingAdminUsernameAndPasswordFromApplicationYmlForTestSuite(){
        Assertions.assertEquals("dummy-admin-name", correctAdminUsername);
        Assertions.assertEquals("bad-password-for-tests1@", correctAdminPassword);
    }

    /** Confirm incorrectAdminUsername and correctAdminUsername are not equal
     * to ensure other tests are valid. */
    @Test
    void test_incorrectAdminUsernameNotEqualToCorrectUsername() {
        Assertions.assertNotEquals(incorrectAdminUsername, correctAdminUsername);
    }

    /** Confirm incorrectAdminPassword and correctAdminPassword are not equal
     * to ensure other tests are valid. */
    @Test
    void test_incorrectAdminPasswordNotEqualToCorrectAdminPassword() {
        Assertions.assertNotEquals(incorrectAdminPassword,correctAdminPassword);
    }




    @Test
    void test_parseTokenReturnsAdminUsername_givenValidToken() throws BadCredentialsException {
        String token = sut.createToken(correctAdminUsername, correctAdminPassword);
        try {
            Assertions.assertEquals(correctAdminUsername, sut.parseToken(token).orElse(null));
        } catch (BadTokenException e) {
            throw new RuntimeException(e);
        }
    }

}
