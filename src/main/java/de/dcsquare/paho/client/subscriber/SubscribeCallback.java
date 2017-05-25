package main.java.de.dcsquare.paho.client.subscriber;

import org.eclipse.paho.client.mqttv3.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Dominik Obermaier
 * @author Christian Götz
 */
public class SubscribeCallback implements MqttCallback {

    // The default system browser under windows.
    private static final String WIN_PATH = "rundll32";
    // The flag to display a url.
    private static final String WIN_FLAG = "url.dll,FileProtocolHandler";

    @Override
    public void connectionLost(Throwable cause) {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Message arrived. Topic: " + topic + "  Message: " + message.toString());

        String url = "";
        String[] parts = message.toString().split(" ");
        if(parts[0].equals("Google")) {
                String query = "";
                for(int i=1; i<parts.length; i++) {
                    if(query.equals("")) query = parts[i];
                    else query = query + "+" + parts[i];
                }
                url = "https://www.google.com/search?q=" +query;
                String cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
                Process p = Runtime.getRuntime().exec(cmd);
        } else if(parts[0].equals("Netflix")) {
            String query = "";
            for(int i=1; i<parts.length; i++) {
                if(query.equals("")) query = parts[i];
                else query = query + "%20" + parts[i];
            }
            url = "https://www.netflix.com/search?q=" +query;
            String cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
            Process p = Runtime.getRuntime().exec(cmd);
        } else if(parts[0].equals("YouTube")) {
            String query = "";
            for(int i=1; i<parts.length; i++) {
                if(query.equals("")) query = parts[i];
                else query = query + "+" + parts[i];
            }
            url = "https://www.youtube.com/results?search_query=" +query;

            String cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
            Process p = Runtime.getRuntime().exec(cmd);
        }


        try {
        // Define your URL
            URL url1 = new URL("https://www.netflix.com/search?q=Prison%20Break&jbv=70140425&jbp=0&jbr=0");
            URLConnection conn = url1.openConnection();

        // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

        //Save text file (HTML Source)
            String fileName = "C:\\Users\\Felix\\Desktop\\file.txt";
            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

        //use FileWriter to write file
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println("Printing WebPage source on console, Please wait...\n");
            while ((inputLine = br.readLine()) != null) {
                bw.write(inputLine);
            }

            bw.close();
            br.close();

            System.out.println("Saved");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ("home/LWT".equals(topic)) {
            System.err.println("Sensor gone!");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //no-op
    }
}
