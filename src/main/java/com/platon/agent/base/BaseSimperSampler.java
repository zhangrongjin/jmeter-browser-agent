package com.platon.agent.base;


import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;

/**
 * 基础设置类
 * @author Rongjin Zhang
 *
 */
public abstract class BaseSimperSampler extends AbstractJavaSamplerClient {
	
	
	protected String chainTypeA = "alaya";
	protected String chainTypeP = "platon";
	protected String chainType;
	
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("chainType", this.chainTypeA);
		this.setArguments(params);
		return params;
	}

	@Override
	public void setupTest(JavaSamplerContext arg) {
		this.chainType = arg.getParameter("chainType");
    }

	public abstract void setArguments(Arguments params);

}
