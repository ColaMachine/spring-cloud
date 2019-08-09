package com.dozenx.web.core.rules;


import com.dozenx.common.util.StringUtil;

public class EmailRule extends Rule {
	
	public EmailRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.isEmail(this.getValue().toString())) {
				return true;
			}
			else {
				this.setMessage("请输入正确格式的邮箱");
				return false;
			}
		}
	}

	@Override
	public boolean valid(Object value) throws Exception {
		if(value == null || value.equals("")){
			return true;
		}else{
			if (StringUtil.isEmail(value.toString())) {
				return true;
			}
			else {
				this.setMessage("请输入正确格式的邮箱");
				return false;
			}
		}
	}

}
