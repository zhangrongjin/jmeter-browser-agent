package com.platon.agent.unit;

import java.math.BigInteger;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.GasProvider;

import com.platon.agent.contract.HumanStandardToken;

public class ContractTest_execute extends AbstractJavaSamplerClient {
	
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("addressPrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("gasPrice", "1000000000");
		params.addArgument("gasLimit", "4700000");
		params.addArgument("contractAddress", "0x90b187980cb23eed8e2b367a9560b23074116e19");
		params.addArgument("toAddress", "0x60ceca9c1290ee56b98d4e160ef0453f7c40d219");
		params.addArgument("value", "1");
		params.addArgument("chainId", "100");
		return params;
	}
	
	private Web3j web3j;
	private Credentials credentials;
	protected TransactionManager transactionManager;
	protected GasProvider gasProvider;
	
	public void setupTest(JavaSamplerContext arg) {
		web3j = Web3j.build(new HttpService(arg.getParameter("url")));
		credentials = Credentials.create(arg.getParameter("addressPrivateKey"));
		transactionManager = new RawTransactionManager(web3j, credentials, Long.valueOf(arg.getParameter("chainId")));
		gasProvider = new ContractGasProvider(new BigInteger(arg.getParameter("gasPrice")), new BigInteger(arg.getParameter("gasLimit")));
	}
	
	/**
	 * 发起委托 typ 表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额 nodeId 被质押的节点的NodeId amount
	 * 委托的金额(按照最小单位算，1LAT = 10**18 von)
	 */
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
	 

}
