/*
 * ok-testing
 * https://github.com/dherges/ok-testing
 *
 * Copyright (c) 2016 David Herges
 * Licensed under the MIT license.
 */
package ext.junit;

import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import spark.servlet.SparkApplication;

import static spark.Spark.awaitInitialization;


/**
 * A JUnit test rule that starts our Spark web application in a test environment.
 *
 * @param <T>
 */
public class SparkAppUnderTest<T extends SparkApplication> extends ExternalResource implements TestRule {

    private final Class<T> sparkAppClz;
    private T sparkApplication;

    public SparkAppUnderTest(Class<T> sparkAppClz) {
        this.sparkAppClz = sparkAppClz;
    }


    @Override
    protected void before() throws Throwable {
        // spawns the application
        sparkApplication = sparkAppClz.newInstance();
        sparkApplication.init();

        awaitInitialization();
    }

    @Override
    protected void after() {
        // destroys and quits the application
        sparkApplication.destroy();
    }

    public String serverUrl() {
        return "http://localhost:4567"; // hard-coded for simplicity
    }

}
