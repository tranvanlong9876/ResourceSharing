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
import java.net.HttpURLConnection;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Admin
 */
public class VerifyCaptcha implements Serializable {
    public static final String URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String SECRET_KEY = "6LfrR88aAAAAAAaSystI_LXVSC0lh-zL84YBTQFi";
    
    public static boolean verify(String gCaptchaRes) {
        boolean check = false;
        if(gCaptchaRes == null || gCaptchaRes.length() == 0) {
            return check;
        }
        try {
            URL obj = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            String postParams = "secret=" + SECRET_KEY + "&response=" + gCaptchaRes;
            conn.setDoOutput(true);
            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());
            outStream.flush();
            outStream.close();
            InputStream inputStream = conn.getInputStream();
            JsonReader jsonReader = Json.createReader(inputStream);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            check = jsonObject.getBoolean("success");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return check;
    }
}
