package tb.eliton.telas;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tb.eliton.testepd.R;
import tb.eliton.util.HttpHelper;
import tb.eliton.util.LogHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	/* Variáveis */
	Bundle options = new Bundle();
	final Context contexto = this;
	
	/*
	 * Usados na conexão do aplicativo com o servidor
	 */
	LogHelper logHelper;
	HttpHelper httpHelper;
	/*
	 * login, senha e nome do usuario, patrao ou empregado
	 */
	String login;
	String senha;
	String nome;
	String nomepatrao;
	
	
	/*
	 * Constante de controle.
	 */
	public final int SEM_ERRO = 0;
	public final int SENHA_INCORRETA = 1;
	public final int LOGIN_INVALIDO = 2;
	public final int PROBLEMA_DE_CONEXAO = 3;

	/*
	 * Flags de controle de sessão e conexão
	 */
	public boolean logadoL = false;
	public boolean isPatrao = false;
	private int cod = SEM_ERRO;	
	
	
	
	/* Funções sobrescritas */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Log.i("tom", "Criação da View, onCreate - login");
	}

	@Override
	protected void onPause(){
		super.onPause();
		Log.i("tom","onPause - login");
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		Log.i("tom","onResume - login");
	}

	@Override
	protected void onRestart(){
		super.onRestart();
		Log.i("tom","onResume - login");
		logadoL = false;
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		Log.i("tom","onStop - login");
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.i("tom","onDestroy - login");
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
	    // getMenuInflater().inflate(R.menu.login, menu);
		Log.i("tommenu", "Menu pressionado - login");
		return true;
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		
		/* Configurando os botões da Activity */
		Button botao = (Button) findViewById(R.id.buttonLogar);
		botao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox cbox = (CheckBox) findViewById(R.id.checkBox1);
				if(cbox.isChecked()){
						isPatrao = true;
				}else{
						isPatrao = false;
				}
				consomeDadosParaAutenticacao();
			}
		});
		Button botsaida = (Button) findViewById(R.id.buttonSair);
		botsaida.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Log.i("tom", "Saindo...");
			}
		});
		Log.i("tom","onStart - login");
	}
	
	
	/*	Funções criadas
	 *
	 *  Função responsável por pegar os valores da activity e chamar
	 *  a função que vai comparar com os dados do servidor
	 */
	public void consomeDadosParaAutenticacao(){
		EditText login = (EditText) findViewById(R.id.editTextLogin);
		EditText senha = (EditText) findViewById(R.id.editTextSenha);

		this.login = login.getText().toString();
		this.senha = senha.getText().toString();
		
		if(isPatrao){
			logarPatrao();
		}else{
			logarEmpregado();
		}
	}

	public void logarEmpregado() {
		LoadWebPageASYNC task = new LoadWebPageASYNC();
		
		String url = "http://"+ HttpHelper.IP +"/GitHub/PontoDigital/public/empregado-rest/"+ this.login;		

		task.execute(url);

		Log.i("tom","Cumpriu o exectue do loginEmpregado");
	}
	
	public void logarPatrao() {
		LoadWebPageASYNC task = new LoadWebPageASYNC();

		String url = "http://"+ HttpHelper.IP +"/GitHub/PontoDigital/public/patrao-rest/"+ this.login;

		task.execute(url);		
		
		
		Log.i("tom","Cumpriu o exectue do loginPatrao"); 
	}
	

	/*
	 *  Classe responsável por fazer a conexão com o seridor
	 *   
	 */
	private class LoadWebPageASYNC extends AsyncTask<String, Void, String> {		
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			Log.i("tom","OnPostExecute");
			options.putString("nome", nome);
			options.putString("login", login);
			
			if(isPatrao){
				if(logadoL){						
						Intent i = new Intent(contexto, HomePatrao.class);
						i.putExtras(options);
						startActivity(i);
						Log.i("tom", "patrao logado");
				}
			}else{
				if(logadoL){					
					Intent i = new Intent(contexto, HomeEmpregado.class);
					options.putString("patrao", nomepatrao);
					i.putExtras(options);
					startActivity(i);
					Log.i("tom", "empregado logado");
				}
			}
			switch(cod){
			   case PROBLEMA_DE_CONEXAO:
				   	Toast.makeText(contexto, "Sem conexão ou servidor não encontrado", Toast.LENGTH_LONG).show();
				   	break;
			   case SENHA_INCORRETA:
					Toast.makeText(contexto, "Senha incorreta", Toast.LENGTH_LONG).show();
				   break;
			   case LOGIN_INVALIDO:
					Toast.makeText(contexto, "Usuário invalido", Toast.LENGTH_LONG).show();
				   break;
			   default:
			}
			
		}		
		
		@Override
		protected String doInBackground(String... urls) {
           
        	Log.i("tom", "doInBackground foi acionado");
        	
            
            try {
				String log = getStringResponseFrom (urls[0]) ;
				Log.i("tom", log);
				
				JSONArray jsonArray = new JSONArray(log);
				Log.i("tomi", jsonArray.toString());

				JSONObject json =  jsonArray.getJSONObject(0);
				
				nome = json.getString("nome");
				String logincorreto = json.getString("login");
				String senhacorreta = json.getString("senha");
				
				if(isPatrao){
					
					if( login.compareTo(logincorreto) == 0){
						Log.i("tom", "login correto");
						if(senha.compareTo(senhacorreta) == 0){
							cod = SEM_ERRO;
							Log.i("tom", "senha correta");
							logadoL = true;
						}else{
							cod = SENHA_INCORRETA;
							Log.i("tom", "senha incorreta");
							logadoL = false;
						}
					}else{
						cod = LOGIN_INVALIDO;
					}
				}else{
					nomepatrao = json.getString("loginp");
					
					Log.i("tom","login " + login + " s: " + senha);
					
					if( login.compareTo(logincorreto) == 0){
						Log.i("tom", "login correto");
						if(senha.compareTo(senhacorreta) == 0){
							cod = SEM_ERRO;
							Log.i("tom", "senha correta");
							logadoL = true;
						}else{
							cod = SENHA_INCORRETA;
							Log.i("tom", "senha incorreta");
							logadoL = false;
						}
					}else{
						cod = LOGIN_INVALIDO;
					}
				}

				return log;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				cod = PROBLEMA_DE_CONEXAO;
				logadoL = false;
				e.printStackTrace();
				Log.e("tomerro indoBackground", e.toString());				
			}
			
			return null;
		}

		private String getStringResponseFrom (String uri) throws Exception
		{
			LogHelper logHelper = new LogHelper(this);
			HttpHelper httpHelper = new HttpHelper();
			
			try
			{
				HttpResponse response = httpHelper.doHttpGet (uri);

				String responseString = httpHelper.getResponseContentString (response);
				Log.i("tom", responseString);
				Log.i("tom", "Entrou no getStringResponseFrom");
				
				logHelper.debug(response);
				
				return responseString;
			}
			catch (JSONException e)
			{
				Log.e("tomjerro1", e.toString() );
				throw new Exception (
						"Could not interpret the server response", e);
			}
		}
	}	
}
