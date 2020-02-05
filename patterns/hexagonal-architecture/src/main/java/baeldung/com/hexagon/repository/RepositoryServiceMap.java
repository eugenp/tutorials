package baeldung.com.hexagon.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import baeldung.com.hexagon.domain.ToDo;

@Component("RepositoryServiceMap")
public class RepositoryServiceMap implements RepositoryService {

	Map<Long, ToDo> map;
	
	@Override
	public String getMyName() {
		return "RepositoryServiceMap";
	}

	@Override
	public void delete(Long id) {
		map.remove(id);
	}

	@Override
	public ToDo update(ToDo entity) throws Exception {
		map.put(entity.getId(), entity);
		return entity;
	}

	@Override
	public ToDo create(ToDo entity) throws Exception {
		TreeMap<Long, ToDo> treeMap = (TreeMap) map;
		Entry<Long, ToDo> lastToDo = treeMap.lastEntry();
		long nextKey = lastToDo.getKey()+1;
		entity.setId(nextKey);
		map.put(nextKey, entity);
		return entity;
	}

	@Override
	public Iterable<ToDo> getList() {
		return map.values();
	}

	public RepositoryServiceMap() {
		map = new TreeMap<>();
		List<ToDo >list = Arrays.asList(new ToDo(1l, "have A beer"), new ToDo(2l, "join a metal band"));
		list.forEach(action->{
			map.put(action.getId(), action);
		});
	}

}
