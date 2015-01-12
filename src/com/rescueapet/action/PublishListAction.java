package com.rescueapet.action;

import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rescueapet.model.Publish;
import com.rescueapet.persistence.EMF;

public class PublishListAction implements Actionable{

	@Override
	public Object execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("select p from Publish p ");
		
		Query query = EMF.get().createEntityManager().createQuery(stringBuilder.toString());
		
		List<Publish> list = query.getResultList();
		
		return list;
	}

}
