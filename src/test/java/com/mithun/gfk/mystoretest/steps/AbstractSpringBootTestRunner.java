package com.mithun.gfk.mystoretest.steps;

import com.mithun.gfk.mystoretest.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Calendar;

@SpringBootTest(classes = Application.class)
@ContextConfiguration
@DirtiesContext
public abstract class AbstractSpringBootTestRunner {

    private final static String firstName="cukes";

    private final static String lastName="belly";

    private final static String email=firstName+ Calendar.getInstance().getTimeInMillis()+"@cukemail.com";

    private final static String password="password";


    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }
}
