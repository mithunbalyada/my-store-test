package com.mithun.gfk.mystoretest.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DriverSetup {

    private WebDriver driver;

    private WebDriverWait webDriverWait;

    @Value("${driver.timeOutInSeconds}")
    private int timeOutInSeconds;

    @Value("${driver.sleepInMilliSeconds}")
    private int sleepInMilliSeconds;

    @Value("${test.browser}")
    private String browser;


    /**
     * <p>
     *      Returns driver of the configured browser.
     *      Browser is configured in application.properties
     * </p>
     *
     * @return {@link WebDriver}
     * @throws IllegalAccessException
     */

    public WebDriver getDriver() throws IllegalAccessException{

        switch (browser){

            case "chrome":
                driver = DriverFactory.getDriver(DriverType.CHROME);
                break;

            case "firefox":
                driver = DriverFactory.getDriver(DriverType.FIREFOX);
                break;

            default:
                throw new IllegalAccessException("Browser is not supported");
        }

        return driver;
    }


    /**
     * <P>
     *          Get waiting object of the WebDriver.
     *          Use preconfigured wait and sleep time; please refer to application.properties
     * </P>
     * @return
     * @throws IllegalAccessException
     */

    public WebDriverWait getWebDriverWait() throws IllegalAccessException{

        if(null == driver)
            getDriver();

        WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutInSeconds, sleepInMilliSeconds);

        return webDriverWait;
    }
}
