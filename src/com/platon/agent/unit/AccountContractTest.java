package com.platon.agent.unit;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.platon.BaseResponse;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.PlatonCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;
import org.web3j.utils.PlatOnUtil;

import com.alibaba.fastjson.JSON;
import com.platon.agent.base.AccountBalanceDto;

public class AccountContractTest extends AbstractJavaSamplerClient {

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.120.76:6797");
		params.addArgument("address1", "0x2e95e3ce0a54951eb9a99152a6d5827872dfb4fd");
		params.addArgument("address1", "0xfd9d508df262a1c968e0d6c757ab08b96d741f4b");
		return params;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		try {
			sr.sampleStart();
			Web3j web3j = Web3j.build(new HttpService(arg.getParameter("url")));

			StringBuilder addressBuilder = new StringBuilder();
			String address = arg.getParameter("address1");
			addressBuilder.append(address).append(";");
			address = arg.getParameter("address2");
			addressBuilder.append(address);

			final Function function = new Function(4101, Arrays.asList(new Utf8String(addressBuilder.toString())),
					Collections.emptyList());

			String encodedFunction = PlatOnUtil.invokeEncode(function);
			PlatonCall ethCall = web3j.platonCall(
					Transaction.createEthCallTransaction(
							"0x1000000000000000000000000000000000000001",
							"0x1000000000000000000000000000000000000001", encodedFunction),
					DefaultBlockParameterName.LATEST).send();
			String value = ethCall.getValue();
			BaseResponse response = JSONUtil.parseObject(new String(Numeric.hexStringToByteArray(value)),BaseResponse.class);
			String data = response.data.toString();
			response.data = JSONUtil.parseArray(data, AccountBalanceDto.class);
			result = JSON.toJSONString(response.data, true);
			sr.setSuccessful(true);
		} catch (IOException e) {
			result = e.getMessage();
			sr.setSuccessful(false);
		}
		sr.setResponseData(result, null);
		sr.setDataType(SampleResult.TEXT);
		sr.sampleEnd();
		return sr;
	}

}
