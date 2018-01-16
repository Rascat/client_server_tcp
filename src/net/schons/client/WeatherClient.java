package net.schons.client;

import java.io.*;
import java.net.Socket;

/**
 * Client class that implements the client-part of 'client-server-paradigm'.
 * Connects to a host with the provided host-, port-number.
 * Takes input from user and outputs reply from server.
 */
public class WeatherClient {

    /**
     * Main method, implements client routine.
     * @param args [0]: name of the host the socket should connect to
     *             [1]: number of the port the socket should connect to
     */
    public static void main(String[] args) {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                )
        ) {
            String fromServer, fromUser;
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));


            while ((fromServer = in.readLine()) != null) {
                System.out.println("> " + fromServer.replaceAll("%n", "\n"));

                if (fromServer.equals("bye"))
                    break;


                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("WeatherClient: " + fromUser);
                    out.println(fromUser);
                }
            }

        } catch (IOException ioEx) {
            System.out.println("Could not connect to server. Exit");
        }
    }
}
