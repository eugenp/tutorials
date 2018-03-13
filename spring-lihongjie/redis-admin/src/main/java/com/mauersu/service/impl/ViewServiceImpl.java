package com.mauersu.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mauersu.service.ViewService;
import com.mauersu.util.Constant;
import com.mauersu.util.Pagination;
import com.mauersu.util.RKey;
import com.mauersu.util.RedisApplication;
import com.mauersu.util.RefreshModeEnum;
import com.mauersu.util.ShowTypeEnum;
import com.mauersu.util.ztree.RedisZtreeUtil;
import com.mauersu.util.ztree.ZNode;

@Service
public class ViewServiceImpl extends RedisApplication implements ViewService, Constant {

	@Override
	public void changeRefreshMode(String mode) {
		refreshMode = RefreshModeEnum.valueOf(mode);
	}
	
	@Override
	public Set<ZNode> getLeftTree() {
		return getLeftTree(refreshMode);
	}
	
	private Set<ZNode> getLeftTree(RefreshModeEnum refreshMode) {
		switch(refreshMode) {
		case manually:
			break;
		case auto:
			for(Map<String, Object> redisServerMap : RedisApplication.redisServerCache) {
				RedisZtreeUtil.refreshRedisNavigateZtree((String)redisServerMap.get("name"));
			}
			break;
		}
		return new TreeSet<ZNode>(redisNavigateZtree);
		
	}
	
	@Override
	public Set<ZNode> refresh() {
		boolean permit = getUpdatePermition();
		Set<ZNode> zTree = null;
		if(permit) {
			try {
logCurrentTime("try {");
				for(Map<String, Object> redisServerMap : RedisApplication.redisServerCache) {
logCurrentTime("refreshKeys(" + (String)redisServerMap.get("name"));
					for(int i=0;i<=REDIS_DEFAULT_DB_SIZE;i++) {
						refreshKeys((String)redisServerMap.get("name"), i);
					}
logCurrentTime("refreshServerTree(" + (String)redisServerMap.get("name"));
					zTree = refreshServerTree((String)redisServerMap.get("name"), DEFAULT_DBINDEX);
					// test limit flow System.out.println("yes permit");
logCurrentTime("continue");
				}
logCurrentTime("finally {");
			} finally {
				finishUpdate();
			}
		} else {
			// test limit flow System.out.println("no permit");
		}
		return zTree;
	}
	
	@Override
	public void refreshAllKeys() {
		boolean permit = getUpdatePermition();
		try {
			for(Map<String, Object> redisServerMap : RedisApplication.redisServerCache) {
				for(int i=0;i<=REDIS_DEFAULT_DB_SIZE;i++) {
					refreshKeys((String)redisServerMap.get("name"), i);
				}
			}
		} finally {
			finishUpdate();
		}
	}
	
	private void refreshKeys(String serverName, int dbIndex) {
		RedisTemplate redisTemplate = RedisApplication.redisTemplatesMap.get(serverName);
		initRedisKeysCache(redisTemplate, serverName, dbIndex);
	}

	private Set<ZNode> refreshServerTree(String serverName,
			int dbIndex) {
		 RedisZtreeUtil.refreshRedisNavigateZtree(serverName) ;
		 return new TreeSet<ZNode>(redisNavigateZtree);
	}

	@Override
	public Set<RKey> getRedisKeys(Pagination pagination, String serverName, String dbIndex, String[] keyPrefixs, String queryKey, String queryValue) {
		List<RKey> allRedisKeys = redisKeysListMap.get(serverName + DEFAULT_SEPARATOR + dbIndex);
		
		Set<RKey> resultRedisKeys = null;
		
		if(allRedisKeys == null || allRedisKeys.size()==0) {
			pagination.setMaxentries(0);
			resultRedisKeys = new TreeSet<RKey>();
			return resultRedisKeys;
		}
		
		if(keyPrefixs == null || keyPrefixs.length == 0) {
			logCurrentTime("keyPrefixs == null");
			if(StringUtils.isEmpty(queryValue)) {
				logCurrentTime("new TreeSet<RKey>(allRedisKeys);");
				int toIndex = pagination.getToIndex()>allRedisKeys.size()?allRedisKeys.size():pagination.getToIndex();
				List<RKey> resultList = allRedisKeys.subList(pagination.getFromIndex(), toIndex);
				resultRedisKeys = new TreeSet<RKey>(resultList);
				pagination.setMaxentries(allRedisKeys.size());
			} else {
				List<RKey> queryRedisKeys = getQueryRedisKeys(allRedisKeys, queryKey, queryValue);
				Collections.sort(queryRedisKeys);//arraylist sort
				int toIndex = pagination.getToIndex()>queryRedisKeys.size()?queryRedisKeys.size():pagination.getToIndex();
				List<RKey> resultList = queryRedisKeys.subList(pagination.getFromIndex(), toIndex);
				resultRedisKeys = new TreeSet<RKey>(resultList);
				pagination.setMaxentries(queryRedisKeys.size());
			}
		} else {
			StringBuffer keyPrefix = new StringBuffer("");
			for(String prefix: keyPrefixs) {
				keyPrefix.append(prefix).append(DEFAULT_REDISKEY_SEPARATOR);
			}
			List<RKey> conformRedisKeys = getConformRedisKeys(allRedisKeys, keyPrefix.toString());
			Collections.sort(conformRedisKeys);//arraylist sort
			int toIndex = pagination.getToIndex()>conformRedisKeys.size()?conformRedisKeys.size():pagination.getToIndex();
			List<RKey> resultList = conformRedisKeys.subList(pagination.getFromIndex(), toIndex);
			resultRedisKeys = new TreeSet<RKey>(resultList);
			pagination.setMaxentries(conformRedisKeys.size());
		}
		return resultRedisKeys;
	}

	private List<RKey> getQueryRedisKeys(List<RKey> allRedisKeys, String queryKey, String queryValue) {
		List<RKey> rKeySet = new ArrayList<RKey>();
		for(RKey rKey : allRedisKeys) {
			switch(queryKey) {
			case MIDDLE_KEY:
				if(rKey.contains(queryValue)) {
					rKeySet.add(rKey);
				}
				break;
			case HEAD_KEY:
				if(rKey.startsWith(queryValue)) {
					rKeySet.add(rKey);
				}
				break;
			case TAIL_KEY:
				if(rKey.endsWith(queryValue)) {
					rKeySet.add(rKey);
				}
				break;
			}
		}
		return rKeySet;
	}

	private List<RKey> getConformRedisKeys(List<RKey> allRedisKeys, String keyPrefix) {
		List<RKey> rKeySet = new ArrayList<RKey>();
		for(RKey rKey : allRedisKeys) {
			if(rKey.startsWith(keyPrefix)) {
				rKeySet.add(rKey);
			}
		}
		return rKeySet;
	}

	@Override
	public void changeShowType(String state) {
		showType = ShowTypeEnum.valueOf(state);
		switch(showType) {
		case show:
			//get redisKeys again if init keys with ShowTypeEnum.hide
			refreshAllKeys();
			break;
		case hide:
			break;
		}
	}

}
