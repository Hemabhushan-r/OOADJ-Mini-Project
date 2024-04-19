package com.stockportfolio.stockservice.Classes;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpRequest {

    public static String sendGetRequest(String url) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        // Set headers if needed
        // request.addHeader("User-Agent", "Java HttpClient");

        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            return EntityUtils.toString(entity);
        } else {
            return null;
        }
    }

    public static String sendPostRequest(String url, String requestBody) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(url);
        StringEntity entity = new StringEntity(requestBody);

        // Set headers if needed
        // postRequest.setHeader("Content-Type", "application/json");

        postRequest.setEntity(entity);
        postRequest.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(postRequest);
        HttpEntity responseEntity = response.getEntity();

        if (responseEntity != null) {
            return EntityUtils.toString(responseEntity);
        } else {
            return null;
        }
    }
}
