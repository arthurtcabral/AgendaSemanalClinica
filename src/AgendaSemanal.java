public class AgendaSemanal {

	private CadaDia[] consultasEEsperas;
	private int mes;
	private int semana;
	public static int[] horariosValidos = { 730, 800, 830, 900, 930, 1000, 1030, 1100, 1130, 1200, 1230, 1300, 1330,
			1400, 1430, 1500, 1530, 1600, 1630, 1700, 1730, 1800, 1830, 1900, 1930, 2000, 2030, 2100, 2130, 2200,
			2230 };

	// Construtor utilizado quando se vai criar uma nova agenda.
	public AgendaSemanal(int mes, int semana) {
		this.consultasEEsperas = new CadaDia[5];
		this.mes = mes;
		this.semana = semana;
	}

	// Construtor utilizado para ler do arquivo.
	public AgendaSemanal(CadaDia[] consultasEEsperasDoDia, int mes, int semana) {
		this.consultasEEsperas = consultasEEsperasDoDia;
		this.mes = mes;
		this.semana = semana;
	}

	public AgendaSemanal() {

	}

	public CadaDia[] getConsultasEEsperasDoDia() {
		return consultasEEsperas;
	}

	public int getMes() {
		return mes;
	}

	public int getSemana() {
		return semana;
	}

	public void exibirTodaAAgenda() {
		for (int i = 0; i < this.consultasEEsperas.length; i++) {
			if (this.consultasEEsperas[i] != null) {
				if (i == 0) {
					System.out.println("-- Segunda-Feira --");
					System.out.println("");
					this.mostrarAgendaParaDiaEspecifico(i);
				} else if (i == 1) {
					System.out.println("-- Terça-Feira --");
					System.out.println("");
					this.mostrarAgendaParaDiaEspecifico(i);
				} else if (i == 2) {
					System.out.println("-- Quarta-Feira --");
					System.out.println("");
					this.mostrarAgendaParaDiaEspecifico(i);
				} else if (i == 3) {
					System.out.println("-- Quinta-Feira --");
					System.out.println("");
					this.mostrarAgendaParaDiaEspecifico(i);
				} else if (i == 4) {
					System.out.println("-- Sexta-Feira --");
					System.out.println("");
					this.mostrarAgendaParaDiaEspecifico(i);
				}
			} else {
				break;
			}
		}
	}

	public void exibirFilaDeEsperaParaDiaEspecifico(int diaDaSemana) {
		if (this.consultasEEsperas[diaDaSemana] != null) {
			if (this.consultasEEsperas[diaDaSemana].getFilaEspera() != null) {
				StaticQueue<Consulta> filaArmazenamento = new StaticQueue<Consulta>(
						this.consultasEEsperas[diaDaSemana].getFilaEspera().numElements());
				while (!this.consultasEEsperas[diaDaSemana].getFilaEspera().isEmpty()) {
					System.out.println(
							"Cliente: " + this.consultasEEsperas[diaDaSemana].getFilaEspera().front().getNome());
					System.out.println(
							"Hor�rio: " + this.consultasEEsperas[diaDaSemana].getFilaEspera().front().getHorario());
					System.out
							.println("Tipo: " + this.consultasEEsperas[diaDaSemana].getFilaEspera().front().getTipo());
					filaArmazenamento.enqueue(this.consultasEEsperas[diaDaSemana].getFilaEspera().dequeue());
				}
				while (!filaArmazenamento.isEmpty()) {
					this.consultasEEsperas[diaDaSemana].getFilaEspera().enqueue(filaArmazenamento.dequeue());
				}
			}
		}
	}

	private void mostrarAgendaParaDiaEspecifico(int diaDaSemana) {
		for (int j = 0; j < this.consultasEEsperas[diaDaSemana].getListaConsultas().numElements; j++) {
			System.out.println("Cliente: " + this.consultasEEsperas[diaDaSemana].getListaConsultas().get(j).getNome());
			System.out
					.println("Hor�rio: " + this.consultasEEsperas[diaDaSemana].getListaConsultas().get(j).getHorario());
		}
	}

	public void marcarConsulta(int diaDaSemana, int horario, String nome, int tipo, int tolerancia) {
		Consulta consulta = new Consulta(nome, horario, tipo, tolerancia);
		if (validarConsulta(consulta)) {
			SinglyLinkedList<Consulta> listaConsultas = this.consultasEEsperas[diaDaSemana].getListaConsultas();
			StaticQueue<Consulta> filaEspera = this.consultasEEsperas[diaDaSemana].getFilaEspera();
			if (listaConsultas.numElements == horariosValidos.length) {
				System.out.println("Não há mais consultas disponíveis nesse dia.");
				if (filaEspera.isFull()) {
					System.out.println("Todos horários estão ocupados.");
				} else {
					filaEspera.enqueue(consulta);
					System.out.println("Você foi movido para a fila de espera.");
				}
			}

			else if (listaConsultas.isEmpty()) {
				listaConsultas.insertFirst(consulta);
				incrementarNumeroConsultas(this.consultasEEsperas[diaDaSemana]);
			}

			else {
				int index = horarioDisponivel(diaDaSemana, horario);
				if (index != -1) {
					listaConsultas.insert(consulta, index);
					incrementarNumeroConsultas(this.consultasEEsperas[diaDaSemana]);
				} else {
					System.out.println("Esse horário não está disponível.");
					if (tolerancia == 0) {
						if (filaEspera.isFull()) {
							System.out.println("Todos horários estão ocupados.");
						} else {
							filaEspera.enqueue(consulta);
							System.out.println("Você foi movido para a fila de espera.");
						}

					}

					else if (tolerancia == 1) {
						int indiceHorarioAnt = horarioAnteriorDisponivel(diaDaSemana, horario);

						if (indiceHorarioAnt != -1) {
							int horaAnterior = getHorarioAnterior(horario);
							consulta.setHorario(horaAnterior);
							listaConsultas.insert(consulta, indiceHorarioAnt);
							incrementarNumeroConsultas(this.consultasEEsperas[diaDaSemana]);
							System.out.println("Consulta marcada para " + horaAnterior + " pela regra de tolerância.");
							return;
						}

						int indiceHorarioSeguinte = horarioPosteriorDisponivel(diaDaSemana, horario);

						if (indiceHorarioSeguinte != -1) {
							int horaSeguinte = getHorarioSeguinte(horario);
							consulta.setHorario(horaSeguinte);
							listaConsultas.insert(consulta, indiceHorarioSeguinte);
							incrementarNumeroConsultas(this.consultasEEsperas[diaDaSemana]);
							System.out.println("Consulta marcada para " + horaSeguinte + " pela regra de tolerância.");
						}

						else {
							if (filaEspera.isFull()) {
								System.out.println("Todos horários estão ocupados.");
							} else {
								filaEspera.enqueue(consulta);
								System.out.println("Você foi movido para a fila de espera.");
							}
						}
					}

					else {
						int indiceHorarioDisponivel = verificaHorarioDisponivelNaLista(diaDaSemana);

						if (indiceHorarioDisponivel != -1) {
							int horaDisponivel = getHorarioDisponivel(diaDaSemana);
							consulta.setHorario(horaDisponivel);
							listaConsultas.insert(consulta, indiceHorarioDisponivel);
							incrementarNumeroConsultas(this.consultasEEsperas[diaDaSemana]);
							System.out
									.println("Consulta marcada para " + horaDisponivel + " pela regra de tolerância.");
						} else {
							if (filaEspera.isFull()) {
								System.out.println("Todos horários estão ocupados.");
							} else {
								filaEspera.enqueue(consulta);
								System.out.println("Você foi movido para a fila de espera.");
							}
						}
					}
				}
			}
		}
	}

	public void desmarcarConsulta(int diaDaSemana, int horario) {
		if (horarioValido(horario)) {
			if (horarioDisponivel(diaDaSemana, horario) == -1) {
				excluirConsultaDaLista(diaDaSemana, horario);
				retirarDaFilaEsperaEInserirNaListaDeConsultas(diaDaSemana, horario);
			} else {
				System.out.println("Não há consultas marcadas nesse horário");
			}
		} else {
			System.out.println("Não há consultas nesse horário.");
		}
	}

	public Consulta getConsulta(int diaDaSemana, int horario) {
		if (horarioValido(horario)) {
			Node<Consulta> current = this.consultasEEsperas[diaDaSemana].getListaConsultas().head;
			while (current != null) {
				if (current.getElement().getHorario() == horario) {
					return current.getElement();
				}
				current = current.getNext();
			}
		}
		return null;
	}

	public void mostrarConsultaNoIntervalo(int dia1, int dia2, int hora1, int hora2){
		if(!horarioValido(hora1) || !horarioValido(hora2)){
			System.out.println("Os horários estão inválidos.");
			return;
		}
		else if(dia1 > dia2){
			System.out.println("O primeiro dia deve ser menor que o segundo.");
			return;
		}
		else if((dia1 < 0 || dia1 > 4) || (dia2 < 0 || dia2 > 4)){
			System.out.println("O intervalo de dias é do número 0 (Segunda-feira) ao 4 (Sexta-feira).");
			return;
		}
		
		for (int i = dia1; i <= dia2; i++) {
			System.out.println("Dia " + i + "\nConsultas");
			Node<Consulta> current = this.consultasEEsperas[i].getListaConsultas().head;
			while (current != null) {
				if(i == dia1 && i == dia2){
					if(current.getElement().getHorario() >= hora1 && current.getElement().getHorario() <= hora2)
						System.out.println(current.getElement());
				}
				else if(i == dia1){
					if(current.getElement().getHorario() >= hora1)
						System.out.println(current.getElement());
				}
				else if(i == dia2){
					if(current.getElement().getHorario() <= hora2)
						System.out.println(current.getElement());
				}else{
					System.out.println(current.getElement());
				}
				current = current.getNext();

				System.out.println("------------------------------------------------------");
			}
			System.out.println("======================================================");
			
		}
	}
	
	private void incrementarNumeroConsultas(CadaDia dia){
		dia.setQuantidadeConsultas(dia.getQuantidadeConsultas() + 1);
	}
	
	
	private void decrementarNumeroConsultas(CadaDia dia){
		dia.setQuantidadeConsultas(dia.getQuantidadeConsultas() - 1);
	}
	
	private boolean validarConsulta(Consulta c) {
		if (c.getHorario() < 100 || c.getHorario() > 9999) {
			System.out.println("Formato da hora inválido.");
			return false;
		} else if (!horarioValido(c.getHorario())) {
			return false;
		} else if (c.getTipo() < 1 || c.getTipo() > 2) {
			System.out.println("Tipo inválido.");
			return false;
		} else if (c.getTolerancia() < 0 || c.getTolerancia() > 2) {
			System.out.println("Tolerância inválida.");
			return false;
		}
		return true;
	}

	private void excluirConsultaDaLista(int diaDaSemana, int horario) {
		Node<Consulta> current = this.consultasEEsperas[diaDaSemana].getListaConsultas().head;
		int index = 0;
		while (current != null) {
			if (current.getElement().getHorario() == horario) {
				decrementarNumeroConsultas(this.consultasEEsperas[diaDaSemana]);
				this.consultasEEsperas[diaDaSemana].getListaConsultas().remove(index);
				return;
			}
			index++;
			current = current.getNext();
		}
	}

	private Consulta retirarDaFilaEsperaEInserirNaListaDeConsultas(int diaDaSemana, int horario) {
		StaticQueue<Consulta> fila = this.consultasEEsperas[diaDaSemana].getFilaEspera();
		StaticQueue<Consulta> filaAux = new StaticQueue<>(fila.numElements());

		Consulta c = null;

		boolean encontrou = false;
		while (!fila.isEmpty()) {
			if (fila.front().getHorario() == horario && encontrou == false) {
				c = this.consultasEEsperas[diaDaSemana].getFilaEspera().dequeue();
				encontrou = true;
			} else {
				filaAux.enqueue(fila.dequeue());
			}
		}

		while (!filaAux.isEmpty()) {
			this.consultasEEsperas[diaDaSemana].getFilaEspera().enqueue(filaAux.dequeue());
		}

		if (c != null) {
			int index = horarioDisponivel(diaDaSemana, horario);
			this.consultasEEsperas[diaDaSemana].getListaConsultas().insert(c, index);
			incrementarNumeroConsultas(this.consultasEEsperas[diaDaSemana]);
			System.out.println("A consulta de " + c.getNome() + " foi movida para a lista de consultas.");
		}

		return c;
	}

	private boolean horarioValido(int hora) {
		for (int i = 0; i < horariosValidos.length; i++) {
			if (hora == horariosValidos[i]) {
				return true;
			}
		}
		System.out.println("As consultas devem ser marcadas de 30 em 30 minutos entre 7:30 e 22:30.");
		return false;
	}

	private int horarioDisponivel(int diaDaSemana, int horario) {
		Node<Consulta> current = this.consultasEEsperas[diaDaSemana].getListaConsultas().head;
		int index = 0;
		while (current != null) {
			if (current.getElement().getHorario() == horario) {
				return -1;
			} else if (current.getElement().getHorario() > horario) {
				return index;
			}
			current = current.getNext();
			index++;
		}
		return index;
	}

	private int horarioAnteriorDisponivel(int diaDaSemana, int horario) {
		if (horario == 730) {
			return -1;
		}

		Node<Consulta> current = this.consultasEEsperas[diaDaSemana].getListaConsultas().head;
		int index = 0;
		while (current.getNext() != null) {
			if (current.getNext().getElement().getHorario() == horario) {
				if (current.getElement().getHorario() != getHorarioAnterior(horario)) {
					return index + 1;
				} else {
					return -1;
				}
			}
			index++;
			current = current.getNext();
		}
		return -1;
	}

	private int horarioPosteriorDisponivel(int diaDaSemana, int horario) {
		if (horario == 1030) {
			return -1;
		}

		Node<Consulta> current = this.consultasEEsperas[diaDaSemana].getListaConsultas().head;
		int index = 0;
		while (current != null) {
			if (current.getNext() == null || (current.getNext().getElement().getHorario() != horario
					&& current.getNext().getElement().getHorario() > horario)) {
				return index + 1;
			}
			index++;
			current = current.getNext();
		}
		return -1;
	}

	private int verificaHorarioDisponivelNaLista(int diaDaSemana) {
		Node<Consulta> current = this.consultasEEsperas[diaDaSemana].getListaConsultas().head;
		int index = 0;
		while (current != null) {
			int hora = current.getElement().getHorario();
			if (horarioDisponivel(diaDaSemana, getHorarioSeguinte(hora)) != -1) {
				return index + 1;
			}
			index++;
			current = current.getNext();
		}
		return -1;
	}

	private int getHorarioDisponivel(int diaDaSemana) {
		Node<Consulta> current = this.consultasEEsperas[diaDaSemana].getListaConsultas().head;
		while (current != null) {
			int hora = current.getElement().getHorario();
			int horaSeguinte = getHorarioSeguinte(hora);
			if (horarioDisponivel(diaDaSemana, getHorarioSeguinte(hora)) != -1) {
				return horaSeguinte;
			}
			current = current.getNext();
		}
		return -1;
	}

	private int getHorarioSeguinte(int horario) {
		String horarioStr = String.valueOf(horario);
		String min = horarioStr.substring(horarioStr.length() - 2);
		if (min.equals("30")) {
			return horario + 70;
		} else {
			return horario + 30;
		}
	}

	private int getHorarioAnterior(int horario) {
		String horarioStr = String.valueOf(horario);
		String min = horarioStr.substring(horarioStr.length() - 2);
		if (min.equals("30")) {
			return horario - 30;
		} else {
			return horario - 70;
		}
	}

}
