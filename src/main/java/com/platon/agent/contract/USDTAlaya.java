package com.platon.agent.contract;

import com.alaya.abi.solidity.EventEncoder;
import com.alaya.abi.solidity.FunctionEncoder;
import com.alaya.abi.solidity.TypeReference;
import com.alaya.abi.solidity.datatypes.*;
import com.alaya.abi.solidity.datatypes.generated.Uint256;
import com.alaya.crypto.Credentials;
import com.alaya.protocol.Web3j;
import com.alaya.protocol.core.DefaultBlockParameter;
import com.alaya.protocol.core.RemoteCall;
import com.alaya.protocol.core.methods.request.PlatonFilter;
import com.alaya.protocol.core.methods.response.Log;
import com.alaya.protocol.core.methods.response.TransactionReceipt;
import com.alaya.tx.Contract;
import com.alaya.tx.TransactionManager;
import com.alaya.tx.gas.GasProvider;
import rx.Observable;
import rx.functions.Func1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://github.com/PlatONnetwork/client-sdk-java/releases">platon-web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/PlatONnetwork/client-sdk-java/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 0.13.0.7.
 */
public class USDTAlaya extends Contract {
    private static final String BINARY = "60606040526000805460a060020a60ff0219168155600381905560045534156200002857600080fd5b604051620017d3380380620017d3833981016040528080519190602001805182019190602001805182019190602001805160008054600160a060020a03191633600160a060020a0316179055600186905591506007905083805162000092929160200190620000dd565b506008828051620000a8929160200190620000dd565b50600955505060008054600160a060020a0316815260026020526040902055600a805460a060020a60ff021916905562000182565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200012057805160ff191683800117855562000150565b8280016001018555821562000150579182015b828111156200015057825182559160200191906001019062000133565b506200015e92915062000162565b5090565b6200017f91905b808211156200015e576000815560010162000169565b90565b61164180620001926000396000f3006060604052361561017a5763ffffffff60e060020a60003504166306fdde03811461017f5780630753c30c14610209578063095ea7b31461022a5780630e136b191461024c5780630ecb93c01461027357806318160ddd1461029257806323b872dd146102b757806326976e3f146102df57806327e235e31461030e578063313ce5671461032d57806335390714146103405780633eaaf86b146103535780633f4ba83a1461036657806359bf1abe146103795780635c658165146103985780635c975abb146103bd57806370a08231146103d05780638456cb59146103ef578063893d20e8146104025780638da5cb5b1461041557806395d89b4114610428578063a9059cbb1461043b578063c0324c771461045d578063cc872b6614610476578063db006a751461048c578063dd62ed3e146104a2578063dd644f72146104c7578063e47d6060146104da578063e4997dc5146104f9578063e5b5019a14610518578063f2fde38b1461052b578063f3bdc2281461054a575b600080fd5b341561018a57600080fd5b610192610569565b60405160208082528190810183818151815260200191508051906020019080838360005b838110156101ce5780820151838201526020016101b6565b50505050905090810190601f1680156101fb5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561021457600080fd5b610228600160a060020a0360043516610607565b005b341561023557600080fd5b610228600160a060020a03600435166024356106aa565b341561025757600080fd5b61025f610757565b604051901515815260200160405180910390f35b341561027e57600080fd5b610228600160a060020a0360043516610767565b341561029d57600080fd5b6102a56107e7565b60405190815260200160405180910390f35b34156102c257600080fd5b610228600160a060020a036004358116906024351660443561086e565b34156102ea57600080fd5b6102f2610932565b604051600160a060020a03909116815260200160405180910390f35b341561031957600080fd5b6102a5600160a060020a0360043516610941565b341561033857600080fd5b6102a5610953565b341561034b57600080fd5b6102a5610959565b341561035e57600080fd5b6102a561095f565b341561037157600080fd5b610228610965565b341561038457600080fd5b61025f600160a060020a03600435166109e4565b34156103a357600080fd5b6102a5600160a060020a0360043581169060243516610a06565b34156103c857600080fd5b61025f610a23565b34156103db57600080fd5b6102a5600160a060020a0360043516610a33565b34156103fa57600080fd5b610228610ad3565b341561040d57600080fd5b6102f2610b57565b341561042057600080fd5b6102f2610b66565b341561043357600080fd5b610192610b75565b341561044657600080fd5b610228600160a060020a0360043516602435610be0565b341561046857600080fd5b610228600435602435610cb9565b341561048157600080fd5b610228600435610d4f565b341561049757600080fd5b610228600435610dfe565b34156104ad57600080fd5b6102a5600160a060020a0360043581169060243516610eaf565b34156104d257600080fd5b6102a5610f5a565b34156104e557600080fd5b61025f600160a060020a0360043516610f60565b341561050457600080fd5b610228600160a060020a0360043516610f75565b341561052357600080fd5b6102a5610ff2565b341561053657600080fd5b610228600160a060020a0360043516610ff8565b341561055557600080fd5b610228600160a060020a036004351661104e565b60078054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105ff5780601f106105d4576101008083540402835291602001916105ff565b820191906000526020600020905b8154815290600101906020018083116105e257829003601f168201915b505050505081565b60005433600160a060020a0390811691161461062257600080fd5b600a805460a060020a74ff0000000000000000000000000000000000000000199091161773ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790557fcc358699805e9a8b7f77b522628c7cb9abd07d9efb86b6fb616af1609036a99e81604051600160a060020a03909116815260200160405180910390a150565b604060443610156106ba57600080fd5b600a5460a060020a900460ff161561074857600a54600160a060020a031663aee92d3333858560405160e060020a63ffffffff8616028152600160a060020a0393841660048201529190921660248201526044810191909152606401600060405180830381600087803b151561072f57600080fd5b6102c65a03f1151561074057600080fd5b505050610752565b610752838361110c565b505050565b600a5460a060020a900460ff1681565b60005433600160a060020a0390811691161461078257600080fd5b600160a060020a03811660009081526006602052604090819020805460ff191660011790557f42e160154868087d6bfdc0ca23d96a1c1cfa32f1b72ba9ba27b69b98a0d819dc90829051600160a060020a03909116815260200160405180910390a150565b600a5460009060a060020a900460ff161561086657600a54600160a060020a03166318160ddd6000604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b151561084457600080fd5b6102c65a03f1151561085557600080fd5b50505060405180519050905061086b565b506001545b90565b60005460a060020a900460ff161561088557600080fd5b600160a060020a03831660009081526006602052604090205460ff16156108ab57600080fd5b600a5460a060020a900460ff161561092757600a54600160a060020a0316638b477adb3385858560405160e060020a63ffffffff8716028152600160a060020a0394851660048201529284166024840152921660448201526064810191909152608401600060405180830381600087803b151561072f57600080fd5b6107528383836111be565b600a54600160a060020a031681565b60026020526000908152604090205481565b60095481565b60045481565b60015481565b60005433600160a060020a0390811691161461098057600080fd5b60005460a060020a900460ff16151561099857600080fd5b6000805474ff0000000000000000000000000000000000000000191690557f7805862f689e2f13df9f062ff482ad3ad112aca9e0847911ed832e158c525b3360405160405180910390a1565b600160a060020a03811660009081526006602052604090205460ff165b919050565b600560209081526000928352604080842090915290825290205481565b60005460a060020a900460ff1681565b600a5460009060a060020a900460ff1615610ac357600a54600160a060020a03166370a082318360006040516020015260405160e060020a63ffffffff8416028152600160a060020a039091166004820152602401602060405180830381600087803b1515610aa157600080fd5b6102c65a03f11515610ab257600080fd5b505050604051805190509050610a01565b610acc826113bd565b9050610a01565b60005433600160a060020a03908116911614610aee57600080fd5b60005460a060020a900460ff1615610b0557600080fd5b6000805474ff0000000000000000000000000000000000000000191660a060020a1790557f6985a02210a168e66602d3235cb6db0e70f92b3ba4d376a33c0f3d9434bff62560405160405180910390a1565b600054600160a060020a031690565b600054600160a060020a031681565b60088054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105ff5780601f106105d4576101008083540402835291602001916105ff565b60005460a060020a900460ff1615610bf757600080fd5b600160a060020a03331660009081526006602052604090205460ff1615610c1d57600080fd5b600a5460a060020a900460ff1615610cab57600a54600160a060020a0316636e18980a33848460405160e060020a63ffffffff8616028152600160a060020a0393841660048201529190921660248201526044810191909152606401600060405180830381600087803b1515610c9257600080fd5b6102c65a03f11515610ca357600080fd5b505050610cb5565b610cb582826113d8565b5050565b60005433600160a060020a03908116911614610cd457600080fd5b60148210610ce157600080fd5b60328110610cee57600080fd5b6003829055600954610d0a908290600a0a63ffffffff61155c16565b60048190556003547fb044a1e409eac5c48e5af22d4af52670dd1a99059537a78b31b48c6500a6354e9160405191825260208201526040908101905180910390a15050565b60005433600160a060020a03908116911614610d6a57600080fd5b60015481810111610d7a57600080fd5b60008054600160a060020a031681526002602052604090205481810111610da057600080fd5b60008054600160a060020a03168152600260205260409081902080548301905560018054830190557fcb8241adb0c3fdb35b70c24ce35c5eb0c17af7431c99f827d44a445ca624176a9082905190815260200160405180910390a150565b60005433600160a060020a03908116911614610e1957600080fd5b60015481901015610e2957600080fd5b60008054600160a060020a031681526002602052604090205481901015610e4f57600080fd5b60018054829003905560008054600160a060020a031681526002602052604090819020805483900390557f702d5967f45f6513a38ffc42d6ba9bf230bd40e8f53b16363c7eb4fd2deb9a449082905190815260200160405180910390a150565b600a5460009060a060020a900460ff1615610f4757600a54600160a060020a031663dd62ed3e848460006040516020015260405160e060020a63ffffffff8516028152600160a060020a03928316600482015291166024820152604401602060405180830381600087803b1515610f2557600080fd5b6102c65a03f11515610f3657600080fd5b505050604051805190509050610f54565b610f518383611592565b90505b92915050565b60035481565b60066020526000908152604090205460ff1681565b60005433600160a060020a03908116911614610f9057600080fd5b600160a060020a03811660009081526006602052604090819020805460ff191690557fd7e9ec6e6ecd65492dce6bf513cd6867560d49544421d0783ddf06e76c24470c90829051600160a060020a03909116815260200160405180910390a150565b60001981565b60005433600160a060020a0390811691161461101357600080fd5b600160a060020a0381161561104b576000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b50565b6000805433600160a060020a0390811691161461106a57600080fd5b600160a060020a03821660009081526006602052604090205460ff16151561109157600080fd5b61109a82610a33565b600160a060020a038316600090815260026020526040808220919091556001805483900390559091507f61e6e66b0d6339b2980aecc6ccc0039736791f0ccde9ed512e789a7fbdd698c6908390839051600160a060020a03909216825260208201526040908101905180910390a15050565b6040604436101561111c57600080fd5b811580159061114f5750600160a060020a0333811660009081526005602090815260408083209387168352929052205415155b1561115957600080fd5b600160a060020a03338116600081815260056020908152604080832094881680845294909152908190208590557f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259085905190815260200160405180910390a3505050565b60008080606060643610156111d257600080fd5b600160a060020a0380881660009081526005602090815260408083203390941683529290522054600354909450611224906127109061121890889063ffffffff61155c16565b9063ffffffff6115bd16565b92506004548311156112365760045492505b60001984101561127857611250848663ffffffff6115d416565b600160a060020a03808916600090815260056020908152604080832033909416835292905220555b611288858463ffffffff6115d416565b600160a060020a0388166000908152600260205260409020549092506112b4908663ffffffff6115d416565b600160a060020a0380891660009081526002602052604080822093909355908816815220546112e9908363ffffffff6115e616565b600160a060020a03871660009081526002602052604081209190915583111561137f5760008054600160a060020a0316815260026020526040902054611335908463ffffffff6115e616565b60008054600160a060020a03908116825260026020526040808320939093559054811691908916906000805160206115f68339815191529086905190815260200160405180910390a35b85600160a060020a031687600160a060020a03166000805160206115f68339815191528460405190815260200160405180910390a350505050505050565b600160a060020a031660009081526002602052604090205490565b600080604060443610156113eb57600080fd5b6114066127106112186003548761155c90919063ffffffff16565b92506004548311156114185760045492505b611428848463ffffffff6115d416565b600160a060020a033316600090815260026020526040902054909250611454908563ffffffff6115d416565b600160a060020a033381166000908152600260205260408082209390935590871681522054611489908363ffffffff6115e616565b600160a060020a0386166000908152600260205260408120919091558311156115205760008054600160a060020a03168152600260205260409020546114d5908463ffffffff6115e616565b60008054600160a060020a0390811682526002602052604080832093909355905481169133909116906000805160206115f68339815191529086905190815260200160405180910390a35b84600160a060020a031633600160a060020a03166000805160206115f68339815191528460405190815260200160405180910390a35050505050565b60008083151561156f576000915061158b565b5082820282848281151561157f57fe5b041461158757fe5b8091505b5092915050565b600160a060020a03918216600090815260056020908152604080832093909416825291909152205490565b60008082848115156115cb57fe5b04949350505050565b6000828211156115e057fe5b50900390565b60008282018381101561158757fe00ddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3efa165627a7a723058207e0a2d0fecfab3a048ba2b63b43bbf3096e89958cb1f20a87002163895802c5e0029";

