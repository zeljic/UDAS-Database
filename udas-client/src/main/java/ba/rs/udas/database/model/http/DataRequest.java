package ba.rs.udas.database.model.http;


import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 *      java.net.http.HttpClient https://openjdk.java.net/groups/net/httpclient/intro.html
 *      TODO Make this thing async, or runnable or something that won't block the UI :)
 * */

public class DataRequest {

    private static final String URL_STRING = "http://127.0.0.1:8080/udas-server/api";
    private static final String CLASS_HEADER = "X-UDAS-class";
    private static final String METHOD_HEADER = "X-UDAS-method";

    public static String doPost(String classFqn, String methodName, String jsonParameters) {

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_STRING))
                .timeout(Duration.ofMinutes(2))
                .header(CLASS_HEADER, classFqn)
                .header(METHOD_HEADER, methodName)
                .POST(HttpRequest.BodyPublishers.ofString(jsonParameters))
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (InterruptedException | IOException e) {
            e.printStackTrace(); //TODO logger
        }

        return response == null ? null : response.body();
    }

    public static void main(String[] args) {

        DataRequest.doPost
                (
                "ba.rs.udas.database.model.dao.HackAdapter",
                "getSomeShitFromHere",
                "{\"key\" : \"value\"}"
                );
    }

}
