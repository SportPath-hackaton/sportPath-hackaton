package org.hackaton.max2025.notification_ms.service;

import org.hackaton.max2025.notification_ms.model.MaxMessageRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MaxApiService {

    @POST("/messages")
    Call<Void> sendMessage(
            @Header("Authorization") String token,
            @Query("user_id") Integer userId,
            @Body MaxMessageRequest message
    );

}
