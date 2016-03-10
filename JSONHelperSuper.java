package com.zhongqinglv.platform.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class JSONHelperSuper  {
	/**
	 * json 属性过滤实现的方式：
	 *	object ：属性的所有者
	 *	String[]  要过滤的属性队列
	 *	对非过滤字短进行空值保护
	 */
	private Object obj;
	
	private Map<Class<?>,String[]> includes;
	
	private Map<Class<?>,String[]> excludes;
	
	public JSONHelperSuper(){}
	public Object getObj() {
		return obj;
	}
	
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
	public Map<Class<?>, String[]> getIncludes() {
		return includes;
	}
	public void setIncludes(Map<Class<?>, String[]> includes) {
		this.includes = includes;
	}
	public Map<Class<?>, String[]> getExcludes() {
		return excludes;
	}
	public void setExcludes(Map<Class<?>, String[]> excludes) {
		this.excludes = excludes;
	}
	/**
	 * first step:
	 * 创建对象 ：并获取全部方法
	 */
	public void init(){
		Class<?> clazz = obj.getClass();
		 Field[]  fields =   clazz.getFields();
		 System.out.println("init ");
		 for(Field f:fields){
			 System.out.println(f.getName());
		 }
		
	}
	public static Object nulllProtectedForProperties(Object obj) throws NoSuchFieldException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		 List<String> getMethods = new ArrayList<String>();
		Class<?> clazz = obj.getClass();
		Field[] fields  = clazz.getDeclaredFields();
		if(fields.length>0){
			for(Field f:fields){
				String name = f.getName();
				String getMethodName = "get"+name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
				getMethods.add(getMethodName);
			}
		}
		//先解析get方法 获取相应的值
		for(String getm:getMethods){
				Method m = clazz.getDeclaredMethod(getm);
					String key = getm.substring(3,getm.length());
					String keyName = key.substring(0, 1).toLowerCase()+key.substring(1, key.length());
					Field f = clazz.getDeclaredField(keyName);
					Class<?> tc = f.getType();	
					if( tc.equals(String.class)){
						String mresut = (String) m.invoke(obj);
						if(mresut==null){
							String setMothodName = "set"+key;
							Method ms = clazz.getDeclaredMethod(setMothodName, String.class);
							ms.invoke(obj, "");
							} 
					}
					else if(tc.equals(Integer.class)){
						Integer mresut = (Integer) m.invoke(obj);
						if(mresut==null){
							String setMothodName = "set"+key;
							Method ms = clazz.getDeclaredMethod(setMothodName, Integer.class);
							ms.invoke(obj, 0);
							} 
					}
					else if(tc.equals(List.class)){
						List<?> mresut = (List<?>) m.invoke(obj);
						if(mresut==null){
							String setMothodName = "set"+key;
							Method ms = clazz.getDeclaredMethod(setMothodName, List.class);
							ms.invoke(obj,new  ArrayList<>());
							} 
					}
					else if(tc.equals(Map.class)){
						@SuppressWarnings("unchecked")
						Map<String,Object> mresut = (Map<String,Object>) m.invoke(obj);
						if(mresut==null){
							String setMothodName = "set"+key;
							Method ms = clazz.getDeclaredMethod(setMothodName, Map.class);
							ms.invoke(obj,new  HashMap<String,Object>());
							} 
					}
					else if(tc.equals(Boolean.class)){
						Boolean mresut = (Boolean) m.invoke(obj);
						if(mresut==null){
							String setMothodName = "set"+key;
							Method ms = clazz.getDeclaredMethod(setMothodName, Boolean.class);
							ms.invoke(obj,false);
							} 
					}
					
				} 
		return obj;
		
	}
	public static void main(String[] args) throws NoSuchFieldException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
			Test rs = new Test();
			rs.setId(null);
			rs.setName(null);
			rs.setList(null);
			rs.setMap(null);
			rs.setBer(null);
			
		rs = 	(Test) JSONHelperSuper.nulllProtectedForProperties(rs);
		System.out.println(rs);
			
		
	}
	
}
