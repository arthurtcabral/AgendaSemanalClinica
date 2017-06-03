import java.io.File;

public class AgendaUtil {
	
	
	public AgendaSemanal criarAgenda(int mes, int semana){
		CadaDia[] dias = new CadaDia[5];
		for(int i=0; i<5; i++){
			dias[i] = new CadaDia();
		}
		AgendaSemanal agendaSemanal = new AgendaSemanal(dias, mes, semana);
		
		File arquivoAgenda = new File("2017"+mes+""+semana+".txt");
		
		return agendaSemanal;
	}
	
	public AgendaSemanal carregarAgenda(int mes, int semana){
		return null;
	}
	
	
}
