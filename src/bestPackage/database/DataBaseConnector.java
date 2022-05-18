package bestPackage.database;

import bestPackage.GlobalData;

import java.sql.*;

public class DataBaseConnector {

    GlobalData globalData;

    private String ip;
    private String port;
    private String user;
    private String password;
    private String whichBase;

    private String url;

    private Connection connection;
    private Statement stmt = null;
    private ResultSet resset = null;
    private ResultSet resset2 = null;
    private ResultSet resset3 = null;

    private String linia_tekstu;

    public DataBaseConnector(GlobalData globalData, String project) {
        this.globalData = globalData;
        chooseProject(project);
        openConnectionToDataBase();
    }

    public ResultSet getData(String query) {
        return doASQLQuery(query);
    }

    private void chooseProject(String project) {
        ip = String.valueOf(globalData.getConnectionDatabaseData().get(project, "ip"));
        port = String.valueOf(globalData.getConnectionDatabaseData().get(project, "port"));
        user = String.valueOf(globalData.getConnectionDatabaseData().get(project, "user"));
        password = String.valueOf(globalData.getConnectionDatabaseData().get(project, "password"));
        whichBase = String.valueOf(globalData.getConnectionDatabaseData().get(project, "databaseName"));

        url = "jdbc:postgresql://" + ip + ":" + port + "/" + whichBase;
    }

    private void openConnectionToDataBase() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.append("Nie masz sterownika");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.append("Zle dane");
        }
    }

    private ResultSet doASQLQuery(String zapytanie) {
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resset = stmt.executeQuery(zapytanie);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resset;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cleanData();
    }

    private void cleanData() {
        ip = null;
        port = null;
        user = null;
        password = null;

        url = null;

        connection = null;
        stmt = null;
        resset = null;
        resset2 = null;
        resset3 = null;

        linia_tekstu = null;
    }
}
