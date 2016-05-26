/**
 * 
 */
package com.anz.HttpToHttp.transform;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.compute.ComputeInfo;
import com.anz.common.transform.ITransformer;


/**
 * @author sanketsw
 * 
 */
public class PreTransformBLSample implements ITransformer<String, String> {
	
	private static final Logger logger = LogManager.getLogger();

	
	/* (non-Javadoc)
	 * @see com.anz.common.transform.IJsonJsonTransformer#execute(java.lang.String)
	 */
	public String execute(String inputJson, Logger appLogger, ComputeInfo metadata) throws Exception {
		
		logger.info("{}: Request: {}", this.getClass().getName(), inputJson);
		
		//NumbersInput json = (NumbersInput) TransformUtils.fromJSON(inputJson,
		//		NumbersInput.class);
		
		
		
		//String out = TransformUtils.toJSON(json);
		return inputJson;
	}


}
