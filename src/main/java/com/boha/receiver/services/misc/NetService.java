package com.boha.receiver.services.misc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.boha.receiver.util.E;

import java.util.Map;
import java.util.Objects;

@Service
public class NetService {
    public NetService() {
        LOGGER.info(E.CAT.concat(E.CAT.concat("NetService ready to go ".concat(E.CAT))));
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(NetService.class.getSimpleName());
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();
    private final OkHttpClient client = new OkHttpClient();


    public static final MediaType JSON_MEDIA_TYPE
            = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType XML_MEDIA_TYPE
            = MediaType.parse("application/xml; charset=utf-8");


    public String post(String url, String json) throws Exception {
        LOGGER.info(E.DICE.concat(E.DICE).concat(" ... Posting to: "
                .concat(url)));
        RequestBody body = RequestBody.create(json, JSON_MEDIA_TYPE);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            LOGGER.info(E.DICE+E.DICE+"POST call returns status code: " + response.code()
                    + " for " + url);
            if (response.isSuccessful()) {
                return Objects.requireNonNull(response.body()).string();
            } else {
                LOGGER.info(E.NOT_OK+E.ERROR+ "POST call is NOT OK: status code: " + response.code()
                        + " " + response.message() + " " + Objects.requireNonNull(response.body()).string());
                throw new Exception("status code: " + response.code() + " " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(E.NOT_OK+E.ERROR+"Call to "+url+" FAILED: " + e.getMessage());
            throw new Exception("POST Failed: " + e.getMessage());
        }
    }

    CloseableHttpClient httpclient = HttpClients.createDefault();


    public String get(String url, String token) throws Exception {
        LOGGER.info(E.DICE.concat(E.DICE).concat(" ... Posting GET to: "
                .concat(url)));
        Request request;
        if (token == null) {
            request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + token)
                    .get()
                    .build();
        }
        try {
            Response response = client.newCall(request).execute();
            LOGGER.info("GET returns status code: " + response.code()
                    + " for " + url);
            if (response.isSuccessful()) {
                return Objects.requireNonNull(response.body()).string();
            } else {
                LOGGER.info("GET is NOT OK: status code: " + response.code() + " "
                        + Objects.requireNonNull(response.body()).string());
                throw new Exception("status code: " + response.code() + " " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Call to "+url+" FAILED", e);
            throw new Exception(E.ERROR+"GET Failed: " + e.getMessage());
        }
    }


}
