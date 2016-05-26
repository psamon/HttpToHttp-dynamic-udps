/**
 * 
 */
package com.anz.HttpToHttp.transform;

import junit.framework.TestCase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.anz.HttpToHttp.transform.TransformBLSample;
import com.anz.HttpToHttp.transform.pojo.NumbersInput;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.transform.TransformUtils;

/**
 * @author sanketsw
 *
 */
public class TransformBLSampleTest extends TestCase {
	
	private static Logger logger = LogManager.getLogger();

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testTransformRequestData() throws Exception {
		String in  = "{  \"left\": 0,  \"right\": 0 }";
		String expected = "{  \"right\": 100, \"left\": 100 }";
		String notExpected = "{  \"left\": 0,  \"right\": 0 }";
		
		String out =  new TransformBLSample().execute(in, logger, new ComputeInfo());
		
		JSONObject json = new JSONObject(out);
		JSONAssert.assertEquals(expected, json, false);
		JSONAssert.assertNotEquals(notExpected, json, false);
		
		NumbersInput outObj = TransformUtils.fromJSON(out, NumbersInput.class);
		assertEquals(100, outObj.getLeft());
	}
	
	
}
