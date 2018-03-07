package com.p1694151.myapplication.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paalwinder on 05/03/18.
 */
public class RestClient {
    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();
    // Define the interceptor, follow authentication headers
    static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder().addHeader("content-type", "application/json").build();
                    /*.addHeader("PHP_AUTH_USER", "admin")
                    .addHeader("PHP_AUTH_PW", "admin").build();*/
            return chain.proceed(newRequest);
        }
    };

    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            /*logger.info(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));*/

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
          /*  logger.info(String.format("Received response for %s in %.1fms%n%s BODY%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers(),response.body().string()));*/
            return response;
        }
    }


    // Add the interceptor to OkHttpClient
    static OkHttpClient getHeaders() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
       // builder.addInterceptor(interceptor);
       // builder.addInterceptor(new LoggingInterceptor());
        return builder.build();
    }


    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getHeaders())
            .build();
    public static API apiService =
            retrofit.create(API.class);

}
