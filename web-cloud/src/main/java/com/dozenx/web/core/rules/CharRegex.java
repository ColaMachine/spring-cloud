package com.dozenx.web.core.rules;

import com.dozenx.common.util.CastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判定特殊字符
 * @Author :王作品
 * @Date: 2018/3/16 14:08
 * @params :  * @param null
 *
 */
public class CharRegex extends Rule {
	public static final String regEx = "^.*[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？].*$";
	private Pattern pattern;

	public CharRegex(){
		pattern = Pattern.compile(regEx);
	}

	@Override
	public boolean valid() throws Exception {
		if(value != null && !value.equals("")) {
			Matcher matcher = this.pattern.matcher(CastUtil.toString(value));
			if(matcher.matches()) {
				message = "含有特殊字符";
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	@Override
	public boolean valid(Object value) throws Exception {
		if(value != null && !value.equals("")) {
			Matcher matcher = this.pattern.matcher(CastUtil.toString(value));
			if(matcher.matches()) {
				message = "含有特殊字符";
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}
