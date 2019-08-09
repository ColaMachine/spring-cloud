package com.dozenx.web.core.rules;


import com.dozenx.common.util.StringUtil;

public class IDCardRule extends Rule {
	
	public IDCardRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.isID(this.getValue().toString())) {
				return true;
			}
			else {
				this.setMessage("请输入身份证号");
				return false;
			}
		}
	}

	@Override
	public boolean valid(Object value) throws Exception {
		if(value == null || value.equals("")){
			return true;
		}else{
			if (StringUtil.isID(value.toString())) {
				return true;
			}
			else {
				this.setMessage("请输入身份证号");
				return false;
			}
		}
	}


}
