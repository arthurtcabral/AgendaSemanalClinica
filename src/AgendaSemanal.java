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
					System.out.println("-- Ter�a-Feira --");
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
			System.out.println("Hor�rio: " + this.consultasEEsperas[diaDaSemana].getListaConsultas().get(j).getHorario());
		}
	}

	public void marcarConsulta(int diaDaSemana, int horario, String nome, int tipo, int tolerancia) {
		if (horarioValido(horario)) {
			Consulta consulta = new Consulta(nome, horario, tipo, tolerancia);

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
			}

			else {
				int index = horarioDisponivel(diaDaSemana, horario);
				if (index != -1) {
					listaConsultas.insert(consulta, index);
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
							System.out.println("Consulta marcada para " + horaAnterior + " pela regra de tolerância.");
							return;
						}

						int indiceHorarioSeguinte = horarioPosteriorDisponivel(diaDaSemana, horario);

						if (indiceHorarioSeguinte != -1) {
							int horaSeguinte = getHorarioSeguinte(horario);
							consulta.setHorario(horaSeguinte);
							listaConsultas.insert(consulta, indiceHorarioSeguinte);
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
		} else {
			System.out.println("Não há consultas nesse horário.");
		}
	}

	public void desmarcarConsulta(int diaDaSemana, int horario) {
		if (horarioValido(horario)) {
			if (horarioDisponivel(diaDaSemana, horario) == -1) {
				excluirConsultaDaLista(diaDaSemana, horario);
				
//				Consulta consultaDaEspera = retirarDaFilaEspera(diaDaSemana, horario);
//				
//				if(consultaDaEspera!=null){
//					int index = horarioDisponivel(diaDaSemana, horario);
//					this.consultasEEsperas[diaDaSemana].getListaConsultas().insert(consultaDaEspera, index);
//					System.out.println("Uma consulta da fila de espera foi movida para a lista de consultas.");
//				}
			} else {
				System.out.println("Não há consultas marcadas nesse horário");
			}
		} else {
			System.out.println("Não há consultas nesse horário.");
		}
	}

	private void excluirConsultaDaLista(int diaDaSemana, int horario){
		Node<Consulta> current = this.consultasEEsperas[diaDaSemana].getListaConsultas().head;
		int index = 0;
		while (current != null) {
			if(current.getElement().getHorario() == horario){
				this.consultasEEsperas[diaDaSemana].getListaConsultas().remove(index);
				return;
			}
			index++;
			current = current.getNext();
		}
	}
	
	private Consulta retirarDaFilaEspera(int diaDaSemana, int horario){
		StaticQueue<Consulta> filaAux = this.consultasEEsperas[diaDaSemana].getFilaEspera();
		int index = 0;
		while(!filaAux.isEmpty()){
			if(filaAux.front().getHorario() == horario){
				return this.consultasEEsperas[diaDaSemana].getFilaEspera().remove(index);
			}
			index++;
			filaAux.dequeue();
		}
		return null;
	}
	
	private boolean horarioValido(int hora) {
		for (int i = 0; i < horariosValidos.length; i++) {
			if (hora == horariosValidos[i]) {
				return true;
			}
		}
		return false;
	}

	private int horarioDisponivel(int diaDaSemana, int horario) {
		Node<Consulta> current = this.consultasEEsperas[diaDaSemana].getListaConsultas().head;
		int index = 0;
		while (current != null) {
			if (current.getElement().getHorario() == horario) {
				return -1;
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
