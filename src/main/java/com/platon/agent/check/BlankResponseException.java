package com.platon.agent.check;


/**
 * 合约调用成功，但结果为空
 * @Description:
 */
public class BlankResponseException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public BlankResponseException(String msg){
		super(msg);
	}
}
