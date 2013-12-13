package tb.eliton.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpHelper
{
	private LogHelper logHelper;
	static public String IP = "gradweb.facom.ufms.br/~eliton_perin";
	
	/*
	 * String url = "http://gradweb.facom.ufms.br/~eliton_perin/topicosjsons/loginP.json"; // facom estável
	 * String url = "http://10.6.71.95/jsons/loginP.json"; //facom
	 * String url = "http://10.6.75.59/jsons/loginP.json"; // facom
	 * String url = "http://10.0.0.5/jsons/loginP.json"; //biblio
	 * 10.6.48.235
	 * 10.0.105.67
	 * 10.6.55.247
	 * 10.6.52.18
	 * 192.168.0.11
	 * String url = "http://10.0.109.122/jsons/loginP.json"; // firma
	 * String url = "http://192.168.0.11/jsons/loginP.json"; // casa 
	 * String url = "http://192.168.0.11/jsons/index.php?"; // casa usando servidor php
	*/
	
	public HttpHelper () {
		logHelper = new LogHelper (this.getClass());
	}

	public HttpResponse doHttpGet (String uri) throws Exception
	{
		try
		{
			HttpClient http = new DefaultHttpClient ();

			HttpGet request = new HttpGet (uri);
			
			logHelper.debug(request);
			
			HttpResponse response = http.execute (request);
			
			
			logHelper.debug(response);
			
			return response;
		}
		catch (ClientProtocolException e)
		{
			throw new Exception ("Problemas na conexão com o servidor!", e);
		}
		catch (IOException e)
		{
			throw new Exception ("Sem rede de dados! Impossível atualizar imformações do aplicativo", e);
		}
	}
	
	public String getResponseContentString (HttpResponse response) throws Exception
	{
		try
		{
			String conversionResult =  EntityUtils.toString (response.getEntity ());
			
			logHelper.debug ("Obtendo conteudo da HTTP response: " + conversionResult);
			
			return conversionResult;
		}
		catch (ParseException e)
		{
			throw new Exception ("Falha na interpretação na resposta do servidor!", e);
		}
		catch (IOException e)
		{
			throw new Exception ("Falha na interpretação na resposta do servidor!", e);
		}
	}

	
}
