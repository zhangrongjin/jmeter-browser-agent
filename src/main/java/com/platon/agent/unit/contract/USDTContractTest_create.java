package com.platon.agent.unit.contract;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.agent.contract.USDT;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.math.BigInteger;

/**
 * 合约创建
 * @author Rongjin Zhang
 *
 */
public class USDTContractTest_create extends BaseSampler {
	
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		
		String result = null;
		try {
			BigInteger initialAmount = new BigInteger(arg.getParameter("initialAmount"));
			String tokenName =  arg.getParameter("tokenName");
			BigInteger decimal = new BigInteger(arg.getParameter("decimal"));
			String tokenSymbol = arg.getParameter("tokenSymbol");

			USDT usdt = USDT.deploy(this.web3j, this.transactionManager, this.gasProvider, this.chainId,initialAmount,tokenName,tokenSymbol,decimal )
					.send();
			String contractAddress = usdt.getContractAddress();
			String transactionHash = usdt.getTransactionReceipt().get().getTransactionHash();
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
		params.addArgument("initialAmount", "10000000000000");
		params.addArgument("tokenName", "My Token");
		params.addArgument("decimal", "2");
		params.addArgument("tokenSymbol", "MT");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		USDTContractTest_create test = new USDTContractTest_create();
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
		params.addArgument("initialAmount", "10000000000000");
		params.addArgument("tokenName", "My Token");
		params.addArgument("decimal", "2");
		params.addArgument("tokenSymbol", "MT");
	}
	 

}
