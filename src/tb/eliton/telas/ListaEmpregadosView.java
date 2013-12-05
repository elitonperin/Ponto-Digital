/**
 *  
 */
package tb.eliton.telas;

import java.util.List;

import tb.eliton.model.Empregado;
import tb.eliton.testepd.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Eliton
 *
 */
public class ListaEmpregadosView extends ArrayAdapter<Empregado>{
	private int recursos;
	private LayoutInflater inflater;
	@SuppressWarnings("unused")
	private Context contexto;
	

	public ListaEmpregadosView(Context context, int resource, List<Empregado> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		recursos = resource;
		inflater = LayoutInflater.from(context);
		contexto = context;
	}
	
	@Override
	public View getView(int posicao, View conversorView, ViewGroup pai){
		conversorView = (RelativeLayout) inflater.inflate(recursos, null);
		
		Empregado empregado = getItem(posicao);
		TextView nome = (TextView) conversorView.findViewById(R.id.nomeEmpregado);
		nome.setText(empregado.getNome());

		TextView telefone = (TextView) conversorView.findViewById(R.id.telefoneEmpregado);
		telefone.setText(empregado.getNumero());

		TextView funcao = (TextView) conversorView.findViewById(R.id.cargoEmpregado);
		funcao.setText(empregado.getFuncao());
		
		return conversorView;
	}
	
}