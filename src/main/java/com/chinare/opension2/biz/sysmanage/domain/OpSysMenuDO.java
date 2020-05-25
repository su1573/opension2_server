package com.chinare.opension2.biz.sysmanage.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * ${comments}
 * 
 * @author chinare
 * @email devops@chinare.com.cn
 * @date 2019-10-13 18:31:54
 */
public class OpSysMenuDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//菜单编号
	private String menuid;
	//菜单名称
	private String menuname;
	//上层菜单编号
	private String upmenuid;
	//顶层菜单编号
	private String topmenuid;
	//菜单层级
	private String menulevel;
	//菜单顺序
	private Long sortorder;
	//页面关联路径
	private String linkpath;
	//子节点数
	private Long childcount;
	//菜单图片路径
	private String userdescription;
	//操作员
	private String operator;
	//入机日期
	private Date makedate;
	//入机时间
	private String maketime;
	//状态
	private String state;
	//菜单图标
	private String iconskin;

	/**
	 * 设置：菜单编号
	 */
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	/**
	 * 获取：菜单编号
	 */
	public String getMenuid() {
		return menuid;
	}
	/**
	 * 设置：菜单名称
	 */
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	/**
	 * 获取：菜单名称
	 */
	public String getMenuname() {
		return menuname;
	}
	/**
	 * 设置：上层菜单编号
	 */
	public void setUpmenuid(String upmenuid) {
		this.upmenuid = upmenuid;
	}
	/**
	 * 获取：上层菜单编号
	 */
	public String getUpmenuid() {
		return upmenuid;
	}
	/**
	 * 设置：顶层菜单编号
	 */
	public void setTopmenuid(String topmenuid) {
		this.topmenuid = topmenuid;
	}
	/**
	 * 获取：顶层菜单编号
	 */
	public String getTopmenuid() {
		return topmenuid;
	}
	/**
	 * 设置：菜单层级
	 */
	public void setMenulevel(String menulevel) {
		this.menulevel = menulevel;
	}
	/**
	 * 获取：菜单层级
	 */
	public String getMenulevel() {
		return menulevel;
	}
	/**
	 * 设置：菜单顺序
	 */
	public void setSortorder(Long sortorder) {
		this.sortorder = sortorder;
	}
	/**
	 * 获取：菜单顺序
	 */
	public Long getSortorder() {
		return sortorder;
	}
	/**
	 * 设置：页面关联路径
	 */
	public void setLinkpath(String linkpath) {
		this.linkpath = linkpath;
	}
	/**
	 * 获取：页面关联路径
	 */
	public String getLinkpath() {
		return linkpath;
	}
	/**
	 * 设置：子节点数
	 */
	public void setChildcount(Long childcount) {
		this.childcount = childcount;
	}
	/**
	 * 获取：子节点数
	 */
	public Long getChildcount() {
		return childcount;
	}
	/**
	 * 设置：菜单图片路径
	 */
	public void setUserdescription(String userdescription) {
		this.userdescription = userdescription;
	}
	/**
	 * 获取：菜单图片路径
	 */
	public String getUserdescription() {
		return userdescription;
	}
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
	 * 设置：状态
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 获取：状态
	 */
	public String getState() {
		return state;
	}
	/**
	 * 设置：菜单图标
	 */
	public void setIconskin(String iconskin) {
		this.iconskin = iconskin;
	}
	/**
	 * 获取：菜单图标
	 */
	public String getIconskin() {
		return iconskin;
	}
}
