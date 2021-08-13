/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Admin
 */
public class LoginGoogle implements Serializable {

    public static String GOOGLE_CLIENT_ID = "1009035370439-jhih7tec412cs537k313giuph9jst9s9.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = ""; //Secret Key.
    public static String GOOGLE_REDIRECT_URI = "http://localhost:8080/ResourceSharing/DispatchServlet?action=loginGoogle";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";

    public static String getToken(String code) throws IOException {
        String response = "code=" + code
                + "&client_id=" + GOOGLE_CLIENT_ID
                + "&client_secret=" + GOOGLE_CLIENT_SECRET
                + "&redirect_uri=" + GOOGLE_REDIRECT_URI
                + "&grant_type=" + GOOGLE_GRANT_TYPE;
        URL url = new URL(GOOGLE_LINK_GET_TOKEN);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStream outStream = conn.getOutputStream();
        outStream.write(response.getBytes());
        outStream.flush();
        outStream.close();
        InputStream inputStream = conn.getInputStream();
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        String accessToken = jsonObject.get("access_token").toString();
        return accessToken;
    }

    public static String getUserInfo(final String accessToken) throws IOException {
        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
        URL url = new URL(link);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        InputStream inputStream = conn.getInputStream();
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        String email = jsonObject.getString("email");
        return email;
    }
}
