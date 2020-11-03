import java.io.InputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class TrataCliente implements Runnable {

    private InputStream cliente;
    private Servidor servidor;
    private List<String> horarios;
    private String horarioMedio;

    public TrataCliente(InputStream cliente, Servidor servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
	this.horarios = new ArrayList<String>();
    }

    public void run() {
        servidor.distribuiMensagem("Horario solicitado..."); //solicita o horario de todos os clientes
	horarios = servidor.recebeHorarios(); //recebe os horarios dos clientes
	horarios.add(servidor.getHorarioServidor()); //adiciona o horario do servidor ao array de horarios
	horarioMedio = servidor.mediaHorarios(horarios); //calcula o horario medio entre os horarios dos clientes e do servidor
	servidor.setHorarioServidor(horarioMedio); //atualiza o horario do servidor para o horario medio
	servidor.devoveHorarioMedio(horarioMedio); //devolve o horario medio para todos os clientes
	horarios.clear(); 
    }
}
