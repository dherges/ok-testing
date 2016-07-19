package oktesting.app;

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

            final Message msg = msgApi.findMessage("foooo").execute().body();

            return new Conversation(msg, "yes, a topic!");
        });
    }
}
