package com.platon.agent.unit.delegate;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DelegateContractTest_unDelegate extends BaseSampler {
	
	/**
	 * 减持/撤销委托(全部减持就是撤销) stakingBlockNum 代表着某个node的某次质押的唯一标示 nodeId 被质押的节点的NodeId
	 * amount 减持委托的金额(按照最小单位算，1LAT = 10**18 von)
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		
		String result = null;
		try {
			BigDecimal stakingAmount = Convert.toVon(arg.getParameter("stakingAmount"), Unit.LAT);
			BigInteger stakingBlockNum = BigInteger.valueOf(Long.parseLong(arg.getParameter("stakingBlockNum")));
			if(this.chainType.equals(this.chainTypeP)) {
				PlatonSendTransaction platonSendTransaction =
						this.delegateContract.unDelegateReturnTransaction(
								arg.getParameter("nodeId"), stakingBlockNum, stakingAmount.toBigInteger(), this.gasProvider).send();
				BaseResponse baseResponse = this.delegateContract.getTransactionResponse(platonSendTransaction).send();
				result = baseResponse.toString();
				if (baseResponse.isStatusOk()) {
					sr.setSuccessful(true);
				} else {
					sr.setSuccessful(false);
				}
			} else {
				com.alaya.protocol.core.methods.response.PlatonSendTransaction platonSendTransaction =
						this.delegateContractA.unDelegateReturnTransaction(
								arg.getParameter("nodeId"), stakingBlockNum, stakingAmount.toBigInteger(), this.gasProviderA).send();
				com.alaya.contracts.ppos.dto.BaseResponse baseResponse = this.delegateContractA.getTransactionResponse(platonSendTransaction).send();
				result = baseResponse.toString();
				if (baseResponse.isStatusOk()) {
					sr.setSuccessful(true);
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
		params.addArgument("fromPrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("stakingAmount", "1000");
		params.addArgument("stakingBlockNum", "21924");
		params.addArgument("chainId", "100");
		params.addArgument("gasPrice", "1000000000");
		params.addArgument("gasLimit", "4700000");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		DelegateContractTest_unDelegate test = new DelegateContractTest_unDelegate();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 
	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.DELEGATE_CONTRACT;
	}

	@Override
	public void setArguments(Arguments params) {
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("stakingAmount", "800000");
		params.addArgument("stakingBlockNum", "12419");
	}

}
