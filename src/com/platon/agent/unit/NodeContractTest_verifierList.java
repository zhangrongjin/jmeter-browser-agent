package com.platon.agent.unit;

import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.alibaba.fastjson.JSON;
import com.platon.sdk.contracts.ppos.NodeContract;
import com.platon.sdk.contracts.ppos.dto.CallResponse;
import com.platon.sdk.contracts.ppos.dto.resp.Node;

public class NodeContractTest_verifierList extends AbstractJavaSamplerClient {
	
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		return params;
	}

	private Web3j web3j;
	private NodeContract nodeContract;

	public void setupTest(JavaSamplerContext arg) {
		web3j = Web3j.build(new HttpService(arg.getParameter("url")));
		nodeContract = NodeContract.load(web3j);
	}
	
	/**
	 * 查询当前结算周期的验证人队列
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		try {
			sr.sampleStart();
			CallResponse<List<Node>> baseResponse = nodeContract.getVerifierList().send();
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
	

}
