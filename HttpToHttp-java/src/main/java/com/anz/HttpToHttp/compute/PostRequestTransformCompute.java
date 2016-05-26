/**
 * 
 */
package com.anz.HttpToHttp.compute;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.HttpToHttp.transform.TransformBLSample;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.impl.CommonBlobTransformCompute;
import com.anz.common.compute.impl.ComputeUtils;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 *
 */
public class PostRequestTransformCompute extends CommonBlobTransformCompute {
	
	private static final Logger logger = LogManager.getLogger();

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJsonJsonTransformCompute#getTransformer()
	 */
	@Override
	public ITransformer<String, String> getTransformer() {
		return new TransformBLSample();
	}

	@Override
	public void prepareForTransformation(ComputeInfo metadata,
			MbMessageAssembly inAssembly, MbMessageAssembly outAssembly) {
		
		// Set HTTP Method and URL to local environment
		try {
			ComputeUtils.setElementInTree("POST", outAssembly.getLocalEnvironment() ,"Destination", "HTTP", "RequestLine", "Method");
			ComputeUtils.setElementInTree(getUserDefinedAttribute("HTTP_POST_URL"), outAssembly.getLocalEnvironment() ,"Destination", "HTTP", "RequestURL");
			ComputeUtils.setElementInTree("application/json", outAssembly.getMessage() ,"Properties", "ContentType");
		} catch (MbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
