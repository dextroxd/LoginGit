package com.dextroxd.logingit.api.service;


import com.dextroxd.logingit.api.model.AccessToken;
import com.dextroxd.logingit.api.model.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GitHubClient
{
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code
    );
    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> reposforUser(@Path("user") String user);
}
