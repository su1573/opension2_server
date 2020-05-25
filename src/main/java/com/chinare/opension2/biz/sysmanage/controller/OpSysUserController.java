package com.chinare.opension2.biz.sysmanage.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinare.opension2.biz.sysmanage.domain.OpSysUserDO;
import com.chinare.opension2.biz.sysmanage.service.OpSysUserService;
import com.chinare.opension2.common.utils.PageUtils;
import com.chinare.opension2.common.utils.Query;
import com.chinare.opension2.common.utils.R;

/**
 * ${comments}
 * 
 * @author chinare
 * @email devops@chinare.com.cn
 * @date 2019-10-13 18:31:57
 */
 
@Controller
@RequestMapping("/sysmanage/opSysUser")
public class OpSysUserController {
	@Autowired
	private OpSysUserService opSysUserService;
	
	@GetMapping()
	@RequiresPermissions("sysmanage:opSysUser:opSysUser")
	String OpSysUser(){
	    return "biz/sysmanage/opSysUser/opSysUser";
	}
	
	@ResponseBody
	@PostMapping("/list")
	@RequiresPermissions("sysmanage:opSysUser:opSysUser")
	public PageUtils list(@RequestBody Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OpSysUserDO> opSysUserList = opSysUserService.list(query);
		int total = opSysUserService.count(query);
		PageUtils pageUtils = new PageUtils(opSysUserList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("sysmanage:opSysUser:add")
	String add(){
	    return "biz/sysmanage/opSysUser/add";
	}

	@GetMapping("/edit/{operator}")
	@RequiresPermissions("sysmanage:opSysUser:edit")
	String edit(@PathVariable("operator") String operator,Model model){
		OpSysUserDO opSysUser = opSysUserService.get(operator);
		model.addAttribute("opSysUser", opSysUser);
	    return "biz/sysmanage/opSysUser/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("sysmanage:opSysUser:add")
	public R save( OpSysUserDO opSysUser){
		if(opSysUserService.save(opSysUser)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("sysmanage:opSysUser:edit")
	public R update(@RequestBody OpSysUserDO opSysUser){
		opSysUserService.update(opSysUser);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("sysmanage:opSysUser:remove")
	public R remove(@RequestBody Map<String, Object> params){
		if(opSysUserService.remove(params)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("sysmanage:opSysUser:batchRemove")
	public R remove(@RequestParam("ids[]") String[] operators){
		opSysUserService.batchRemove(operators);
		return R.ok();
	}
	
}
