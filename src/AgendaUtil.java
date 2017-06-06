import java.io.*;
import java.util.*;

public class AgendaUtil {

	public AgendaSemanal criarAgenda(int mes, int semana) throws Exception {
		CadaDia[] dias = new CadaDia[5];
		for (int i = 0; i < 5; i++) {
			dias[i] = new CadaDia();
		}
		
		
		/* TESTE
		SinglyLinkedList<Consulta> consultas = new SinglyLinkedList<Consulta>();
		Consulta consulta = new Consulta("Pedro",730,1,0);
		consultas.insertLast(consulta);
		consultas.insertLast(consulta);
		consultas.insertLast(consulta);
		
		StaticQueue<Consulta> filaEspera = new StaticQueue<Consulta>(7);
		
		CadaDia dia = new CadaDia(3,consultas, filaEspera);
		
		dias[0] = dia;
		
		SinglyLinkedList<Consulta> consultas2 = new SinglyLinkedList<Consulta>();
		Consulta consulta2 = new Consulta("Lausa",800,2,2);
		consultas2.insertLast(consulta2);
		consultas2.insertLast(consulta2);
		
		StaticQueue<Consulta> filaEspera2 = new StaticQueue<Consulta>(7);
		
		filaEspera2.enqueue(consulta2);
		filaEspera2.enqueue(consulta2);
		
		CadaDia dia2 = new CadaDia(2,consultas2, filaEspera2);
		
		dias[2] = dia2;
		*/
		
		AgendaSemanal agendaSemanal = new AgendaSemanal(dias, mes, semana);	
		
		try{
			salvarAgenda(agendaSemanal, mes, semana);
		}catch(Exception e){
			System.out.println(e);
		}
		
		return agendaSemanal;
	}
	
	public void salvarAgenda(AgendaSemanal agendaSemanal, int mes, int semana) throws Exception{
		File arquivoAgenda = new File("2017" + mes + "" + semana + ".txt");
		try {
			FileWriter fw = new FileWriter(arquivoAgenda, false);
			PrintWriter pw = new PrintWriter(fw);
			for (int i = 0; i < 5; i++) {
				
				pw.print(mes+"/"+semana+"/");
				CadaDia dia = agendaSemanal.getConsultasEEsperasDoDia()[i];	
				
				pw.print(dia.getQuantidadeConsultas()+"/");
				
				SinglyLinkedList<Consulta> consultas = dia.getListaConsultas();
				for(int j=0; j< consultas.numElements();j++){
					Consulta consulta = consultas.get(j);
					pw.print(consulta.getNome()+"="+ consulta.getHorario() +"="+ consulta.getTipo() +"="+ consulta.getTolerancia()+"-");
				}
				pw.print("/");
				
				StaticQueue<Consulta> filaEspera = dia.getFilaEspera();
				while(!filaEspera.isEmpty()){
					Consulta consulta = filaEspera.dequeue();
					pw.print(consulta.getNome()+"="+ consulta.getHorario() +"="+ consulta.getTipo() +"="+ consulta.getTolerancia()+"-");
				}
				pw.print("/");
				
				pw.println("");
			}
			pw.close();
		} catch (Exception e) {
			System.out.println("Erro ao gravar no arquivo.");
		}
		
		
	}

	public AgendaSemanal carregarAgenda(int mes, int semana) {
		return null;
	}

}
