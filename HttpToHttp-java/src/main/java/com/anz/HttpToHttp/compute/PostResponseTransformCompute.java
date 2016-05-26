/**
 * 
 */
package com.anz.HttpToHttp.compute;

import com.anz.HttpToHttp.transform.TransformBLSampleWithCache;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.impl.CommonBlobTransformCompute;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 *
 */
public class PostResponseTransformCompute extends CommonBlobTransformCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJsonJsonTransformCompute#getTransformer()
	 */
	@Override
	public ITransformer<String, String> getTransformer() {
		return new TransformBLSampleWithCache();
	}




}
