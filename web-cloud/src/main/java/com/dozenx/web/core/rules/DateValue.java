package com.dozenx.web.core.rules;


import com.dozenx.common.util.DateUtil;
import com.dozenx.common.util.StringUtil;

public class DateValue extends Rule {
	public String formatStr="";
	public DateValue(String formatStr) {
		this.formatStr=formatStr;
	}
	public DateValue() {
    }
	@Override
	public boolean valid() throws Exception {
		if (this.getValue()==null|| StringUtil.isBlank(this.getValue().toString())) {
			return true;
		}
			
		java.util.Date dtValue = DateUtil.parseToDateTry(this.getValue().toString());
		
		if (dtValue == null) {
			this.setMessage("请输入日期");
			return false;
		}
		else {
			return true;
		}
	}



	@Override
	public boolean valid(Object value) throws Exception {
		if (value==null|| StringUtil.isBlank(value.toString())) {
			return true;
		}

		java.util.Date dtValue = DateUtil.parseToDateTry(value.toString());

		if (dtValue == null) {
			this.setMessage("请输入日期");
			return false;
		}
		else {
			return true;
		}
	}
}
