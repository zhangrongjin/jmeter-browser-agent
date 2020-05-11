package com.platon.agent.unit;

import java.math.BigDecimal;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

public abstract class TestBase extends AbstractJavaSamplerClient {
	
	
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
			Transfer.sendFunds(web3j, fromCredentials, Long.valueOf(arg0.getParameter("chainId")), 
					toCredentials.getAddress(Long.valueOf(arg0.getParameter("chainId"))),new BigDecimal(arg0.getParameter("amount")), Unit.LAT).send();
			result = "stakingCredentials balance=" + 
					web3j.platonGetBalance(toCredentials.getAddress(Long.valueOf(arg0.getParameter("chainId"))), DefaultBlockParameterName.LATEST).send().getBalance();
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

	public abstract SampleResult set();  
	
}
