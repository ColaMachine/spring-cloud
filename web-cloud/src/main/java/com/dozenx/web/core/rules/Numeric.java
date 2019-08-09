package com.dozenx.web.core.rules;


import com.dozenx.common.util.StringUtil;

public class Numeric extends Rule {
	
	public Numeric() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.checkNumeric(this.getValue().toString())) {
				return true;
			}
			else {
				this.setMessage("请输入数字");
				return false;
			}
		}
	}

	@Override
	public boolean valid(Object value) throws Exception {
		if(value == null || value.equals("")){
			return true;
		}else{
			if (StringUtil.checkNumeric(value.toString())) {
				return true;
			}
			else {
				this.setMessage("请输入数字");
				return false;
			}
		}
	}

}
