/*
 * ok-testing
 * https://github.com/dherges/ok-testing
 *
 * Copyright (c) 2016 David Herges
 * Licensed under the MIT license.
 */
package oktesting.app;

import ext.junit.SparkAppUnderTest;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static oktesting.app.Config.configSet;
import static org.assertj.core.api.Assertions.assertThat;


public class ConversationAppTest {

  @Rule
  public MockWebServer mockBackend = new MockWebServer();

  @Rule
  public SparkAppUnderTest sparkApp = new SparkAppUnderTest<>(ConversationApp.class);

  private final OkHttpClient appClient = new OkHttpClient.Builder().build();

  @Test
  public void conversation_GET() throws IOException, InterruptedException {
    // instructs the mock backend to respond with well-known data
    mockBackend.enqueue(new MockResponse().setBody("{\"text\":\"hello testing!\"}"));
    // configures our application to connect to the mock backend
    configSet("backendUrl", mockBackend.url("/").toString());


    // prepares an HTTP call to our web app (the web app will be the unit under test)
    final Call appCall = appClient.newCall(new Request.Builder()
      .url(sparkApp.serverUrl() + "/conversation?q=test123")
      .get()
      .build());
    // obtains the HTTP response from our app
    final Response appResponse = appCall.execute();


    // asserts that our app responds with expected content
    assertThat(appResponse.code()).isEqualTo(200);
    assertThat(appResponse.header("Content-Type")).contains("application/json");
    assertThat(appResponse.body().source().readUtf8())
      .isEqualTo("{\"message\":{\"text\":\"hello testing!\"},\"topic\":\"Conversation for keyword test123\"}");

    // asserts that our app actually calls through to the mock backend
    final RecordedRequest recordedRequest = mockBackend.takeRequest();
    assertThat(recordedRequest.getMethod()).isEqualTo("GET");
    assertThat(recordedRequest.getPath()).isEqualTo("/message?query=test123");
  }

}
