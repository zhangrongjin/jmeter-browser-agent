package com.platon.agent.unit;


import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

public class Test_amountkey extends AbstractJavaSamplerClient {
	
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "D:\\UTC--2019-08-29T13-44-59.945000000Z--2e95e3ce0a54951eb9a99152a6d5827872dfb4fd.json");
		params.addArgument("password", "88888888");
		return params;
	}
	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		String result = null;
		try {
			Credentials credentials = WalletUtils.loadCredentials(arg0.getParameter("password"), arg0.getParameter("url"));
			byte[] byteArray = credentials.getEcKeyPair().getPrivateKey().toByteArray();
			result = Hex.toHexString(byteArray);
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
		params.addArgument("url", "D:\\UTC--2019-08-29T13-44-59.945000000Z--2e95e3ce0a54951eb9a99152a6d5827872dfb4fd.json");
		params.addArgument("password", "88888888");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		Test_amountkey test = new Test_amountkey();
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 

}
