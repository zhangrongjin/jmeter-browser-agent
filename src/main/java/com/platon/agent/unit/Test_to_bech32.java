package com.platon.agent.unit;


import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.platon.sdk.utlis.Bech32;
import com.platon.sdk.utlis.NetworkParameters;

/**
 * 钱包地址转换
 * @author Rongjin Zhang
 *
 */
public class Test_to_bech32 extends AbstractJavaSamplerClient {
	
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("address", "0x60ceca9c1290ee56b98d4e160ef0453f7c40d219");
		return params;
	}
	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		String result = null;
		try {
			String testAddr = Bech32.addressEncode(NetworkParameters.TestNetParams.getHrp(),arg0.getParameter("address"));
			String mainAddr = Bech32.addressEncode(NetworkParameters.MainNetParams.getHrp(),arg0.getParameter("address"));
			result = "testAddr:" + testAddr + ";" + "mainAddr:"+ mainAddr; 
			sr.setSuccessful(true);
		} catch (Exception e) {
			result = e.getMessage();
			sr.setSuccessful(false);
		}
		sr.setResponseData(result, null);
		sr.setDataType(SampleResult.TEXT);
		sr.sampleEnd();
		return sr;
	}
	
	
	public static void main(String[] args) {
		Arguments params = new Arguments();
		params.addArgument("url", "D:\\");
		params.addArgument("password", "88888888");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		Test_to_bech32 test = new Test_to_bech32();
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 

}
