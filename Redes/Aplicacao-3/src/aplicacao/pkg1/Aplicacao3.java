import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Aplicacao3 {
	
	private DatagramaIP datagram;
	private static ArrayList<DatagramaIP> datagram_frag = new ArrayList<DatagramaIP>();
	private static ServerSocket welcomeSocket;
	/**
	 * @param args
	 */
	
	public static DatagramaIP deserializeDatagram(byte[] buffer) {
		ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
		ObjectInputStream in = null;
    	DatagramaIP datagrama = null;
    	try {
    		//arquivo onde estao os dados serializados

    		try {
    			System.out.println(Integer.toString(bis.available()));
    		  in = new ObjectInputStream(bis);
    		  datagrama = (DatagramaIP)in.readObject(); 
    		} finally {

    		}
    	} catch (ClassNotFoundException ex) {
    		ex.printStackTrace();
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		try {
      		  bis.close();
      		  in.close();
    		} catch (IOException ex) {
    			ex.printStackTrace();
    		}
    	}
     
    	return datagrama;
    }
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		welcomeSocket = new ServerSocket(9876);

		while(true)
		{
			Socket connectionSocket = welcomeSocket.accept();
			
			DataInputStream inFromClient = 
	    			new DataInputStream((connectionSocket.getInputStream())); 

			byte[] clientbuffer = new byte[inFromClient.available()];
			DatagramaIP datagram = deserializeDatagram(clientbuffer);
			datagram_frag.add(datagram);
		    if(datagram.getFragflag() == 0) //ultimo pacote chegou
			{
				//chamar um metoo para colocar em ordem o ArrayList datagram_flag de acordo com o offset
				//juntar em um unico Bytearray
				//abrir uma janela para escolha do local e novo nome do arquivo que será salvo
				//método save
			}

			
		}//while
		
	}//static main
	
}//MainClass


