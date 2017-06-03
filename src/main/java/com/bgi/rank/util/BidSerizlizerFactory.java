package com.bgi.rank.util;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;

public class BidSerizlizerFactory extends BeanSerializerFactory{

   public final static BidSerizlizerFactory instance = new BidSerizlizerFactory(null); 
	 
	private static final long serialVersionUID = 1L;
	
	private String filterId;
	

	protected BidSerizlizerFactory(SerializerFactoryConfig arg0) {
		super(arg0);
	}

	@Override
	protected Object findFilterId(SerializationConfig arg0, BeanDescription arg1) {
		return filterId;
	}

	public String getFilterId() {
		return filterId;
	}


	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}
	
	

}
