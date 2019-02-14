package com.wulizhou.ct.log.autoconfigue.util;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectUtils {

	/**
	 * 将Object转化为对象
	 * @param from			原对象
	 * @param ignores		忽略的内容
	 * @return
	 */
	public static String object2String(Object from, String[] ignores) {
		
		if (null == from) {
			return "";
		}
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.valueToTree(from);
		if (CollectionUtil.isNotEmpty(ignores)) {
			for (String ignore : ignores) {
				if (StringUtils.isNotBlank(ignore)) {
					ignore = ignore.trim().replaceAll("\\.+", ".").replaceAll("^\\.", "").replaceAll("\\.$", "");
				}
				removeParam(node, ignore);
			}
		}
		return node.toString();
	}

	private static void removeParam(JsonNode node, String ignore) {
		if (!node.isNull()) {
			int splitLocation = ignore.indexOf(".");
			if (node instanceof ObjectNode) { 					// 为普通对象
				if (-1 == splitLocation) {						// 到达处理点
					((ObjectNode) node).remove(ignore);
				} else {
					removeParam(node.get(ignore.substring(0, splitLocation)), ignore.substring(splitLocation + 1));
				}
			} else if (node instanceof ArrayNode) { 			// 数组对象不直接处理，遍历数组时对象进行处理
				ArrayNode arrayNode = (ArrayNode) node;
				for (int i = 0; i < arrayNode.size(); i++) {
					removeParam(arrayNode.get(i), ignore);
				}
			} else {
			}
		}
	}
	
}
