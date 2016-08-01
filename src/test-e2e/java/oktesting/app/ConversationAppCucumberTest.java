/*
 * ok-testing
 * https://github.com/dherges/ok-testing
 *
 * Copyright (c) 2016 David Herges
 * Licensed under the MIT license.
 */
package oktesting.app;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
public class ConversationAppCucumberTest {

    @BeforeClass
    public static void setUp() {
    	 System.out.println("setting up");
    }

    @AfterClass
    public static void tearDown() {
    	 System.out.println("tearing down");
    }

}
