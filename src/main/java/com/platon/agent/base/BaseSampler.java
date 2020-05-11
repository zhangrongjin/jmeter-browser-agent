package com.platon.agent.base;


import java.math.BigInteger;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.GasProvider;

import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.DelegateContract;
import com.platon.sdk.contracts.ppos.NodeContract;
import com.platon.sdk.contracts.ppos.ProposalContract;
import com.platon.sdk.contracts.ppos.RestrictingPlanContract;
import com.platon.sdk.contracts.ppos.RewardContract;
import com.platon.sdk.contracts.ppos.SlashContract;
import com.platon.sdk.contracts.ppos.StakingContract;

/**
 * 基础设置类
 * @author Rongjin Zhang
 *
 */
public abstract class BaseSampler extends AbstractJavaSamplerClient {
	
	
	protected Web3j web3j;
	protected Credentials credentials;
	protected DelegateContract delegateContract;
	protected NodeContract nodeContract;
	protected GasProvider gasProvider;
	protected TransactionManager transactionManager;
	protected ProposalContract proposalContract;
	protected RestrictingPlanContract restrictingPlanContract;
	protected RewardContract rewardContract;
	protected SlashContract slashContract;
	protected StakingContract stakingContract;
	
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("chainId", "100");
		params.addArgument("gasPrice", "1000000000");
		params.addArgument("gasLimit", "4700000");
		params.addArgument("fromPrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		this.setArguments(params);
		return params;
	}

	
	public void setupTest(JavaSamplerContext arg) {
		web3j = Web3j.build(new HttpService(arg.getParameter("url")));
		gasProvider = new ContractGasProvider(new BigInteger(arg.getParameter("gasPrice")), new BigInteger(arg.getParameter("gasLimit")));
		credentials = Credentials.create(arg.getParameter("fromPrivateKey"));
		transactionManager = new RawTransactionManager(web3j, credentials, Long.valueOf(arg.getParameter("chainId")));
		switch (this.getType()) {
		case DELEGATE_CONTRACT:
			delegateContract = DelegateContract.load(web3j, credentials, Long.valueOf(arg.getParameter("chainId")));
			break;
		case NODE_CONTRACT:
			nodeContract = NodeContract.load(web3j,credentials, Long.valueOf(arg.getParameter("chainId")));
			break;
		case PROPOSAL_CONTRACT:
			proposalContract = ProposalContract.load(web3j, credentials, Long.valueOf(arg.getParameter("chainId")));
			break;
		case RESTRICTING_PLAN_CONTRACT:
			restrictingPlanContract = RestrictingPlanContract.load(web3j, credentials, Long.valueOf(arg.getParameter("chainId")));
			break;
		case REWARD_CONTRACT:
			rewardContract = RewardContract.load(web3j,credentials, Long.valueOf(arg.getParameter("chainId")));
			break;
		case SLASH_CONTRACT:
			slashContract = SlashContract.load(web3j, credentials, Long.valueOf(arg.getParameter("chainId")));
			break;
		case STAKING_CONTRACT:
			stakingContract = StakingContract.load(web3j, credentials, Long.valueOf(arg.getParameter("chainId")));
			break;
		default:
			break;
		}
		
    }

	/**
	 * @return
	 */
	public abstract InnerContractAddrEnum getType();  
	
	public abstract void setArguments(Arguments params);
	
}
