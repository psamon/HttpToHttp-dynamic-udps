/**
 * 
 */
package com.anz.flow.test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.anz.HttpToHttp.transform.pojo.NumbersInput;
import com.anz.HttpToHttp.transform.pojo.Result;
import com.anz.common.dataaccess.models.iib.Operation;
import com.anz.common.test.FlowTest;
import com.anz.common.transform.TransformUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ibm.broker.config.proxy.AttributeConstants;
import com.ibm.broker.config.proxy.ConfigManagerProxyLoggedException;
import com.ibm.broker.config.proxy.ConfigManagerProxyPropertyNotInitializedException;
import com.ibm.broker.config.proxy.MessageFlowProxy;
import com.ibm.broker.config.proxy.RecordedTestData;

/**
 * @author sanketsw
 * 
 */
public class HttpToHttpPostFlowTest extends FlowTest {

	private static final Logger logger = LogManager.getLogger();
	
	private Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();

	private static final String TEST_FILE_001 = "HttpToHttp.Test001.xml";
	private static final String applicationName = "HttpToHttp-app";
	private static final String flowName = "Main";
	private static final String injectNodeName ="HTTP Input";
	private static final String MESSAGE_FORMAT = "PostMessageFormat.xml";
	
	@Override
	@Before
	public void setup()
			throws ConfigManagerProxyPropertyNotInitializedException,
			ConfigManagerProxyLoggedException, IOException {
		super.setup();	
		
		logger.info("integrationServerProxy: {}", getIntegrationServerProxy());
		MessageFlowProxy flowProxy = getIntegrationServerProxy().getMessageFlowByName(flowName, applicationName, null);
		logger.info("flowProxy: {}", flowProxy);
		setFlowProxy(flowProxy);
	}
	
	public void injectData() throws IOException, ConfigManagerProxyPropertyNotInitializedException, ConfigManagerProxyLoggedException {
		
		logger.info("injecting data...");
		// load test data from file
		String message = IOUtils.toString(HttpToHttpPostFlowTest.class.getResourceAsStream(TEST_FILE_001));
		String jsonBlob = TransformUtils.getBlob(message);
		String messageFormat = IOUtils.toString(HttpToHttpPostFlowTest.class.getResourceAsStream(MESSAGE_FORMAT));
		message = messageFormat.replace("MESSAGE_FORMAT", jsonBlob);
		logger.info("Injecting message: \n {}", message);
		
		Properties injectProps = new Properties();
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_APPLICATION_LABEL, applicationName); 		
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_MESSAGEFLOW_LABEL, flowName); 			
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_NODE_UUID, getNodeUUID(injectNodeName));
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_WAIT_TIME, "60000");
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_MESSAGE_SECTION, message);
		
		// execute flow in sychronous mode
		@SuppressWarnings("unused")
		boolean result = getIntegrationServerProxy().injectTestData(injectProps, true);
		
	}
	
	@Test
	public void testMainFlow() throws ConfigManagerProxyPropertyNotInitializedException, ConfigManagerProxyLoggedException, IOException, XPathExpressionException, SAXException, ParserConfigurationException, TransformerException, JSONException {
		
		injectData();
		testPostResult();

	}
	

	private void testPostResult() throws ConfigManagerProxyPropertyNotInitializedException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {

		List<RecordedTestData> dataList = getTestDataList("Post");
		
		String json = getNodeOutputJsonStringFromBlob(dataList.get(0));
		Result out = gson.fromJson(json, Result.class);
		
		
		assertEquals("IIB REST API implementation", out.getImeplementation());
		assertEquals("209", out.getResult());
		
	}

}
