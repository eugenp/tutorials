package service.impl;

import java.util.List;

import dao.DaoFactory;
import dao.LinkDao;

import bean.Link;
import service.LinkService;

public class LinkServiceImpl implements LinkService{
	//Dao的注入
	private LinkDao linkDao;
	//初始化DAO
	public LinkServiceImpl(){
		linkDao = DaoFactory.getInstance().getLinkDao();
	}
	//实现添加友情链接列表
	public boolean addLink(Link link) {
		linkDao.save(link);
		return true;
	}
	//友情链接列表
	public List<Link> listLink() {
		
		return linkDao.findAll();
	}
	//修改友情链接
	public boolean editLink(Link link) {
		linkDao.update(link);
		return true;
	}
	//删除友情链接列表
	public boolean deleteLink(Link link) {
		linkDao.delete(link);
		return true;
	}
	//通过id找到友情链接列表
	public Link findLinkById(Integer id) {
		
		return linkDao.findById(id);
	}

}
