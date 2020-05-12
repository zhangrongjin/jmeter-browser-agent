package com.platon.agent.unit.staking;

import java.math.BigInteger;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.alibaba.fastjson.JSONObject;
import com.platon.agent.base.BaseSampler;
import com.platon.agent.base.EpochInfo;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.agent.check.SpecialApi;

/**
 * 查询历史待提取收益
 * @author Rongjin Zhang
 *
 */
public class StakingContractTest_historyReward extends BaseSampler {

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

	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.STAKING_CONTRACT;
	}

	@Override
	public void setArguments(Arguments params) {
		params.addArgument("blockNumber", "0");
	}
	 

}
