/**
 *
 */
package businesscomponents;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
//import qc.rest.examples.infrastructure.Response;
import org.openqa.selenium.remote.Response;

/**
 * This class keeps the state of the connection for the examples. This class is
 * a singleton, and all examples get the instance in their default constructors
 * - thus sharing state (cookies, server url).
 *
 * Some simple methods are implemented to get commonly used paths.
 *
 */
public class RestConnector {
	private static Logger log = Logger.getLogger("tracer");

	public RestConnector init() {
		log.trace("Entering RestConnector");
		return this;
	}

	private RestConnector() {
	}

	private static RestConnector instance = new RestConnector();

	public static RestConnector getInstance() {
		log.trace("Entering RestConnector instance");
		return instance;

	}

	public Response httpGet(String url, String queryString,
			Map<String, String> headers) throws Exception {
		return doHttp("GET", url, queryString, headers);
	}

	/**
	 * @param type
	 *            of the http operation: get post put delete
	 * @param url
	 *            to work on
	 * @param queryString
	 * @param data
	 *            to write, if a writable operation
	 * @param headers
	 *            to use in the request
	 * @param cookies
	 *            to use in the request and update from the response
	 * @return http response
	 * @throws Exception
	 */
	private Response doHttp(String type, String url, String queryString,
			Map<String, String> headers) throws Exception {

		if ((queryString != null) && !queryString.isEmpty()) {
			queryString = queryString.replaceAll("\\s", "%20");
			url += "?" + queryString;
		}

		System.out.println("URL in do HTTP is " + url + " and type is:" + type);
		HttpURLConnection con = (HttpURLConnection) new URL(url)
				.openConnection();
		con.setRequestMethod(type);

		prepareHttpRequest(con, headers);
		con.connect();
		Response ret = retrieveHtmlResponse(con);

		return ret;
	}

	/**
	 * @param con
	 *            to set the headers and bytes in
	 * @param headers
	 *            to use in the request, such as content-type
	 * @param bytes
	 *            the actual data to post in the connection.
	 * @param cookiestring
	 *            the cookies data from clientside, such as lwsso, qcsession,
	 *            jsession etc..
	 * @throws IOException
	 */
	private void prepareHttpRequest(HttpURLConnection con,
			Map<String, String> headers) throws IOException {
		String contentType = null;

		// Send data from headers.
		if (headers != null) {
			// Skip the content-type header. The content-type header should only
			// be sent if you are sending content. See below.
			contentType = headers.remove("Content-Type");
			Iterator<Entry<String, String>> headersIterator = headers
					.entrySet().iterator();
			while (headersIterator.hasNext()) {
				Entry<String, String> header = headersIterator.next();
				con.setRequestProperty(header.getKey(), header.getValue());
			}
		}

	}

	/**
	 * @param con
	 *            that already connected to it's url with an http request, and
	 *            that should contain a response for us to retrieve
	 * @return a response from the server to the previously submitted http
	 *         request
	 * @throws Exception
	 */
	private Response retrieveHtmlResponse(HttpURLConnection con)
			throws Exception {
		Response ret = new Response();
		//ret.setStatusCode(con.getResponseCode());
	//ret.setResponseHeaders(con.getHeaderFields());
		InputStream inputStream;
		// Select the source of the input bytes, first try "regular" input
		try {
			inputStream = con.getInputStream();
		}
		/*
		 * If the connection to the server failed, for example 404 or 500,
		 * con.getInputStream() throws an exception, which is saved. The body of
		 * the exception page is stored in the response data.
		 */
		catch (Exception e) {
			inputStream = con.getErrorStream();
			//ret.setFailure(e);
		}
		// This takes the data from the previously decided stream (error or
		// input) and stores it in a byte[] inside the response.
		ByteArrayOutputStream container = new ByteArrayOutputStream();
		byte[] buf = new byte[2048]; //1024
		int read;
		while ((read = inputStream.read(buf, 0, 2048)) > 0) {
			container.write(buf, 0, read);
		}
		//ret.setResponseData(container.toByteArray());
		return ret;
	}

}
