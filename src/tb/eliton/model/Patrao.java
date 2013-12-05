/**
 * 
 */
package tb.eliton.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;



/**
 * @author Eliton
 *
 */
public class Patrao {
	private String nome;
	private String numero;
	private String login;
	private String senha;
	private List<Empregado> empregados = new ArrayList<Empregado>();
	private Local casa = new Local();
	
	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
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
	 * @return the empregados
	 */
	public List<Empregado> getEmpregados() {
		return empregados;
	}
	/**
	 * @param empregados the empregados to set
	 */
	public void setEmpregados(List<Empregado> empregados) {
		this.empregados = empregados;
	}
	/**
	 * @return the casa
	 */
	public Local getCasa() {
		return casa;
	}
	/**
	 * @param casa2 the casa to set
	 */
	public void setCasa(Local casa2) {
		Log.i("tom","setCarai");
		this.casa = casa2;
	}
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}
	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}