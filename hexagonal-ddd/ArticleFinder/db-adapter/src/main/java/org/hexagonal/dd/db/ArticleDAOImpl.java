package org.hexagonal.dd.db;

import org.hexagonal.ddd.domain.Article;
import org.hexagonal.ddd.domain.ports.infra.IArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hexagonal.dd.utils.Constants.*;

public class ArticleDAOImpl implements IArticleDAO {

    @PostConstruct
    public void test() {
        jdbcTemplate.execute(CREATE_TABLE);
        jdbcTemplate.execute("insert into article values ('b1', 'article1', 'ravi')");
        jdbcTemplate.execute("insert into article values ('b2', 'article2', 'ravi')");
        jdbcTemplate.execute("insert into article values ('b3', 'article3', 'raj')");
        jdbcTemplate.execute("insert into article values ('b4', 'article4', 'chandra')");
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String addArticle(Article article) {
        UUID uuid = UUID.randomUUID();
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid.toString());
        params.put("title", article.getTitle());
        params.put("author", article.getAuthor());
        int retVal = jdbcTemplate.update(INSERT_ARTICLE, params);
        if (retVal > 0) {
            return uuid.toString();
        }
        return "Insert Operation Failed";
    }

    @Override
    public String deleteArticle(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        int retVal = jdbcTemplate.update(DELETE_ARTICLE, params);
        if (retVal > 0) {
            return id;
        }
        return "Insert Operation Failed";
    }

    @Override
    public String updateArticle(Article article) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", article.getId());
        params.put("title", article.getTitle());
        params.put("author", article.getAuthor());
        int retVal = jdbcTemplate.update(UPDATE_ARTICLE, params);
        if (retVal > 0) {
            return article.getId();
        }
        return "Insert Operation Failed";
    }

    @Override
    public List<Article> getArticles() {
        return jdbcTemplate.query(GET_TOP_10_ARTICLES,
                BeanPropertyRowMapper.newInstance(Article.class));
    }

    @Override
    public Article getArticleById(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return jdbcTemplate.queryForObject(GET_ARTICLE_BY_ID,
                Article.class, params);
    }
}
