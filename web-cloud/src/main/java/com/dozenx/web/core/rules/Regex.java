package com.dozenx.web.core.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex extends Rule {
	private String reg;
	private Pattern pattern;
	public Regex(String reg){
		this.reg = reg;
		pattern = Pattern.compile(reg);
	}
	public Regex(Pattern pattern){
		this.pattern = pattern;
	}

	public String getReg() {
		return reg;
	}
	public void setReg(String reg) {
		this.reg = reg;
	}

	public Pattern getPattern() {
		return pattern;
	}
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
	
	@Override
	public boolean valid() throws Exception {
		if(value != null && !value.equals("")) {
			Matcher matcher = this.pattern.matcher(value.toString());
			if(!matcher.matches()) {
				message = "格式不正确";
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
			Matcher matcher = this.pattern.matcher(value.toString());
			if(!matcher.matches()) {
				message = "格式不正确";
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}
