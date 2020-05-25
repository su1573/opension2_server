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

import com.chinare.opension2.biz.sysmanage.domain.OpSysMenuDO;
import com.chinare.opension2.biz.sysmanage.service.OpSysMenuService;
import com.chinare.opension2.common.utils.PageUtils;
import com.chinare.opension2.common.utils.Query;
import com.chinare.opension2.common.utils.R;

/**
 * ${comments}
 * 
 * @author chinare
 * @email devops@chinare.com.cn
 * @date 2019-10-13 18:31:54
 */
 
@Controller
@RequestMapping("/sysmanage/opSysMenu")
public class OpSysMenuController {
	@Autowired
	private OpSysMenuService opSysMenuService;
	
	@GetMapping()
	@RequiresPermissions("sysmanage:opSysMenu:opSysMenu")
	String OpSysMenu(){
	    return "biz/sysmanage/opSysMenu/opSysMenu";
	}
	
	@ResponseBody
	@PostMapping("/list")
	@RequiresPermissions("sysmanage:opSysMenu:opSysMenu")
	public PageUtils list(@RequestBody Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OpSysMenuDO> opSysMenuList = opSysMenuService.list(query);
		int total = opSysMenuService.count(query);
		PageUtils pageUtils = new PageUtils(opSysMenuList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("sysmanage:opSysMenu:add")
	String add(){
	    return "biz/sysmanage/opSysMenu/add";
	}

	@GetMapping("/edit/{menuid}")
	@RequiresPermissions("sysmanage:opSysMenu:edit")
	String edit(@PathVariable("menuid") String menuid,Model model){
		OpSysMenuDO opSysMenu = opSysMenuService.get(menuid);
		model.addAttribute("opSysMenu", opSysMenu);
	    return "biz/sysmanage/opSysMenu/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("sysmanage:opSysMenu:add")
	public R save( OpSysMenuDO opSysMenu){
		if(opSysMenuService.save(opSysMenu)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("sysmanage:opSysMenu:edit")
	public R update(@RequestBody OpSysMenuDO opSysMenu){
		opSysMenuService.update(opSysMenu);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("sysmanage:opSysMenu:remove")
	public R remove(@RequestBody Map<String, Object> params){
		if(opSysMenuService.remove(params)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("sysmanage:opSysMenu:batchRemove")
	public R remove(@RequestParam("ids[]") String[] menuids){
		opSysMenuService.batchRemove(menuids);
		return R.ok();
	}
	
}
