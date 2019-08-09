package com.dozenx.web.core.rules;

import java.math.BigDecimal;


public class NumberRange extends Rule {
	private String min;
	private String max;
	
	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public NumberRange(String max){
		this.min = "0";
		this.max = max;
	}
	
	public NumberRange(String min, String max){
		this.min = min;
		this.max = max;
	}

	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() != null && !this.getValue().equals("")){
			BigDecimal realValue = new BigDecimal(this.getValue().toString());
			BigDecimal minValue = new BigDecimal(min);
			
			if(this.max==null || "".equals(this.max)){
				if(realValue.compareTo(minValue) == -1) {
					message = "err.param.min." + min;
					return false;
				}else {
					return true;
				}
			}else {
				BigDecimal maxValue = new BigDecimal(max);
				if(realValue.compareTo(minValue)==-1 || realValue.compareTo(maxValue)==1) {
					message = "数字应在范围" + min + "-" + max+"之内";
					return false;
				}else {
					return true;
				}
			}
		}
		else {
			return true;
		}
	}


	@Override
	public boolean valid(Object value) throws Exception {
		if(value != null && !value.equals("")){
			BigDecimal realValue = new BigDecimal(value.toString());
			BigDecimal minValue = new BigDecimal(min);

			if(this.max==null || "".equals(this.max)){
				if(realValue.compareTo(minValue) == -1) {
					message = "err.param.min." + min;
					return false;
				}else {
					return true;
				}
			}else {
				BigDecimal maxValue = new BigDecimal(max);
				if(realValue.compareTo(minValue)==-1 || realValue.compareTo(maxValue)==1) {
					message = "数字应在范围" + min + "-" + max+"之内";
					return false;
				}else {
					return true;
				}
			}
		}
		else {
			return true;
		}
	}
}
