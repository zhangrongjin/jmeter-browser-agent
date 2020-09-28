package com.platon.agent.unit.delegate;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.math.BigInteger;

/**
 * 委托详情
 * @author Rongjin Zhang
 *
 */
public class DelegateContractTest_delegateInfo extends BaseSampler {
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		this.setupTest(arg);
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		
		String result = null;
		try {
			BigInteger stakingBlockNum = BigInteger.valueOf(Long.parseLong(arg.getParameter("stakingBlockNum")));
			if(this.chainType.equals(this.chainTypeP)) {
				BaseResponse baseResponse =
						this.delegateContract.getDelegateInfo(arg.getParameter("nodeId"), arg.getParameter("delAddr"), stakingBlockNum).send();
				result = baseResponse.toString();
				if (baseResponse.isStatusOk()) {
					sr.setSuccessful(true);
				} else {
					sr.setSuccessful(false);
				}
			} else {
				com.alaya.contracts.ppos.dto.BaseResponse baseResponse =
						this.delegateContractA.getDelegateInfo(arg.getParameter("nodeId"), arg.getParameter("delAddr"), stakingBlockNum).send();
				result = baseResponse.toString();
				if (baseResponse.isStatusOk()) {
					sr.setSuccessful(true);
				} else {
					sr.setSuccessful(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = e.toString();
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
		params.addArgument("delAddr", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("stakingBlockNum", "21924");
		params.addArgument("chainId", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		DelegateContractTest_delegateInfo test = new DelegateContractTest_delegateInfo();
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
		params.addArgument("delAddr", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("stakingBlockNum", "21924");
	}
}
