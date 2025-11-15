package org.hackaton.max2025.notification_ms.config;

import org.hackaton.max2025.notification_ms.service.MaxApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    private static final String BASE_URL = "https://platform-api.max.ru";

    @Bean
    public MaxApiService maxApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MaxApiService.class);
    }
}
