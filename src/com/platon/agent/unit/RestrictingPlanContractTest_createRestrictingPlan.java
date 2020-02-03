package com.platon.agent.unit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.platon.sdk.contracts.ppos.RestrictingPlanContract;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import com.platon.sdk.contracts.ppos.dto.resp.RestrictingPlan;

/**
 * 锁仓计划接口，包括， 创建锁仓计划 获取锁仓信息
 */
public class RestrictingPlanContractTest_createRestrictingPlan extends AbstractJavaSamplerClient {

	private Web3j web3j;
	private RestrictingPlanContract restrictingPlanContract;
	private Credentials credentials;

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("privateKey", "0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("chainId", "100");
		params.addArgument("address", "0xFD9d508df262a1c968e0D6C757AB08b96D741f4B");
		params.addArgument("plan", "200,100000000000000000000000;300,200000000000000000000000");
		return params;
	}

	public void setupTest(JavaSamplerContext arg) {
		web3j = Web3j.build(new HttpService(arg.getParameter("url")));
		credentials = Credentials.create(arg.getParameter("privateKey"));
		restrictingPlanContract = RestrictingPlanContract.load(web3j, credentials, arg.getParameter("chainId"));
	}

	/**
	 * 创建锁仓计划 account 锁仓释放到账账户 plan plan 为 RestrictingPlan 类型的列表（数组），RestrictingPlan
	 * 定义如下：type RestrictingPlan struct { Epoch uint64
	 * Amount：*big.Int}其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。Epoch
	 * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
            List<RestrictingPlan> restrictingPlans = new ArrayList<>();
            String plan = arg.getParameter("plan");
            String[] plans = plan.split(";");
            for(String p:plans) {
            	String[] pd = p.split(",");
            	BigDecimal pdamount = Convert.toVon(pd[1], Unit.LAT);
            	restrictingPlans.add(new RestrictingPlan( new BigInteger(pd[0]), pdamount.toBigInteger()));
            }
    		String address = arg.getParameter("address");
    		PlatonSendTransaction platonSendTransaction = 
    				restrictingPlanContract.createRestrictingPlanReturnTransaction(address, restrictingPlans).send();
			BaseResponse baseResponse = restrictingPlanContract.getTransactionResponse(platonSendTransaction).send();
			
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
		params.addArgument("privateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("chainId", "100");
		params.addArgument("address", "0xFD9d508df262a1c968e0D6C757AB08b96D741f4B");
		params.addArgument("plan", "200,100000000000000000000000;300,200000000000000000000000");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		RestrictingPlanContractTest_createRestrictingPlan test = new RestrictingPlanContractTest_createRestrictingPlan();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}
	 

}
