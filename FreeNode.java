import java.io.*;
import java.net.*;
import org.jibble.pircbot.*;

public class FreeNode extends PircBot {
	
    public FreeNode() {
        this.setName("MMMBot");
    }
    
    public void onMessage(String channel, String sender,
                       String login, String hostname, String message) {
    	
    	
    	String[] output = message.split(" ");
    	String first = output[0].toLowerCase();
        
    	if (message.toLowerCase().contains("weather")) {
    		String zipcode = output[output.length-1].replaceAll("\\?", "");
    		
    		String response = WeatherFetcher.getResponse(zipcode);
    		if(response != null)
    		{
    			sendMessage(channel, sender + ": The weather's going to be " +  WeatherFetcher.getWeather(response) + " with the temperature being " + WeatherFetcher.getTemperature(response)
	    		+ " degrees Fahrenheit right now, with a high of " + WeatherFetcher.getHighTemp(response) + " degrees Fahrenheit, and with a low of " + WeatherFetcher.getLowTemp(response) + " degrees Fahrenheit.");
    		} else {
    			sendMessage(channel, sender + ": Could not get temperature for zipcode: " + zipcode);
    		}
        }
    }

    public static void main(String[] args) throws Exception {
        // The server to connect to and our details.
        String server = "irc.freenode.net";
        String nick = "Karthik";
        String login = "simple_bot";

        // The channel which the bot will join.
        String channel = "#irchacks";
        
        // Now start our bot up.
        FreeNode bot = new FreeNode();
        
        // Enable debugging output.
        bot.setVerbose(true);
        
        // Connect to the IRC server.
        bot.connect("irc.freenode.net");

        // Join the #pircbot channel.
        bot.joinChannel("#pircbot");     
            

        
        // Connect directly to the IRC server.
        Socket socket = new Socket(server, 6667);
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream( )));
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream( )));
        
        // Log on to the server.
        writer.write("NICK " + nick + "\r\n");
        writer.write("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");
        writer.flush( );
        
        // Read lines from the server until it tells us we have connected.
        String line = null;
        while ((line = reader.readLine( )) != null) {
            if (line.indexOf("004") >= 0) {
                // We are now logged in.
                break;
            }
            else if (line.indexOf("433") >= 0) {
                System.out.println("Nickname is already in use.");
                return;
            }
        }
        
        // Join the channel.
        writer.write("JOIN " + channel + "\r\n");
        writer.flush( );
        
        // Keep reading lines from the server.
        while ((line = reader.readLine( )) != null) {
           // if ()
            {
            	
            }
            //else {
                // Print the raw line received by the bot.
               // System.out.println(line);
            }
        }
    }

//}
