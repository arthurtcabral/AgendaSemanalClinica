
public class AgendaSemanal {

	private CadaDia [] consultasEEsperasDoDia;
	private int mes;
	private int semana;
	
	//Construtor utilizado quando se vai criar uma nova agenda.
	public AgendaSemanal(int mes, int semana){
		this.consultasEEsperasDoDia = new CadaDia[5];
		this.mes = mes;
		this.semana = semana;
	}
		
	//Construtor utilizado para ler do arquivo.
	public AgendaSemanal(CadaDia[] consultasEEsperasDoDia, int mes, int semana) {
		this.consultasEEsperasDoDia = consultasEEsperasDoDia;
		this.mes = mes;
		this.semana = semana;
	}

	public CadaDia[] getConsultasEEsperasDoDia() {
		return consultasEEsperasDoDia;
	}
	public int getMes() {
		return mes;
	}
	public int getSemana() {
		return semana;
	}
	
}
