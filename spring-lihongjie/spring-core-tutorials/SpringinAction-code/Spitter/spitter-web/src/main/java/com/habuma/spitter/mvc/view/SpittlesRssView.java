package com.habuma.spitter.mvc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import com.habuma.spitter.domain.Spittle;
import com.sun.syndication.feed.rss.Item;

public class SpittlesRssView extends AbstractRssFeedView {

  @Override
  protected List<Item> buildFeedItems(
          Map<String, Object> model,
          HttpServletRequest request,
          HttpServletResponse response) throws Exception {

    @SuppressWarnings("unchecked")
    List<Spittle> spittles = (List<Spittle>) model.get("spittles");
    List<Item> items = new ArrayList<Item>();
    for (Spittle spittle : spittles) {
      Item item = new Item();
      item.setTitle(spittle.getText());
      item.setPubDate(spittle.getWhen());
      item.setAuthor(spittle.getSpitter().getFullName());
      items.add(item);
    }
    
    return items;
  }

}
