package com.platon.agent.unit.contract;

import java.math.BigInteger;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.agent.contract.HumanStandardToken;

/**
 * 合约执行
 * @author Rongjin Zhang
 *
 */
public class ContractTest_execute extends BaseSampler {
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		
		String result = null;
		try {
			HumanStandardToken humanStandardToken = HumanStandardToken.load(arg.getParameter("contractAddress"), web3j, transactionManager, gasProvider);
			TransactionReceipt receipt = humanStandardToken.transfer(arg.getParameter("toAddress"), new BigInteger(arg.getParameter("value")).multiply(BigInteger.valueOf(10^18))).send();
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
		params.addArgument("contractAddress", "0x90b187980cb23eed8e2b367a9560b23074116e19");
		params.addArgument("toAddress", "0x60ceca9c1290ee56b98d4e160ef0453f7c40d219");
		params.addArgument("value", "1");
		params.addArgument("chainId", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		ContractTest_execute test = new ContractTest_execute();
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
		params.addArgument("contractAddress", "0x90b187980cb23eed8e2b367a9560b23074116e19");
		params.addArgument("toAddress", "0x60ceca9c1290ee56b98d4e160ef0453f7c40d219");
		params.addArgument("value", "1");
	}
	 

}
