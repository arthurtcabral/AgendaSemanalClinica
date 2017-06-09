
public class Consulta {

	private String nome;
	private int horario;
	private int tipo; //1- convï¿½nio; 2 - particular.
	private int tolerancia; // 0- 1- 2-;

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

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setHorario(int horario) {
		this.horario = horario;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public void setTolerancia(int tolerancia) {
		this.tolerancia = tolerancia;
	}

	@Override
	public String toString() {
		return "Nome: " + nome + "\nHorário: " + horario + "\nTipo: " + tipo + "\nTolerância: " + tolerancia;
	}

}
