package tb.eliton.telas;

import java.util.Date;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tb.eliton.model.Empregado;
import tb.eliton.model.Local;
import tb.eliton.model.Tarefas;
import tb.eliton.model.GPSTracker;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeEmpregado extends Activity {
	private Empregado empregado;
	private ListView listaTarefas;
	private Context contexto = this;
	private GPSTracker gps;
	public double latitude;
	public double longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_home_empregado);
		empregado = new Empregado();
		TextView texto = (TextView) findViewById(R.id.textHomeEmpregado);

		Intent i = getIntent();
		Bundle opts = i.getExtras();
		empregado.setPatrao(opts.getString("patrao"));
		buscaCasaPatrao();

		empregado.setNome(opts.getString("nome"));
		Log.i("tom", empregado.getPatrao());
		texto.setText("Bem vindo, " + empregado.getNome());
		
		empregado.getTarefas().add(new Tarefas("Campo Grande", true));
		empregado.getTarefas().add(new Tarefas("Sidrolândia", false));
		empregado.getTarefas().add(new Tarefas("Maracaju", false));
		empregado.getTarefas().add(new Tarefas("Dourados", false));
		
		listaTarefas = (ListView) findViewById(R.id.lista_tarefas);
		listaTarefas.setAdapter(new ListaTarefasView(contexto,
				R.layout.linha_tarefa, empregado.getTarefas()));
	}
	@Override
	protected void onStart(){	
		super.onStart();		
		Log.i("tom", "onStar - homeempregado");
		
		Button botsaida = (Button) findViewById(R.id.sairEmpregado);
		botsaida.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Log.i("tom", "Saindo do homeempregado...");
			}
		});
		
		Button botaoAdd = (Button) findViewById(R.id.marcaPontoEmpregado);
		botaoAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gps = new GPSTracker(contexto);
				
				if(gps.PossoObterConexao()){
					if(true){
						
						Date data = new Date();

						empregado.getLocalAtual().setLatitude(gps.getLatitude());
						empregado.getLocalAtual().setLongitude(gps.getLongitude());
						
						Local casaPatrao = new Local(latitude, longitude);
						Log.i("tom", "hora da comparação");

						Log.i("tom","e: lat "+empregado.getLocalAtual().getLatitude());
						Log.i("tom","e: lon "+empregado.getLocalAtual().getLongitude());
						Log.i("tom","e: lat "+ casaPatrao.getLatitude());
						Log.i("tom","e: lon "+ casaPatrao.getLongitude());
						
						if(casaPatrao.distaceOf(empregado.getLocalAtual()) < 0.1){
						
							empregado.getPonto().add(data);
							
							
							Toast.makeText(contexto, "Ponto Realizado "+empregado.getPonto().get
									(empregado.getPonto().size()-1).toString() ,
									Toast.LENGTH_LONG).show();
						}
					}
				}else{
					Toast.makeText(contexto, "Verifique sua conexão e GPS e tente novamente" , Toast.LENGTH_LONG).show();
				}
				
				
			    Log.i("tom", "Marca Ponto...");
			}
		});
	}

	protected void buscaCasaPatrao() {
		// TODO Auto-generated method stub
		LoadDadosASYNC task = new LoadDadosASYNC();
		
		String url = "http://"+ HttpHelper.IP + "/GitHub/PontoDigital/public/patrao-rest/"+empregado.getPatrao();
		
		task.execute(url);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		Log.i("tom","onPause - homeempregado");
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(!empregado.getPonto().isEmpty())
			Toast.makeText(contexto, "Ultimo Ponto Realizado "+ 
		empregado.getPonto().get(empregado.getPonto().size()-1).toString() ,
		Toast.LENGTH_LONG).show();
		
		Log.i("tom","onResume - homeempregado");
	}

	@Override
	protected void onRestart(){
		super.onRestart();
		Log.i("tom","onResume - homeempregado");
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		Log.i("tom","onStop - homeempregado");
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();		
		Log.i("tom","onDestroy - homeempregado");
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.home_empregado, menu);
		Log.i("tommenu", "Menu pressionado - homeempregado");
		return true;
	}
	
	protected class LoadDadosASYNC extends AsyncTask<String, Void, String> {				

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			
			Log.i("tom","OnPostExecute - HomeEmpregado");

			// Bundle options = new Bundle();
		}		
		
		@Override
		protected String doInBackground(String... urls) {
           
        	Log.i("tom", "doInBackground foi acionado - HomePatrao");
            
        	
	            try {
						String log = getStringResponseFrom (urls[0]) ;
						Log.i("tom", log);
				
							JSONArray json = new JSONArray(log);
							JSONObject patrao = json.getJSONObject(0);
							latitude = patrao.getDouble("latitude");
							longitude = patrao.getDouble("longitude");
		
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
