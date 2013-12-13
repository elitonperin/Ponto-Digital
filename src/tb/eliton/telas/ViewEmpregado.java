package tb.eliton.telas;

import tb.eliton.model.Empregado;
import tb.eliton.model.Tarefas;
import tb.eliton.testepd.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ViewEmpregado extends Activity {
	
	Empregado empregado = new Empregado();
	private ListView listaTarefas;
	private Context contexto = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("tom", "onCreate - ViewEmpregado");
		setContentView(R.layout.activity_view_empregado);

		/*
		TextView inicio = (TextView) findViewById(R.id.texto_inicial_view_emp);
		TextView entrada = (TextView) findViewById(R.id.texto_inicial_view_emp);
		TextView saida = (TextView) findViewById(R.id.texto_inicial_view_emp);
		*/
		
		empregado.getTarefas().add(new Tarefas("Arrumar a .. na ...", true));
		empregado.getTarefas().add(new Tarefas("Limpar ...", false));
		empregado.getTarefas().add(new Tarefas("Levar ...", false));
		empregado.getTarefas().add(new Tarefas(" ... ", false));
		
		listaTarefas = (ListView) findViewById(R.id.lista_tarefas_patrao);
		listaTarefas.setAdapter(new ListaTarefasView(contexto,
				R.layout.linha_tarefa, empregado.getTarefas()));
		
		
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		Button botsaida = (Button) findViewById(R.id.volta_view_empregado);
		botsaida.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Log.i("tom", "Saindo do homeempregado...");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Log.i("tom", "onCreateOptionsMenu - ViewEmpregado");
		getMenuInflater().inflate(R.menu.view_empregado, menu);
		return true;
	}

}
