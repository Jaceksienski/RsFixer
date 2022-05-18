package bestPackage.kiedyPrzyjedzie;

import bestPackage.HttpRequestSend;

import java.util.Map;

public class KiedyPrzyjedzieConfig {
    private Map<String, String> data;
    private int id;

    public KiedyPrzyjedzieConfig(Map<String, String> data, int id) {
        this.data = data;
        this.id = id;
    }

    public void isOnRoad() {
        HttpRequestSend httpRequestSend = new HttpRequestSend();
        getResponse(httpRequestSend.getHttpRequest(data, id));
    }

    private void getResponse(String response) {
        System.out.println("Pojazd " + id + ", Status: " + response);
    }
}
