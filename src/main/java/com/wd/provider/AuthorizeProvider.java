package com.wd.provider;


import com.alibaba.fastjson.JSON;
import com.wd.dto.GithubUser;
import com.wd.dto.AccessTokenDto;
import okhttp3.*;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthorizeProvider {

    public String getAccessToke(AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();

            String access_token = string.split("&")[0].split("=")[1];
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+access_token)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String string = response.body().string();
            return JSON.parseObject(string,GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
