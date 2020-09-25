package com.platon.agent.unit.node;

import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.alibaba.fastjson.JSON;
import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.CallResponse;
import com.platon.sdk.contracts.ppos.dto.resp.Node;

/**
 * 查询当前共识周期的验证人列表
 */
public class NodeContractTest_validatorList extends BaseSampler {
	

	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		try {
			sr.sampleStart();
			CallResponse<List<Node>> baseResponse = this.nodeContract.getValidatorList().send();
			List<Node> nodeList = baseResponse.getData();
			result = JSON.toJSONString(nodeList, true);
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
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		NodeContractTest_validatorList test = new NodeContractTest_validatorList();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}

	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.NODE_CONTRACT;
	}

	@Override
	public void setArguments(Arguments params) {
		
	}
	 

}
