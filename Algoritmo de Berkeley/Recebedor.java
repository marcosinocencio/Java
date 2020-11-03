import java.io.InputStream;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Recebedor implements Runnable {

    private InputStream servidor;
    private Cliente cliente;    

    public Recebedor(InputStream servidor, Cliente cliente) {
        this.servidor = servidor;
	this.cliente = cliente;
    }

    public void run() {
        String str = new String();
	String msg = new String();
        Scanner s = new Scanner(this.servidor);
        while (s.hasNextLine()) {
	    msg = s.nextLine();
            if (msg.equals("Horario solicitado...")) {
		System.out.print("///////////////////////////////////// \nhora atual retornada: ");
		str = cliente.criaHorario(); //cria um horario aleatorio
		System.out.println(str);
	        cliente.retornaHora(str); //retorna o horario atual para o servidor
	    }
	    else {
		cliente.setHorarioCliente(msg); //atualiza horario do cliente
		System.out.println("\nHorario atualizado para: " + msg);
	    }
        }
	s.close();
    }
}
