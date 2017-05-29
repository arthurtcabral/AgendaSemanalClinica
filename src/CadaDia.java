
public class CadaDia {

	private int quantidadeConsultas;
	private SinglyLinkedList<Consulta> listaConsultas;
	private StaticQueue<Consulta> filaEspera;

	//Construtor utilizado quando se vai criar uma nova agenda.
	public CadaDia() {
		this.quantidadeConsultas = 0;
		this.listaConsultas = new SinglyLinkedList<Consulta>();
		this.filaEspera = new StaticQueue<Consulta>(7);
	}
	
	//Construtor utilizado para ler do arquivo.
	public CadaDia(int quantidadeConsultas,
			SinglyLinkedList<Consulta> listaConsultas,
			StaticQueue<Consulta> filaEspera) {
		this.quantidadeConsultas = quantidadeConsultas;
		this.listaConsultas = listaConsultas;
		this.filaEspera = filaEspera;
	}



	public int getQuantidadeConsultas() {
		return quantidadeConsultas;
	}

	public SinglyLinkedList<Consulta> getListaConsultas() {
		return listaConsultas;
	}

	public StaticQueue<Consulta> getFilaEspera() {
		return filaEspera;
	}
	
}
