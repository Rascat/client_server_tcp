package net.lulu.server;


public class Main {
    public static void main(String args[]) {
        WeatherProtocol wp = new WeatherProtocol();
        String result = wp.handleInput("Berlin");
        System.out.println(result);
    }
}
