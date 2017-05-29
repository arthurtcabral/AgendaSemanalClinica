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
		int op = 0;

		do {
			System.out.println("1 - Criar Agenda: ");
			System.out.println("2 - Abrir Agenda: ");
			System.out.println("3 - SAIR: ");
			op = teclado.nextInt();

			int mes = 0;
			int semana = 0;
			boolean mesInvalido = mes < 1 || mes > 12;
			boolean semanaInvalida = semana < 1 || semana > 52;

			if (op == 1) {
				AgendaSemanal agendaSemanal;

				while (mesInvalido || semanaInvalida) {
					mes = informarMes();
					semana = informarSemana();

					if (mesInvalido) {
						System.out.println("Por favor, informe um mês válido.");
					} else if (semanaInvalida) {
						System.out
								.println("Por favor, informe uma semana válida.");
					} else {
						agendaSemanal = new AgendaSemanal(mes, semana);
						File arquivoAgenda = new File("2017" + mes + ""
								+ semana);
						// TODO: Salvar agenda.
					}
				}

			} else if (op == 2) {
				// TODO: Desenvolver.
			}

		} while (op != 3);
		teclado.close();

	}

	private static int informarMes() {
		Scanner teclado = new Scanner(System.in);
		int mes;
		System.out.println("Informe um mês (1 - 12): ");
		mes = teclado.nextInt();
		teclado.close();
		return mes;
	}

	private static int informarSemana() {
		Scanner teclado = new Scanner(System.in);
		int semana;
		System.out.println("Informe uma semana (1 - 52): ");
		semana = teclado.nextInt();
		teclado.close();
		return semana;
	}

}
