package com.platon.agent.unit;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.platon.sdk.contracts.ppos.RestrictingPlanContract;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;

/**
 * 锁仓计划接口，包括， 创建锁仓计划 获取锁仓信息
 */
public class RestrictingPlanContractTest_restrictingPlanInfo extends AbstractJavaSamplerClient {

	private Web3j web3j;
	private RestrictingPlanContract restrictingPlanContract;
	private Credentials credentials;

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("privateKey", "0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("chainId", "100");
		params.addArgument("address", "0xFD9d508df262a1c968e0D6C757AB08b96D741f4B");
		return params;
	}

	public void setupTest(JavaSamplerContext arg) {
		web3j = Web3j.build(new HttpService(arg.getParameter("url")));
		credentials = Credentials.create(arg.getParameter("privateKey"));
		restrictingPlanContract = RestrictingPlanContract.load(web3j, credentials, arg.getParameter("chainId"));
	}

	/**
	 * 获取锁仓信息。 account 锁仓释放到账账户
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			String address = arg.getParameter("address");
			BaseResponse baseResponse = restrictingPlanContract.getRestrictingInfo(address).send();
			result = baseResponse.toString();
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
		params.addArgument("privateKey", "0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("chainId", "100");
		params.addArgument("address", "0xFD9d508df262a1c968e0D6C757AB08b96D741f4B");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		RestrictingPlanContractTest_restrictingPlanInfo test = new RestrictingPlanContractTest_restrictingPlanInfo();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 

}
