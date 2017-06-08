import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		AgendaUtil agendaUtil = new AgendaUtil();
		int op = 0;

		do {
			System.out.println("1 - Criar Agenda: ");
			System.out.println("2 - Abrir Agenda: ");
			System.out.println("3 - SAIR: ");
			op = teclado.nextInt();

			if (op == 1) {
				int mes = 0;
				int semana = 0;
				AgendaSemanal agendaSemanal;

				while ((mes < 1 || mes > 12) || (semana < 1 || semana > 52)) {
					System.out.println("Informe um m�s (1 - 12): ");
					mes = teclado.nextInt();
					System.out.println("Informe uma semana (1 - 52): ");
					semana = teclado.nextInt();

					if (mes < 1 || mes > 12) {
						System.out.println("Por favor, informe um m�s v�lido.");
					} else if (semana < 1 || semana > 52) {
						System.out.println("Por favor, informe uma semana v�lida.");
					} else {
						try{
							agendaSemanal = agendaUtil.criarAgenda(mes, semana);
						}catch(Exception e){
							System.out.println(e);
						}
					}
				}

			} else if (op == 2) {
				
				//TODO: Ler do teclado med e semana para passar por parâmetro
				try{
					AgendaSemanal agendaSemanal =  agendaUtil.carregarAgenda(1, 1);
				
				
				int op2 = 0;
				
				do {			
				
				System.out.println("");
				System.out.println("--MENU AGENDA--");
				System.out.println("");
				System.out.println("1 - Exibir toda a agenda: ");
				System.out.println("2 - Marcar consulta: ");
				System.out.println("3 - Desmarcar consulta: ");
				System.out.println("4 - Alterar dia e/ou hor�rio da consulta: ");
				System.out.println("5 - Exibir as consultas marcadas em intervalos de dias e hor�rios: ");
				System.out.println("6 - Exibir as consultas em fila de espera para o dia: ");
				System.out.println("7 - Exibir os hor�rios livres para o dia: ");
				System.out.println("8 - Criar matriz: ");
				System.out.println("9 - Salvar agenda: ");
				System.out.println("10 - SAIR: ");
				System.out.println("");
				op2 = teclado.nextInt();
				
				if(op2 == 1){
					agendaSemanal.exibirTodaAAgenda();
				}else if(op2 == 2){
					int diaDaSemana, horario, tipo, tolerancia;
					String nome;
					
					System.out.println("Dia da semana:");
					diaDaSemana = teclado.nextInt();
					System.out.println("Horário");
					horario = teclado.nextInt();
					System.out.println("Nome:");
					nome = teclado.next();
					System.out.println("Tipo:");
					tipo = teclado.nextInt();
					System.out.println("Tolerância:");
					tolerancia = teclado.nextInt();
					
					agendaSemanal.marcarConsulta(diaDaSemana, horario, nome, tipo, tolerancia);
				}else if(op2 == 3){
					int diaDaSemana, horario;
					System.out.println("Dia da semana:");
					diaDaSemana = teclado.nextInt();
					System.out.println("Horário");
					horario = teclado.nextInt();
					
					agendaSemanal.desmarcarConsulta(diaDaSemana, horario);
				}else if(op2 == 4){
					
				}else if(op2 == 5){
					
				}else if(op2 == 6){
					mostraMenuDiasDaSemana();
					int diaDaSemana = 5;
					diaDaSemana = teclado.nextInt();
					while(diaDaSemana < 0 || diaDaSemana > 4){
						System.out.println("Por favor, informe um dia v�lido.");
						diaDaSemana = teclado.nextInt();
					}
					agendaSemanal.exibirFilaDeEsperaParaDiaEspecifico(diaDaSemana);
				}else if(op2 == 7){
					
				}else if(op2 == 8){
					
				}else if(op2 == 9){
					
				}
				
				} while (op2 != 10);
				
				
				}catch(Exception e){
					System.out.println(e);
				}
			}
				

		} while (op != 3);
		teclado.close();

	}
	
	public static void mostraMenuDiasDaSemana(){
		System.out.println("0 - Segunda-Feira");
		System.out.println("1 - Ter�a-Feira");
		System.out.println("2 - Quarta-Feira");
		System.out.println("3 - Quinta-Feira");
		System.out.println("4 - Sexta-Feira");
		System.out.println("");
	}

}
