/*
 * ok-testing
 * https://github.com/dherges/ok-testing
 *
 * Copyright (c) 2016 David Herges
 * Licensed under the MIT license.
 */
package oktesting.app;

import ext.spark.MoshiResponseTransformer;
import oktesting.messaging.Message;
import oktesting.messaging.MessagesApi;
import spark.servlet.SparkApplication;

import static oktesting.app.Config.config;
import static spark.Spark.get;


public class ConversationApp implements SparkApplication {

    @Override
    public void init() {

        get("/conversation", (req, res) -> {
            final MessagesApi msgApi = new MessagesApi.Builder()
                    .baseUrl(config("backendUrl", ""))
                    .build();

            final String keyword = req.queryParams("q");

            final Message msg = msgApi.findMessage(keyword).execute().body();

            res.header("Content-Type", "application/json;charset=utf-8");

            return new Conversation(msg, "Conversation for keyword " + keyword);
        }, MoshiResponseTransformer.create(Conversation.class));
    }
}
