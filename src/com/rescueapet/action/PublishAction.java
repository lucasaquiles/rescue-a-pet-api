package com.rescueapet.action;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rescueapet.model.Publish;
import com.rescueapet.persistence.EMF;

public class PublishAction implements Actionable {

	@Override
	public Object execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String strTitle = request.getParameter("title");
		String strContent = request.getParameter("content");
		String strLat = request.getParameter("lat");
		String strLng = request.getParameter("lng");
		
		Publish publish = new Publish();
		publish.setTitle(strTitle);
		publish.setContent(strContent);
		publish.setLat(Double.parseDouble(strLat));
		publish.setLng(Double.parseDouble(strLng));
		
		EntityManager em = EMF.get().createEntityManager();
		em.getTransaction().begin();
		em.persist(publish);
		em.getTransaction().commit();
		
		return publish;
	}
}


