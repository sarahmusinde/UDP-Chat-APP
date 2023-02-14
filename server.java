import java.net.*;
import java.io.*;
import java.util.*;


 class server{
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    InetAddress inetAddress;



    public static void main(String[] args) {
        System.out.println("Receiving Message.....");
        try {
            ServerSocket serverSocket = new ServerSocket(5887);
            while(true){
             Socket socket= serverSocket.accept();
                System.out.println("Enter your text..... ");
                DatagramSocket datagramSocket = new DatagramSocket(serverSocket.getLocalPort());
                ServerOperation client2=new ServerOperation(datagramSocket,socket);
                client2.ReceiveSend();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}


class ServerOperation{

    private DatagramSocket datagramSocket;
    Scanner sc;
    private byte[] buffer = new byte[1024];
    private Socket socket;
    public ServerOperation(DatagramSocket datagramSocket,Socket socket){
        this.datagramSocket = datagramSocket;
        this.socket = socket;
    }
    public void ReceiveSend(){
        while(true){
            try{
                Scanner sc = new Scanner(System.in);
                DatagramPacket datagramPacket= new DatagramPacket(buffer,buffer.length);
                datagramSocket.receive(datagramPacket);
                InetAddress inetAddress= datagramPacket.getAddress();
                int port =datagramPacket.getPort();

                String MessageReceived = new String(datagramPacket.getData(),0,datagramPacket.getLength());
                if(MessageReceived.equalsIgnoreCase("bye") ){

                    datagramSocket.close();
                    socket.close();
                   break;
                }
                System.out.println("client says:  "+MessageReceived+"\nEnter your message: \n");
                String myMsg = sc.nextLine();
                buffer=myMsg.getBytes();
                datagramPacket=new DatagramPacket(buffer,buffer.length,inetAddress,port);
                datagramSocket.send(datagramPacket);

            }catch(IOException e){e.printStackTrace();break;}

        }
    }

}