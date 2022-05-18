package bestPackage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class HttpRequestSend {

    private DefaultHttpClient httpClient = new DefaultHttpClient();

    public String getHttpRequest(Map<String, String> map, int id) {
        boolean isok = false;
        String result = "";
        try {
            HttpGet getRequest = new HttpGet(map.get("url") + id);
            getRequest.addHeader(map.get("name"), map.get("token"));
            HttpResponse response = null;
            try {
                response = httpClient.execute(getRequest);
            } catch (IOException e) {
                isok = false;
                e.printStackTrace();
            }

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                isok = false;
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            isok = true;
            HttpEntity httpEntity = response.getEntity();
            String apiOutput = EntityUtils.toString(httpEntity);
            result = apiOutput.substring(11, 12);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        if (!isok) System.out.println("Problem z połączeniem! Sprawdz HTTP Request Sender");
        return result;
    }
}
