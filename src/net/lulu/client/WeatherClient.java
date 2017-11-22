package net.lulu.client;

import java.io.*;
import java.net.Socket;

public class WeatherClient {

    public static void main(String[] args) throws IOException {
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

            while((fromServer = in.readLine()) != null) {
                System.out.println("WeatherServer: " + fromServer);
                if(fromServer.equals("bye"))
                    break;

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("WeatherClient: " + fromUser);
                    out.println(fromUser);
                }
            }
        }
    }
}
