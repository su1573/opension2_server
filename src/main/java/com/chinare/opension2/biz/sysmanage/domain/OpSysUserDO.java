package com.chinare.opension2.biz.sysmanage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * ${comments}
 * 
 * @author chinare
 * @email devops@chinare.com.cn
 * @date 2019-10-13 18:31:57
 */
public class OpSysUserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//操作员
	private String operator;
	//入机日期
	private Date makedate;
	//入机时间
	private String maketime;
	//用户编码
	private String usercode;
	//用户姓名
	private String username;
	//机构编码
	private String comcode;
	//登录密码
	private String password;
	//电子邮箱
	private String email;
	//电话
	private String phone;
	//用户描述
	private String userdescription;
	//01-有效
//02-无效

	private String userstate;
	//有效开始日期
	private Date validstartdate;
	//有效结束日期
	private Date validenddate;
	//错误记录
	private Long logonwrongnumber;
	//最后退出时间
	private Date lastlogondate;
	//01-未锁定
//02-锁定
	private String lockstatus;
	//密码修改日期
	private Date alterpassworddate;
	//用户在线状态
	private Long onlinestate;

	/**
	 * 设置：操作员
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * 获取：操作员
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * 设置：入机日期
	 */
	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}
	/**
	 * 获取：入机日期
	 */
	public Date getMakedate() {
		return makedate;
	}
	/**
	 * 设置：入机时间
	 */
	public void setMaketime(String maketime) {
		this.maketime = maketime;
	}
	/**
	 * 获取：入机时间
	 */
	public String getMaketime() {
		return maketime;
	}
	/**
	 * 设置：用户编码
	 */
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	/**
	 * 获取：用户编码
	 */
	public String getUsercode() {
		return usercode;
	}
	/**
	 * 设置：用户姓名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：用户姓名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：机构编码
	 */
	public void setComcode(String comcode) {
		this.comcode = comcode;
	}
	/**
	 * 获取：机构编码
	 */
	public String getComcode() {
		return comcode;
	}
	/**
	 * 设置：登录密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：登录密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：电子邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：电子邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：用户描述
	 */
	public void setUserdescription(String userdescription) {
		this.userdescription = userdescription;
	}
	/**
	 * 获取：用户描述
	 */
	public String getUserdescription() {
		return userdescription;
	}
	/**
	 * 设置：01-有效
02-无效

	 */
	public void setUserstate(String userstate) {
		this.userstate = userstate;
	}
	/**
	 * 获取：01-有效
02-无效

	 */
	public String getUserstate() {
		return userstate;
	}
	/**
	 * 设置：有效开始日期
	 */
	public void setValidstartdate(Date validstartdate) {
		this.validstartdate = validstartdate;
	}
	/**
	 * 获取：有效开始日期
	 */
	public Date getValidstartdate() {
		return validstartdate;
	}
	/**
	 * 设置：有效结束日期
	 */
	public void setValidenddate(Date validenddate) {
		this.validenddate = validenddate;
	}
	/**
	 * 获取：有效结束日期
	 */
	public Date getValidenddate() {
		return validenddate;
	}
	/**
	 * 设置：错误记录
	 */
	public void setLogonwrongnumber(Long logonwrongnumber) {
		this.logonwrongnumber = logonwrongnumber;
	}
	/**
	 * 获取：错误记录
	 */
	public Long getLogonwrongnumber() {
		return logonwrongnumber;
	}
	/**
	 * 设置：最后退出时间
	 */
	public void setLastlogondate(Date lastlogondate) {
		this.lastlogondate = lastlogondate;
	}
	/**
	 * 获取：最后退出时间
	 */
	public Date getLastlogondate() {
		return lastlogondate;
	}
	/**
	 * 设置：01-未锁定
02-锁定
	 */
	public void setLockstatus(String lockstatus) {
		this.lockstatus = lockstatus;
	}
	/**
	 * 获取：01-未锁定
02-锁定
	 */
	public String getLockstatus() {
		return lockstatus;
	}
	/**
	 * 设置：密码修改日期
	 */
	public void setAlterpassworddate(Date alterpassworddate) {
		this.alterpassworddate = alterpassworddate;
	}
	/**
	 * 获取：密码修改日期
	 */
	public Date getAlterpassworddate() {
		return alterpassworddate;
	}
	/**
	 * 设置：用户在线状态
	 */
	public void setOnlinestate(Long onlinestate) {
		this.onlinestate = onlinestate;
	}
	/**
	 * 获取：用户在线状态
	 */
	public Long getOnlinestate() {
		return onlinestate;
	}
}
