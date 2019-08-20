/*
rebuild - Building your business-systems freely.
Copyright (C) 2018 devezhao <zhaofang123@gmail.com>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package com.rebuild.web.base;

import cn.devezhao.commons.web.ServletUtils;
import cn.devezhao.persist4j.Record;
import cn.devezhao.persist4j.engine.ID;
import com.alibaba.fastjson.JSON;
import com.rebuild.server.Application;
import com.rebuild.server.configuration.portals.BaseLayoutManager;
import com.rebuild.server.configuration.portals.NavManager;
import com.rebuild.server.configuration.portals.SharableManager;
import com.rebuild.server.metadata.EntityHelper;
import com.rebuild.server.service.bizz.UserHelper;
import com.rebuild.server.service.configuration.LayoutConfigService;
import com.rebuild.web.BaseControll;
import com.rebuild.web.PortalsConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 导航菜单设置
 * 
 * @author zhaofang123@gmail.com
 * @since 09/19/2018
 */
@Controller
@RequestMapping("/app/settings/")
public class NavSettings extends BaseControll implements PortalsConfiguration {
	
	@Override
	public void sets(String entity, HttpServletRequest request, HttpServletResponse response) { }
	@Override
	public void gets(String entity, HttpServletRequest request, HttpServletResponse response) { }
	
	@RequestMapping(value = "nav-settings", method = RequestMethod.POST)
	public void sets(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ID user = getRequestUser(request);
		boolean toAll = getBoolParameter(request, "toAll", false);
		if (toAll) {
			toAll = UserHelper.isAdmin(user);
		}
		
		JSON config = ServletUtils.getRequestJson(request);
		ID cfgid = getIdParameter(request, "cfgid");
		if (cfgid != null && !NavManager.instance.isSelf(user, cfgid)) {
			cfgid = null;
		}
		
		Record record;
		if (cfgid == null) {
			record = EntityHelper.forNew(EntityHelper.LayoutConfig, user);
			record.setString("belongEntity", "N");
			record.setString("applyType", BaseLayoutManager.TYPE_NAV);
		} else {
			record = EntityHelper.forUpdate(cfgid, user);
		}
		record.setString("config", config.toJSONString());
		record.setString("shareTo", toAll ? SharableManager.SHARE_ALL : SharableManager.SHARE_SELF);
		Application.getBean(LayoutConfigService.class).createOrUpdate(record);
		
		writeSuccess(response);
	}
	
	@RequestMapping(value = "nav-settings", method = RequestMethod.GET)
	public void gets(HttpServletRequest request, HttpServletResponse response) {
		JSON config = NavManager.instance.getNav(getRequestUser(request));
		writeSuccess(response, config);
	}
}
