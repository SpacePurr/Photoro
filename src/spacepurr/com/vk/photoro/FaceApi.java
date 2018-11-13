package spacepurr.com.vk.photoro;

import java.io.File;
import java.net.URI;

import javafx.scene.image.Image;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


class FaceApi {
    private static final String subscriptionKey = "c10a3ae7e00f45b4bfcc783b2e5508c8";



    private static final String uriBase =
            "https://westeurope.api.cognitive.microsoft.com/face/v1.0/detect";
    private static final String uriVerify =
            "https://westeurope.api.cognitive.microsoft.com/face/v1.0/verify";

    static String result;

    private static String faceDetect(Image image) {
        HttpClient httpclient = HttpClients.createDefault();

        try {
            URIBuilder builder = new URIBuilder(uriBase);

            builder.setParameter("returnFaceId", "true");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
            File file = new File(image.getUrl().replaceAll("file:/", ""));

            FileEntity reqEntity = new FileEntity(file, ContentType.APPLICATION_OCTET_STREAM);
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            //System.out.println(response.getStatusLine());

            if (entity != null) {
                String jsonString = EntityUtils.toString(entity).trim();
                return jsonString.substring(jsonString.indexOf(":") + 1, jsonString.indexOf(","));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    static void verify(Image passport, Image webCam) {
        String passportPhotoId = faceDetect(passport);
        String webCamId = faceDetect(webCam);

        HttpClient httpclient = HttpClients.createDefault();

        try {
            URIBuilder builder = new URIBuilder(uriVerify);

            URI uri = builder.build();

            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);


            StringEntity reqEntity = new StringEntity("{\"faceId1\": " + passportPhotoId +
                    ",\"faceId2\": " + webCamId + "}");
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
