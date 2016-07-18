package oktesting.messaging;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/** Client for an HTTP API (whatever-service) */
public interface MessagesApi {

    @GET("/message")
    Call<Message> findMessage(@Query("query") String keyword);
}
