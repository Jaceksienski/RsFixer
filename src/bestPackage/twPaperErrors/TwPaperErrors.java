package bestPackage.twPaperErrors;

import bestPackage.GlobalData;
import bestPackage.database.DataBaseConnector;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class TwPaperErrors extends Thread {

    private GlobalData globalData;
    private DataBaseConnector dataBaseConnector;

    private ArrayList<Device> devices = new ArrayList<>();

    private FileWriter fileWriter = null;

    public TwPaperErrors(GlobalData globalData) {
        this.globalData = globalData;
        loadData();
        try {
            writeDownSQLQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sortData();
        try {
            printPaperErrors();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clearData();
        dataBaseConnector.closeConnection();
    }

    private void loadData() {
        this.dataBaseConnector = new DataBaseConnector(globalData, "TW");
    }

    private void writeDownSQLQuery() throws SQLException {
        ResultSet resultQuery1 = dataBaseConnector.getData(globalData.queries.queryTW1());
        ResultSetMetaData rsmd = resultQuery1.getMetaData();
        int columnsNumber = rsmd.getColumnCount();


        while (resultQuery1.next()) {
            int x = 1;
            int lineNumber = 0;
            for (int i = 1; i <= columnsNumber; i++) {
                if (x == 2) {
                    devices.add(lineNumber, new Device(resultQuery1.getString(i).substring(0, 4) + "-" + resultQuery1.getString(i).substring(4, 5)));
                    ResultSet resultQuery3 = dataBaseConnector.getData(globalData.queries.queryTW2(resultQuery1.getString(i)));
                    resultQuery3.next();

                    if (resultQuery3.getBoolean(1)) {
                        ResultSet resultQuery2 = dataBaseConnector.getData(globalData.queries.queryTW3(devices.get(lineNumber).getVehicleNumber()));
                        resultQuery2.next();
                        devices.get(lineNumber).setDepotNumber(resultQuery2.getString(1) + " ");
                    }
                }
                x++;
                if (x == 4) {
                    x = 1;
                    lineNumber++;
                }
            }
        }
    }

    private void sortData() {
        devices.sort(Comparator.comparing(Device::getDepotNumber));
    }

    private void printPaperErrors() throws IOException {
        sortData();
        GlobalData globalData = new GlobalData();

        String fileDir = "TW_Paper_5";
        String filePath = "TW_Paper_5\\Malo_Papieru_Blad_5_" + globalData.getDateWithSlash() + ".txt";
        fileWriter = new FileWriter(filePath);

        devices.forEach(device -> {
            try {
                fileWriter.write(device.getVehicleNumber() + " " + device.getDepotNumber());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileWriter.write(System.lineSeparator());
        fileWriter.close();

        Runtime.getRuntime().exec("explorer " + fileDir);
    }

    private void clearData() {
        fileWriter = null;
    }
}
