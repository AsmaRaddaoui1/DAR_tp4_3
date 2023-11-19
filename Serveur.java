package Activite4_3;

import java.io.IOException;
import java.net.*;
import java.util.HashSet;

public class Serveur {
    private static final int PORT = 1234;
    private static byte[] buffer = new byte[1024];
    private static HashSet<InetSocketAddress> clients = new HashSet<>();

    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(PORT);
            System.out.println("Démarrage du Serveur sur le port " + PORT);

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetSocketAddress senderAddress = new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort());

                if (!clients.contains(senderAddress)) {
                    clients.add(senderAddress);
                    System.out.println("Nouveau client connecté: " + senderAddress);
                }

                System.out.println("Message reçu de " + senderAddress + ": " + message);

                for (InetSocketAddress client : clients) {
                    if (!client.equals(senderAddress)) {
                        DatagramPacket sendPacket = new DatagramPacket(receivePacket.getData(), receivePacket.getLength(), client.getAddress(), client.getPort());
                        serverSocket.send(sendPacket);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
