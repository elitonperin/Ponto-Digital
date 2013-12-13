package tb.eliton.telas;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import tb.eliton.model.Empregado;
import tb.eliton.model.GPSTracker;
import tb.eliton.model.Patrao;
import tb.eliton.testepd.R;
import tb.eliton.util.HttpHelper;
import tb.eliton.util.LogHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomePatrao extends Activity {
	/* Variáveis */
	private ListView listaEmpregados;
	final private Context contexto = this;
	protected Empregado emp = new Empregado();
	private Patrao patrao = new Patrao();
	GPSTracker gps;
	LogHelper logHelper;
	HttpHelper httpHelper;
	public boolean queroResposta = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_patrao);
		Intent i = getIntent();
		Bundle opts = i.getExtras();
		
		if(opts != null){
			TextView texto = (TextView) findViewById(R.id.textHomePatrao);
			patrao.setNome(opts.getString("nome"));
			patrao.setLogin(opts.getString("login"));
			texto.setText("Seja bem-vindo, " + patrao.getNome());
			
			Log.i("tom", "Começando...");
			
			buscaDadosEmpregado();
			
		}
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		Log.i("tom", "onStar - homepatrao");
		
		listaEmpregados = ( ListView ) findViewById( R.id.lista_empregados);
		listaEmpregados.setAdapter( new ListaEmpregadosView(contexto, R.layout.linha_empregado, patrao.getEmpregados() ) );
		

	
		// Click event for single list row
		listaEmpregados.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Empregado o = (Empregado) parent.getItemAtPosition(position);  
				Toast.makeText(HomePatrao.this, o.getNome(), Toast.LENGTH_SHORT).show();
				Bundle bd = new Bundle();
				bd.putString("empregado", o.getLogin());
				Intent i = new Intent(contexto, ViewEmpregado.class);
				startActivity(i);
			}
		});
		
		Button botsaida = (Button) findViewById(R.id.botaoVoltaHPatrao);
		botsaida.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Log.i("tom", "Saindo do homepatrao...");
			}
		});
		
		Button botaoAdd = (Button) findViewById(R.id.botaoAddEmpregado);
		botaoAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addEmpregado();
				Log.i("tom", "Adiciona empregado...");
			}
		});
	}

	@Override
	protected void onPause(){
		super.onPause();
		Log.i("tom","onPause - homepatrao");
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		Log.i("tom","onResume - homepatrao");
	}

	@Override
	protected void onRestart(){
		super.onRestart();
		Log.i("tom","onResume - homepatrao");
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		
		Log.i("tom","onStop - homepatrao");
	}
	
	@Override
	protected void onDestroy(){
		//addOnServer();
		super.onDestroy();	
		Log.i("tom","onDestroy - homepatrao");
	}
	
	public void buscaDadosEmpregado(){
		queroResposta = true;
		LoadDadosASYNC task = new LoadDadosASYNC();
		
		String params = "http://"+ HttpHelper.IP +"/GitHub/PontoDigital/public/patrao-mobile/"
		+ patrao.getLogin() + "?findAll=" + patrao.getLogin() ;
		
		// "http://gradweb.facom.ufms.br/~eliton_perin/topicosjsons/loginE.json"; // facom
		task.execute(params);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_patrao, menu);
		Log.i("tommenu", "Menu pressionado - homepatrao");
		
		return true;
	}
	
	// @SuppressWarnings("null")
	public boolean onOptionsItemSelected(MenuItem menu){
		if(menu.getItemId() == R.id.configuracao){
			obterPosicao();
		}
		return true;
	}
	
	public void obterPosicao(){
		Log.i("tom", "passo 1");

		gps = new GPSTracker(HomePatrao.this); 
		
		if(gps.possoObterConexao){
			Log.i("tom", "passo 2");
			patrao.getCasa().setLongitude(gps.getLongitude());

			Log.i("tom", "passo 3");
			patrao.getCasa().setLatitude(gps.getLatitude());

			Log.i("tom", "passo 4");
			queroResposta = false;
			LoadDadosASYNC task = new LoadDadosASYNC();
			String url = "http://"+ HttpHelper.IP +"/GitHub/PontoDigital/public/patrao-mobile/"+ patrao.getLogin() + 
					"?long="+ patrao.getCasa().getLongitude() +	"&lat="+patrao.getCasa().getLatitude();
			/*
			 * "http://localhost/json?gps=1&long="+ patrao.getCasa().getLongitude() +"&lat="+ patrao.getCasa().getLatitude()
			 */
			task.execute(url);
			
			Toast.makeText(contexto, "Lat: " + patrao.getCasa().getLatitude() + "\nLong: " + patrao.getCasa().getLongitude() , Toast.LENGTH_LONG).show();
			Log.i("tom", "Obtive Location");
		}else{
			Toast.makeText(contexto, "Verifique sua conexão e GPS e tente novamente" , Toast.LENGTH_LONG).show();
		}
		
		Log.i("tom", "Entrou aqui e saiu!");
	}

	private void addEmpregado() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alerta = new AlertDialog.Builder(contexto);
		
		alerta.setTitle("Cadastrar empregado");
		LinearLayout ly = new LinearLayout(contexto);
		final EditText login = new EditText(contexto);
		final TextView tlogin = new TextView(contexto);
		tlogin.setText("Login do funcionário: ");
		ly.addView(tlogin);
		ly.addView(login);
		login.setWidth(BIND_ADJUST_WITH_ACTIVITY);
		alerta.setView(ly);		
        
        alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	
            	LoadDadosASYNC task = new LoadDadosASYNC();
            	queroResposta = false;
            	
            	String params = "http://"+ HttpHelper.IP +"/GitHub/PontoDigital/public/empregado-mobile/"
            			+ login.getText() + "?patrao=" + patrao.getLogin();
            	
            	task.execute(params);
            	
            	patrao.getEmpregados().clear();
            	
            	buscaDadosEmpregado();
            	
                listaEmpregados.setAdapter( new ListaEmpregadosView(contexto, R.layout.linha_empregado, patrao.getEmpregados() ));
                Toast.makeText(contexto, "Logo será anexado a sua lista, se existir o empregado referente a esse login", Toast.LENGTH_LONG).show();
            }
        });
 
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Cancelado.
            }
        });
        alerta.show();
	}
	
	
	
	/* Classe */
	protected class LoadDadosASYNC extends AsyncTask<String, Void, String> {				

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			listaEmpregados.setAdapter( new ListaEmpregadosView(contexto, R.layout.linha_empregado, patrao.getEmpregados() ) );
			
			Log.i("tom","OnPostExecute - HomePatrao");

			// Bundle options = new Bundle();
		}		
		
		@Override
		protected String doInBackground(String... urls) {
           
        	Log.i("tom", "doInBackground foi acionado - HomePatrao");
            
        	
	            try {
						String log = getStringResponseFrom (urls[0]) ;
						Log.i("tom", log);
						if(queroResposta){			
							JSONArray json = new JSONArray(log);				
							int tam = json.length();
							for(int i = 0; i < tam; i++){
								Empregado empr = new Empregado();
								
								Log.i("tom", "json empregado:" + json.getJSONObject(i).toString());
								
								empr.setLogin(json.getJSONObject(i).getString("login"));
								empr.setNome(json.getJSONObject(i).getString("nome"));
								empr.setFuncao(json.getJSONObject(i).getString("cargo"));
								empr.setNumero(json.getJSONObject(i).getString("telefone"));			
								patrao.getEmpregados().add(empr);
							}
						}
		
					return log;
	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("tomerro indoBackground - HomePatrao", e.toString());
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
				Log.i("tom", "Entrou no getStringResponseFrom - HomePatrao");
				
				logHelper.debug(response);
				
				return responseString;
			}
			catch (JSONException e)
			{
				Log.e("tomjerro", e.toString() );
				throw new Exception (
						"Could not interpret the server response", e);
			}
		}

	}

}
