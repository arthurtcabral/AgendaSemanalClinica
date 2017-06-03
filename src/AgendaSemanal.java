public class AgendaSemanal {

	private CadaDia[] consultasEEsperas;
	private int mes;
	private int semana;

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
	
	public void exibirFilaDeEsperaParaDiaEspecifico(int diaDaSemana){
		if(this.consultasEEsperas[diaDaSemana] != null){
			if(this.consultasEEsperas[diaDaSemana].getFilaEspera() != null){
				StaticQueue<Consulta> filaArmazenamento = new StaticQueue<Consulta>(this.consultasEEsperas[diaDaSemana].getFilaEspera().numElements());
				while(!this.consultasEEsperas[diaDaSemana].getFilaEspera().isEmpty()){
					System.out.println("Cliente: " + this.consultasEEsperas[diaDaSemana].getFilaEspera().front().getNome());
					System.out.println("Hor�rio: " + this.consultasEEsperas[diaDaSemana].getFilaEspera().front().getHorario());
					System.out.println("Tipo: " + this.consultasEEsperas[diaDaSemana].getFilaEspera().front().getTipo());
					filaArmazenamento.enqueue(this.consultasEEsperas[diaDaSemana].getFilaEspera().dequeue());
				}
				while(!filaArmazenamento.isEmpty()){
					this.consultasEEsperas[diaDaSemana].getFilaEspera().enqueue(filaArmazenamento.dequeue());
				}
			}		
		}
	}

	private void mostrarAgendaParaDiaEspecifico(int diaDaSemana) {
		for(int j = 0; j < this.consultasEEsperas[diaDaSemana].getListaConsultas().numElements; j++){
			System.out.println("Cliente: " + this.consultasEEsperas[diaDaSemana].getListaConsultas().get(j).getNome());
			System.out.println("Hor�rio: " + this.consultasEEsperas[diaDaSemana].getListaConsultas().get(j).getHorario());
		}
	}

}
