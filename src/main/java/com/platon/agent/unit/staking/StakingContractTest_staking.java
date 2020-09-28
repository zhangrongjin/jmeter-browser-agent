package com.platon.agent.unit.staking;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import com.platon.sdk.contracts.ppos.dto.enums.StakingAmountType;
import com.platon.sdk.contracts.ppos.dto.req.StakingParam;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

public class StakingContractTest_staking extends BaseSampler {


	/**
	 * 发起质押 typ 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额 benefitAddress
	 * 用于接受出块奖励和质押奖励的收益账户 nodeId 被质押的节点Id(也叫候选人的节点Id) externalId
	 * 外部Id(有长度限制，给第三方拉取节点描述的Id) nodeName 被质押节点的名称(有长度限制，表示该节点的名称) website
	 * 节点的第三方主页(有长度限制，表示该节点的主页) details 节点的描述(有长度限制，表示该节点的描述) amount 质押的von
	 * 
	 * @throws Exception
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			String type = arg.getParameter("type");

			String benifitAddress = arg.getParameter("benefitAddress");
			String externalId = arg.getParameter("externalId");
			String nodeName = arg.getParameter("nodeName");
			String webSite = arg.getParameter("webSite");
			String details = arg.getParameter("details");
			BigDecimal stakingAmount = Convert.toVon(arg.getParameter("stakingAmount"), Unit.LAT);
			String nodeId = arg.getParameter("nodeId");
			String blsPubKey = arg.getParameter("blsPubKey");
			String rewardPer = arg.getParameter("rewardPer");
			if(this.chainType.equals(this.chainTypeP)) {
				StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
				if (type.equals("1")) {
					stakingAmountType = StakingAmountType.RESTRICTING_AMOUNT_TYPE;
				}
				PlatonSendTransaction platonSendTransaction = this.stakingContract
						.stakingReturnTransaction(new StakingParam.Builder().setNodeId(nodeId)
								.setAmount(stakingAmount.toBigInteger()).setStakingAmountType(stakingAmountType)
								.setBenifitAddress(benifitAddress).setExternalId(externalId).setNodeName(nodeName)
								.setWebSite(webSite).setDetails(details).setBlsPubKey(blsPubKey)
								.setRewardPer(new BigInteger(rewardPer))
								.setProcessVersion(this.web3j.getProgramVersion().send().getAdminProgramVersion())
								.setBlsProof(this.web3j.getSchnorrNIZKProve().send().getAdminSchnorrNIZKProve()).build(), this.gasProvider)
						.send();
				BaseResponse baseResponse = this.stakingContract.getTransactionResponse(platonSendTransaction).send();
				result = baseResponse.toString();
				if (baseResponse.isStatusOk()) {
					sr.setSuccessful(true);
				} else {
					sr.setSuccessful(false);
				}
			} else {
				com.alaya.contracts.ppos.dto.enums.StakingAmountType stakingAmountType = com.alaya.contracts.ppos.dto.enums.StakingAmountType.FREE_AMOUNT_TYPE;
				if (type.equals("1")) {
					stakingAmountType = com.alaya.contracts.ppos.dto.enums.StakingAmountType.RESTRICTING_AMOUNT_TYPE;
				}
				com.alaya.protocol.core.methods.response.PlatonSendTransaction platonSendTransaction = this.stakingContractA
						.stakingReturnTransaction(new com.alaya.contracts.ppos.dto.req.StakingParam.Builder().setNodeId(nodeId)
								.setAmount(stakingAmount.toBigInteger()).setStakingAmountType(stakingAmountType)
								.setBenifitAddress(benifitAddress).setExternalId(externalId).setNodeName(nodeName)
								.setWebSite(webSite).setDetails(details).setBlsPubKey(blsPubKey)
								.setRewardPer(new BigInteger(rewardPer))
								.setProcessVersion(this.web3jA.getProgramVersion().send().getAdminProgramVersion())
								.setBlsProof(this.web3jA.getSchnorrNIZKProve().send().getAdminSchnorrNIZKProve()).build(), this.gasProviderA)
						.send();
				com.alaya.contracts.ppos.dto.BaseResponse baseResponse = this.stakingContractA.getTransactionResponse(platonSendTransaction).send();
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
		params.addArgument("url", "http://192.168.112.172:8789");
		params.addArgument("stakingPrivateKey", "31ce70ffc219be8beaea82727034ff7b2de147885a1c9e9de6c5090c55ca195b");
		params.addArgument("benefitPrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("chainId", "100");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("blsPubKey", "b601ed8838a8c02abd9e0a48aba3315d497ffcdde490cf9c4b46de4599135cdd276b45b49e44beb31eea4bfd1f147c0045c987baf45c0addb89f83089886e3b6e1d4443f00dc4be3808de96e1c9f02c060867040867a624085bb38d01bac0107");
		params.addArgument("stakingAmount", "-1");
		params.addArgument("externalId", "");
		params.addArgument("nodeName", "chendai-node2");
		params.addArgument("webSite", "www.baidu.com");
		params.addArgument("details", "chendai-node2-details");
		params.addArgument("rewardPer", "100");
		params.addArgument("type", "1");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		StakingContractTest_staking test = new StakingContractTest_staking();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}

	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.STAKING_CONTRACT;
	}

	@Override
	public void setArguments(Arguments params) {
		params.addArgument("benefitAddress", "lax1vr8v48qjjrh9dwvdfctqauz98a7yp5se77fm2e");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("blsPubKey", "b601ed8838a8c02abd9e0a48aba3315d497ffcdde490cf9c4b46de4599135cdd276b45b49e44beb31eea4bfd1f147c0045c987baf45c0addb89f83089886e3b6e1d4443f00dc4be3808de96e1c9f02c060867040867a624085bb38d01bac0107");
		params.addArgument("stakingAmount", "5000000");
		params.addArgument("externalId", "");
		params.addArgument("nodeName", "chendai-node2");
		params.addArgument("webSite", "www.baidu.com");
		params.addArgument("details", "chendai-node2-details");
		params.addArgument("rewardPer", "100");
		params.addArgument("type", "2");
	}
	 

}
