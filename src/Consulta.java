
public class Consulta {

	private String nome;
	private int horario;
	private int tipo; //1- convênio; 2 - particular.
	private int tolerancia;

	public Consulta(String nome, int horario, int tipo, int tolerancia) {
		this.nome = nome;
		this.horario = horario;
		this.tipo = tipo;
		this.tolerancia = tolerancia;
	}

	public String getNome() {
		return nome;
	}

	public int getHorario() {
		return horario;
	}

	public int getTipo() {
		return tipo;
	}

	public int getTolerancia() {
		return tolerancia;
	}

}
