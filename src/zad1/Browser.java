package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;

public class Browser{

    String city;
    private static JFrame jframe;

    public Browser(String city,JFrame frame){
        this.city = city;
        this.jframe = frame;

        try {
            startGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGUI() throws Exception {
        jframe = new JFrame("TPO");
        jframe.setSize(1000,800);


        JFXPanel jfxPanel = new JFXPanel();
        jframe.add(jfxPanel);
        Platform.runLater(() -> {
            WebView webView = new WebView();
            jfxPanel.setScene(new Scene(webView));
            webView.getEngine().load("https://en.wikipedia.org/wiki/" + city);
        });

        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jframe.setVisible(true);
    }
}
