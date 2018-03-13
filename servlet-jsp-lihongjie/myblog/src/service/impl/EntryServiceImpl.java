package service.impl;

import java.util.ArrayList;
import java.util.List;

import service.EntryService;
import vo.EntryVo;
import bean.Category;
import bean.Entry;
import dao.DaoFactory;
import dao.EntryDao;

public class EntryServiceImpl implements EntryService {
	
	private EntryDao entryDao;
	
	public EntryServiceImpl() {
		entryDao = DaoFactory.getInstance().getEntryDao();
	}
	public boolean addEntry(Entry entry) {
		entryDao.save(entry);
		return true;
	}

	public List<Entry> listEntry() {
		return entryDao.findAll();
	}

	public boolean editEntry(Entry entry) {
		return false;
	}

	public boolean deleteEntry(Entry entry) {
		entryDao.delete(entry);
		return true;
	}

	public Entry findById(Object id) {
		return entryDao.findById(id);
	}
	//vo
	public List<EntryVo> listEntryVo() {
		List<EntryVo> listvo = new ArrayList<EntryVo>();
		List<Entry> list = entryDao.findAll();
		EntryVo vo = null;
		for(Entry entry:list) {
			vo = new EntryVo();
			//封装 和解封
			vo.setAllowComment(entry.getAllowComment());
			vo.setTitle(entry.getTitle());
			vo.setCommentHit(entry.getCommentHit());
			vo.setContent(entry.getContent());
			
			vo.setCreateTime(entry.getCreateTime());
			vo.setHits(entry.getHits());
			vo.setStatus(entry.getStatus());
			vo.setUpdateTime(entry.getUpdateTime());
			vo.setId(entry.getId());
			
			//根据类别id拿到类别信息
			Integer id = entry.getId();
			Category category = DaoFactory.getInstance().getCategoryDao().findById(id);
			vo.setCategory(category);
			//放入listVo
			listvo.add(vo);
		}
			return listvo;
	}

}
