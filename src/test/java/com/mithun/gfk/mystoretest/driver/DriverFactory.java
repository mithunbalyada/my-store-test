package com.mithun.gfk.mystoretest.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * <p>
 *     Factory implementation of Driver using HashMaps
 * </p>
 */

public class DriverFactory {

    private static  Map<DriverType, Supplier<WebDriver>> driverManager = new HashMap<>();

    /**
     * Chrome Driver implementation
     */
    private static  Supplier<WebDriver> chromeDriverSupplier = () -> {
        System.setProperty("webdriver.chrome.driver", getDriverPath()+"chromedriver");
        return new ChromeDriver();
    };


    /**
     * Firefox driver implementation
     */
    private static  Supplier<WebDriver> firefoxDriverSupplier = () -> {
        System.setProperty("webdriver.gecko.driver", getDriverPath()+"firefoxdriver");
        return new FirefoxDriver();
    };



    static{
        driverManager.put(DriverType.CHROME, chromeDriverSupplier);
        driverManager.put(DriverType.FIREFOX, firefoxDriverSupplier);
    }


    /**
     * Returns respective driver for the Type provided
     * @param type - Enum {@link DriverType}
     * @return {@link WebDriver} for the respective browser
     */
    public static  WebDriver getDriver(DriverType type){
        return driverManager.get(type).get();
    }


    public static String getDriverPath(){

        String operatingSystem = System.getProperty("os.name");

        if(null != operatingSystem && operatingSystem.contains("Mac")){
            return "bin/driver/macos/";
        }else{
            return "bin/driver/windows/";
        }

    }

}