package net.wildpig.base.common.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @FileName VariableExpander.java
 * @Description: 
 *
 * @Date Sep 17, 2015 
 * @author YangShengJun
 * @version 1.0
 * 
 */
public class VariableExpander {

	private Properties variables; // 原始属性信息

	private String pre = "${"; // 定义前缀

	private String post = "}"; // 定义后缀

	private Map<String, String> cache; // 解析过的嵌套值cache

	public VariableExpander(Properties variables) {
		this.variables = variables;
		cache = new HashMap<String, String>();
	}

	/**
	 * 清除cache
	 * 
	 * @see 参考的JavaDoc
	 */
	public void clearCache() {
		cache.clear();
	}

	/**
	 * 获得支持嵌套的属性值
	 * 
	 * @param name
	 *            ：名称
	 * @return String
	 * @see 参考的JavaDoc
	 */
	public String getValue(String name) {
		String value = this.variables.getProperty(name);
		return expandVariables(value);
	}

	private String expandVariables(String source) {
		String result = (String) this.cache.get(source);

		if (source == null || result != null) {
			return result;
		}

		int fIndex = source.indexOf(this.pre);

		if (fIndex == -1) {
			return source;
		}

		StringBuffer sb = new StringBuffer(source);

		while (fIndex > -1) {
			int lIndex = sb.indexOf(this.post);

			int start = fIndex + this.pre.length();

			if (fIndex == 0) {
				String varName = sb.substring(start, start + lIndex - this.pre.length());
				sb.replace(fIndex, fIndex + lIndex + 1, this.variables.getProperty(varName));
			} else {
				String varName = sb.substring(start, lIndex);
				sb.replace(fIndex, lIndex + 1, this.variables.getProperty(varName));
			}

			fIndex = sb.indexOf(this.pre);
		}

		result = sb.toString();

		this.cache.put(source, result);

		return result;
	}

}
