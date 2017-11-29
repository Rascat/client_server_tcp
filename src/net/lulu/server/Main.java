package net.lulu.server;


public class Main {
    public static void main(String args[]) {
        WeatherProtocol wp = new WeatherProtocol();
        String result = wp.handleInput("Berlin", "5e3219d626df00b31959756027de968d");
        System.out.println(result);
    }
}
