package com.platon.agent.unit;


import com.platon.agent.base.BaseSimperSampler;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;

/**
 * 根据私钥创建钱包
 * @author Rongjin Zhang
 *
 */
public class Test_createwalletByPri extends BaseSimperSampler {
	
	@Override
	public void setArguments(Arguments params) {
		params.addArgument("url", "D:\\");
		params.addArgument("password", "88888888");
		params.addArgument("privateKey", "88888888");
	}

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		String result = null;
		try {
			if(this.chainType.equals(this.chainTypeP)) {
				Credentials credentials = Credentials.create(arg0.getParameter("privateKey"));
				result = WalletUtils.generatePlatONWalletFile(arg0.getParameter("password"), credentials.getEcKeyPair(), new File(arg0.getParameter("url")));
			} else {
				com.alaya.crypto.Credentials credentials = com.alaya.crypto.Credentials.create(arg0.getParameter("privateKey"));
				result = com.alaya.crypto.WalletUtils.generatePlatONWalletFile(arg0.getParameter("password"), credentials.getEcKeyPair(), new File(arg0.getParameter("url")));
			}
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
		Test_createwalletByPri test = new Test_createwalletByPri();
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 

}
