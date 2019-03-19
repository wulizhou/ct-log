package com.wulizhou.ct.log.core.util;

import java.util.Collection;

public class CollectionUtil {

	public static boolean isEmpty(Collection<?> collection) {
		return null == collection || 0 == collection.size();
	}
	
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}
	
	public static <T> boolean isEmpty(T[] arrays) {
		return null == arrays || 0 == arrays.length;
	}
	
	public static <T> boolean isNotEmpty(T[] arrays) {
		return !isEmpty(arrays);
	}
	
	/**
	 * 获取集合第一个值，若不存在返回defaultValue
	 * @param collection
	 * @param defaultValue
	 * @return
	 */
	public static <T> T getFirst(Collection<T> collection, T defaultValue) {
		return isEmpty(collection) ? defaultValue : collection.iterator().next();
	}
	
	/**
	 * 获取集合第一个值，若不存在返回null
	 * @param collection
	 * @param defaultValue
	 * @return
	 */
	public static<T> T getFirst(Collection<T> collection) {
		return getFirst(collection, null);
	}
	
}
