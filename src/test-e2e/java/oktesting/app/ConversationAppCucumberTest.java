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
import ext.junit.SparkAppUnderTest;
import spark.servlet.SparkApplication;

import static spark.Spark.awaitInitialization;


@RunWith(Cucumber.class)
public class ConversationAppCucumberTest {

    public static SparkApplication sparkApp;

    @BeforeClass
    public static void setUp() {
    	 // XX .. it's a bit hacky to set up the embedded server in before class
       sparkApp = new ConversationApp();
       sparkApp.init();

       awaitInitialization();
    }

    @AfterClass
    public static void tearDown() {
      // destroys and quits the application
      sparkApp.destroy();
    }

}