    public static final String FUNC_ADDBLACKLIST = "addBlackList";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_DEPRECATE = "deprecate";

    public static final String FUNC_DESTROYBLACKFUNDS = "destroyBlackFunds";

    public static final String FUNC_ISSUE = "issue";

    public static final String FUNC_PAUSE = "pause";

    public static final String FUNC_REDEEM = "redeem";

    public static final String FUNC_REMOVEBLACKLIST = "removeBlackList";

    public static final String FUNC_SETPARAMS = "setParams";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UNPAUSE = "unpause";

    public static final String FUNC__TOTALSUPPLY = "_totalSupply";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_ALLOWED = "allowed";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BALANCES = "balances";

    public static final String FUNC_BASISPOINTSRATE = "basisPointsRate";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_DEPRECATED = "deprecated";

    public static final String FUNC_GETBLACKLISTSTATUS = "getBlackListStatus";

    public static final String FUNC_GETOWNER = "getOwner";

    public static final String FUNC_ISBLACKLISTED = "isBlackListed";

    public static final String FUNC_MAX_UINT = "MAX_UINT";

    public static final String FUNC_MAXIMUMFEE = "maximumFee";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PAUSED = "paused";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_UPGRADEDADDRESS = "upgradedAddress";

    public static final Event ISSUE_EVENT = new Event("Issue",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event REDEEM_EVENT = new Event("Redeem",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event DEPRECATE_EVENT = new Event("Deprecate",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event PARAMS_EVENT = new Event("Params",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DESTROYEDBLACKFUNDS_EVENT = new Event("DestroyedBlackFunds",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ADDEDBLACKLIST_EVENT = new Event("AddedBlackList",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event REMOVEDBLACKLIST_EVENT = new Event("RemovedBlackList",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PAUSE_EVENT = new Event("Pause",
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event UNPAUSE_EVENT = new Event("Unpause",
            Arrays.<TypeReference<?>>asList());
    ;

    protected USDTAlaya(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider, Long chainId) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider, chainId);
    }

    protected USDTAlaya(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider, Long chainId) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider, chainId);
    }

    public RemoteCall<TransactionReceipt> addBlackList(String _evilUser) {
        final Function function = new Function(
                FUNC_ADDBLACKLIST, 
                Arrays.<Type>asList(new Address(_evilUser)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> approve(String _spender, BigInteger _value) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new Address(_spender),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deprecate(String _upgradedAddress) {
        final Function function = new Function(
                FUNC_DEPRECATE, 
                Arrays.<Type>asList(new Address(_upgradedAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> destroyBlackFunds(String _blackListedUser) {
        final Function function = new Function(
                FUNC_DESTROYBLACKFUNDS, 
                Arrays.<Type>asList(new Address(_blackListedUser)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> issue(BigInteger amount) {
        final Function function = new Function(
                FUNC_ISSUE, 
                Arrays.<Type>asList(new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> pause() {
        final Function function = new Function(
                FUNC_PAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> redeem(BigInteger amount) {
        final Function function = new Function(
                FUNC_REDEEM, 
                Arrays.<Type>asList(new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeBlackList(String _clearedUser) {
        final Function function = new Function(
                FUNC_REMOVEBLACKLIST, 
                Arrays.<Type>asList(new Address(_clearedUser)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setParams(BigInteger newBasisPoints, BigInteger newMaxFee) {
        final Function function = new Function(
                FUNC_SETPARAMS, 
                Arrays.<Type>asList(new Uint256(newBasisPoints),
                new Uint256(newMaxFee)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new Address(_to),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String _from, String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new Address(_from),
                new Address(_to),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> unpause() {
        final Function function = new Function(
                FUNC_UNPAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<USDTAlaya> deploy(Web3j web3j, Credentials credentials, GasProvider contractGasProvider, Long chainId, BigInteger _initialSupply, String _name, String _symbol, BigInteger _decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(_initialSupply),
                new Utf8String(_name),
                new Utf8String(_symbol),
                new Uint256(_decimals)));
        return deployRemoteCall(USDTAlaya.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor, chainId);
    }

    public static RemoteCall<USDTAlaya> deploy(Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider, Long chainId, BigInteger _initialSupply, String _name, String _symbol, BigInteger _decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Uint256(_initialSupply),
                new Utf8String(_name),
                new Utf8String(_symbol),
                new Uint256(_decimals)));
        return deployRemoteCall(USDTAlaya.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor, chainId);
    }

    public List<IssueEventResponse> getIssueEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ISSUE_EVENT, transactionReceipt);
        ArrayList<IssueEventResponse> responses = new ArrayList<IssueEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IssueEventResponse typedResponse = new IssueEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<IssueEventResponse> issueEventObservable(PlatonFilter filter) {
        return this.web3j.platonLogObservable(filter).map(new Func1<Log, IssueEventResponse>() {
            @Override
            public IssueEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ISSUE_EVENT, log);
                IssueEventResponse typedResponse = new IssueEventResponse();
                typedResponse.log = log;
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<IssueEventResponse> issueEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISSUE_EVENT));
        return issueEventObservable(filter);
    }

    public List<RedeemEventResponse> getRedeemEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REDEEM_EVENT, transactionReceipt);
        ArrayList<RedeemEventResponse> responses = new ArrayList<RedeemEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RedeemEventResponse typedResponse = new RedeemEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RedeemEventResponse> redeemEventObservable(PlatonFilter filter) {
        return this.web3j.platonLogObservable(filter).map(new Func1<Log, RedeemEventResponse>() {
            @Override
            public RedeemEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REDEEM_EVENT, log);
                RedeemEventResponse typedResponse = new RedeemEventResponse();
                typedResponse.log = log;
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<RedeemEventResponse> redeemEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REDEEM_EVENT));
        return redeemEventObservable(filter);
    }

    public List<DeprecateEventResponse> getDeprecateEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DEPRECATE_EVENT, transactionReceipt);
        ArrayList<DeprecateEventResponse> responses = new ArrayList<DeprecateEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DeprecateEventResponse typedResponse = new DeprecateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<DeprecateEventResponse> deprecateEventObservable(PlatonFilter filter) {
        return this.web3j.platonLogObservable(filter).map(new Func1<Log, DeprecateEventResponse>() {
            @Override
            public DeprecateEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DEPRECATE_EVENT, log);
                DeprecateEventResponse typedResponse = new DeprecateEventResponse();
                typedResponse.log = log;
                typedResponse.newAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<DeprecateEventResponse> deprecateEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPRECATE_EVENT));
        return deprecateEventObservable(filter);
    }

    public List<ParamsEventResponse> getParamsEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(PARAMS_EVENT, transactionReceipt);
        ArrayList<ParamsEventResponse> responses = new ArrayList<ParamsEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ParamsEventResponse typedResponse = new ParamsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.feeBasisPoints = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.maxFee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ParamsEventResponse> paramsEventObservable(PlatonFilter filter) {
        return this.web3j.platonLogObservable(filter).map(new Func1<Log, ParamsEventResponse>() {
            @Override
            public ParamsEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(PARAMS_EVENT, log);
                ParamsEventResponse typedResponse = new ParamsEventResponse();
                typedResponse.log = log;
                typedResponse.feeBasisPoints = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.maxFee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ParamsEventResponse> paramsEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PARAMS_EVENT));
        return paramsEventObservable(filter);
    }

    public List<DestroyedBlackFundsEventResponse> getDestroyedBlackFundsEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DESTROYEDBLACKFUNDS_EVENT, transactionReceipt);
        ArrayList<DestroyedBlackFundsEventResponse> responses = new ArrayList<DestroyedBlackFundsEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DestroyedBlackFundsEventResponse typedResponse = new DestroyedBlackFundsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._blackListedUser = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._balance = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<DestroyedBlackFundsEventResponse> destroyedBlackFundsEventObservable(PlatonFilter filter) {
        return this.web3j.platonLogObservable(filter).map(new Func1<Log, DestroyedBlackFundsEventResponse>() {
            @Override
            public DestroyedBlackFundsEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DESTROYEDBLACKFUNDS_EVENT, log);
                DestroyedBlackFundsEventResponse typedResponse = new DestroyedBlackFundsEventResponse();
                typedResponse.log = log;
                typedResponse._blackListedUser = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._balance = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<DestroyedBlackFundsEventResponse> destroyedBlackFundsEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DESTROYEDBLACKFUNDS_EVENT));
        return destroyedBlackFundsEventObservable(filter);
    }

    public List<AddedBlackListEventResponse> getAddedBlackListEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ADDEDBLACKLIST_EVENT, transactionReceipt);
        ArrayList<AddedBlackListEventResponse> responses = new ArrayList<AddedBlackListEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AddedBlackListEventResponse typedResponse = new AddedBlackListEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AddedBlackListEventResponse> addedBlackListEventObservable(PlatonFilter filter) {
        return this.web3j.platonLogObservable(filter).map(new Func1<Log, AddedBlackListEventResponse>() {
            @Override
            public AddedBlackListEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ADDEDBLACKLIST_EVENT, log);
                AddedBlackListEventResponse typedResponse = new AddedBlackListEventResponse();
                typedResponse.log = log;
                typedResponse._user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<AddedBlackListEventResponse> addedBlackListEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDEDBLACKLIST_EVENT));
        return addedBlackListEventObservable(filter);
    }

    public List<RemovedBlackListEventResponse> getRemovedBlackListEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REMOVEDBLACKLIST_EVENT, transactionReceipt);
        ArrayList<RemovedBlackListEventResponse> responses = new ArrayList<RemovedBlackListEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RemovedBlackListEventResponse typedResponse = new RemovedBlackListEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RemovedBlackListEventResponse> removedBlackListEventObservable(PlatonFilter filter) {
        return this.web3j.platonLogObservable(filter).map(new Func1<Log, RemovedBlackListEventResponse>() {
            @Override
            public RemovedBlackListEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REMOVEDBLACKLIST_EVENT, log);
                RemovedBlackListEventResponse typedResponse = new RemovedBlackListEventResponse();
                typedResponse.log = log;
                typedResponse._user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<RemovedBlackListEventResponse> removedBlackListEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REMOVEDBLACKLIST_EVENT));
        return removedBlackListEventObservable(filter);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(PlatonFilter filter) {
        return this.web3j.platonLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventObservable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(PlatonFilter filter) {
        return this.web3j.platonLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventObservable(filter);
    }

    public List<PauseEventResponse> getPauseEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(PAUSE_EVENT, transactionReceipt);
        ArrayList<PauseEventResponse> responses = new ArrayList<PauseEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            PauseEventResponse typedResponse = new PauseEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<PauseEventResponse> pauseEventObservable(PlatonFilter filter) {
        return this.web3j.platonLogObservable(filter).map(new Func1<Log, PauseEventResponse>() {
            @Override
            public PauseEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(PAUSE_EVENT, log);
                PauseEventResponse typedResponse = new PauseEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Observable<PauseEventResponse> pauseEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAUSE_EVENT));
        return pauseEventObservable(filter);
    }

    public List<UnpauseEventResponse> getUnpauseEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(UNPAUSE_EVENT, transactionReceipt);
        ArrayList<UnpauseEventResponse> responses = new ArrayList<UnpauseEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            UnpauseEventResponse typedResponse = new UnpauseEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<UnpauseEventResponse> unpauseEventObservable(PlatonFilter filter) {
        return this.web3j.platonLogObservable(filter).map(new Func1<Log, UnpauseEventResponse>() {
            @Override
            public UnpauseEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(UNPAUSE_EVENT, log);
                UnpauseEventResponse typedResponse = new UnpauseEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Observable<UnpauseEventResponse> unpauseEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UNPAUSE_EVENT));
        return unpauseEventObservable(filter);
    }

    public RemoteCall<BigInteger> _totalSupply() {
        final Function function = new Function(FUNC__TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> allowance(String _owner, String _spender) {
        final Function function = new Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new Address(_owner),
                new Address(_spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> allowed(String param0, String param1) {
        final Function function = new Function(FUNC_ALLOWED, 
                Arrays.<Type>asList(new Address(param0),
                new Address(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> balanceOf(String who) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new Address(who)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> balances(String param0) {
        final Function function = new Function(FUNC_BALANCES, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> basisPointsRate() {
        final Function function = new Function(FUNC_BASISPOINTSRATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> deprecated() {
        final Function function = new Function(FUNC_DEPRECATED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Boolean> getBlackListStatus(String _maker) {
        final Function function = new Function(FUNC_GETBLACKLISTSTATUS, 
                Arrays.<Type>asList(new Address(_maker)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> getOwner() {
        final Function function = new Function(FUNC_GETOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> isBlackListed(String param0) {
        final Function function = new Function(FUNC_ISBLACKLISTED, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> MAX_UINT() {
        final Function function = new Function(FUNC_MAX_UINT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> maximumFee() {
        final Function function = new Function(FUNC_MAXIMUMFEE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> paused() {
        final Function function = new Function(FUNC_PAUSED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> upgradedAddress() {
        final Function function = new Function(FUNC_UPGRADEDADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static USDTAlaya load(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider, Long chainId) {
        return new USDTAlaya(contractAddress, web3j, credentials, contractGasProvider, chainId);
    }

    public static USDTAlaya load(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider, Long chainId) {
        return new USDTAlaya(contractAddress, web3j, transactionManager, contractGasProvider, chainId);
    }

    public static class IssueEventResponse {
        public Log log;

        public BigInteger amount;
    }

    public static class RedeemEventResponse {
        public Log log;

        public BigInteger amount;
    }

    public static class DeprecateEventResponse {
        public Log log;

        public String newAddress;
    }

    public static class ParamsEventResponse {
        public Log log;

        public BigInteger feeBasisPoints;

        public BigInteger maxFee;
    }

    public static class DestroyedBlackFundsEventResponse {
        public Log log;

        public String _blackListedUser;

        public BigInteger _balance;
    }

    public static class AddedBlackListEventResponse {
        public Log log;

        public String _user;
    }

    public static class RemovedBlackListEventResponse {
        public Log log;

        public String _user;
    }

    public static class ApprovalEventResponse {
        public Log log;

        public String owner;

        public String spender;

        public BigInteger value;
    }

    public static class TransferEventResponse {
        public Log log;

        public String from;

        public String to;

        public BigInteger value;
    }

    public static class PauseEventResponse {
        public Log log;
    }

    public static class UnpauseEventResponse {
        public Log log;
    }
}
