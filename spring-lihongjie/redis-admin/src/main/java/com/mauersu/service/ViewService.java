package com.mauersu.service;

import java.util.Set;

import com.mauersu.util.Pagination;
import com.mauersu.util.RKey;
import com.mauersu.util.ztree.ZNode;

public interface ViewService {

	Set<ZNode> getLeftTree();

	Set<RKey> getRedisKeys(Pagination pagination, String serverName, String dbIndex,
			String[] keyPrefixs, String queryKey, String queryValue);

	Set<ZNode> refresh();

	void changeRefreshMode(String mode);

	void changeShowType(String state);

	void refreshAllKeys();

}
