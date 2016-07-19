package oktesting.app;

import oktesting.messaging.Message;


public class Conversation {

    public final Message message;
    public final String topic;

    public Conversation(Message message, String topic) {
        this.message = message;
        this.topic = topic;
    }
}
