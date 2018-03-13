package com.mauersu.util;

public class Pagination {

	private int maxentries;
	private int items_per_page;
	private int num_display_entries;
	private int current_page;
	private int num_edge_entries;
	private String link_to;
	private String prev_text;
	private String next_text;
	private String ellipse_text;
	private boolean prev_show_always;
	private boolean next_show_always;
	
	public Pagination() {
		items_per_page = 10;
		num_display_entries = 10;
		current_page = 0;
		num_edge_entries = 0;
		link_to = "#";
		prev_text = "Prev";
		next_text = "Next";
		ellipse_text = "...";
		prev_show_always = true;
		next_show_always = true;
	}
	
	public int getMaxentries() {
		return maxentries;
	}
	public void setMaxentries(int maxentries) {
		this.maxentries = maxentries;
	}
	public int getItems_per_page() {
		return items_per_page;
	}
	public void setItems_per_page(int items_per_page) {
		this.items_per_page = items_per_page;
	}
	public int getNum_display_entries() {
		return num_display_entries;
	}
	public void setNum_display_entries(int num_display_entries) {
		this.num_display_entries = num_display_entries;
	}
	public int getCurrent_page() {
		return current_page;
	}
	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}
	public int getNum_edge_entries() {
		return num_edge_entries;
	}
	public void setNum_edge_entries(int num_edge_entries) {
		this.num_edge_entries = num_edge_entries;
	}
	public String getLink_to() {
		return link_to;
	}
	public void setLink_to(String link_to) {
		this.link_to = link_to;
	}
	public String getPrev_text() {
		return prev_text;
	}
	public void setPrev_text(String prev_text) {
		this.prev_text = prev_text;
	}
	public String getNext_text() {
		return next_text;
	}
	public void setNext_text(String next_text) {
		this.next_text = next_text;
	}
	public String getEllipse_text() {
		return ellipse_text;
	}
	public void setEllipse_text(String ellipse_text) {
		this.ellipse_text = ellipse_text;
	}
	public boolean isPrev_show_always() {
		return prev_show_always;
	}
	public void setPrev_show_always(boolean prev_show_always) {
		this.prev_show_always = prev_show_always;
	}
	public boolean isNext_show_always() {
		return next_show_always;
	}
	public void setNext_show_always(boolean next_show_always) {
		this.next_show_always = next_show_always;
	}

	public int getFromIndex() {
		int fromIndex = items_per_page * (current_page );
		return fromIndex;
	}

	public int getToIndex() {
		int toIndex = items_per_page * (current_page + 1);
		return toIndex;
	}

	public Pagination createLinkTo() {
		String link_to = (this.getLink_to()== null ||  this.getLink_to().equals("")) ? "&" :this.getLink_to();
		link_to += "&items_per_page=" + items_per_page;
		link_to += "&num_display_entries=" + num_display_entries;
		link_to += "&visit_page=__id__";
		link_to += "&num_edge_entries=" + num_edge_entries;
		link_to += "&prev_text=" + prev_text;
		link_to += "&next_text=" + next_text;
		link_to += "&ellipse_text=" + ellipse_text;
		link_to += "&prev_show_always=" + prev_show_always;
		link_to += "&next_show_always=" + next_show_always;
		this.setLink_to(link_to);
		return this;
	}
}
