/**
 * 
 */
package tb.eliton.model;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Eliton
 *
 */
public class GPSTracker extends Service implements LocationListener{
	private Context contextoGPS = null;
	
	// Flag de status do GPS 
	boolean GPSAtivado = false;
	// flag de conexão
	boolean NetworkAtivada = false;

	public boolean possoObterConexao = false;

	Location localizacao;
	double latitude;
	double longitude;
	
	
	public double getLatitude() {
		if(localizacao != null){
			latitude = localizacao.getLatitude();
		}
		return latitude;
	}

	public double getLongitude() {
		if(localizacao != null){
			longitude = localizacao.getLongitude();
		}
		return longitude;
	}

	
	public boolean PossoObterConexao() {
		return possoObterConexao;
	}




	// Define o minimo de distancia para atualizações.
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 metros
	
	// Define o minimo de tempo para atualizações.
	private static final long MIN_TIME_BW_UPDATES = 1000*60*1; // 1 minuto
	
	// Controlador de localização
	protected LocationManager controleLocal;
	
	public GPSTracker(Context contexto){
		this.contextoGPS = contexto;
		getLocation();
		Log.i("tom","Inicio do GPS");
	}
	
	private Location getLocation() {
		// TODO Auto-generated method stub
		try{
			controleLocal = (LocationManager) contextoGPS.getSystemService(LOCATION_SERVICE);
			
			GPSAtivado = controleLocal.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			NetworkAtivada = controleLocal.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if(!GPSAtivado){
				Log.i("tom","GPS desativado");
			}else if(!NetworkAtivada){
				Log.i("tom", "GPS sem net");
			}else{	
				 this.possoObterConexao = true;
				 Log.i("tom", "Posso te usar!!!");
				 if (NetworkAtivada) {
	                 controleLocal.requestLocationUpdates(
	                         LocationManager.NETWORK_PROVIDER,
	                         MIN_TIME_BW_UPDATES,
	                         MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                 Log.d("Network", "Network");
	                 if (controleLocal != null) {
	                     localizacao = controleLocal.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	                     if (localizacao != null) {
	                         latitude = localizacao.getLatitude();
	                         longitude = localizacao.getLongitude();
	                     }
	                 }
	             }
	             // se GPSAtivado obetr lat/long usando os serviços do GPS
	             if (GPSAtivado) {
	                 if (localizacao == null) {
	                     controleLocal.requestLocationUpdates(
	                             LocationManager.GPS_PROVIDER,
	                             MIN_TIME_BW_UPDATES,
	                             MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                     Log.d("GPS Enabled", "GPS Enabled");
	                     if (controleLocal != null) {
	                         localizacao = controleLocal.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	                         if (localizacao != null) {
	                             latitude = localizacao.getLatitude();
	                             longitude = localizacao.getLongitude();
	                         }
	                     }
	                 }
	             }
	         }
			
		}catch(Exception e){
			e.getStackTrace();
		}
		
		return localizacao;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the contextoGPS
	 */
	public Context getContextoGPS() {
		return contextoGPS;
	}
	
	public void pararServicosGPS(){
		if(localizacao != null){
			controleLocal.removeUpdates(GPSTracker.this);
		}
	}

}
