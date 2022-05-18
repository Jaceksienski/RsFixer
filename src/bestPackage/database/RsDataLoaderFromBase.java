package bestPackage.database;

import bestPackage.GlobalData;
import bestPackage.scenes.RsTableData;
import javafx.collections.ObservableList;

import java.sql.*;

public class RsDataLoaderFromBase {
    private GlobalData globalData;
    private ResultSet resset = null;
    private ObservableList<RsTableData> rsTableData;

    private String query = "";

    public RsDataLoaderFromBase(GlobalData globalData, ObservableList<RsTableData> rsTableData, String project) {
        this.globalData = globalData;
        this.rsTableData = rsTableData;
        chooseQuery(project);
        if (project.equals("Bydgoszcz") || project.equals("Gdansk") || project.equals("Lodz") || project.equals("Jaworzno")) {
            DataBaseConnector dataBaseConnector = new DataBaseConnector(globalData, project);
            resset = dataBaseConnector.getData(query);
            try {
                queryToArray(project);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dataBaseConnector.closeConnection();
        }
    }

    private void chooseQuery(String projekt) {
        switch (projekt) {
            case "Bydgoszcz":
                query = globalData.queries.queryDBloadBydgoszcz();
                break;

            case "Gdansk":
                query = globalData.queries.queryDBloadGdansk();
                break;

            case "Lodz":
                query = globalData.queries.queryDBloadLodz();
                break;

            case "Jaworzno":
                query = globalData.queries.queryDBloadJaworzno();
                break;

            default:
                query = "brak zapytania";
                break;
        }
    }

    private void queryToArray(String projekt) throws SQLException {
        rsTableData.clear();
        String tymczasStr = "";
        int tymczasInt = 0;
        resset.next();

        tymczasStr = resset.getString(1);
        tymczasInt = resset.getInt(2);

        processTheDataIP(tymczasStr);
        processTheDataID(projekt, tymczasInt);

        rsTableData.add(new RsTableData(globalData, processTheDataID(projekt, tymczasInt), processTheDataIP(tymczasStr), projekt));

        while (resset.next()) {
            tymczasStr = resset.getString(1);
            tymczasInt = resset.getInt(2);
            processTheDataIP(tymczasStr);
            rsTableData.add(new RsTableData(globalData, processTheDataID(projekt, tymczasInt), processTheDataIP(tymczasStr), projekt));
        }
    }

    public ObservableList<RsTableData> refreshRsTableData() {
        return rsTableData;
    }

    private String processTheDataIP(String ip) {
        String tmp;
        String[] numberSplit = ip.split(":");
        String[] numberSplit2 = numberSplit[1].split("/");
        tmp = numberSplit2[2];
        return tmp;
    }

    private int processTheDataID(String projekt, int id) {
        int tmp = id;
        String tmpSt;
        switch (projekt) {
//            case "Bydgoszcz":
//                break;
            case "Gdansk":
            case "Lodz":
                tmpSt = String.valueOf(id).substring(0, 4);
                tmp = Integer.parseInt(tmpSt);
                break;

            case "Jaworzno":
                tmpSt = String.valueOf(id).substring(1, 4);
                tmp = Integer.parseInt(tmpSt);
                break;
        }
        return tmp;
    }

}
