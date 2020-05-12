package com.platon.agent.unit.contract;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.agent.contract.SuicideAndSelfdestruct;

/**
 * 销毁合约创建
 * @author Rongjin Zhang
 *
 */
public class DestroyContractTest_execute extends BaseSampler {
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		
		String result = null;
		try {
			SuicideAndSelfdestruct suicideAndSelfdestruct = SuicideAndSelfdestruct.deploy(web3j, transactionManager, gasProvider).send();
			TransactionReceipt receipt = suicideAndSelfdestruct.kill().send();
			String transactionHash = receipt.getTransactionHash();
			result += "合约调用成功，交易hash："+transactionHash;
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
		params.addArgument("addressPrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("gasPrice", "1000000000");
		params.addArgument("gasLimit", "4700000");
		params.addArgument("contractAddress", "0xffabff71deaac519126acad5cdcfd5769f1907f1");
		params.addArgument("chainId", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		DestroyContractTest_execute test = new DestroyContractTest_execute();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}

	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.OTHER;
	}

	@Override
	public void setArguments(Arguments params) {
		params.addArgument("contractAddress", "0xffabff71deaac519126acad5cdcfd5769f1907f1");
		
	}
	 

}
