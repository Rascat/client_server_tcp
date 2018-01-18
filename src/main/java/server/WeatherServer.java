package main.java.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The WeatherServer is the the main server routine in this client/server application.
 * It binds a ServerSocket to the provided port and waits for an incoming request from a WeatherClient.
 * The server program uses an instance of WeatherProtocol to handle the communication between itself
 * and the WeatherClient.
 * Once the client sends a 'bye' string, the server routine will come to a halt.
 */
public class WeatherServer {

    /**
     * Main method, implements server routine
     * @param args [0]: the port number to which the server socket should be bound
     *             [1]: a valid OWM api key
     */
    public static void main (String args[]) {

        int portNumber = Integer.parseInt(args[0]);
        String apiKey = args[1];
        System.out.println("WeatherServer has been initialized with the following configuration:\n" +
                "Port Number: " + portNumber + "\n" +
                "OWM Api Key: " + apiKey);

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream())
                )
        ){
            String inputLine, outputLine;
            WeatherProtocol wp = new WeatherProtocol();

            // Initiate conversation with client
            outputLine = wp.greeter();
            out.println(outputLine);

            // Handle input from client and send a reply, until client sends 'bye'
            while ((inputLine = in.readLine()) != null) {
                outputLine = wp.handleInput(inputLine, apiKey);
                out.println(outputLine);
                if (outputLine.equalsIgnoreCase("bye"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
