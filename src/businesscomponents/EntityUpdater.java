package businesscomponents;

/**
*
*/
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.Response;

//import pages.HomeView_AccountAndDevicePage;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

import util.FrameworkException;

/*public class EntityUpdater {
	RestConnector con;
	
	public EntityUpdater(){
		this.con =  RestConnector.getInstance().init(
	                    );
	}*/
   public class EntityUpdater extends ReusableLibrary {
	RestConnector con;
	
	public EntityUpdater(ScriptHelper scriptHelper){
		super(scriptHelper);
		this.con =  RestConnector.getInstance().init();
	}	
	 String env = properties.getProperty("ENV");
	/**The Method is to Login to ALM via REST calls.
	 * Should be invoked to establish the connection/session, before calling any other method in the class to update the data in ALM.
	 * @throws Exception
	 */
public Response getRestResponse(String ApiName, String entityUrl, String updatedEntityXml) throws Exception {
	//System.out.println("In fetch:"+entityUrl);
	
	
	 String siteId = dataTable.getData(env, "SiteId");
     String transactionId = dataTable.getData(env, "TransactionId");
     String authorization = dataTable.getData(env, "Authorization");
     String contentType = dataTable.getData(env, "Content Type");
     String accept = dataTable.getData(env, "Accept");
     String xtrackingId = dataTable.getData(env, "x-trackingId");
    // Response putPV=null;
     
     
        Map<String, String> requestHeaders = new HashMap<String, String>();
     /*  String siteId = dataTable.getData(env, "SiteId");
       String transactionId = dataTable.getData(env, "TransactionId");
       String authorization = dataTable.getData(env, "Authorization");
       String contentType = dataTable.getData(env, "Content Type");
       String accept = dataTable.getData(env, "Accept");*/
        
        if(ApiName == "getSNMPWALK"){        	
        	requestHeaders.put("X-siteId", "001");	       
	        requestHeaders.put("Transaction-Id", "1234567");	        
	        requestHeaders.put("Content-Type", "application/json");
	        requestHeaders.put("Accept", "application/json");
	        requestHeaders.put("Authorization", "Basic ZmJjOkZiY0BTQTFM");
	        requestHeaders.put("X-Transaction-Id", "123456789");	              
        	}
        	    
        if(ApiName == "SNMPSetBulk" ){   
        	  requestHeaders.put("X-transactionId", "12334");	 
        	  requestHeaders.put("X-siteId", "001");	  
        	  requestHeaders.put("Content-Type", "application/json");
  	          requestHeaders.put("accept", "application/json");
  	          requestHeaders.put("Transaction-Id", "3242434324");	 
  	          requestHeaders.put("Authorization", "Basic aG9tZXZpZXc6SDBtM3ZpZXdTQTFM");
    	}   

        if(ApiName == "getNodeSummary" ){   
        	requestHeaders.put("Authorization", authorization);
	        requestHeaders.put("Transaction-Id",transactionId);
	        //requestHeaders.put("X-transactionId", "1234");
	        //requestHeaders.put("X-siteId", "007");
	        requestHeaders.put("SiteId", siteId);
	        requestHeaders.put("Content-Type",contentType);
	        requestHeaders.put("Accept", accept);
	        
    	}
        
        if(ApiName == "getDispositionAPIQA" ){   
        	requestHeaders.put("authorization", authorization);
	        requestHeaders.put("Accept", accept);
	        requestHeaders.put("x-trackingId", xtrackingId);
	        
	        
    	}
        
        if(ApiName == "getDispositionAPI" ){   
        	requestHeaders.put("Authorization", authorization);
	        requestHeaders.put("x-trackingId", xtrackingId);
	        
	        
    	}
		 Response put = con.httpGet(entityUrl,updatedEntityXml, requestHeaders);
	     System.out.println("put is"+put.toString());
        /*Response put = con.httpGet(entityUrl, 
        		updatedEntityXml, requestHeaders);
        System.out.println("put is"+put.toString());*/      
	        
        return put;
      }
}




