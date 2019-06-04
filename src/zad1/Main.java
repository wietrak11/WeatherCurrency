/**
 *
 *  @author Wietrak Mateusz S16686
 *
 */

package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.aksingh.owmjapis.model.CurrentWeather;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main{
    private static JFrame jframe;

    public static void main(String[] args) {
        Service s = new Service("Italy");
        String weatherJson = s.getWeather("Rome");
        Double rate1 = s.getRateFor("USD");
        Double rate2 = s.getNBPRate();

        prepareGUI();
    }

    public static net.aksingh.owmjapis.model.param.Main getWeatherInfo(String json){
        if(json == null){
            return null;
        }
        net.aksingh.owmjapis.model.param.Main weather = CurrentWeather.fromJson(json).component8();
        return weather;
    }

    public static double getTemp(net.aksingh.owmjapis.model.param.Main main){
        if(main == null){
            return 0.0;
        }
        double temp = main.getTemp();
        return temp;
    }

    public static double getMinTemp(net.aksingh.owmjapis.model.param.Main main){
        if(main == null){
            return 0.0;
        }
        double minTemp = main.getTempMin();
        return minTemp;
    }

    public static double getMaxTemp(net.aksingh.owmjapis.model.param.Main main){
        if(main == null){
            return 0.0;
        }
        double maxTemp = main.getTempMax();
        return maxTemp;
    }

    public static double getPressure(net.aksingh.owmjapis.model.param.Main main){
        if(main == null){
            return 0.0;
        }
        double pressure = main.getPressure();
        return pressure;
    }

    public static double getHumidity(net.aksingh.owmjapis.model.param.Main main){
        if(main == null){
            return 0.0;
        }
        double humidity = main.getHumidity();
        return humidity;
    }

    public static void prepareGUI(){
        jframe = new JFrame("TPO");
        jframe.setSize(400,800);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(20,1));

        leftPanel.setSize(400,800);
        leftPanel.setBackground(new Color(200,200,200));

        JPanel cityPanel = new JPanel();
        cityPanel.setBackground(new Color(200,200,200));
        JLabel city = new JLabel("City: ");
        JTextField cityTextF = new JTextField("",20);
        JButton citybtn = new JButton("OK");
        cityPanel.add(city);
        cityPanel.add(cityTextF);
        cityPanel.add(citybtn);
        leftPanel.add(cityPanel);

        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(new Color(200,200,200));
        JLabel temp = new JLabel("Temperature:       ");
        temp.setSize(100,100);
        JTextField tempTextF = new JTextField("",20);
        tempTextF.setEnabled(false);
        tempPanel.add(temp);
        tempPanel.add(tempTextF);
        leftPanel.add(tempPanel);

        JPanel minTempPanel = new JPanel();
        minTempPanel.setBackground(new Color(200,200,200));
        JLabel minTemp = new JLabel("Min temperature:");
        JTextField minTempTextF = new JTextField("",20);
        minTempTextF.setEnabled(false);
        minTempPanel.add(minTemp);
        minTempPanel.add(minTempTextF);
        leftPanel.add(minTempPanel);

        JPanel maxTempPanel = new JPanel();
        maxTempPanel.setBackground(new Color(200,200,200));
        JLabel maxTemp = new JLabel("Max temperature:");
        JTextField maxTempTextF = new JTextField("",20);
        maxTempTextF.setEnabled(false);
        maxTempPanel.add(maxTemp);
        maxTempPanel.add(maxTempTextF);
        leftPanel.add(maxTempPanel);

        JPanel pressurePanel = new JPanel();
        pressurePanel.setBackground(new Color(200,200,200));
        JLabel pressure = new JLabel("Pressure:               ");
        JTextField pressureTextF = new JTextField("",20);
        pressureTextF.setEnabled(false);
        pressurePanel.add(pressure);
        pressurePanel.add(pressureTextF);
        leftPanel.add(pressurePanel);

        JPanel humidityPanel = new JPanel();
        humidityPanel.setBackground(new Color(200,200,200));
        JLabel humidity = new JLabel("Humidity:                ");
        JTextField humidityTextF = new JTextField("",20);
        humidityTextF.setEnabled(false);
        humidityPanel.add(humidity);
        humidityPanel.add(humidityTextF);
        leftPanel.add(humidityPanel);



        citybtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!cityTextF.getText().equals("")){
                    Service s = new Service();
                    String weatherJson = s.getWeather(cityTextF.getText());

                    net.aksingh.owmjapis.model.param.Main weatherInfo = getWeatherInfo(weatherJson);
                    double temp = getTemp(weatherInfo);
                    double minTemp = getMinTemp(weatherInfo);
                    double maxTemp = getMaxTemp(weatherInfo);
                    double pressure = getPressure(weatherInfo);
                    double humidity = getHumidity(weatherInfo);

                    tempTextF.setText(Double.toString(temp));
                    minTempTextF.setText(Double.toString(minTemp));
                    maxTempTextF.setText(Double.toString(maxTemp));
                    pressureTextF.setText(Double.toString(pressure));
                    humidityTextF.setText(Double.toString(humidity));


                    Browser wiki = new Browser(cityTextF.getText(),new JFrame());

                }
            }
        });

        JPanel space = new JPanel();
        space.setBackground(new Color(200,200,200));
        leftPanel.add(space);
        JPanel space2 = new JPanel();
        space2.setBackground(new Color(200,200,200));
        leftPanel.add(space2);
        JPanel space5 = new JPanel();
        space5.setBackground(new Color(200,200,200));
        leftPanel.add(space5);

        JPanel countryPanel = new JPanel();
        countryPanel.setBackground(new Color(200,200,200));
        JLabel country = new JLabel("Country:              ");
        JTextField countryTextF = new JTextField("",20);
        countryPanel.add(country);
        countryPanel.add(countryTextF);
        leftPanel.add(countryPanel);

        JPanel currencyCodePanel = new JPanel();
        currencyCodePanel.setBackground(new Color(200,200,200));
        JLabel currencyCode = new JLabel("Currency Code: ");
        JTextField currencyCodeTextF = new JTextField("",20);
        currencyCodePanel.add(currencyCode);
        currencyCodePanel.add(currencyCodeTextF);
        leftPanel.add(currencyCodePanel);

        JPanel curCountryPanel = new JPanel();
        curCountryPanel.setBackground(new Color(200,200,200));
        JButton curCountrybtn = new JButton("OK");
        curCountryPanel.add(curCountrybtn);
        leftPanel.add(curCountryPanel);

        JPanel ratePanel = new JPanel();
        ratePanel.setBackground(new Color(200,200,200));
        JLabel rate = new JLabel("Rate: ");
        JTextField rateTextF = new JTextField("",20);
        rateTextF.setEnabled(false);
        ratePanel.add(rate);
        ratePanel.add(rateTextF);
        leftPanel.add(ratePanel);

        curCountrybtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!currencyCodeTextF.getText().equals("") && !countryTextF.getText().equals("")){
                    Service s = new Service(countryTextF.getText());
                    double rate = s.getRateFor(currencyCodeTextF.getText());
                    rateTextF.setText(Double.toString(rate));
                }
            }
        });

        JPanel space3 = new JPanel();
        space3.setBackground(new Color(200,200,200));
        leftPanel.add(space3);
        JPanel space4 = new JPanel();
        space4.setBackground(new Color(200,200,200));
        leftPanel.add(space4);
        JPanel space6 = new JPanel();
        space6.setBackground(new Color(200,200,200));
        leftPanel.add(space6);

        JPanel countryPanel2 = new JPanel();
        countryPanel2.setBackground(new Color(200,200,200));
        JLabel country2 = new JLabel("Country: ");
        JTextField countryTextF2 = new JTextField("",20);
        JButton countrybtn2 = new JButton("OK");
        countryPanel2.add(country2);
        countryPanel2.add(countryTextF2);
        countryPanel2.add(countrybtn2);
        leftPanel.add(countryPanel2);

        JPanel nbpPanel = new JPanel();
        nbpPanel.setBackground(new Color(200,200,200));
        JLabel nbp = new JLabel("NBP: ");
        JTextField nbpTextF = new JTextField("",20);
        nbpTextF.setEnabled(false);
        nbpPanel.add(nbp);
        nbpPanel.add(nbpTextF);
        leftPanel.add(nbpPanel);

        countrybtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!countryTextF2.getText().equals("")){
                    Service s = new Service(countryTextF2.getText());
                    double rate = s.getNBPRate();
                    nbpTextF.setText(Double.toString(rate));
                }
            }
        });
        jframe.add(leftPanel);

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
        jframe.setResizable(false);
    }

}
