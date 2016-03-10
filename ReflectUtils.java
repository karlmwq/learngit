package com.zhongqinglv.platform.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class ReflectUtils {

	/**
	 * 反射方法，打印对象的属性，方法，构造器属性
	 * 
	 * @param obj
	 *            被反射对象
	 */
	public static void reflect(Object obj) {
		Class<?> cls = obj.getClass();
		System.out.println("************构  造  器************");
		Constructor<?>[] constructors = cls.getConstructors();
		for (Constructor<?> constructor : constructors) {
			System.out.println("构造器名称:" + constructor.getName() + "\t" + "    "
					+ "构造器参数类型:"
					+ Arrays.toString(constructor.getParameterTypes()));
		}
		System.out.println("************属     性************");
		Field[] fields = cls.getDeclaredFields();
		// cls.getFields() 该方法只能访问共有的属性
		// cls.getDeclaredFields() 可以访问私有属性
		for (Field field : fields) {
			System.out.println("属性名称:" + field.getName() + "\t" + "属性类型:"
					+ field.getType() + "\t");
		}
		System.out.println("************方   法************");
		Method[] methods = cls.getMethods();
		for (Method method : methods) {
			System.out.println("方法名:" + method.getName() + "\t" + "方法返回类型："
					+ method.getReturnType() + "\t" + "方法参数类型:"
					+ Arrays.toString(method.getParameterTypes()));
		}
	}

	/**
	 * 通过构造函数实例化对象
	 * 
	 * @param className
	 *            类的全路径名称
	 * @param parameterTypes
	 *            参数类型
	 * @param initargs
	 *            参数值
	 * @return
	 */
	public static Object constructorNewInstance(String className,
			Class[] parameterTypes, Object[] initargs) {
		try {
			Constructor<?> constructor = (Constructor<?>) Class.forName(
					className).getDeclaredConstructor(parameterTypes); // 暴力反射
			constructor.setAccessible(true);
			return constructor.newInstance(initargs);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	/**
	 * 暴力反射获取字段值
	 * 
	 * @param fieldName
	 *            属性名
	 * @param obj
	 *            实例对象
	 * @return 属性值
	 */
	public static Object getFieldValue(String propertyName, Object obj) {
		try {
			Field field = obj.getClass().getDeclaredField(propertyName);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 暴力反射获取字段值
	 * 
	 * @param propertyName
	 *            属性名
	 * @param object
	 *            实例对象
	 * @return 字段值
	 */
	public static Object getProperty(String propertyName, Object object) {
		try {

			PropertyDescriptor pd = new PropertyDescriptor(propertyName, object
					.getClass());
			Method method = pd.getReadMethod();
			return method.invoke(object);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 设置字段值
	 * 
	 * @param obj
	 *            实例对象
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            新的字段值
	 * @return
	 */
	public static void setProperties(Object object, String propertyName,
			Object value) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		PropertyDescriptor pd = new PropertyDescriptor(propertyName, object
				.getClass());
		Method methodSet = pd.getWriteMethod();
		methodSet.invoke(object, value);
	}

	/**
	 * 通过BeanUtils工具包获取反射获取字段值,注意此值是以字符串形式存在的,它支持属性连缀操作:如,.对象.属性
	 * 
	 * @param propertyName
	 *            属性名
	 * @param object
	 *            实例对象
	 * @return 字段值
	 */
	public static String getBeanInfoProperty(String propertyName, Object object) {
		try {
			return BeanUtils.getProperty(object, propertyName);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 通过BeanUtils工具包获取反射获取字段值,注意此值是以字符串形式存在的
	 * 
	 * @param object
	 *            实例对象
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            字段值
	 * @return
	 */
	public static void setBeanInfoProperty(Object object, String propertyName,
			String value) {
		try {
			BeanUtils.setProperty(object, propertyName, value);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 通过BeanUtils工具包获取反射获取字段值,注意此值是以对象属性的实际类型
	 * 
	 * @param propertyName
	 *            属性名
	 * @param object
	 *            实例对象
	 * @return 字段值
	 */
	public static Object getPropertyUtilProperty(String propertyName,
			Object object) {
		try {
			return PropertyUtils.getProperty(object, propertyName);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 通过BeanUtils工具包获取反射获取字段值,注意此值是以对象属性的实际类型,这是PropertyUtils与BeanUtils的根本区别
	 * 
	 * @param object
	 *            实例对象
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            字段值
	 * @return
	 */
	public static void setPropertyUtilProperty(Object object,
			String propertyName, Object value) {
		try {
			PropertyUtils.setProperty(object, propertyName, value);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 设置字段值
	 * 
	 * @param propertyName
	 *            字段名
	 * @param obj
	 *            实例对象
	 * @param value
	 *            新的字段值
	 * @return
	 */
	public static void setFieldValue(Object obj, String propertyName,
			Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(propertyName);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static List<String> traverseObjProp(Object obj, String propNames[]) {
		List<String> propValues = new ArrayList<String>();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass());
		} catch (IntrospectionException ex) {
			throw new RuntimeException(ex);
		}
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		if(propNames==null||propNames.length==0){
			for (Field field : fields) {
				if(Modifier.isTransient(field.getModifiers())){
					continue;
				}
				if(field.getName().equals("serialVersionUID")){
					continue;
				}
				//判断属性上是否有反射注解，若有注解，判断是否可以序列化
				ReflectField annotatoin = field.getAnnotation(ReflectField.class);
				if(annotatoin!=null&&!annotatoin.serialize()){
					continue;
				}
				field.setAccessible(true);
				try {
					Object value = field.get(obj);
					propValues.add(value==null?null:value.toString());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}else{
			for(String propName:propNames){
				propValues.add(getBeanInfoProperty(propName, obj));
			}
		}
		return propValues;
	}

	/**
	 * 设置字段值
	 * 
	 * @param className
	 *            类的全路径名称
	 * @param methodName
	 *            调用方法名
	 * @param parameterTypes
	 *            参数类型
	 * @param values
	 *            参数值
	 * @param object
	 *            实例对象
	 * @return
	 */
	public static Object methodInvoke(String className, String methodName,
			Class[] parameterTypes, Object[] values, Object object) {
		try {
			Method method = Class.forName(className).getDeclaredMethod(
					methodName, parameterTypes);
			method.setAccessible(true);
			return method.invoke(object, values);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
