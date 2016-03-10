package com.zhongqinglv.platform.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

/**
 * 
 * @author karlmax
 * 分页bean设计
 * pageNow  :当前页
 * pageSize :分页大小
 * pageTotalNumber:分页总数
 * totalSize ：总条数
 * results 返回结果集合
 * conMap   条件map
 * obj     条件bean
 * url    自定义链接
 * queryStart 查询起始
 * pageRange 分页自定义范围
 * pageMaxShow  每页显示的最大分页数目
 * index 首页
 * end  尾页
 * 
 */
public class PageBean {
	
private Integer  pageNow = 1;

private Integer pageSize = 10;

private Integer  pageTotalNumber;

private Integer  totalSize;

private  List<T> results;

private  Map<String,Object> conMap;

private Object obj;

private String url;

private Integer queryStart = 0;

private  List<Integer> pageRange;

private Integer  pageMaxShow = 10;

private Integer index;



//基本构造方法
public  PageBean(){}

public PageBean(Integer pageNow,Integer pageSize){
	this.pageNow = pageNow;
	this.pageSize = pageSize;
	
}

public PageBean(Integer pageNow,Integer pageSize,Object obj){
	this.pageNow = pageNow;
	this.pageSize = pageSize;
	this.obj = obj;
}
public PageBean(Integer pageNow,Integer pageSize,Map<String,Object> conMap){
	this.pageNow = pageNow;
	this.pageSize = pageSize;
	this.conMap = conMap;
}

public PageBean(Integer pageNow,Integer pageSize,Object obj,Map<String,Object> conMap){
	this.pageNow = pageNow;
	this.pageSize = pageSize;
	this.conMap = conMap;
	this.obj = obj;
}
public Integer getPageNow() {
	return pageNow;
}

public void setPageNow(Integer pageNow) {
	this.pageNow = pageNow;
}

public Integer getPageSize() {
	return pageSize;
}

public void setPageSize(Integer pageSize) {
	this.pageSize = pageSize;
}



public Integer getPageMaxShow() {
	return pageMaxShow;
}

public void setPageMaxShow(Integer pageMaxShow) {
	this.pageMaxShow = pageMaxShow;
}

public Integer getPageTotalNumber() {
	pageTotalNumber = (this.totalSize+this.pageSize/2)/this.pageSize;
	return pageTotalNumber;
}

public void setPageTotalNumber(Integer pageTotalNumber) {
	this.pageTotalNumber = pageTotalNumber;
}

public Integer getTotalSize() {
	return totalSize;
}

public void setTotalSize(Integer totalSize) {
	this.totalSize = totalSize;
}

public List<T> getResults() {
	return results;
}

public void setResults(List<T> results) {
	this.results = results;
}

public Map<String, Object> getConMap() {
	return conMap;
}

public void setConMap(Map<String, Object> conMap) {
	this.conMap = conMap;
}

public Object getObj() {
	return obj;
}

public void setObj(Object obj) {
	this.obj = obj;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

public Integer getQueryStart() {
	queryStart =  (this.pageNow-1)*this.pageSize;
	return queryStart;
}

public void setQueryStart(Integer queryStart) {
	this.queryStart = queryStart;
}

public List<Integer> getPageRange() {
	 this.pageTotalNumber = (this.totalSize+this.pageSize/2)/this.pageSize;
	pageRange = PageBean.createPagRange(this.pageMaxShow, this.pageNow, this.pageTotalNumber);
	return pageRange;
}

public void setPageRange(List<Integer> pageRange) {

	this.pageRange = pageRange;
}

public Integer getIndex() {
	return index;
}

public void setIndex(Integer index) {
	this.index = index;
}



public static List<Integer>  createPagRange(Integer  max,Integer pageNow,Integer pageTotalSize){
	//正常生产序列
	List<Integer> index = new ArrayList<Integer>();
	
	Integer half = max/2;
	
	//生成前半段
	for( int i =1 ;i<=half;i++){
		Integer result = pageNow -i;
		if(result>0){
			index.add(result);
		}else{
			break;
		}
		
	}
	Collections.sort(index);
	//生成中间段
	index.add(pageNow);
	//生成后半段
	for(int i=1;i<=half;i++){
		Integer result = pageNow +i;
		if(result<=pageTotalSize){
			index.add(result);
		}else{
			break;
		}
	}
	
	
	return index;
}

	public static void main(String[] args) {
		PageBean  page = new PageBean(5,5);
		page.setTotalSize(100);
		page.setPageMaxShow(7);
		List<Integer>  list = page.getPageRange();
		for(Integer i:list){
			System.out.println("pageRange"+i);
		}
		Integer pageTotal = page.getPageTotalNumber();
		System.out.println(pageTotal);
	}

}
