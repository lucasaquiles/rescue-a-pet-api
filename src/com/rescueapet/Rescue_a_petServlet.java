package com.rescueapet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rescueapet.action.Actionable;
import com.rescueapet.action.PublishAction;
import com.rescueapet.action.PublishListAction;

@SuppressWarnings("serial")
public class Rescue_a_petServlet extends HttpServlet {
	
	private static final Map<String, Actionable> SERVICES_BY_NAME = new HashMap<String, Actionable>();
	private static final String SERVICE_PARAMETER = "service";
	
	static {
	
		SERVICES_BY_NAME.put("publish",  new PublishAction());
		SERVICES_BY_NAME.put("publish_list",  new PublishListAction());
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String serviceName = req.getParameter(SERVICE_PARAMETER);
		
		ReturnMessage returnMessage = null;
		
		Object result = null;
		if (isValidServiceRequest(serviceName)) {

			
			Actionable action = getActionableByServiceName(serviceName);
			try {
				
				GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.enableComplexMapKeySerialization();
				gsonRegisterTypeAdapters( gsonBuilder );

				Gson gson = gsonBuilder.create();
			
				result =  action.execute(req, resp);
				
				String json =  gson.toJson( result );
				
				resp.setContentType( "application/json; charset=UTF-8" );
				resp.setCharacterEncoding("UTF-8");
				
				resp.addHeader( "Access-Control-Allow-Origin", "*" ); 
				resp.addHeader( "Access-Control-Allow-Methods", "POST" ); 
				
				resp.getWriter().print( json );
				resp.getWriter().flush();
			} catch (Exception e) {

				returnMessage = new ReturnMessage("error", e.getMessage());
			}
			
		}
	}
	private boolean isValidServiceRequest(String serviceName) {
		return serviceName != null && !serviceName.trim().isEmpty();
	}
	
	public class ReturnMessage {

		private String type;
		private Object object;

		public ReturnMessage(String type, Object object) {

			this.type = type;
			this.object = object;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}
	}
	
	private String parseToJson(Object returnMessage) {

		Gson gson = new Gson();
		return gson.toJson(returnMessage);
	}

	private Actionable getActionableByServiceName(String serviceName) {
		return SERVICES_BY_NAME.get(serviceName);
	}
	
	public void gsonRegisterTypeAdapters( GsonBuilder gsonBuilder ) {
	}
}
