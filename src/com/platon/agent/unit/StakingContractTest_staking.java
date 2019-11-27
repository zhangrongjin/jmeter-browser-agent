package com.platon.agent.unit;

import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.StakingAmountType;
import org.web3j.platon.bean.StakingParam;
import org.web3j.platon.contracts.StakingContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

public class StakingContractTest_staking extends AbstractJavaSamplerClient {

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("stakingPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("benefitPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("chainId", "100");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("blsPubKey", "b601ed8838a8c02abd9e0a48aba3315d497ffcdde490cf9c4b46de4599135cdd276b45b49e44beb31eea4bfd1f147c0045c987baf45c0addb89f83089886e3b6e1d4443f00dc4be3808de96e1c9f02c060867040867a624085bb38d01bac0107");
		params.addArgument("stakingAmount", "5000000");
		params.addArgument("externalId", "");
		params.addArgument("nodeName", "chendai-node2");
		params.addArgument("webSite", "www.baidu.com");
		params.addArgument("details", "chendai-node2-details");
		return params;
	}

	private Web3j web3j;
	private Credentials stakingCredentials;
	private Credentials benefitCredentials;
	private StakingContract stakingContract;

	public void setupTest(JavaSamplerContext arg) {
		try {
			stakingCredentials = Credentials.create(arg.getParameter("stakingPrivateKey"));
			benefitCredentials = Credentials.create(arg.getParameter("benefitPrivateKey"));
			web3j = Web3j.build(new HttpService(arg.getParameter("url")));
			stakingContract = StakingContract.load(web3j, stakingCredentials, arg.getParameter("chainId"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
			StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
			String benifitAddress = benefitCredentials.getAddress();
			String externalId = arg.getParameter("externalId");
			String nodeName = arg.getParameter("nodeName");
			String webSite = arg.getParameter("webSite");
			String details = arg.getParameter("details");
			BigDecimal stakingAmount = Convert.toVon(arg.getParameter("stakingAmount"), Unit.LAT).add(BigDecimal.valueOf(1L));
			String nodeId = arg.getParameter("nodeId");
			String blsPubKey = arg.getParameter("blsPubKey");
			
			PlatonSendTransaction platonSendTransaction = stakingContract
					.stakingReturnTransaction(new StakingParam.Builder().setNodeId(nodeId)
							.setAmount(stakingAmount.toBigInteger()).setStakingAmountType(stakingAmountType)
							.setBenifitAddress(benifitAddress).setExternalId(externalId).setNodeName(nodeName)
							.setWebSite(webSite).setDetails(details).setBlsPubKey(blsPubKey).setProcessVersion(stakingContract.getProgramVersion())
				               .setBlsProof(stakingContract.getAdminSchnorrNIZKProve()).build())
					.send();
			BaseResponse<?> baseResponse = stakingContract.getStakingResult(platonSendTransaction).send();
			result = baseResponse.toString();
			if(baseResponse.isStatusOk()) {
				sr.setSuccessful(true);
			} else {
				sr.setSuccessful(false);
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
		params.addArgument("stakingPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("benefitPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("chainId", "100");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("blsPubKey", "b601ed8838a8c02abd9e0a48aba3315d497ffcdde490cf9c4b46de4599135cdd276b45b49e44beb31eea4bfd1f147c0045c987baf45c0addb89f83089886e3b6e1d4443f00dc4be3808de96e1c9f02c060867040867a624085bb38d01bac0107");
		params.addArgument("stakingAmount", "5000000");
		params.addArgument("externalId", "");
		params.addArgument("nodeName", "chendai-node2");
		params.addArgument("webSite", "www.baidu.com");
		params.addArgument("details", "chendai-node2-details");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		StakingContractTest_staking test = new StakingContractTest_staking();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 

}
