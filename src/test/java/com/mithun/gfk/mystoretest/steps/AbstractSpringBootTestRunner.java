package com.mithun.gfk.mystoretest.steps;

import com.mithun.gfk.mystoretest.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * <p>
 *     Abstract class that provides Spring-Boot configurations for Cucumber Step definitions.
 *     Spring with Cucumber is used for Dependency injection.
 * </p>
 */

@SpringBootTest(classes = Application.class)
@ContextConfiguration
@DirtiesContext
public abstract class AbstractSpringBootTestRunner {

}
