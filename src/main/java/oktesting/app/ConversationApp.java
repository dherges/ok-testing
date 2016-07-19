package oktesting.app;

import spark.servlet.SparkApplication;

import static spark.Spark.get;


public class ConversationApp implements SparkApplication {

    @Override
    public void init() {

        get("/conversation", (req, res) -> {

            res.status(201);
            return "abc";
        });
    }
}
