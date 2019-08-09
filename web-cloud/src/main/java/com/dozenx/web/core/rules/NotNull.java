package com.dozenx.web.core.rules;


public class NotNull extends Rule {
	

	
	public NotNull(){
	}

	@Override
	public boolean valid() throws Exception{
		if(this.getValue() == null ){
			
				message = "不能为空";
				return false;
			}else {
				return true;
			}
		
	}

	@Override
	public boolean valid(Object value) throws Exception{
		if(value == null ){

			message = "不能为空";
			return false;
		}else {
			return true;
		}

	}
}
