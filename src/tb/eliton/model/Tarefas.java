/**
 * 
 */
package tb.eliton.model;

/**
 * @author Eliton
 *
 */
public class Tarefas {
	private String nome;
	private boolean feita;
	
	public Tarefas(String string, boolean b) {
		// TODO Auto-generated constructor stub
		nome = string;
		feita = b;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the feita
	 */
	public boolean isFeita() {
		return feita;
	}
	/**
	 * @param feita the feita to set
	 */
	public void setFeita(boolean feita) {
		this.feita = feita;
	}
	
	
	

}
