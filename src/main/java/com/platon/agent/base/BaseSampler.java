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

	protected com.alaya.protocol.Web3j web3jA;
	protected com.alaya.crypto.Credentials credentialsA;
	protected com.alaya.contracts.ppos.DelegateContract delegateContractA;
	protected com.alaya.contracts.ppos.NodeContract nodeContractA;
	protected com.alaya.tx.gas.GasProvider gasProviderA;
	protected com.alaya.tx.TransactionManager transactionManagerA;
	protected com.alaya.contracts.ppos.ProposalContract proposalContractA;
	protected com.alaya.contracts.ppos.RestrictingPlanContract restrictingPlanContractA;
	protected com.alaya.contracts.ppos.RewardContract rewardContractA;
	protected com.alaya.contracts.ppos.SlashContract slashContractA;
	protected com.alaya.contracts.ppos.StakingContract stakingContractA;
	protected Long chainId;
	protected String chainTypeA = "alaya";
	protected String chainTypeP = "platon";
	protected String chainType;
	
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("chainId", "108");
		params.addArgument("gasPrice", "1000000000");
		params.addArgument("gasLimit", "4700000");
		params.addArgument("fromPrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("chainType", this.chainTypeA);
		this.setArguments(params);
		return params;
	}

	
	public void setupTest(JavaSamplerContext arg) {
		this.chainType = arg.getParameter("chainType");
		this.chainId = Long.valueOf(arg.getParameter("chainId"));
		this.web3j = Web3j.build(new HttpService(arg.getParameter("url")));
		this.gasProvider = new ContractGasProvider(new BigInteger(arg.getParameter("gasPrice")), new BigInteger(arg.getParameter("gasLimit")));
		this.credentials = Credentials.create(arg.getParameter("fromPrivateKey"));
		this.transactionManager = new RawTransactionManager(this.web3j, this.credentials, this.chainId);

		this.web3jA = com.alaya.protocol.Web3j.build(new com.alaya.protocol.http.HttpService(arg.getParameter("url")));
		this.gasProviderA = new com.alaya.tx.gas.ContractGasProvider(new BigInteger(arg.getParameter("gasPrice")), new BigInteger(arg.getParameter("gasLimit")));
		this.credentialsA = com.alaya.crypto.Credentials.create(arg.getParameter("fromPrivateKey"));
		this.transactionManagerA = new com.alaya.tx.RawTransactionManager(this.web3jA, this.credentialsA, this.chainId);
		switch (this.getType()) {
		case DELEGATE_CONTRACT:
			this.delegateContract = DelegateContract.load(this.web3j, this.credentials, this.chainId);
			this.delegateContractA = com.alaya.contracts.ppos.DelegateContract.load(this.web3jA, this.credentialsA, this.chainId);
			break;
		case NODE_CONTRACT:
			this.nodeContract = NodeContract.load(this.web3j, this.credentials, this.chainId);
			this.nodeContractA = com.alaya.contracts.ppos.NodeContract.load(this.web3jA, this.credentialsA, this.chainId);
			break;
		case PROPOSAL_CONTRACT:
			this.proposalContract = ProposalContract.load(this.web3j, this.credentials, this.chainId);
			this.proposalContractA = com.alaya.contracts.ppos.ProposalContract.load(this.web3jA, this.credentialsA, this.chainId);
			break;
		case RESTRICTING_PLAN_CONTRACT:
			this.restrictingPlanContract = RestrictingPlanContract.load(this.web3j, this.credentials, this.chainId);
			this.restrictingPlanContractA = com.alaya.contracts.ppos.RestrictingPlanContract.load(this.web3jA, this.credentialsA, this.chainId);
			break;
		case REWARD_CONTRACT:
			this.rewardContract = RewardContract.load(this.web3j, this.credentials, this.chainId);
			this.rewardContractA = com.alaya.contracts.ppos.RewardContract.load(this.web3jA, this.credentialsA, this.chainId);
			break;
		case SLASH_CONTRACT:
			this.slashContract = SlashContract.load(this.web3j, this.credentials, this.chainId);
			this.slashContractA = com.alaya.contracts.ppos.SlashContract.load(this.web3jA, this.credentialsA, this.chainId);
			break;
		case STAKING_CONTRACT:
			this.stakingContract = StakingContract.load(this.web3j, this.credentials, this.chainId);
			this.stakingContractA = com.alaya.contracts.ppos.StakingContract.load(this.web3jA, this.credentialsA, this.chainId);
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
