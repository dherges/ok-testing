/*
 * ok-testing
 * https://github.com/dherges/ok-testing
 *
 * Copyright (c) 2016 David Herges
 * Licensed under the MIT license.
 */
package oktesting.app;

import cucumber.api.java8.En;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.io.IOException;

import static oktesting.app.Config.configSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class StepDefinitions implements En {


  private final MockWebServer mockWebServer = new MockWebServer();
  private final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

  private Response response;


  public StepDefinitions() {

    Before(() -> {
      try {
        mockWebServer.start();
      } catch (IOException e) {
        fail("mock web server failed to start", e);
      }
      configSet("backendUrl", mockWebServer.url("/").toString());
    });

    After(() -> {
      try {
        mockWebServer.shutdown();
      } catch (IOException e) {
        fail("mock web server failed to start", e);
      }
    });

    Given("^mock backend responds with$", (String docString) -> {
      mockWebServer.enqueue(new MockResponse().setBody(docString));
    });

    When("^client calls ([A-Z]+) (.+)$", (String verb, String path) -> {
      // prepares an HTTP call to our web app (the web app will be the unit under test)
      final Request request = new Request.Builder()
        .method(verb, null)
        .url("http://localhost:4567" + path)
        .build();

      // obtains the HTTP response from our app
      try {
        response = okHttpClient.newCall(request).execute();
      } catch (IOException e) {
        fail("client failed to execute request", e);
      }
    });

    Then("^response code is ([0-9]+)$", (Integer code) -> {
      assertThat(response.code()).isEqualTo(code);
    });

    Then("^response content type contains (.+)$", (String contentType) -> {
      assertThat(response.header("Content-Type", "")).contains(contentType);
    });

    Then("^response body is$", (String docString) -> {
      try {
        assertThat(response.body().string()).isEqualTo(docString);
      } catch (IOException e) {
        fail("failed to read response body", e);
      }
    });

    Then("^recorded request was ([A-Z]+) (.+)$", (String verb, String path) -> {
      // asserts that our app actually called through to the mock backend
      try {
        final RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertThat(recordedRequest.getMethod()).isEqualTo(verb);
        assertThat(recordedRequest.getPath()).isEqualTo(path);
      } catch (InterruptedException e) {
        fail("failed to read recorded request from mock web server", e);
      }
    });
  }
}
