package com.platon.agent.unit.contract;

import com.platon.agent.contract.SuicideAndSelfdestructAlaya;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.agent.contract.SuicideAndSelfdestruct;

/**
 * 转账evm合约创建
 * @author Rongjin Zhang
 *
 */
public class TransferContractTest_create extends BaseSampler {
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		
		String result = null;
		try {
			String contractAddress = "";
			String transactionHash = "";
			if(this.chainType.equals(this.chainTypeP)) {
				SuicideAndSelfdestruct suicideAndSelfdestruct = SuicideAndSelfdestruct.deploy(this.web3j, this.transactionManager, this.gasProvider, this.chainId).send();
				contractAddress = suicideAndSelfdestruct.getContractAddress();
				transactionHash = suicideAndSelfdestruct.getTransactionReceipt().get().getTransactionHash();
			} else {
				SuicideAndSelfdestructAlaya suicideAndSelfdestruct = SuicideAndSelfdestructAlaya.deploy(this.web3jA, this.transactionManagerA, this.gasProviderA, this.chainId).send();
				contractAddress = suicideAndSelfdestruct.getContractAddress();
				transactionHash = suicideAndSelfdestruct.getTransactionReceipt().get().getTransactionHash();
			}
			result += "合约发布成功，合约地址：" + contractAddress + "；交易hash："+transactionHash;
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
		params.addArgument("chainId", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		TransferContractTest_create test = new TransferContractTest_create();
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
		
	}
	 

}
