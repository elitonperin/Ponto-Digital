/**
 * 
 */
package tb.eliton.model;

import android.util.Log;

/**
 * @author Eliton
 *
 */
public class Local {
	private double latitude;
	private double longitude;
	
	public Local(){
		this.latitude = 0.0;
		this.longitude = 0.0;		
	}
	
	/**
	 * @param latitude
	 * @param longitude
	 */
	public Local(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		Log.i("tom", "setLat");
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		Log.i("tom", "setLong");
		this.longitude = longitude;
	}
	
	public double distaceOf(Local local){
		return Math.sqrt((local.getLatitude()-latitude)*(local.getLatitude()-latitude) 
				+ (local.getLongitude()-longitude)*(local.getLongitude()-longitude));
	}
	
}
