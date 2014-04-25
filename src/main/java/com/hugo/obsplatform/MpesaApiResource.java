package com.hugo.obsplatform;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


@Path("/tigo")
public class MpesaApiResource {
	
	static Properties prop = new Properties();
	public static final String OBS_BASE_DIR = System.getProperty("user.home") + File.separator + ".obs";
	
@POST
@Consumes({ MediaType.WILDCARD })
@Produces({ MediaType.APPLICATION_XML})
public String postTigoDetails(String jsonData) {
		
		try{
		prop.load(new FileInputStream(OBS_BASE_DIR+File.separator+"MpesaIntegrator.properties"));
		final String username =prop.getProperty("username").trim();
		final String password =prop.getProperty("password").trim();
		HttpClient httpClient = new DefaultHttpClient();
		httpClient = wrapClient(httpClient);
		System.out.println("------------" + jsonData.toString());

		StringEntity se = new StringEntity(jsonData.toString());

		HttpPost postRequest1 = new HttpPost(prop.getProperty("PostQuery").trim()+"?username="+username+"&password="+password);
		postRequest1.setEntity(se);

		HttpResponse response1 = httpClient.execute(postRequest1);
		
		if (response1.getStatusLine().getStatusCode() == 204) {
			
			return null;
		}
		else if (response1.getStatusLine().getStatusCode() != 200) {
			
			System.out.println("Failed : HTTP error code : "
					+ response1.getStatusLine().getStatusCode());
			return null;
		}
		BufferedReader br1 = new BufferedReader(new InputStreamReader(
				(response1.getEntity().getContent())));

		String output1;
		String output = "";
		while ((output1 = br1.readLine()) != null) {
			output=output.concat(output1);
		}
		XmlToJsonConvertion data=new XmlToJsonConvertion();
		String returnOutput=data.convertion(output);
		System.out.println("payment successfully completed ----> "+returnOutput);
		return returnOutput;
 
	}catch(IOException exception){
		exception.printStackTrace();
		return null;
	}catch(Exception exception){
		exception.printStackTrace();
		return null;
	}
		
	
	}
	public static HttpClient wrapClient(HttpClient base) {

		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				@SuppressWarnings("unused")
				public void checkClientTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				@SuppressWarnings("unused")
				public void checkServerTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub

				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
			return new DefaultHttpClient(ccm, base.getParams());
		} catch (Exception ex) {
			return null;
		}
	}
}