package com.imguang.demo.web.common.vo;

public interface BaseVO {

	/**
	 * 添加需要序列化的属性
	 * @param paramName
	 * @return
	 */
	public BaseVO include(String paramName);

	/**
	 * 添加需要序列化的属性集合
	 * @param paramNames
	 * @return
	 */
	public BaseVO include(String... paramNames);

	/**
	 * 排除需要序列化的属性
	 * @param paramName
	 * @return
	 */
	public BaseVO exclude(String paramName);

	/**
	 * 排除需要序列化的属性集合
	 * @param paramNames
	 * @return
	 */
	public BaseVO exclude(String... paramNames);

	/**
	 * 生成序列化对象 <br/>
	 * 若没有调用过include，则使用排除模式 <br/>
	 * 若调用过include，则使用包含模式 <br/>
	 * @return
	 */
	public Object toData();

}
