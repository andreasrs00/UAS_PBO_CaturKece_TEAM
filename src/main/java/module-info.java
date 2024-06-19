module caturkece.com.uas_pbo_caturkece_team {
        requires javafx.controls;
        requires javafx.fxml;
//        requires javafx.web;
        requires java.sql;
        requires javafx.media;

//        requires org.controlsfx.controls;
//        requires com.dlsc.formsfx;
//        requires net.synedra.validatorfx;
//        requires org.kordamp.ikonli.javafx;
//        requires org.kordamp.bootstrapfx.core;
//        requires eu.hansolo.tilesfx;
//        requires com.almasb.fxgl.all;
        requires java.desktop;
//        requires javafx.swing;
        requires javafx.graphics;

        opens Main to javafx.fxml;
        exports Main;
        exports pieces;


        opens login to javafx.fxml;
        exports login;

        opens Setting to javafx.fxml;
        exports Setting;

        opens menu to javafx.fxml;
        exports menu;

        opens register to javafx.fxml;
        exports register;

}
