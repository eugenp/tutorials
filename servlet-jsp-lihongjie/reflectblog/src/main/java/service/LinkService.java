package service;

import bean.Link;

import java.util.List;

public interface LinkService {

    public boolean addLink(Link link);

    public List<Link> listLink();

    public boolean editLink(Link link);

    public boolean deleteLink(Link link);

    public Link findLinkById(Integer id);
}
