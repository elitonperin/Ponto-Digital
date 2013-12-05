/**
 * 
 */
package tb.eliton.telas;

import java.util.List;

import tb.eliton.model.Tarefas;
import tb.eliton.testepd.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;

/**
 * @author Eliton
 *
 */
public class ListaTarefasView extends ArrayAdapter<Tarefas>{
	int recursos;
	private LayoutInflater inflater;
	
	@SuppressWarnings("unused")
	private Context contexto;
	
	public ListaTarefasView(Context context, int resource, List<Tarefas> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		recursos = resource;
		inflater = LayoutInflater.from(context);
		contexto = context;
	}

	@Override
	public View getView(int posicao, View conversorView, ViewGroup pai){
		conversorView = (RelativeLayout) inflater.inflate(recursos, null);
		
		Tarefas tarefa = getItem(posicao);
		CheckedTextView nome = (CheckedTextView) conversorView.findViewById(R.id.tarefaEmpregadoTexto);
		nome.setText(tarefa.getNome());

		CheckBox checkbox = (CheckBox) conversorView.findViewById(R.id.tarefaEmpregadoCheckBox);
		checkbox.setChecked(tarefa.isFeita());
		
		
		return conversorView;
	}
	
}
