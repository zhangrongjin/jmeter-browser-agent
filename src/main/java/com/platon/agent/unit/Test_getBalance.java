package com.platon.agent.unit;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.alibaba.fastjson.JSONObject;
import com.platon.agent.check.RestrictingBalance;
import com.platon.agent.check.SpecialApi;

public class Test_getBalance extends AbstractJavaSamplerClient {
	
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("addresses", "0x1000000000000000000000000000000000000001");
		return params;
	}
	
	private Web3j web3j;
	
	public void setupTest(JavaSamplerContext arg) {
		try {
			web3j = Web3j.build(new HttpService(arg.getParameter("url")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		SpecialApi specialContractApi = new SpecialApi();
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		String result = null;
		try {
			List<RestrictingBalance> restrictingBalances = specialContractApi.getRestrictingBalance(web3j, arg0.getParameter("addresses"));
			result = JSONObject.toJSONString(restrictingBalances);
			
			BigInteger balance = web3j.platonGetBalance(arg0.getParameter("addresses"), DefaultBlockParameterName.LATEST).send().getBalance();
			BigDecimal pdamount = Convert.fromVon(balance.toString(), Unit.LAT);
			result += ";查询balance接口：" +pdamount.toString();
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
		params.addArgument("addresses", "0x1000000000000000000000000000000000000001");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		Test_getBalance test = new Test_getBalance();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 

}
