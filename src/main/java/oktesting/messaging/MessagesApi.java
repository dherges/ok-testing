/*
 * ok-testing
 * https://github.com/dherges/ok-testing
 *
 * Copyright (c) 2016 David Herges
 * Licensed under the MIT license.
 */
package oktesting.messaging;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Client for an HTTP API (whatever-service)
 */
public interface MessagesApi {

  @GET("/message")
  Call<Message> findMessage(@Query("query") String keyword);


  /**
   * Convenient builder for MessagesApi
   */
  class Builder {
    private String baseUrl;

    public Builder() {
    }

    public Builder baseUrl(String baseUrl) {
      this.baseUrl = baseUrl;

      return this;
    }

    public MessagesApi build() {
      return new Retrofit.Builder()
        .client(new OkHttpClient.Builder().build())
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseUrl)
        .build()
        .create(MessagesApi.class);
    }
  }
}
