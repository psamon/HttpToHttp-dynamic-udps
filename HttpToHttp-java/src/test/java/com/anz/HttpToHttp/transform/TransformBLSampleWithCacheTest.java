/**
 * 
 */
package com.anz.HttpToHttp.transform;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.anz.common.compute.ComputeInfo;
import com.anz.common.dataaccess.daos.IOperationDao;
import com.anz.common.dataaccess.models.iib.Operation;
import com.anz.common.ioc.IIoCFactory;
import com.anz.common.ioc.spring.AnzSpringIoCFactory;

/**
 * @author sanketsw
 *
 */
public class TransformBLSampleWithCacheTest {
	
	private static Logger logger = LogManager.getLogger();
	
	private void createOperationInDatabase() throws Exception {
		
		IIoCFactory factory = AnzSpringIoCFactory.getInstance();
		IOperationDao operationDao = factory.getBean(IOperationDao.class);
		
		Operation operation2 = new Operation();
		operation2.setQualifier(Operation.ADD);
		operation2.setOperation("Add test operation");
		operation2.setImeplementation("IIB REST API implementation test");
		operationDao.saveAndFlush(operation2);
	}

	
	@Test
	public void testTransformResponseData() throws Exception {
		
		createOperationInDatabase();
		
		String in  = "{\"imeplementation\":\"Java_SpringBoot\",\"result\":\"35\"}";
		String expected = "{\"imeplementation\":\"IIB REST API implementation test\",  \"operation\": \"Add test operation\"}";
		String notExpected = "{\"imeplementation\":\"Java_SpringBoot\"}";
		
		String out =  new TransformBLSampleWithCache().execute(in, logger, new ComputeInfo());
		
		JSONObject json = new JSONObject(out);
		JSONAssert.assertEquals(expected, json, false);
		JSONAssert.assertNotEquals(notExpected, json, false);
	}
	
	@Test
	public void testInvalidResponseDataWithoutResultField() {
		String in  = "{\"imeplementation\":\"Java_SpringBoot\"}";
		Operation op = new Operation();	
		op.setOperation(Operation.ADD);
		op.setImeplementation("IIB REST API implementation");
		
		boolean exceptionThrown =  false;
		try {
			String out = new TransformBLSampleWithCache().execute(in, logger, new ComputeInfo());
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue(exceptionThrown);
		
	}

}
