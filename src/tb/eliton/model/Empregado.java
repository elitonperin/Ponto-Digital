/**
 * 
 */
package tb.eliton.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Eliton
 *
 */
public class Empregado {
	private String nome;
	private String telefone;
	private String funcao;
	private String login;
	private String senha;
	private List<Tarefas> tarefas = new ArrayList<Tarefas>();
	private List<Date> ponto = new ArrayList<Date>();
	private String patrao;
	private Local localAtual = new Local();
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getPatrao() {
		return patrao;
	}
	public Empregado(String n, String tel, String func){
		nome = n;
		telefone = tel;
		funcao = func;
	}
	public Empregado() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the tarefas
	 */
	public List<Tarefas> getTarefas() {
		return tarefas;
	}
	/**
	 * @param tarefas the tarefas to set
	 */
	public void setTarefas(List<Tarefas> tarefas) {
		this.tarefas = tarefas;
	}
	/**
	 * @return the telefone
	 */
	public String getNumero() {
		return telefone;
	}
	/**
	 * @param numero the telefone to set
	 */
	public void setNumero(String numero) {
		this.telefone = numero;
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
	 * @return the funcao
	 */
	public String getFuncao() {
		return funcao;
	}
	/**
	 * @param funcao the funcao to set
	 */
	public void setFuncao(String funcao) {
		this.funcao = funcao;
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
	 * @return the ponto
	 */
	public List<Date> getPonto() {
		return ponto;
	}
	/**
	 * @param ponto the ponto to set
	 */
	public void setPonto(List<Date> ponto) {
		this.ponto = ponto;
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
	public void setPatrao(String string) {
		// TODO Auto-generated method stub
		this.patrao = string;
	}
	/**
	 * @return the localAtual
	 */
	public Local getLocalAtual() {
		return localAtual;
	}
	/**
	 * @param localAtual the localAtual to set
	 */
	public void setLocalAtual(Local localAtual) {
		this.localAtual = localAtual;
	}

}
