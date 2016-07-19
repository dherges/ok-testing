package oktesting.app;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Rule
    public MockWebServer mockBackend = new MockWebServer();

    private final OkHttpClient appClient = new OkHttpClient.Builder().build();

    @Test
    public void conversation_GET() throws IOException, InterruptedException {
        // instructs the mock backend to respond with well-known data
        mockBackend.enqueue(new MockResponse().setBody("{\"text\":\"hello testing!\"}"));


        // prepares an HTTP call to our web app (the web app will be the unit under test)
        final Call appCall = appClient.newCall(new Request.Builder()
                .url("http://localhost:4567/conversation?q=test123")
                .get()
                .build());
        // obtains the HTTP response from our app
        final Response appResponse = appCall.execute();


        // asserts expected behaviour
        assertThat(appResponse).isNotNull();
        assertThat(appResponse.code()).isEqualTo(200);
    }

}
