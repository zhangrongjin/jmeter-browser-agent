package com.platon.agent.unit;

import java.math.BigInteger;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.alibaba.fastjson.JSONObject;
import com.platon.agent.base.EpochInfo;
import com.platon.agent.check.SpecialApi;

public class StakingContractTest_historyReward extends AbstractJavaSamplerClient {

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("blockNumber", "0");
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

	/**
	 * 查询当前节点的质押信息 nodeId 被质押的节点的NodeId
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			String blockNumber = arg.getParameter("blockNumber");
			SpecialApi specialContractApi = new SpecialApi();
			EpochInfo epochInfo = specialContractApi.getEpochInfo(web3j, new BigInteger(blockNumber));
			if(epochInfo != null) {
				result = JSONObject.toJSONString(epochInfo);
				sr.setSuccessful(true);
			}
		} catch (Exception e) {
			result = e.toString();
			sr.setSuccessful(false);
		}
		sr.setResponseData(result, null);
		sr.setDataType(SampleResult.TEXT);
		sr.sampleEnd();
		return sr;
	}
	
	
	public static void main(String[] args) {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.172:8789");
		params.addArgument("blockNumber", "1600");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		StakingContractTest_historyReward test = new StakingContractTest_historyReward();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 

}
