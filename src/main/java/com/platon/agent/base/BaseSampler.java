package com.platon.agent.base;


import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.*;
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

import java.math.BigInteger;

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
	protected Long chainId;
	
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
		this.chainId = Long.valueOf(arg.getParameter("chainId"));
		this.web3j = Web3j.build(new HttpService(arg.getParameter("url")));
		this.gasProvider = new ContractGasProvider(new BigInteger(arg.getParameter("gasPrice")), new BigInteger(arg.getParameter("gasLimit")));
		this.credentials = Credentials.create(arg.getParameter("fromPrivateKey"));
		this.transactionManager = new RawTransactionManager(this.web3j, this.credentials, this.chainId);
		switch (this.getType()) {
		case DELEGATE_CONTRACT:
			this.delegateContract = DelegateContract.load(this.web3j, this.credentials, this.chainId);
			break;
		case NODE_CONTRACT:
			this.nodeContract = NodeContract.load(this.web3j, this.credentials, this.chainId);
			break;
		case PROPOSAL_CONTRACT:
			this.proposalContract = ProposalContract.load(this.web3j, this.credentials, this.chainId);
			break;
		case RESTRICTING_PLAN_CONTRACT:
			this.restrictingPlanContract = RestrictingPlanContract.load(this.web3j, this.credentials, this.chainId);
			break;
		case REWARD_CONTRACT:
			this.rewardContract = RewardContract.load(this.web3j, this.credentials, this.chainId);
			break;
		case SLASH_CONTRACT:
			this.slashContract = SlashContract.load(this.web3j, this.credentials, this.chainId);
			break;
		case STAKING_CONTRACT:
			this.stakingContract = StakingContract.load(this.web3j, this.credentials, this.chainId);
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
