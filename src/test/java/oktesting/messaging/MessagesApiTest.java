package oktesting.messaging;

import org.junit.Test;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MessagesApiTest {

    @Test
    public void builder_returnsInstance() {
        final MessagesApi messagesApi = new MessagesApi.Builder()
                .baseUrl("http://localhost:8888")
                .build();

        assertThat(messagesApi).isNotNull();
    }

}
