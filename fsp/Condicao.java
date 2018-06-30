public class Condicao {
	private String operandoE;
	private String operandoD;
	private String comparador;
	
	/**
	 * @param operandoE
	 * @param operandoD
	 * @param comparador
	 */
	public Condicao() {
		this.operandoE = null;
		this.operandoD = null;
		this.comparador = null;
	}

	/**
	 * @return the operandoE
	 */
	public String getOperandoE() {
		return operandoE;
	}

	/**
	 * @param operandoE the operandoE to set
	 */
	public void setOperandoE(String operandoE) {
		this.operandoE = operandoE;
	}

	/**
	 * @return the operandoD
	 */
	public String getOperandoD() {
		return operandoD;
	}

	/**
	 * @param operandoD the operandoD to set
	 */
	public void setOperandoD(String operandoD) {
		this.operandoD = operandoD;
	}

	/**
	 * @return the comparador
	 */
	public String getComparador() {
		return comparador;
	}

	/**
	 * @param comparador the comparador to set
	 */
	public void setComparador(String comparador) {
		this.comparador = comparador;
	}	
}
