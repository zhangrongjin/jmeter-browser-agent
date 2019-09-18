package com.platon.agent.unit;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.VoteOption;
import org.web3j.platon.contracts.ProposalContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class ProposalContractTest_submitVoteProposal extends AbstractJavaSamplerClient {
	
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.172:8789");
		params.addArgument("votePrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("chainId", "100");
		params.addArgument("proposalHash", "0x2a38e844c8a8969b8f8f153ccdd1a4e7da85c1d3227c46c5aaf92c55f85125d0");
		params.addArgument("voteoption", "1");
		return params;
	}
    
	private Web3j web3j;
	
    private Credentials voteCredentials;
	private ProposalContract voteContract;

	public void setupTest(JavaSamplerContext arg) {
		web3j = Web3j.build(new HttpService(arg.getParameter("url")));
    	voteCredentials = Credentials.create(arg.getParameter("votePrivateKey"));
    	voteContract = ProposalContract.load(web3j,voteCredentials, arg.getParameter("chainId"));
	}
	
	/**
	 * 提交文本提案
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			VoteOption voteOption = null;
			switch (arg.getParameter("voteoption")) {
			case "1":
				voteOption = VoteOption.YEAS;
				break;
			case "2":
				voteOption = VoteOption.NAYS;
				break;
			case "3":
				voteOption = VoteOption.ABSTENTIONS;
				break;
			default:
				voteOption = VoteOption.YEAS;
				break;
			}
			String nodeId = arg.getParameter("nodeId");
            BaseResponse<?> baseResponse = voteContract.vote(arg.getParameter("proposalHash"), nodeId,voteOption).send();
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
		params.addArgument("url", "http://192.168.112.172:8789");
		params.addArgument("votePrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("chainId", "100");
		params.addArgument("proposalHash", "0x2a38e844c8a8969b8f8f153ccdd1a4e7da85c1d3227c46c5aaf92c55f85125d0");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		ProposalContractTest_submitVoteProposal test = new ProposalContractTest_submitVoteProposal();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 

}
