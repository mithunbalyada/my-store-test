package com.mithun.gfk.mystoretest.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * <p>
 *     "Customer World" to be used with Cucumber to provide data
 * </p>
 *  @author Mithun
 */

@Component
@Description(value = "Class that represents Singleton.")
public class CustomerInfo {

    private static CustomerInfo INSTANCE;

    private static final String firstName = "cukes";
    private static final String lastName = "belly";
    private static final String email = "cukes"+ Calendar.getInstance().getTimeInMillis()+"@cukemail.com";;
    private static final String password = "password";
    private static final String existingEmail = "cukes1553040384665@cukemail.com";
    private static final String existingPassword  = "password";

    /**
     * Making the default constructor as private so that no one can instantiate it
     */
    private CustomerInfo(){ }

    private CustomerInfo getInstance(){
        if(null == INSTANCE){
            synchronized (this){
                if(null == INSTANCE)
                    INSTANCE = new CustomerInfo();
            }
        }
        return INSTANCE;
    }


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

    public static String getExistingEmail() {
        return existingEmail;
    }

    public static String getExistingPassword() {
        return existingPassword;
    }
}
