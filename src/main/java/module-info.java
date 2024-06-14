module caturkece.com.uas_pbo_caturkece_team {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens login to javafx.fxml;
    exports login;

    opens Database.Controller to javafx.fxml;
    opens Database.Model to javafx.graphics, javafx.fxml;
    exports Database;








}