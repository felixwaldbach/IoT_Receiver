package main.java.de.dcsquare.paho.client.subscriber;

import org.eclipse.paho.client.mqttv3.*;

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


        String[] parts = message.toString().split(" ");
        if(parts[0].equals("Google")) {
                String query = "";
                for(int i=1; i<parts.length; i++) {
                    if(query.equals("")) query = parts[i];
                    else query = query + "+" + parts[i];
                }
                String url = "https://www.google.com/search?q=" +query;
                String cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
                Process p = Runtime.getRuntime().exec(cmd);
        } else if(parts[0].equals("Netflix")) {
            String query = "";
            for(int i=1; i<parts.length; i++) {
                if(query.equals("")) query = parts[i];
                else query = query + "%20" + parts[i];
            }
            String url = "https://www.netflix.com/search?q=" +query;
            String cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
            Process p = Runtime.getRuntime().exec(cmd);
        } else if(parts[0].equals("YouTube")) {
            String query = "";
            for(int i=1; i<parts.length; i++) {
                if(query.equals("")) query = parts[i];
                else query = query + "+" + parts[i];
            }
            String url = "https://www.youtube.com/results?search_query=" +query;

            String cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
            Process p = Runtime.getRuntime().exec(cmd);
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
