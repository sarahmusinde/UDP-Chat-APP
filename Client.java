import java.net.*;
import java.io.*;
import java.util.*;


public class Client {
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;


    public static void main(String[] args) throws IOException {
        Socket socket;
        InetAddress inetAddress= InetAddress.getByName("localhost");

        try{
            socket = new Socket(inetAddress,5887);

            DatagramSocket datagramSocket = new DatagramSocket();



            System.out.println("Chatting.....\nEnter your message:");
            Client1Operation client1Operation = new Client1Operation(datagramSocket,inetAddress) ;

            client1Operation.SendThenReceiveMsg();

        }catch (RuntimeException e){e.printStackTrace();}

      }


    }

 class Client1Operation {
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    InetAddress inetAddress;
    private byte[] buffer;
    public Client1Operation(DatagramSocket datagramSocket, InetAddress inetAddress) {
        this.datagramSocket=datagramSocket;
        this.inetAddress = inetAddress;
    }

    public void SendThenReceiveMsg() {

        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                String msg = sc.nextLine();
                buffer = msg.getBytes();
                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 5887);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket);
                String msgReceived = new String(datagramPacket.getData(), 0, datagramPacket.getLength());

                System.out.println("Client2 says : " + msgReceived + "\nEnter your message");

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}




