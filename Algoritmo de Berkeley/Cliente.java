import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Random;

public class Cliente {

    public static void main(String[] args)
            throws UnknownHostException, IOException {
        
        new Cliente("127.0.0.1", 12345).executa();
    }
    private String host;
    private int porta;
    private PrintStream saida;
    private String horarioCliente;

    public Cliente(String host, int porta) {
        this.host = host;
        this.porta = porta;
	horarioCliente = new String();
    }

    public void executa() throws UnknownHostException, IOException {
        Socket cliente = new Socket(this.host, this.porta);
        System.out.println("O cliente se conectou ao servidor!");

      
        Recebedor r = new Recebedor(cliente.getInputStream(), this);
        new Thread(r).start();

 
        //Scanner teclado = new Scanner(System.in);
        saida = new PrintStream(cliente.getOutputStream());
        //while (teclado.hasNextLine()) {
            //saida.println(teclado.nextLine());
        //}

        //saida.close();
        //teclado.close();
        //cliente.close();
    }

    public void retornaHora(String msg) { //retorna o horario atual para o servidor
	    saida.flush();
            saida.println(msg);
    }
    
    public void setHorarioCliente(String hor) {
	horarioCliente = hor;
    }

    public String criaHorario() { //cria um horario aleatorio
    	int min, hora;
	String str = new String();
	Random gerador = new Random();
	min = gerador.nextInt(61);
	hora = gerador.nextInt(24);
        if ((hora < 10)&&(min < 10)) str = "0" + hora + ":0" + min;
        else if (min < 10) str = hora + ":0" + min;
	     else if (hora < 10) str = "0" + hora + ":" + min;
	          else str = hora + ":" + min;
	
	return str;
    }
}
