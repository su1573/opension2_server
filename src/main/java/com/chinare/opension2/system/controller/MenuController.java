package com.chinare.opension2.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinare.opension2.common.annotation.Log;
import com.chinare.opension2.common.config.ApplicationContextRegister;
import com.chinare.opension2.common.config.Constant;
import com.chinare.opension2.common.controller.BaseController;
import com.chinare.opension2.common.domain.Tree;
import com.chinare.opension2.common.utils.R;
import com.chinare.opension2.system.dao.UserDao;
import com.chinare.opension2.system.domain.MenuDO;
import com.chinare.opension2.system.service.AuthCenterService;
import com.chinare.opension2.system.service.MenuService;

/**
 * @author demo 1992lcg@163.com
 */
@RequestMapping("/sys/menu")
@Controller
public class MenuController extends BaseController {
	String prefix = "system/menu";
	@Autowired
	MenuService menuService;
	
	@Value("${generator.authServer}")
	private String authServer;
	
	@Autowired
	UserDao userMapper;

	@Value("${spring.application.name}")
	private String applEglName;

	@RequiresPermissions("sys:menu:menu")
	@GetMapping()
	String menu(Model model) {
		return prefix + "/menu";
	}

	@RequestMapping("/list")
	@ResponseBody
	Map<String, Object> list(@RequestParam Map<String, Object> params) {
		params.put("appEglName", applEglName.replace("_server", "").trim());
		params.put("userId", this.getUserId());
		params.put("username", this.getUsername());
		
		List<MenuDO> menuDos = null;
		if("AUTH-CENTER".equals(authServer)) {
			AuthCenterService authCenterService = ApplicationContextRegister.getBean(AuthCenterService.class);
			menuDos = authCenterService.getMenuList(params);
		}else {
			menuDos = userMapper.getMenuList(params);
		}
		// 初始化顶级menu。
		List<Menu> topMenus = new ArrayList<Menu>();
		// 递归填装子menu。
		for (MenuDO menuDo : menuDos) {
			if (menuDo.getParentId() == 0) {
				Menu menu = new Menu();
				// menu.setLevel(1);
				menu.setTitle(menuDo.getNameChn());
				menu.setPath("/menu" + menuDo.getMenuId());
				menu.setComponent("Layout");
				menu.setName("menu" + menuDo.getMenuId());
				menu.setIcon((menuDo.getIcon() == null || "".equals(menuDo.getIcon())) ? "iconfont icon-gongnengdingyi": menuDo.getIcon());
				menu.setNameEgl(menuDo.getNameEgl());
				topMenus.add(menu);
				addChildrens(1, menu, menuDo.getMenuId(), menuDos);
			}
		}
		Set<String> perms = menuService.listPerms(this.getUserId());
		Map<String, Object> map = new HashMap<>();
		map.put("data", topMenus);
		map.put("perms", perms);
		map.put("name", this.getUser().getName());
		return map;
	}

	/**
	 * 添加子菜单
	 * 
	 * @param menu
	 * @param menuDos
	 */
	private void addChildrens(int level, Menu menu, Long menuId, List<MenuDO> menuDos) {
		int menuLevel = level + 1;
		List<Menu> ChildMenus = new ArrayList<Menu>();
		int menuId_int = menuId.intValue();
		for (MenuDO menuDo : menuDos) {
			if (menuDo.getType() != 2 && menuDo.getParentId() == menuId_int) {
				Menu ChildMenu = new Menu();
				ChildMenu.setTitle(menuDo.getNameChn());
				ChildMenu.setPath("/menu" + menuDo.getMenuId());
				String componentStr = "aml/test";
				if (menuDo.getType() == 1) {
					componentStr = "biz/" + menuDo.getPerms().replace(":", "/");
				} else if (menuLevel == 2) {
					componentStr = "aml/test";
				} else if (menuLevel == 3) {
					componentStr = "aml/test" + menuLevel;
				}
				ChildMenu.setComponent(componentStr);
				ChildMenu.setName("menu" + menuDo.getMenuId());
				ChildMenu.setIcon((menuDo.getIcon() == null || "".equals(menuDo.getIcon())) ? "iconfont icon-gongnengdingyi": menuDo.getIcon());
				ChildMenu.setNameEgl(menuDo.getNameEgl());
				ChildMenus.add(ChildMenu);
				addChildrens(menuLevel, ChildMenu, menuDo.getMenuId(), menuDos);
			}
		}
		if (ChildMenus.size() > 0) {
			menu.setChildrens(ChildMenus);
		}
	}

	@Log("添加菜单")
	@RequiresPermissions("sys:menu:add")
	@GetMapping("/add/{pId}")
	String add(Model model, @PathVariable("pId") Long pId) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", menuService.get(pId).getNameChn());
		}
		return prefix + "/add";
	}

	@Log("编辑菜单")
	@RequiresPermissions("sys:menu:edit")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		MenuDO mdo = menuService.get(id);
		Long pId = mdo.getParentId();
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", menuService.get(pId).getNameChn());
		}
		model.addAttribute("menu", mdo);
		return prefix + "/edit";
	}

	@Log("保存菜单")
	@RequiresPermissions("sys:menu:add")
	@PostMapping("/save")
	@ResponseBody
	R save(MenuDO menu) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (menuService.save(menu) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}

	@Log("更新菜单")
	@RequiresPermissions("sys:menu:edit")
	@PostMapping("/update")
	@ResponseBody
	R update(MenuDO menu) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (menuService.update(menu) > 0) {
			return R.ok();
		} else {
			return R.error(1, "更新失败");
		}
	}

	@Log("删除菜单")
	@RequiresPermissions("sys:menu:remove")
	@PostMapping("/remove")
	@ResponseBody
	R remove(Long id) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (menuService.remove(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}

	@GetMapping("/tree")
	@ResponseBody
	Tree<MenuDO> tree() {
		Tree<MenuDO> tree = menuService.getTree();
		return tree;
	}

	@GetMapping("/tree/{roleId}")
	@ResponseBody
	Tree<MenuDO> tree(@PathVariable("roleId") Long roleId) {
		Tree<MenuDO> tree = menuService.getTree(roleId);
		return tree;
	}

	private class Menu {
		@SuppressWarnings("unused")
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		@SuppressWarnings("unused")
		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		@SuppressWarnings("unused")
		public String getComponent() {
			return component;
		}

		public void setComponent(String component) {
			this.component = component;
		}

		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@SuppressWarnings("unused")
		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		@SuppressWarnings("unused")
		public List<Menu> getChildrens() {
			return childrens;
		}

		public void setChildrens(List<Menu> childrens) {
			this.childrens = childrens;
		}

		private String title;
		private String path;
		private String component;
		private String name;
		private String nameEgl;
		@SuppressWarnings("unused")
		public String getNameEgl() {
			return nameEgl;
		}

		@SuppressWarnings("unused")
		public void setNameEgl(String nameEgl) {
			this.nameEgl = nameEgl;
		}
		private String icon;
		private List<Menu> childrens;
	}
}
