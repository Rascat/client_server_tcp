package net.lulu.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WeatherServer {

    public static void main (String args[]) throws IOException {
        int portNumber = Integer.parseInt(args[0]);

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
            outputLine = "Start Server [" + serverSocket.getInetAddress() + "] " + "on port nr.: " + portNumber;
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = wp.handleInput(inputLine);
                out.println(outputLine);
                if (outputLine.equalsIgnoreCase("bye"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
