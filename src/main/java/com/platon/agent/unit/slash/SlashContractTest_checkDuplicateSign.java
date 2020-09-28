package com.platon.agent.unit.slash;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import com.platon.sdk.contracts.ppos.dto.common.DuplicateSignType;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.math.BigInteger;

public class SlashContractTest_checkDuplicateSign extends BaseSampler {

	
	/**
	 * 查询节点是否已被举报过多签 typ 代表双签类型，1：prepare，2：viewChange，3：TimestampViewChange addr
	 * 举报的节点地址 blockNumber 多签的块高
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			if(this.chainType.equals(this.chainTypeP)) {
				BaseResponse baseResponse = this.slashContract.checkDoubleSign(DuplicateSignType.PREPARE_BLOCK,
						arg.getParameter("address"), new BigInteger(arg.getParameter("blockNumber"))).send();
				result = baseResponse.toString();
				if (baseResponse.isStatusOk()) {
					sr.setSuccessful(true);
				} else {
					sr.setSuccessful(false);
				}
			}else {
				com.alaya.contracts.ppos.dto.BaseResponse baseResponse = this.slashContractA.checkDoubleSign(com.alaya.contracts.ppos.dto.common.DuplicateSignType.PREPARE_BLOCK,
						arg.getParameter("address"), new BigInteger(arg.getParameter("blockNumber"))).send();
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

	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.SLASH_CONTRACT;
	}

	@Override
	public void setArguments(Arguments params) {
		params.addArgument("address", "0xe1eb63c6f8d4d2b131b12ea4d06dd690c719afbe703bf9c152346317b0794d57");
		params.addArgument("blockNumber", "10");
	}
	
	/*
	public static void main(String[] args) {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("privateKey", "0xe1eb63c6f8d4d2b131b12ea4d06dd690c719afbe703bf9c152346317b0794d57");
		params.addArgument("chainId", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		SlashContractTest_checkDuplicateSign test = new SlashContractTest_checkDuplicateSign();
		test.setupTest(arg0);
		test.runTest(arg0);
	}
	 */

}
