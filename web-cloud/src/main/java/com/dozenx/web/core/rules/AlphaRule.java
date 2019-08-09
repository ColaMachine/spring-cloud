package com.dozenx.web.core.rules;


import com.dozenx.common.util.StringUtil;

public class AlphaRule extends Rule {
	
	public AlphaRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.checkAlpha(this.getValue().toString())) {
				return true;
			}
			else {
				this.setMessage("请输入字母");
				return false;
			}
		}
	}

	@Override
	public boolean valid(Object value) throws Exception {
		if(value== null || value.equals("")){
			return true;
		}else{
			if (StringUtil.checkAlpha(this.getValue().toString())) {
				return true;
			}
			else {
				this.setMessage("请输入字母");
				return false;
			}
		}
	}

}
