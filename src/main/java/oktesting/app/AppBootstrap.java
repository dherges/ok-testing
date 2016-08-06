/*
 * ok-testing
 * https://github.com/dherges/ok-testing
 *
 * Copyright (c) 2016 David Herges
 * Licensed under the MIT license.
 */
package oktesting.app;

import static spark.Spark.externalStaticFileLocation;

public class AppBootstrap {

    public static void main(String[] args) {

      externalStaticFileLocation("build/reports");

        new ConversationApp().init();
    }

}
