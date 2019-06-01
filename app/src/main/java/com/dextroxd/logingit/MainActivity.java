package com.dextroxd.logingit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dextroxd.logingit.api.model.AccessToken;
import com.dextroxd.logingit.api.service.GitHubClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String clientId = "client_id";
    private String clientSecret = "client_secret";
    private String redirectUri = "redirect_uri";
    String code="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.loginbut);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize"+"?client_id="+clientId+"&scope=repo&redirect_uri="+redirectUri));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getIntent().getData();
        if(uri!=null&&uri.toString().startsWith(redirectUri)){
            code = uri.getQueryParameter("code");
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://github.com/")
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            GitHubClient gitHubClient = retrofit.create(GitHubClient.class);
            Call<AccessToken> accessTokenCall=gitHubClient.getAccessToken(clientId,clientSecret,code);
            accessTokenCall.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    AccessToken accessToken = response.body();
                    Toast.makeText(MainActivity.this,"Yay code="+code+"access token = "+accessToken.getAccessToken(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(MainActivity.this,"No",Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}
