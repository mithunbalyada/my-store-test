package com.mithun.gfk.mystoretest;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:features"},
        monochrome = true,
        dryRun = false,
        plugin = {"json:target/cucumber-report/report.json"}
)
public class ApplicationTest {

}
