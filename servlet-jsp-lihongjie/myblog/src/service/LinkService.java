package service;

import java.util.List;

import bean.Link;

public interface LinkService {
	
	public boolean addLink(Link link);
	
	public List<Link> listLink();
	
	public boolean editLink(Link link);
	
	public boolean deleteLink(Link link);
	
	public Link findLinkById(Integer id);
}
