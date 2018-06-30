import java.util.ArrayList;

public class EscolhaCaminho {
	private String nomeProcesso;
	private ArrayList<String> arrayAcoes;
	private String nomeSubprocesso;
		
	/**
	 * @param nomeProcesso
	 * @param arrayAcoes
	 * @param nomeSubprocesso
	 */
	public EscolhaCaminho(String nomeProcesso, ArrayList<String> arrayAcoes, String nomeSubprocesso) {
		this.nomeProcesso = nomeProcesso;
		this.arrayAcoes = new ArrayList<String>(arrayAcoes);
		this.nomeSubprocesso = nomeSubprocesso;
	}

	/**
	 * @return the nomeProcesso
	 */
	public String getNomeProcesso() {
		return nomeProcesso;
	}

	/**
	 * @param nomeProcesso the nomeProcesso to set
	 */
	public void setNomeProcesso(String nomeProcesso) {
		this.nomeProcesso = nomeProcesso;
	}

	/**
	 * @return the arrayAcoes
	 */
	public ArrayList<String> getArrayAcoes() {
		return arrayAcoes;
	}

	/**
	 * @param arrayAcoes the arrayAcoes to set
	 */
	public void setArrayAcoes(ArrayList<String> arrayAcoes) {
		this.arrayAcoes = arrayAcoes;
	}

	/**
	 * @return the nomeSubprocesso
	 */
	public String getNomeSubprocesso() {
		return nomeSubprocesso;
	}

	/**
	 * @param nomeSubprocesso the nomeSubprocesso to set
	 */
	public void setNomeSubprocesso(String nomeSubprocesso) {
		this.nomeSubprocesso = nomeSubprocesso;
	}
}

