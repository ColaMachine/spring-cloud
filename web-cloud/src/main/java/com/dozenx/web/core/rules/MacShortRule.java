package com.dozenx.web.core.rules;


import com.dozenx.common.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacShortRule extends Rule {
	public 	String regex = "[0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F][0-9A-F]";

	public MacShortRule() {
    }
	@Override
	public boolean valid() throws Exception {
		if (this.getValue()==null || StringUtil.isBlank(this.getValue().toString())) {
			return true;
		}


		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value.toString().toUpperCase());


		if (!matcher.find()) {
			this.setMessage("请输入正确的mac地址");
			return false;
		}
		else {
			return true;
		}
	}


	@Override
	public boolean valid(Object value) throws Exception {
		if (value==null || StringUtil.isBlank(value.toString())) {
			return true;
		}


		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value.toString().toUpperCase());


		if (!matcher.find()) {
			this.setMessage("请输入正确的mac地址");
			return false;
		}
		else {
			return true;
		}
	}

}
