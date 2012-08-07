package org.tophat.android.networking;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.tophat.android.exceptions.HttpException;
import org.tophat.android.exceptions.NoInternetConnection;
import org.tophat.android.networking.requests.TopHatDelete;
import org.tophat.android.networking.requests.TopHatGet;
import org.tophat.android.networking.requests.TopHatPost;
import org.tophat.android.networking.requests.TopHatPut;

public class TopHatHttpClient 
{

	/**
	 * The APICommunicator is abstraction level above this class, providing exception handling and other items.
	 */
	protected ApiCommunicator apic;
	
	protected String host;
	protected String username;
	protected String password;
	protected HttpClient http;
	protected HttpGet httpGet;
	protected HttpPut httpPut;
	protected HttpDelete httpDelete;
	protected HttpPost httppost;
	protected ResponseProcessor rp;
	
	/**
	 * 
	 * @param apic The apicommunicator for the application in question
	 */
	public TopHatHttpClient(ApiCommunicator apic)
	{
		this.apic = apic;
	}
	
	/**
	 * Prepares for a connection to the JSON API.
	 */
	public void connect() {

		host = apic.getConstants().API_URL;
		
		HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
	     
		http = new DefaultHttpClient();
	
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
		
		HttpProtocolParams.setUserAgent( http.getParams(), apic.getConstants().USER_AGENT + " "+ apic.getConstants().APP_VERSION );
	}

	/**
	 * Requests a HTTP get
	 * @param req
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws HttpException 
	 * @throws ParseException 
	 */
	public String get(String url) throws ParseException, HttpException
	{
		try
		{
			return new TopHatGet(http, this.host+url).execute();
		} 
		catch (ClientProtocolException e) 
		{
			throw new NoInternetConnection();
		} 
		catch (IOException e) 
		{
			throw new NoInternetConnection();
		}
	}
	

	/**
	 * 
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws HttpException
	 */
	public String delete(String url) throws ParseException, HttpException
	{
		try
		{
			return new TopHatDelete(http, this.host+url).execute();
		} 
		catch (ClientProtocolException e) 
		{
			throw new NoInternetConnection();
		} 
		catch (IOException e) 
		{
			throw new NoInternetConnection();
		}
	}
	

	/**
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws ParseException
	 * @throws HttpException
	 */
	public String put(String url, String json) throws ParseException, HttpException
	{
		try
		{
			return new TopHatPut(http, this.host+url, json).execute();
		} 
		catch (ClientProtocolException e) 
		{
			throw new NoInternetConnection();
		} 
		catch (IOException e) 
		{
			throw new NoInternetConnection();
		}
	}
	
	/**
	 * Performs a Http Post request using the Apache Libraries
	 * @param url
	 * @param json
	 * @return
	 * @throws ParseException
	 * @throws HttpException
	 */
	public String post(String url, String json) throws ParseException, HttpException
	{
		try
		{
			return new TopHatPost(http, this.host+url, json).execute();
		} 
		catch (ClientProtocolException e) 
		{
			throw new NoInternetConnection();
		} 
		catch (IOException e) 
		{
			throw new NoInternetConnection();
		}
	}
}
