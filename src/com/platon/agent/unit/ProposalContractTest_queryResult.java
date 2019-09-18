package com.platon.agent.unit;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.bean.Proposal;
import org.web3j.platon.bean.TallyResult;
import org.web3j.platon.contracts.ProposalContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class ProposalContractTest_queryResult extends AbstractJavaSamplerClient {
	
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("stakingPrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("chainId", "100");
		params.addArgument("proposalID", "0x5a4609ccf2ff65ac8596397cecb9795d286dfe894232bcdeb2553a49ea39200f");
		return params;
	}
    
	private Web3j web3j;
	
    private Credentials stakingCredentials;
	private ProposalContract proposalContract;

	public void setupTest(JavaSamplerContext arg) {
		web3j = Web3j.build(new HttpService(arg.getParameter("url")));
    	stakingCredentials = Credentials.create(arg.getParameter("stakingPrivateKey"));
        proposalContract = ProposalContract.load(web3j,stakingCredentials, arg.getParameter("chainId"));
	}
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			String proposalID = arg.getParameter("proposalID");
			BaseResponse<Proposal> baseResponse = proposalContract.getProposal(proposalID).send();
			BaseResponse<TallyResult> response = proposalContract.getTallyResult(proposalID).send();

			result = "提案信息：" + baseResponse.toString() + ",投票结果：" + response.toString();
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
		params.addArgument("stakingPrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("chainId", "100");
		params.addArgument("proposalID", "0x5a4609ccf2ff65ac8596397cecb9795d286dfe894232bcdeb2553a49ea39200f");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		ProposalContractTest_queryResult test = new ProposalContractTest_queryResult();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 

}
