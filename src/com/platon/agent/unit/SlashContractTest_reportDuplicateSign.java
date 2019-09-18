package com.platon.agent.unit;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.DuplicateSignType;
import org.web3j.platon.contracts.SlashContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;

public class SlashContractTest_reportDuplicateSign extends AbstractJavaSamplerClient {

	private String data = "{\n" + "\t\"duplicate_prepare\": [{\n" + "\t\t\"VoteA\": {\n" + "\t\t\t\"timestamp\": 0,\n"
			+ "\t\t\t\"block_hash\": \"0x0a0409021f020b080a16070609071c141f19011d090b091303121e1802130407\",\n"
			+ "\t\t\t\"block_number\": 2,\n" + "\t\t\t\"validator_index\": 1,\n"
			+ "\t\t\t\"validator_address\": \"0x120b77ab712589ebd42d69003893ef962cc52832\",\n"
			+ "\t\t\t\"signature\": \"0xa65e16b3bc4862fdd893eaaaaecf1e415cdc2c8a08e4bbb1f6b2a1f4bf4e2d0c0ec27857da86a5f3150b32bee75322073cec320e51fe0a123cc4238ee4155bf001\"\n"
			+ "\t\t},\n" + "\t\t\"VoteB\": {\n" + "\t\t\t\"timestamp\": 0,\n"
			+ "\t\t\t\"block_hash\": \"0x18030d1e01071b1d071a12151e100a091f060801031917161e0a0d0f02161d0e\",\n"
			+ "\t\t\t\"block_number\": 2,\n" + "\t\t\t\"validator_index\": 1,\n"
			+ "\t\t\t\"validator_address\": \"0x120b77ab712589ebd42d69003893ef962cc52832\",\n"
			+ "\t\t\t\"signature\": \"0x9126f9a339c8c4a873efc397062d67e9e9109895cd9da0d09a010d5f5ebbc6e76d285f7d87f801850c8552234101b651c8b7601b4ea077328c27e4f86d66a1bf00\"\n"
			+ "\t\t}\n" + "\t}],\n" + "\t\"duplicate_viewchange\": [],\n" + "\t\"timestamp_viewchange\": []\n" + "}";

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("privateKey", "0xe1eb63c6f8d4d2b131b12ea4d06dd690c719afbe703bf9c152346317b0794d57");
		params.addArgument("chainId", "100");
		params.addArgument("data", data);
		return params;
	}

	private Web3j web3j = Web3j.build(new HttpService("http://192.168.9.76:6789"));
	private SlashContract slashContract;
	private Credentials credentials;

	public void setupTest(JavaSamplerContext arg) {
		web3j = Web3j.build(new HttpService(arg.getParameter("url")));
		credentials = Credentials.create(arg.getParameter("privateKey"));
		slashContract = SlashContract.load(web3j, credentials, arg.getParameter("chainId"));
	}
	
	/**
	 * 举报双签 data 证据的json值，格式为RPC接口Evidences的返回值
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			PlatonSendTransaction platonSendTransaction = 
				slashContract.reportDoubleSignReturnTransaction(DuplicateSignType.PREPARE_BLOCK, arg.getParameter("data")).send();
			BaseResponse<?> baseResponse = slashContract.getReportDoubleSignResult(platonSendTransaction).send();
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
	
	/*
	public static void main(String[] args) {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("privateKey", "0xe1eb63c6f8d4d2b131b12ea4d06dd690c719afbe703bf9c152346317b0794d57");
		params.addArgument("chainId", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		SlashContractTest_reportDuplicateSign test = new SlashContractTest_reportDuplicateSign();
		test.setupTest(arg0);
		test.runTest(arg0);
	}
	 */

}
