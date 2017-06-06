import java.io.*;
import java.util.*;

public class AgendaUtil {

	public AgendaSemanal criarAgenda(int mes, int semana) throws Exception {
		CadaDia[] dias = new CadaDia[5];
		for (int i = 0; i < 5; i++) {
			dias[i] = new CadaDia();
		}

		/*
		 * TESTE SinglyLinkedList<Consulta> consultas = new
		 * SinglyLinkedList<Consulta>(); Consulta consulta = new
		 * Consulta("Pedro",730,1,0); consultas.insertLast(consulta);
		 * consultas.insertLast(consulta); consultas.insertLast(consulta);
		 * 
		 * StaticQueue<Consulta> filaEspera = new StaticQueue<Consulta>(7);
		 * 
		 * CadaDia dia = new CadaDia(3,consultas, filaEspera);
		 * 
		 * dias[0] = dia;
		 * 
		 * SinglyLinkedList<Consulta> consultas2 = new
		 * SinglyLinkedList<Consulta>(); Consulta consulta2 = new
		 * Consulta("Lausa",800,2,2); consultas2.insertLast(consulta2);
		 * consultas2.insertLast(consulta2);
		 * 
		 * StaticQueue<Consulta> filaEspera2 = new StaticQueue<Consulta>(7);
		 * 
		 * filaEspera2.enqueue(consulta2); filaEspera2.enqueue(consulta2);
		 * 
		 * CadaDia dia2 = new CadaDia(2,consultas2, filaEspera2);
		 * 
		 * dias[2] = dia2;
		 */

		AgendaSemanal agendaSemanal = new AgendaSemanal(dias, mes, semana);

		try {
			salvarAgenda(agendaSemanal, mes, semana);
		} catch (Exception e) {
			System.out.println(e);
		}

		return agendaSemanal;
	}

	public void salvarAgenda(AgendaSemanal agendaSemanal, int mes, int semana) throws Exception {
		File arquivoAgenda = new File("2017" + mes + "" + semana + ".txt");
		try {
			FileWriter fw = new FileWriter(arquivoAgenda, false);
			PrintWriter pw = new PrintWriter(fw);
			for (int i = 0; i < 5; i++) {

				pw.print(mes + "/" + semana + "/");
				CadaDia dia = agendaSemanal.getConsultasEEsperasDoDia()[i];

				pw.print(dia.getQuantidadeConsultas() + "/");

				SinglyLinkedList<Consulta> consultas = dia.getListaConsultas();
				for (int j = 0; j < consultas.numElements(); j++) {
					Consulta consulta = consultas.get(j);
					pw.print(consulta.getNome() + "=" + consulta.getHorario() + "=" + consulta.getTipo() + "="
							+ consulta.getTolerancia() + "-");
				}
				pw.print("/");

				StaticQueue<Consulta> filaEspera = dia.getFilaEspera();
				while (!filaEspera.isEmpty()) {
					Consulta consulta = filaEspera.dequeue();
					pw.print(consulta.getNome() + "=" + consulta.getHorario() + "=" + consulta.getTipo() + "="
							+ consulta.getTolerancia() + "-");
				}
				pw.print("/");

				pw.println("");
			}
			pw.close();
		} catch (Exception e) {
			System.out.println("Erro ao gravar no arquivo.");
		}

	}

	public AgendaSemanal carregarAgenda(int mes, int semana) throws Exception {

		String filename = "2017" + mes + "" + semana + ".txt";

		CadaDia[] cadaDia = new CadaDia[5];
		int cont = 0;

		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {

				String[] linha = line.split("/");

				int qtdConsultas = Integer.parseInt(linha[2]);

				SinglyLinkedList<Consulta> listaConsultas = new SinglyLinkedList<Consulta>();
				if (linha.length >= 4) {
					String[] sConsultas = linha[3].split("-");
					for (int i = 0; i < sConsultas.length; i++) {
						String[] sDados = sConsultas[i].split("=");
						String nome = sDados[0];
						int horario = Integer.parseInt(sDados[1]);
						int tipo = Integer.parseInt(sDados[2]);
						int tolerancia = Integer.parseInt(sDados[3]);
						Consulta consulta = new Consulta(nome, horario, tipo, tolerancia);

						listaConsultas.insertFirst(consulta);
					}
				}

				StaticQueue<Consulta> filaEspera = new StaticQueue<Consulta>(7);
				if (linha.length >= 5) {
					String[] sFilaEspera = linha[4].split("-");
					for (int i = 0; i < sFilaEspera.length; i++) {
						String[] sDados = sFilaEspera[i].split("=");
						String nome = sDados[0];
						int horario = Integer.parseInt(sDados[1]);
						int tipo = Integer.parseInt(sDados[2]);
						int tolerancia = Integer.parseInt(sDados[3]);
						Consulta consulta = new Consulta(nome, horario, tipo, tolerancia);

						filaEspera.enqueue(consulta);
					}
				}

				cadaDia[cont++] = new CadaDia(qtdConsultas, listaConsultas, filaEspera);

				System.out.println(Arrays.toString(linha));

				line = br.readLine();
			}

			AgendaSemanal agendaSemanal = new AgendaSemanal(cadaDia, mes, semana);

			br.close();

			return agendaSemanal;

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo \"" + filename + "\" não existe.");
			return null;
		} catch (IOException e) {
			System.out.println("Erro na leitura do arquivo " + filename + ".");
			return null;
		}

	}

}
