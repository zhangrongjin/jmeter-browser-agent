package com.platon.agent.unit;

import java.math.BigInteger;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;

import com.platon.sdk.contracts.ppos.StakingContract;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import com.platon.sdk.contracts.ppos.dto.req.UpdateStakingParam;

public class StakingContractTest_updateStakingInfo extends AbstractJavaSamplerClient {

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("stakingPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("benefitPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("chainId", "100");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("blsPubKey", "b601ed8838a8c02abd9e0a48aba3315d497ffcdde490cf9c4b46de4599135cdd276b45b49e44beb31eea4bfd1f147c0045c987baf45c0addb89f83089886e3b6e1d4443f00dc4be3808de96e1c9f02c060867040867a624085bb38d01bac0107");
		params.addArgument("externalId", "");
		params.addArgument("nodeName", "chendai-node1-001");
		params.addArgument("webSite", "www.baidu.com-001");
		params.addArgument("details", "chendai-node1-details-001");
		params.addArgument("rewardPer", "100");
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
	 * 修改质押信息 benefitAddress 用于接受出块奖励和质押奖励的收益账户 nodeId 被质押的节点Id(也叫候选人的节点Id)
	 * externalId 外部Id(有长度限制，给第三方拉取节点描述的Id) nodeName 被质押节点的名称(有长度限制，表示该节点的名称)
	 * website 节点的第三方主页(有长度限制，表示该节点的主页) details 节点的描述(有长度限制，表示该节点的描述)
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			String benifitAddress = benefitCredentials.getAddress();
			String externalId = arg.getParameter("externalId");
			String nodeName = arg.getParameter("nodeName");
			String webSite = arg.getParameter("webSite");
			String details = arg.getParameter("details");
			String nodeId = arg.getParameter("nodeId");
			String rewardPer = arg.getParameter("rewardPer");

			PlatonSendTransaction platonSendTransaction = stakingContract.updateStakingInfoReturnTransaction(
					new UpdateStakingParam.Builder().setBenifitAddress(benifitAddress).setExternalId(externalId)
							.setNodeId(nodeId).setNodeName(nodeName).setWebSite(webSite).setRewardPer(new BigInteger(rewardPer))
							.setDetails(details).build())
					.send();

			BaseResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
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
		params.addArgument("externalId", "");
		params.addArgument("nodeName", "chendai-node1-001");
		params.addArgument("webSite", "www.baidu.com-001");
		params.addArgument("details", "chendai-node1-details-001");
		params.addArgument("rewardPer", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		StakingContractTest_updateStakingInfo test = new StakingContractTest_updateStakingInfo();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 

}
