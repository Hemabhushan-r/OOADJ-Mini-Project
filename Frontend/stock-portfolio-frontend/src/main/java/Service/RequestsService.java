package Service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class RequestsService {
    static public String postRequest(String url, String json) {
        String jsonResponse = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            String requestBody = json;
            StringEntity entity = new StringEntity(requestBody);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    // System.out.println("Response Code: " +
                    // response.getStatusLine().getStatusCode());
                    // System.out.println("Response Body: " +
                    // responseEntity.getContent().toString());
                    jsonResponse = EntityUtils.toString(responseEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    static public String postRequest(String url, String json, String token) {
        String jsonResponse = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            String requestBody = json;
            StringEntity entity = new StringEntity(requestBody);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", String.format("Bearer %s", token));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    // System.out.println("Response Code: " +
                    // response.getStatusLine().getStatusCode());
                    // System.out.println("Response Body: " +
                    // responseEntity.getContent().toString());
                    jsonResponse = EntityUtils.toString(responseEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    static public String getRequest(String url, String token) {
        String jsonResponse = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Authorization", String.format("Bearer %s", token));
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    // System.out.println("Response Code: " +
                    // response.getStatusLine().getStatusCode());
                    // System.out.println("Response Body: " +
                    // responseEntity.getContent().toString());
                    jsonResponse = EntityUtils.toString(responseEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }
}
