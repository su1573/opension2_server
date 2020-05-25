package com.chinare.opension2.biz.sysmanage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 


 * 
 * @author chinare
 * @email devops@chinare.com.cn
 * @date 2019-10-13 18:31:56
 */
public class OpSysCodeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//最后操作日期
	private Date lastoptdate;
	//最后操作时间
	private String lastopttime;
	//关联代码类型
	private String linkcodetype;
	//客户服务平台处理人
	private String customerserviceper;
	//代码类型
	private String codetype;
	//代码编号
	private String code;
	//代码名称
	private String codename;
	//关联代码编号
	private String linkcode;
	//排序
	private Long sortorder;
	//其它标识
	private String othersign;
	//创建操作员
	private String createoperator;
	//创建日期
	private Date createoptdate;
	//创建时间
	private String createopttime;
	//最后操作员
	private String lastoperator;

	/**
	 * 设置：最后操作日期
	 */
	public void setLastoptdate(Date lastoptdate) {
		this.lastoptdate = lastoptdate;
	}
	/**
	 * 获取：最后操作日期
	 */
	public Date getLastoptdate() {
		return lastoptdate;
	}
	/**
	 * 设置：最后操作时间
	 */
	public void setLastopttime(String lastopttime) {
		this.lastopttime = lastopttime;
	}
	/**
	 * 获取：最后操作时间
	 */
	public String getLastopttime() {
		return lastopttime;
	}
	/**
	 * 设置：关联代码类型
	 */
	public void setLinkcodetype(String linkcodetype) {
		this.linkcodetype = linkcodetype;
	}
	/**
	 * 获取：关联代码类型
	 */
	public String getLinkcodetype() {
		return linkcodetype;
	}
	/**
	 * 设置：客户服务平台处理人
	 */
	public void setCustomerserviceper(String customerserviceper) {
		this.customerserviceper = customerserviceper;
	}
	/**
	 * 获取：客户服务平台处理人
	 */
	public String getCustomerserviceper() {
		return customerserviceper;
	}
	/**
	 * 设置：代码类型
	 */
	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}
	/**
	 * 获取：代码类型
	 */
	public String getCodetype() {
		return codetype;
	}
	/**
	 * 设置：代码编号
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：代码编号
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：代码名称
	 */
	public void setCodename(String codename) {
		this.codename = codename;
	}
	/**
	 * 获取：代码名称
	 */
	public String getCodename() {
		return codename;
	}
	/**
	 * 设置：关联代码编号
	 */
	public void setLinkcode(String linkcode) {
		this.linkcode = linkcode;
	}
	/**
	 * 获取：关联代码编号
	 */
	public String getLinkcode() {
		return linkcode;
	}
	/**
	 * 设置：排序
	 */
	public void setSortorder(Long sortorder) {
		this.sortorder = sortorder;
	}
	/**
	 * 获取：排序
	 */
	public Long getSortorder() {
		return sortorder;
	}
	/**
	 * 设置：其它标识
	 */
	public void setOthersign(String othersign) {
		this.othersign = othersign;
	}
	/**
	 * 获取：其它标识
	 */
	public String getOthersign() {
		return othersign;
	}
	/**
	 * 设置：创建操作员
	 */
	public void setCreateoperator(String createoperator) {
		this.createoperator = createoperator;
	}
	/**
	 * 获取：创建操作员
	 */
	public String getCreateoperator() {
		return createoperator;
	}
	/**
	 * 设置：创建日期
	 */
	public void setCreateoptdate(Date createoptdate) {
		this.createoptdate = createoptdate;
	}
	/**
	 * 获取：创建日期
	 */
	public Date getCreateoptdate() {
		return createoptdate;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateopttime(String createopttime) {
		this.createopttime = createopttime;
	}
	/**
	 * 获取：创建时间
	 */
	public String getCreateopttime() {
		return createopttime;
	}
	/**
	 * 设置：最后操作员
	 */
	public void setLastoperator(String lastoperator) {
		this.lastoperator = lastoperator;
	}
	/**
	 * 获取：最后操作员
	 */
	public String getLastoperator() {
		return lastoperator;
	}
}
