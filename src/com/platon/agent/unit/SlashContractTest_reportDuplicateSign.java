package com.platon.agent.unit;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;

import com.platon.sdk.contracts.ppos.SlashContract;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import com.platon.sdk.contracts.ppos.dto.common.DuplicateSignType;

public class SlashContractTest_reportDuplicateSign extends AbstractJavaSamplerClient {

	private String data = "{\"prepareA\":{\"epoch\":0,\"viewNumber\":0,\"blockHash\":\"0x286d279e9f6714cb6e87ade33f0220b0fefb052a87e3be719ea68ab09aac28d4\",\"blockNumber\":168810,\"blockIndex\":0,\"blockData\":\"0xe3fc462786d798b370125d06e819ba7b86d460c4b9bbdd295aeeee3bb6dcef58\",\"validateNode\":{\"index\":0,\"address\":\"0xcfe51d85f9965f6d031e4e3cce688eab7c95e940\",\"nodeId\":\"bfc9d6578bab4e510755575e47b7d137fcf0ad0bcf10ed4d023640dfb41b197b9f0d8014e47ecbe4d51f15db514009cbda109ebcf0b7afe06600d6d423bb7fbf\",\"blsPubKey\":\"b4713797d296c9fe1749d22eb59b03d9694ab896b71449b0e6daf2d1ecb3a9d3d6e9c258b37acb2d07fa82bcb55ced144fb4b056d6cd192a509859615b090128d6e5686e84df47951e1781625627907054975f76e427da8d32d3f30b9a53e60f\"},\"signature\":\"0x54574c456de48ed11721a781dab0a9a7ab19f0e116592b0f08cc80019203c5052c043fff7dcf9d14796a7f3de1364d9000000000000000000000000000000000\"},\"prepareB\":{\"epoch\":0,\"viewNumber\":0,\"blockHash\":\"0x9c81cb22ddc88fa7cad5528070fcb1f9599f4364d44b4dba8003aebf33171c00\",\"blockNumber\":168810,\"blockIndex\":0,\"blockData\":\"0x90261400319df7722541f87e8f4f6055135135e1befb27f94abd67362dcefd8e\",\"validateNode\":{\"index\":0,\"address\":\"0xcfe51d85f9965f6d031e4e3cce688eab7c95e940\",\"nodeId\":\"bfc9d6578bab4e510755575e47b7d137fcf0ad0bcf10ed4d023640dfb41b197b9f0d8014e47ecbe4d51f15db514009cbda109ebcf0b7afe06600d6d423bb7fbf\",\"blsPubKey\":\"b4713797d296c9fe1749d22eb59b03d9694ab896b71449b0e6daf2d1ecb3a9d3d6e9c258b37acb2d07fa82bcb55ced144fb4b056d6cd192a509859615b090128d6e5686e84df47951e1781625627907054975f76e427da8d32d3f30b9a53e60f\"},\"signature\":\"0xa992558ef4e92418a11153ba6c1540110af215ecb439798be2cfad500a69fa334b79283ca25992380baecc22d35a5e8c00000000000000000000000000000000\"}}";

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
			BaseResponse baseResponse = slashContract.getTransactionResponse(platonSendTransaction).send();
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
		params.addArgument("privateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("chainId", "100");
		params.addArgument("data", "{\"prepareA\":{\"epoch\":0,\"viewNumber\":0,\"blockHash\":\"0x11133bee7d758022a8b21d21f9cbd954ae1263b97726cdbb8a02cb1c90e2a12b\",\"blockNumber\":169010,\"blockIndex\":0,\"blockData\":\"0x4975da42456845c4bc87b443f20868fac334592ccca2f70863d30ff698da7a0d\",\"validateNode\":{\"index\":0,\"address\":\"0xcfe51d85f9965f6d031e4e3cce688eab7c95e940\",\"nodeId\":\"bfc9d6578bab4e510755575e47b7d137fcf0ad0bcf10ed4d023640dfb41b197b9f0d8014e47ecbe4d51f15db514009cbda109ebcf0b7afe06600d6d423bb7fbf\",\"blsPubKey\":\"b4713797d296c9fe1749d22eb59b03d9694ab896b71449b0e6daf2d1ecb3a9d3d6e9c258b37acb2d07fa82bcb55ced144fb4b056d6cd192a509859615b090128d6e5686e84df47951e1781625627907054975f76e427da8d32d3f30b9a53e60f\"},\"signature\":\"0xd922cfa3cf422b9beb35e3cb952a1ec4ea9576d45e046152a036aad32fef756dd7836bf6181db412980950ad6c35270500000000000000000000000000000000\"},\"prepareB\":{\"epoch\":0,\"viewNumber\":0,\"blockHash\":\"0xda44ca898ff98c38731ef170c0bf11575cee85eed1054bc7227f4636551a6054\",\"blockNumber\":169010,\"blockIndex\":0,\"blockData\":\"0x219bfe20417d973a8f34508d0abd607d45f4e8dc9ced8e88d9b46e52a1360f07\",\"validateNode\":{\"index\":0,\"address\":\"0xcfe51d85f9965f6d031e4e3cce688eab7c95e940\",\"nodeId\":\"bfc9d6578bab4e510755575e47b7d137fcf0ad0bcf10ed4d023640dfb41b197b9f0d8014e47ecbe4d51f15db514009cbda109ebcf0b7afe06600d6d423bb7fbf\",\"blsPubKey\":\"b4713797d296c9fe1749d22eb59b03d9694ab896b71449b0e6daf2d1ecb3a9d3d6e9c258b37acb2d07fa82bcb55ced144fb4b056d6cd192a509859615b090128d6e5686e84df47951e1781625627907054975f76e427da8d32d3f30b9a53e60f\"},\"signature\":\"0xdda3461958d3a65b6e4e1ab085b2efa7177adfd156e942db8047f6245bfb1e11fa94fdbd9ec9719b9b92ca8deac8df0200000000000000000000000000000000\"}}");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		SlashContractTest_reportDuplicateSign test = new SlashContractTest_reportDuplicateSign();
		test.setupTest(arg0);
		test.runTest(arg0);
	}
	 

}
