package com.laptrinhjavaweb.paging;

import com.laptrinhjavaweb.sort.Sorter;

public class PageRequest implements Pageble {

	private Integer page;
	private Integer maxPageItem;
	private Sorter sorted;

	public PageRequest(Integer page, Integer maxPageItem, Sorter sorted) {
		this.page = page;
		this.maxPageItem = maxPageItem;
		this.sorted = sorted;
	}

	@Override
	public Integer getPage() {
		return this.page;
	}

	@Override
	public Integer getOffset() {
		if (this.page != null && this.maxPageItem != null) {
			return (this.page - 1) * this.maxPageItem;
		}
		return null;
	}

	@Override
	public Integer getLimit() {
		return this.maxPageItem;
	}

	@Override
	public Sorter getSorted() {
		if (this.sorted != null) {
			return this.sorted;
		}
		return null;
	}

}
