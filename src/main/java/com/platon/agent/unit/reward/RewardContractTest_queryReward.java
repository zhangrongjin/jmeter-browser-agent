package com.platon.agent.unit.reward;

import com.alibaba.fastjson.JSONObject;
import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.resp.Reward;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询待提取委托奖励
 * @author Rongjin Zhang
 *
 */
public class RewardContractTest_queryReward extends BaseSampler {
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		String address = arg.getParameter("address");
		String nodeIds = arg.getParameter("nodeIds");
		List<String> nodeList = new ArrayList<>();
		for(String nodeId:nodeIds.split(",")) {
			nodeList.add(nodeId);
		}
		String result = "";
		try {
			if(this.chainType.equals(this.chainTypeP)) {
				List<Reward> res = this.rewardContract.getDelegateReward(address, nodeList).send().getData();
				if (res != null) {
					sr.setSuccessful(true);
					result = result + JSONObject.toJSONString(res);
				} else {
					sr.setSuccessful(false);
				}
			}else {
				List<com.alaya.contracts.ppos.dto.resp.Reward> res = this.rewardContractA.getDelegateReward(address, nodeList).send().getData();
				if (res != null) {
					sr.setSuccessful(true);
					result = result + JSONObject.toJSONString(res);
				} else {
					sr.setSuccessful(false);
				}
			}
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
		params.addArgument("delegatePrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("address", "0x60ceca9c1290ee56b98d4e160ef0453f7c40d219");
		params.addArgument("nodeIds", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("chainId", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		RewardContractTest_queryReward test = new RewardContractTest_queryReward();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}

	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.REWARD_CONTRACT;
	}

	@Override
	public void setArguments(Arguments params) {
		params.addArgument("address", "0x60ceca9c1290ee56b98d4e160ef0453f7c40d219");
		params.addArgument("nodeIds", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
	}
	 

}
