package com.dozenx.web.core.rules;


public class CheckBox extends Rule {
	private String[] cherkArr;
	public String[] getCherkArr() {
		return cherkArr;
	}
	public void setCherkArr(String[] cherkArr) {
		this.cherkArr = cherkArr;
	}

	public CheckBox(String[] cherkArr) {
		super();
		this.cherkArr = cherkArr;
	}
	
	@Override
	public boolean valid() throws Exception{
        if (this.getValue() == null || this.getValue().equals("")) {
            return true;
        }

		boolean result = false;
		if(this.getCherkArr()!=null){
			for(String item : this.getCherkArr()){
				if(item.equalsIgnoreCase(this.getValue().toString())){
					result = true;
					break;
				}
			}
		}
		
		if(result){
			return true;
		}else {
			this.setMessage("请选择");
			return false;
		}
	}


	@Override
	public boolean valid(Object value) throws Exception{
		if (value == null || value.equals("")) {
			return true;
		}

		boolean result = false;
		if(this.getCherkArr()!=null){
			for(String item : this.getCherkArr()){
				if(item.equalsIgnoreCase(value.toString())){
					result = true;
					break;
				}
			}
		}

		if(result){
			return true;
		}else {
			this.setMessage("请选择");
			return false;
		}
	}
}
