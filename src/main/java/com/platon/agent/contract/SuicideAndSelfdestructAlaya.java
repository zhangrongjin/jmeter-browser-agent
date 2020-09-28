package com.platon.agent.contract;

import com.alaya.abi.solidity.TypeReference;
import com.alaya.abi.solidity.datatypes.Function;
import com.alaya.abi.solidity.datatypes.Type;
import com.alaya.abi.solidity.datatypes.generated.Uint256;
import com.alaya.crypto.Credentials;
import com.alaya.protocol.Web3j;
import com.alaya.protocol.core.RemoteCall;
import com.alaya.protocol.core.methods.response.TransactionReceipt;
import com.alaya.tx.Contract;
import com.alaya.tx.TransactionManager;
import com.alaya.tx.gas.GasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the com.alaya.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 0.7.5.0.
 */
@SuppressWarnings("rawtypes")
public class SuicideAndSelfdestructAlaya extends Contract {
    private static final String BINARY = "60806040526000805560018054600160a060020a03191633179055610147806100296000396000f3fe6080604052600436106100565763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166341c0e1b58114610058578063a87d942c1461006d578063d09de08a14610094575b005b34801561006457600080fd5b506100566100a9565b34801561007957600080fd5b506100826100e6565b60408051918252519081900360200190f35b3480156100a057600080fd5b506100566100ec565b60015473ffffffffffffffffffffffffffffffffffffffff163314156100e45760015473ffffffffffffffffffffffffffffffffffffffff16ff5b565b60005490565b600154600a9073ffffffffffffffffffffffffffffffffffffffff163314156101185760008054820190555b5056fea165627a7a723058208ebb6b9af3e498c693ba6940d3c9800527e2e8b8f75fd3b01abddb8de5ee76cc0029";

    public static final String FUNC_KILL = "kill";

    public static final String FUNC_GETCOUNT = "getCount";

    public static final String FUNC_INCREMENT = "increment";

    protected SuicideAndSelfdestructAlaya(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider, Long chainId) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider,chainId);
    }


    protected SuicideAndSelfdestructAlaya(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider, Long chainId) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider,chainId);
    }

    public RemoteCall<TransactionReceipt> kill() {
        final Function function = new Function(
                FUNC_KILL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getCount() {
        final Function function = new Function(FUNC_GETCOUNT, 
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> increment() {
        final Function function = new Function(
                FUNC_INCREMENT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<SuicideAndSelfdestructAlaya> deploy(Web3j web3j, Credentials credentials, GasProvider contractGasProvider, Long chainId) {
        return deployRemoteCall(SuicideAndSelfdestructAlaya.class, web3j, credentials, contractGasProvider, BINARY, "",chainId);
    }

    public static RemoteCall<SuicideAndSelfdestructAlaya> deploy(Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider, Long chainId) {
        return deployRemoteCall(SuicideAndSelfdestructAlaya.class, web3j, transactionManager, contractGasProvider, BINARY, "",chainId);
    }


    public static SuicideAndSelfdestructAlaya load(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider, Long chainId) {
        return new SuicideAndSelfdestructAlaya(contractAddress, web3j, credentials, contractGasProvider,chainId);
    }

    public static SuicideAndSelfdestructAlaya load(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider, Long chainId) {
        return new SuicideAndSelfdestructAlaya(contractAddress, web3j, transactionManager, contractGasProvider,chainId);
    }
}
