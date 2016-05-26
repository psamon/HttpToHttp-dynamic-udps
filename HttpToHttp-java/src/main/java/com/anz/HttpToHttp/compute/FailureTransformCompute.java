/**
 * 
 */
package com.anz.HttpToHttp.compute;

import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonErrorTransformCompute;

/**
 * @author root
 *
 */
public class FailureTransformCompute extends CommonErrorTransformCompute {

	public TransformType getTransformationType() {
		return TransformType.HTTP_HHTP;
	}



}
