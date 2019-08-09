package com.dozenx.web.core.rules;


public class NotEmpty extends Rule {
	

	
	public NotEmpty(){
	}

	@Override
	public boolean valid() throws Exception{
		if(this.getValue() == null || this.getValue().toString().trim().equals("") ){
				message = "不能为空";
				return false;
			}else {
				return true;
			}
		
	}

	@Override
	public boolean valid(Object value) throws Exception{
		if(value == null || value.toString().trim().equals("") ){
			message = "不能为空";
			return false;
		}else {
			return true;
		}

	}
}
