package bestPackage.scenes;

import bestPackage.AlertBoxUniversal;
import bestPackage.GlobalData;
import bestPackage.Ping;
import bestPackage.kiedyPrzyjedzie.KiedyPrzyjedzieConfig;
import com.jcraft.jsch.*;
import javafx.scene.control.Button;

import java.io.IOException;
import java.io.InputStream;

public class RsTableData {
    GlobalData globalData;
    private int id;
    private String ip;

    public Button getButtPing() {
        return buttPing;
    }

    public void setButtPing(Button buttPing) {
        this.buttPing = buttPing;
    }

    public Button getButtRestart() {
        return buttRestart;
    }

    public void setButtRestart(Button buttRestart) {
        this.buttRestart = buttRestart;
    }

    public Button getButtGlobal() {
        return buttGlobal;
    }

    public void setButtGlobal(Button buttGlobal) {
        this.buttGlobal = buttGlobal;
    }

    public Button getButtKiedyPrzyjedzie() {
        return buttKiedyPrzyjedzie;
    }

    public void setButtKiedyPrzyjedzie(Button buttKiedyPrzyjedzie) {
        this.buttKiedyPrzyjedzie = buttKiedyPrzyjedzie;
    }

    private Button buttPing;
    private Button buttRestart;
    private Button buttGlobal;
    private Button buttKiedyPrzyjedzie;

    public RsTableData() {
        this.id = 0;
        this.ip = "";
    }

    public RsTableData(GlobalData globalData, int id, String ip, String projekt) {
        this.globalData = globalData;
        this.id = id;
        this.ip = ip;
        this.buttPing = new Button("Ping");
        this.buttRestart = new Button("Restart");
        this.buttKiedyPrzyjedzie = new Button("CzyJezdzi");

        buttPing.setOnAction(e -> {
            pingIt(id, ip);

        });

        buttRestart.setOnAction(e -> {
            try {
                restart_RS_NIE_JAWORZNO(id, ip, projekt);
            } catch (JSchException | IOException ex) {
                ex.printStackTrace();
            }

        });

        buttKiedyPrzyjedzie.setOnAction(e -> {
            KiedyPrzyjedzieConfig kiedyPrzyjedzieConfig = new KiedyPrzyjedzieConfig(globalData.getKiedyPrzyjedzieData(), id);
            kiedyPrzyjedzieConfig.isOnRoad();
        });
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private void doACommand(String host, String projekt, String zapytanie) {
        if (pingIt(id, host).equals("Online")) {


            try {
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                JSch jsch;
                Session session;
                if (projekt.equals("Jaworzno")) {
                    jsch = new JSch();
                    session = jsch.getSession(String.valueOf(globalData.getConnectionRSData().get("Jaworzno", "user")), host);
                    session.setPassword(String.valueOf(globalData.getConnectionRSData().get("Jaworzno", "password")));
                    session.setConfig(config);
                    session.connect();
                    System.out.println("Connected");

                    Channel channel5 = session.openChannel("exec");
                    ((ChannelExec) channel5).setCommand(zapytanie);
                    channel5.connect();
                    channel5.disconnect();

                } else {
                    jsch = new JSch();
                    session = jsch.getSession(String.valueOf(globalData.getConnectionRSData().get("Multi", "user")), host);
                    session.setPassword(String.valueOf(globalData.getConnectionRSData().get("Multi", "password")));
                    session.setConfig(config);
                    session.connect();
                    System.out.println("Connected");


                    Channel channel5 = session.openChannel("exec");
                    ((ChannelExec) channel5).setCommand(zapytanie);
                    channel5.setInputStream(null);
                    ((ChannelExec) channel5).setErrStream(System.err);
                    InputStream in5 = channel5.getInputStream();
                    channel5.connect();
                    byte[] tmp5 = new byte[1024];
                    while (true) {
                        while (in5.available() > 0) {
                            int i = in5.read(tmp5, 0, 1024);
                            if (i < 0) break;
                            System.out.print(new String(tmp5, 0, i));
                        }
                        if (channel5.isClosed()) {
                            System.out.println("Końcowa konfiguracja OK");
                            System.out.println("Restart!");
                            System.out.println("exit-status: " + channel5.getExitStatus());
                            break;
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (Exception ee) {
                        }
                    }
                    channel5.disconnect();
                }


                session.disconnect();
                System.out.println("DONE");
                AlertBoxUniversal.display("Restart", "Sprawdz konsole, aby zobaczyc czy RS zostal poprawnie zrestartowany.", "OK");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertBoxUniversal.display("Restart", "Urządzenie nie pinguje, restart nie przejdzie. Może już się restartuje?", "Kurde :(");
        }
    }

    void restart_RS_NIE_JAWORZNO(int id, String host, String projekt) throws JSchException, IOException {
        String zapytanie = "sudo reboot";
        doACommand(host, projekt, zapytanie);
    }

    String pingIt(int id, String host) {

        Ping ping = new Ping();

        if (ping.isPinging(host, 22)) {
            AlertBoxUniversal.display("PING", "RS " + id + " jest ONLINE!", "Wow, superancko!");
            return "Online";
        } else {
            AlertBoxUniversal.display("PING", "RS " + id + " jest OFFLINE!", ":(");
            return "Offline";
        }
    }

    private String chooseZapytanieMakeGlobal(String projekt, int id) {
        String zapytanie = "";
        switch (projekt) {
            case "Bydgoszcz":
                zapytanie =
                        "printf " +
                                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                                "<cereal>" +
                                "\\t<Global>" +
                                "\\t\\t<DeviceID>" + id + "</DeviceID>" +
                                "\\t\\t<SideNumber>" + id + "</SideNumber>" + //TODO zrobić globale na wszystkie projekty

                                "\\t</Global>" +
                                "</cereal>";
                break;



            default:
                zapytanie = "brak zapytania";
                break;
        }
        System.out.println(zapytanie);
        return zapytanie;
    }
}
