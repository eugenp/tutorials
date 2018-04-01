package service.impl;

import java.util.List;

import service.CommentService;
import bean.Comment;
import dao.CommentDao;
import dao.DaoFactory;

public class CommentServiceImpl implements CommentService {
	//Dao的注入
	private CommentDao commentDao;
	//初始化DAO
	public CommentServiceImpl() {
		commentDao = DaoFactory.getInstance().getCommentDao();
	}
	public List<Comment> listComment() {
		return commentDao.findAll();
	}

}
