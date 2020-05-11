package com.platon.agent.check;


/**
 * 合约调用异常
 * @Description:
 */
public class ContractInvokeException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ContractInvokeException(String msg){
		super(msg);
	}
}
