/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package webapp.jobtask.server;

import java.util.List;

import webapp.jobtask.client.TreeService;
import webapp.jobtask.server.controller.Server;
import webapp.jobtask.shared.CustomTreeItemDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TreeServiceImpl extends RemoteServiceServlet implements TreeService {

	@Override
	public List<CustomTreeItemDTO> get() {
		return Server.getInstance().getTreeItemDAO().listAll();
	}

	@Override
	public void update(CustomTreeItemDTO item) {
		Server.getInstance().getTreeItemDAO().update(item);
	}

	@Override
	public CustomTreeItemDTO add(CustomTreeItemDTO item) {
		return Server.getInstance().getTreeItemDAO().create(item);

	}

	@Override
	public void delete(List<Long> list) {
		System.out.println(list.toString());
		Server.getInstance().getTreeItemDAO().delete(list);
		
	}

	
}
