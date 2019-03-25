package com.excilys.cdb.model;

import java.util.List;

public class Page<T> {
	
	private List<T> data;
	private int pageSize = 30;
	private int index = 0;
	private int nbLine;

	public Page(List<T> data) {
		this.data = data;
		nbLine = data.size();
	}

	public Page(List<T> data, int pageSize) {
		this.data = data;
		this.pageSize = pageSize;
		nbLine = data.size();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
		nbLine = data.size();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getIndex() {
		return index;
	}
	
	public void setIndex (int index) {
		this.index = index;
	}
	
	public int getNbLine() {
		return nbLine;
	}
	
	public boolean previous() {
		if (--index * pageSize >= 0) {
			return true;
		} else {
			index++;
			return false;
		}
	}
	
	public boolean next() {
		if (++index * pageSize <= nbLine) {;
			return true;
		} else {
			index--;
			return false;
		}
	}
	
	public List<T> getPage() {
		return data.subList(index * pageSize, Math.min((index + 1) * pageSize, nbLine));
	}
}
