package service;

import bean.Comment;

import java.util.List;

public interface CommentService {
    //评论列表
    public List<Comment> listComment();
}
