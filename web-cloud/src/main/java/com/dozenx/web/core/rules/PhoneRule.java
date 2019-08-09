package com.dozenx.web.core.rules;


import com.dozenx.common.util.StringUtil;

public class PhoneRule extends Rule {
	
	public PhoneRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.isPhone(this.getValue().toString())) {
				return true;
			}
			else {
				this.setMessage("请输入正确的手机号码");
				return false;
			}
		}
	}

	@Override
	public boolean valid(Object value) throws Exception {
		if(value == null || value.equals("")){
			return true;
		}else{
			if (StringUtil.isPhone(value.toString())) {
				return true;
			}
			else {
				this.setMessage("请输入正确的手机号码");
				return false;
			}
		}
	}

}
