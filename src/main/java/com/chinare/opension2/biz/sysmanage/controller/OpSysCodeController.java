package com.chinare.opension2.biz.sysmanage.controller;

import java.util.Date;
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

import com.chinare.opension2.biz.sysmanage.domain.OpSysCodeDO;
import com.chinare.opension2.biz.sysmanage.service.OpSysCodeService;
import com.chinare.opension2.common.utils.PageUtils;
import com.chinare.opension2.common.utils.Query;
import com.chinare.opension2.common.utils.R;

/**
 * 


 * 
 * @author chinare
 * @email devops@chinare.com.cn
 * @date 2019-10-13 18:31:56
 */
 
@Controller
@RequestMapping("/sysmanage/opSysCode")
public class OpSysCodeController {
	@Autowired
	private OpSysCodeService opSysCodeService;
	
	@GetMapping()
	@RequiresPermissions("sysmanage:opSysCode:opSysCode")
	String OpSysCode(){
	    return "biz/sysmanage/opSysCode/opSysCode";
	}
	
	@ResponseBody
	@PostMapping("/list")
	@RequiresPermissions("sysmanage:opSysCode:opSysCode")
	public PageUtils list(@RequestBody Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OpSysCodeDO> opSysCodeList = opSysCodeService.list(query);
		int total = opSysCodeService.count(query);
		PageUtils pageUtils = new PageUtils(opSysCodeList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("sysmanage:opSysCode:add")
	String add(){
	    return "biz/sysmanage/opSysCode/add";
	}

	@GetMapping("/edit/{lastoptdate}")
	@RequiresPermissions("sysmanage:opSysCode:edit")
	String edit(@PathVariable("lastoptdate") Date lastoptdate, Model model){
		OpSysCodeDO opSysCode = opSysCodeService.get(lastoptdate);
		model.addAttribute("opSysCode", opSysCode);
	    return "biz/sysmanage/opSysCode/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("sysmanage:opSysCode:add")
	public R save( OpSysCodeDO opSysCode){
		if(opSysCodeService.save(opSysCode)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("sysmanage:opSysCode:edit")
	public R update(@RequestBody OpSysCodeDO opSysCode){
		opSysCodeService.update(opSysCode);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("sysmanage:opSysCode:remove")
	public R remove(@RequestBody Map<String, Object> params){
		if(opSysCodeService.remove(params)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("sysmanage:opSysCode:batchRemove")
	public R remove(@RequestParam("ids[]") Date[] lastoptdates){
		opSysCodeService.batchRemove(lastoptdates);
		return R.ok();
	}
	
}
