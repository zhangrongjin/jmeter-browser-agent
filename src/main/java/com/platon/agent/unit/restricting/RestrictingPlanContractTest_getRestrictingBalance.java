package com.platon.agent.unit.restricting;

import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.alibaba.fastjson.JSONObject;
import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.agent.check.RestrictingBalance;
import com.platon.agent.check.SpecialApi;

/**
 * 锁仓计划接口，包括， 创建锁仓计划 获取锁仓信息
 */
public class RestrictingPlanContractTest_getRestrictingBalance extends BaseSampler {


	/**
	 * 创建锁仓计划 account 锁仓释放到账账户 plan plan 为 RestrictingPlan 类型的列表（数组），RestrictingPlan
	 * 定义如下：type RestrictingPlan struct { Epoch uint64
	 * Amount：*big.Int}其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。Epoch
	 * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			String addresses = arg.getParameter("addresses");
			SpecialApi specialApi = new SpecialApi();
			List<RestrictingBalance> restrictingBalances = specialApi.getRestrictingBalance(web3j, addresses);
			result = JSONObject.toJSONString(restrictingBalances);
			if(restrictingBalances != null) {
				sr.setSuccessful(true);
			} else {
				sr.setSuccessful(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		params.addArgument("url", "http://192.168.112.172:9789");
		params.addArgument("fromPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("chainId", "108");
		params.addArgument("gasPrice", "1000000000");
		params.addArgument("gasLimit", "4700000");
		params.addArgument("addresses", "0");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		RestrictingPlanContractTest_getRestrictingBalance test = new RestrictingPlanContractTest_getRestrictingBalance();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}

	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.RESTRICTING_PLAN_CONTRACT;
	}

	@Override
	public void setArguments(Arguments params) {
		params.addArgument("addresses", "0xFD9d508df262a1c968e0D6C757AB08b96D741f4B");
	}
	 

}
