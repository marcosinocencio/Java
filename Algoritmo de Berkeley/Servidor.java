import java.util.Scanner;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Random;


public class Servidor {

    public static void main(String[] args) throws IOException {
        
        new Servidor(12345).executa();
    }
    private int porta;
    private List<PrintStream> clientes;
    private List<InputStream> Inputclientes;
    private List<String> horarios;
    private String horarioServidor;

    public Servidor(int porta) {
        this.porta = porta;
        this.clientes = new ArrayList<PrintStream>();
	this.Inputclientes = new ArrayList<InputStream>();
	this.horarios = new ArrayList<String>();
	horarioServidor = new String();
    }

    public void executa() throws IOException { 
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta 12345 aberta!");

        while (true) {
         
            Socket cliente = servidor.accept();
            System.out.println("////////////////////////////////////////////// \nNova conexao com o cliente "
                    + cliente.getInetAddress().getHostAddress());
	    
	    horarioServidor = criaHorario();
           
	    System.out.println("horario atual do servidor: " + horarioServidor);

            PrintStream ps = new PrintStream(cliente.getOutputStream());
            this.clientes.add(ps);
	    this.Inputclientes.add(cliente.getInputStream());		

            TrataCliente tc = new TrataCliente(cliente.getInputStream(), this);
            new Thread(tc).start();
        }

    }

    public void distribuiMensagem(String msg) { //distribui a mensagem para todos os clientes
       	System.out.println("\n" + msg);
        for (PrintStream cliente : this.clientes) {
            cliente.println(msg);
        }
    }
 
    public void devoveHorarioMedio(String msg) { //devolve o horario medio para todos os clientes
       	System.out.println("\nhorario medio retornado para os clientes: " + msg);
        for (PrintStream cliente : this.clientes) {
            cliente.println(msg);
        }
    }

    public String mediaHorarios(List<String> horarios) { //calcula o horario medio entre todos os horarios
	List<Integer> horariosEmMinutos;
	horariosEmMinutos = new ArrayList<Integer>();
	int minuto, hora, total = 0, media;
	String horarioMedio = new String();

        for (String horario : horarios) { //converte os horarios para minutos
            hora = Integer.parseInt(horario.split(":")[0]);
	    minuto = Integer.parseInt(horario.split(":")[1]);
	    minuto = minuto + hora * 60;
	    horariosEmMinutos.add(minuto);
        }

	for (Integer min : horariosEmMinutos) { //soma todos os minutos
	    total = total + min;
	}

	media = total/horariosEmMinutos.size(); //calcula media

	hora = media/60;
	minuto = media%60;

	if ((hora < 10)&&(minuto < 10)) horarioMedio = "0" + hora + ":0" + minuto;
		else if (minuto < 10) horarioMedio = hora + ":0" + minuto;
		     else if (hora < 10) horarioMedio = "0" + hora + ":" + minuto;
			  else horarioMedio = hora + ":" + minuto;

	return horarioMedio;
    }

    public List<String> recebeHorarios() { //recebe os horarios de todos os clientes
	String str = new String();
	System.out.println("\nhorarios retornados dos clientes:");
	for (InputStream Inputcliente : this.Inputclientes) {
	    Scanner s = new Scanner(Inputcliente);
            while (true) {

	    	str = s.next();
            	System.out.println(str);

		if (s.nextLine().equals("")) {
			break;
	    	}
            }
	    horarios.add(str);
        }
	return horarios;
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
	
    public String getHorarioServidor() {
    	return horarioServidor;
    }

    public void setHorarioServidor(String hor) {
    	horarioServidor = hor;
    }

}
