package com.platon.agent.unit;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;

public class Test_transfer extends BaseSampler {

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		String result = null;
		try {
			Transfer transfer = new Transfer(web3j, transactionManager);
			transfer.sendFunds(arg0.getParameter("toAddress"), new BigDecimal(arg0.getParameter("amount")), Unit.LAT
					,new BigInteger(arg0.getParameter("gasPrice")),new BigInteger(arg0.getParameter("gasLimit"))).send();
			result = "stakingCredentials balance=" + 
					web3j.platonGetBalance(arg0.getParameter("toAddress"), DefaultBlockParameterName.LATEST).send().getBalance();
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
		params.addArgument("toAddress", "0xcd76b1456a814c20b5ddcd28707c0b7a2a3240bb");
		params.addArgument("amount", "10");
		params.addArgument("chainId", "100");
		params.addArgument("gasPrice", "1000000000");
		params.addArgument("gasLimit", "4700000");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		Test_transfer test = new Test_transfer();
		test.setupTest(arg0);
		test.runTest(arg0);
	}

	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.OTHER;
	}

	@Override
	public void setArguments(Arguments params) {
		params.addArgument("toAddress", "0xcd76b1456a814c20b5ddcd28707c0b7a2a3240bb");
		params.addArgument("amount", "5000");
	}
	 

}
