/*
rebuild - Building your business-systems freely.
Copyright (C) 2019 devezhao <zhaofang123@gmail.com>

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

package com.rebuild.web.dashboard;

import com.rebuild.server.service.bizz.UserService;
import com.rebuild.web.MvcResponse;
import com.rebuild.web.TestSupportWithMVC;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author devezhao zhaofang123@gmail.com
 * @since 2019/03/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DashboardControllTest extends TestSupportWithMVC {

	@Test
	public void testDashGets() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/dashboard/dash-gets");
		MvcResponse resp = perform(builder, UserService.ADMIN_USER);
		System.out.println(resp);
		Assert.assertTrue(resp.isSuccess());
	}
}
