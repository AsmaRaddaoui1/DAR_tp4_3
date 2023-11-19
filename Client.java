package Activite4_3;

import java.io.IOException;
import java.net.*;
import java.util.Scanner; // Import de la classe Scanner


public class Client {
    private static final int PORT = 1234;
    private static byte[] buffer = new byte[1024];

    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez votre nom d'utilisateur: ");
            String username = scanner.nextLine();
            System.out.print("Message: ");
            String message = scanner.nextLine();

            String fullMessage = "[" + username + "]: " + message;
            byte[] sendData = fullMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, PORT);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(receivePacket);
            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(receivedMessage);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
