package api;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("/hello")
@ApplicationPath("/test")

public class hello extends Application {
	
	//Route : http://localhost:8080/api/test/hello/corpusdata
	//Read JSON file from drive 'D' JSON FILE : test.json
	//Output JSON data on localhost 
	@GET
	@Path("/corpusdata")
	@Produces(MediaType.APPLICATION_JSON)
	public testdata[] getdata() {

		  JSONParser parser = new JSONParser();

		  testdata objd[] = new testdata[11] ;	        
		  
		  try {

	            Object obj = parser.parse(new FileReader("D:\\test.json"));

	            JSONArray jsonObject = (JSONArray) obj;
	            
	            
	            // loop array
	            Iterator<JSONObject> iterator = jsonObject.iterator();
	            int i = 0;

	            while (iterator.hasNext()) {
	            	
	            	
	                JSONObject o = iterator.next();
	                
	          objd[i]=new testdata(o.get("id").toString(),(String) o.get("name"),(String)o.get("address"));
	               
	          i++;
	                
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		
	
		return objd;
	
		
	}
	
	//Route : http://localhost:8080/api/test/hello/corpusdata
	//Request Type : Post
	//Accept JSON Data : {"address":"g-8/4","name":"Hassan","id":"1234"}
	// *IF THE POSTED JSON OBJECT EXISTS IT WILL UPDATE THE FILE OTHERWISE WILL APPEND THE POSTED JSON OBJECT*
	//Output ok if successful otherwise throws EXCEPTIONS 
	
	@POST
	@Path("/corpusupdate")
	@Produces({MediaType.TEXT_PLAIN})
	@Consumes({MediaType.APPLICATION_JSON})
	public String postdata(testdata d) {
		
		
		boolean is_update=false;
		JSONArray a = new JSONArray();
	
		  JSONParser parser = new JSONParser();

		  testdata objd[] = new testdata[10] ;	        
		  
		  try {

	            Object obj = parser.parse(new FileReader("D:\\test.json"));

	            JSONArray jsonObject = (JSONArray) obj;
	            
	            
	            // loop array
	            Iterator<JSONObject> iterator = jsonObject.iterator();
	            int i = 0;

	            while (iterator.hasNext()) {
	            	
	            	
	            	
	                JSONObject o = iterator.next();
	                if(d.getId().equals(o.get("id").toString())) {
	                	is_update=true;
	                	JSONObject newo=new JSONObject();
	                			newo.put("id", d.getId());
	                			newo.put("name", d.getName());
	                			newo.put("address",d.getAddress());
	                			a.add(newo);
	                }
	                else {
	                	 a.add(o);
	                }
	              
	          i++;
	                
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		
		
		
		
		
		  try (FileWriter file = new FileWriter("D:\\test.json")) {
			  	
			  JSONObject obj = new JSONObject();
			  if(!is_update) {
		        obj.put("id",  d.getId());
		        obj.put("name", d.getName());
		        obj.put("address",d.getAddress());
		        a.add(obj);
	            file.write(a.toJSONString());
	            file.flush();
			  }
			  else {
				  file.write(a.toJSONString());
		          file.flush();
			  }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		 return "ok";
		
	}
}
