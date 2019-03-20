package com.mithun.gfk.mystoretest.steps;

import com.mithun.gfk.mystoretest.customer.CustomerInfo;
import com.mithun.gfk.mystoretest.driver.DriverSetup;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Fail;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *     Cucumber Step definition class that is extending AbstractSpringBootTestRunner.class
 *     Spring boot is used here for Dependency Injection (DI) as Cucumber executes each Scenario in new world.
 *     DI allows us to share members and their states between scenarios and features.
 * </p>
 * @author Mithun Balyada
 */


public class ApplicationSteps extends AbstractSpringBootTestRunner implements En {

    private final static Logger LOG = LoggerFactory.getLogger(ApplicationSteps.class);

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    @Autowired
    DriverSetup driverSetup;

    @Autowired
    CustomerInfo customerInfo;

    @Value("${test.url}")
    private String testURL;


    @Before
    public void setUp()  {

        try {

            Assertions.assertThat(customerInfo).isNotNull();
            LOG.info("Customer Info bean is not null");

            Assertions.assertThat(driverSetup).isNotNull();
            driver = driverSetup.getDriver();
            webDriverWait = driverSetup.getWebDriverWait();


            Assertions.assertThat(driver).isNotNull();
            LOG.info("Driver is loaded");

            Assertions.assertThat(webDriverWait).isNotNull();
            LOG.info("WebDriverWait is initalized");


        } catch (IllegalAccessException e) {

            LOG.error(e.getMessage());
            Fail.fail("Test failed: Unable to load proper driver");

        }
    }



    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }


    public ApplicationSteps(){

        Before(() ->{

            LOG.info("------------- TEST CONTEXT SETUP -------------");
            LOG.info("Scenario execution begins >");
            LOG.info("DATE: "+new SimpleDateFormat("dd-MM-YYYY HH:mm:ss").format(new Date()));

        });



        After((Scenario scenario) ->{
            LOG.info("Scenario: \""+scenario.getName()+"\" is completed");
            LOG.info("------------- SCENARIO COMPLETED -------------");
        });



        Given("the new user is on the home page as given in property files", () -> {

            Assertions.assertThat(testURL).isNotNull();
            driver.get(testURL);

            LOG.info("Application URL under test is \""+testURL+"\"");

        });



        When("I click on the Sign in button", () -> {

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login"))).click();
            LOG.info("Sign in button is located and clicked");

        });



        When("I fill my email address in create account section of the page", () -> {

            driver.findElement(By.id("email_create")).sendKeys(customerInfo.getEmail());
            LOG.info("Filled in email '"+customerInfo.getEmail()+"'");

        });



        When("I click on the Create an Account button", () -> {

            driver.findElement(By.id("SubmitCreate")).click();
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_gender2"))).click();

            LOG.info("Clicked on the Submit button");

        });


        /**
         * Validate that the section header has content "CREATE AN ACCOUNT"
         */
        When("I am taken to Create an Account page", () -> {

            Assertions.assertThat(driver.findElement(By.xpath("//*[@id=\"noSlide\"]/h1")).getText()).contains("CREATE AN ACCOUNT");
            LOG.info("Navigated to next page. Section header content is 'CREATE AN ACCOUNT'");

        });



        When("I provide fill the necessary information", () -> {

            driver.findElement(By.id("customer_firstname")).sendKeys(customerInfo.getFirstName());
            LOG.info("Keyed in first name as "+customerInfo.getFirstName());

            driver.findElement(By.id("customer_lastname")).sendKeys(customerInfo.getLastName());
            LOG.info("Keyed in last name as "+customerInfo.getLastName());

            driver.findElement(By.id("passwd")).sendKeys(customerInfo.getPassword());
            LOG.info("Keyed in password as XXXXXXXXXX");

            LOG.info("Entering date of birth");
            Select select = new Select(driver.findElement(By.id("days")));
            select.selectByValue("1");
            LOG.info("Entered days");

            select = new Select(driver.findElement(By.id("months")));
            select.selectByValue("1");
            LOG.info("Entered Months");

            select = new Select(driver.findElement(By.id("years")));
            select.selectByValue("1985");
            LOG.info("Entered Year");

            driver.findElement(By.id("company")).sendKeys("myTestOrg");
            LOG.info("Entered Company");

            driver.findElement(By.id("address1")).sendKeys("Address1, Flat1");
            LOG.info("Entered Address1");

            driver.findElement(By.id("address2")).sendKeys("Address2");
            LOG.info("Entered Address2");

            driver.findElement(By.id("city")).sendKeys("Milkyway");
            LOG.info("Entered City");

            select = new Select(driver.findElement(By.id("id_state")));
            select.selectByVisibleText("Colorado");
            LOG.info("Selected state from the dropdown");

            driver.findElement(By.id("postcode")).sendKeys("12345");
            LOG.info("Entered postcode");

            driver.findElement(By.id("other")).sendKeys("Lorem Ipsum");
            LOG.info("Entered other details");

            driver.findElement(By.id("phone")).sendKeys("91184453863");
            LOG.info("Entered phone numbers");

            driver.findElement(By.id("phone_mobile")).sendKeys("80723272672");
            LOG.info("Entered mobile phone number");

            driver.findElement(By.id("alias")).sendKeys("serenity");
            LOG.info("Enetered alias details");

        });



        When("I click on the Register button", () -> {

            driver.findElement(By.id("submitAccount")).click();
            LOG.info("Submitted registration form by clicking submit button");

        });



        Then("my registration should be successful", () -> {

            WebElement heading = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));

            Assertions.assertThat(heading.getText()).isEqualTo("MY ACCOUNT");
            LOG.info("******Registration is successful*******");

            Assertions.assertThat(driver.findElement(By.className("account")).getText()).contains(customerInfo.getFirstName()+" "+customerInfo.getLastName());
            LOG.info("FirstName and LastName is displayed on the screen");

            Assertions.assertThat(driver.findElement(By.className("info-account")).getText()).contains("Welcome to your account.");
            LOG.info("'Welcome to your account' message is displayed on the screen");

            Assertions.assertThat(driver.getCurrentUrl()).contains("controller=my-account");
            Assertions.assertThat(driver.findElement(By.className("logout")).isDisplayed()).isEqualTo(true);
            LOG.info("'logout' option is available on the screen");

        });


        /* -------------------SCENARIO-2-----------------------*/


        Given("I am a registered user", () -> {

            Assertions.assertThat(customerInfo.getEmail()).isNotNull();
            LOG.info("Trying to sign in with the email - "+customerInfo.getEmail());

            Assertions.assertThat(customerInfo.getPassword()).isNotNull();
        });



        Given("I navigate to the home page", () -> {

            Assertions.assertThat(testURL).isNotNull();
            Assertions.assertThat(driver).isNotNull();
            Assertions.assertThat(webDriverWait).isNotNull();

            driver.get(testURL);

            LOG.info("Application URL under test is \""+testURL+"\"");

        });



        When("I fill my Email Address on Already Registered block", () -> {

            driver.findElement(By.id("email")).sendKeys(customerInfo.getExistingEmail());
            LOG.info("Filled in email '"+customerInfo.getExistingEmail()+"'");

        });



        When("I fill my Password on Already Registered block", () -> {

            driver.findElement(By.id("passwd")).sendKeys(customerInfo.getExistingPassword());
            LOG.info("Keyed in password XXXXXX");

        });



        When("I click on the Sign in button in Already Registered block", () -> {

            driver.findElement(By.id("SubmitLogin")).click();
            LOG.info("Clicked login button");

        });




        Then("My account page is opened", () -> {

            Assertions.assertThat(driver.findElement(By.xpath("//*[@id=\"center_column\"]/p")).getText()).contains("Welcome to your account.");
            LOG.info("'Welcome to your account' message is displayed on the page");

            WebElement heading = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));
            Assertions.assertThat(heading.getText()).contains("MY ACCOUNT");
            LOG.info("Heading is showing 'MY ACCOUNT'");

            Assertions.assertThat(driver.getCurrentUrl()).contains("controller=my-account");
            LOG.info("URL has the controller as :"+driver.getCurrentUrl());

        });




        Then("My username is shown in the header", () -> {

            Assertions.assertThat(driver.findElement(By.className("account")).getText()).contains(customerInfo.getFirstName()+" "+customerInfo.getLastName());
            LOG.info(customerInfo.getFirstName()+" "+customerInfo.getLastName()+" is displayed in the navigation menu.");

        });


        Then("I am able to see the logout button", () -> {

            Assertions.assertThat(driver.findElement(By.className("logout")).isDisplayed()).isEqualTo(true);
            LOG.info("Logout button is noticed");

        });


        /* ------------------SCENARIO-3--------------------------*/

        Given("I am a registered user and I am able to login to the portal", () ->{

            LOG.info("Performing Login operation using the credentials "+customerInfo.getExistingEmail());
            Assertions.assertThat(testURL).isNotNull();
            Assertions.assertThat(driver).isNotNull();
            Assertions.assertThat(webDriverWait).isNotNull();

            driver.get(testURL);

            LOG.info("Application URL under test is \""+testURL+"\"");

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login"))).click();
            LOG.info("Sign in button is located and clicked");

            driver.findElement(By.id("email")).sendKeys(customerInfo.getExistingEmail());
            LOG.info("Filled in email '"+customerInfo.getExistingEmail()+"'");

            driver.findElement(By.id("passwd")).sendKeys(customerInfo.getExistingPassword());
            LOG.info("Keyed in password XXXXXX");

            driver.findElement(By.id("SubmitLogin")).click();
            LOG.info("Clicked login button");

        });

        When("I click Women button in the header", () -> {

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Women"))).click();
            LOG.info("Clicked on the button 'Women' on the navigation bar");

        });


        When("I click the product with name", (DataTable dataTable) -> {

            String productName = (String) dataTable.asList(String.class).get(0);

            driver.findElement(By.xpath("//a[@title='"+productName+"']/ancestor::li")).click();
            driver.findElement(By.xpath("//a[@title='"+productName+"']/ancestor::li")).click();
            LOG.info("Clicked on the product name '"+productName+"'");

        });


        When("I click Add to cart", () -> {

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();
            LOG.info("Clicked on Add to cart button");

        });


        When("I click on Proceed to Checkout on the popup window", () -> {

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id='layer_cart']//a[@class and @title='Proceed to checkout']"))).click();
            LOG.info("Clicked on proceed to Checkout button on the popup window");

        });


        When("I click on Proceed to Checkout on the summary section", () -> {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(@class,'cart_navigation')]/a[@title='Proceed to checkout']"))).click();
            LOG.info("Clicked on proceed to Checkout button in the summary section");
        });



        When("I click on Proceed to Checkout on the address section", () -> {

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("processAddress"))).click();
            LOG.info("Clicked on proceed to Checkout button in the address section");

        });



        When("I agree to the Terms of Service", () -> {

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uniform-cgv"))).click();
            LOG.info("Checked the check box to agree to the terms and conditions");
        });



        When("I click on Proceed to Checkout on the shipping section", () -> {

            driver.findElement(By.name("processCarrier")).click();
            LOG.info("Clicked on proceed to Checkout button in the shipping section");
        });



        When("I click on Payment method as Pay By Bank Wire", () -> {

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bankwire"))).click();
            LOG.info("Selected Payment method as Pay By Bank Wire");

        });



        When("I click on confirm my order", () -> {

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='cart_navigation']/button")))
                    .click();
            LOG.info("Clicked on the confirm my order button");

        });



        Then("Order confirmation page is opened", () -> {

            WebElement heading = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));
            Assertions.assertThat(heading.getText()).isEqualTo("ORDER CONFIRMATION");

            LOG.info("Order Confirmation message is visible on the new page");

        });



        Then("The order is complete", () -> {

            Assertions.assertThat(driver.findElement(By.xpath("//li[@class='step_done step_done_last four']"))
                    .isDisplayed()).isEqualTo(true);
            LOG.info("This is the last page towards order completion");

            Assertions.assertThat(driver.findElement(By.xpath("//li[@id='step_end' and @class='step_current last']"))
                    .isDisplayed()).isEqualTo(true);

            Assertions.assertThat(driver.findElement(By.xpath("//*[@class='cheque-indent']/strong")).getText())
                    .contains("Your order on My Store is complete.");
            LOG.info("Page is displaying the message 'Your order on My Store is complete'");

        });


        Then("current page is the last step of ordering", () -> {

            Assertions.assertThat(driver.getCurrentUrl()).contains("controller=order-confirmation");
            LOG.info("This is the last step. Page is pointing to the URL '"+driver.getCurrentUrl()+"'");

        });

    }

}
