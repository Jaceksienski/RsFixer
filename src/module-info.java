module JacaTulsFX {
    requires javafx.fxml;
    requires javafx.controls;
    requires jsch;
    requires java.sql;
    requires jdom;
    requires org.postgresql.jdbc;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires commons.logging;
    requires commons.codec;
    requires com.google.common;


    opens bestPackage;

}