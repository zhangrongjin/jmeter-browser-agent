package com.platon.agent.unit;

import java.math.BigDecimal;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

public class Test_transfer extends AbstractJavaSamplerClient {
	
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789","","请求地址");
		params.addArgument("fromPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("toPrivateKey", "0xda8e68e664b8cfb6cdf1a4609eea0452d717bc7f1a48b52bb5b94453877ee8bb");
		params.addArgument("amount", "5000");
		params.addArgument("chainId", "100");
		return params;
	}
	
	private Web3j web3j;
	private Credentials toCredentials;
	private Credentials fromCredentials;
	
	public void setupTest(JavaSamplerContext arg) {
		web3j = Web3j.build(new HttpService(arg.getParameter("url")));
		fromCredentials = Credentials.create(arg.getParameter("fromPrivateKey"));
		toCredentials = Credentials.create(arg.getParameter("toPrivateKey"));
    }
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		String result = null;
		try {
			Transfer.sendFunds(web3j, fromCredentials, arg0.getParameter("chainId"), 
					toCredentials.getAddress(),new BigDecimal(arg0.getParameter("amount")), Unit.LAT).send();
			result = "stakingCredentials balance=" + 
					web3j.platonGetBalance(toCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
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
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("fromPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("toPrivateKey", "0xda8e68e664b8cfb6cdf1a4609eea0452d717bc7f1a48b52bb5b94453877ee8bb");
		params.addArgument("amount", "5000");
		params.addArgument("chainId", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		Test_transfer test = new Test_transfer();
		test.setupTest(arg0);
		test.runTest(arg0);
	}
	 

}
