package oktesting.messaging;

import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.Test;
import retrofit2.Response;


import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MessagesApiTest {

    @Rule
    public MockWebServer mockBackend = new MockWebServer();

    private MessagesApi mockApi() {
        return new MessagesApi.Builder()
                .baseUrl(mockBackend.url("/").toString())
                .build();
    }

    @Test
    public void builder_returnsInstance() {
        final MessagesApi messagesApi = new MessagesApi.Builder()
                .baseUrl("http://localhost:8888")
                .build();

        assertThat(messagesApi).isNotNull();
    }

    @Test
    public void findMessage_receivesAScriptedResponseFromMockBackend() throws IOException {
        mockBackend.enqueue(
            new MockResponse()
                .setBody("{\"text\":\"hello!\"}")
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json;charset=utf-8")
        );

        final Response<Message> response = mockApi().findMessage("123").execute();

        assertThat(response.code()).isEqualTo(200);
        assertThat(response.headers().get("Content-Type")).contains("application/json");

        final ResponseBody rawResponseBody = response.raw().body();
        assertThat(rawResponseBody.contentType().type()).isEqualTo("application");
        assertThat(rawResponseBody.contentType().subtype()).isEqualTo("json");
    }

}
