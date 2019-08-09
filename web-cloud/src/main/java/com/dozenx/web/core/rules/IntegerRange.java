package com.dozenx.web.core.rules;

import java.math.BigDecimal;


public class IntegerRange extends Rule {
	private Integer min;
	private Integer max;





	public IntegerRange(Integer min, Integer max){
		this.min = min;
		this.max = max;
	}

	
	@Override
	public boolean valid() throws Exception {
		if (this.getValue() != null && !this.getValue().equals("")) {
			Integer realValue = Integer.valueOf(this.getValue().toString());
			//BigDecimal minValue = new BigDecimal(min);

			if (this.min != null) {
				if (realValue<min) {
					message = "err.param.min." + min;
					return false;
				}
			}
			if (this.max != null) {

				if (realValue>max) {
					message = "数字应在范围" + min + "-" + max + "之内";
					return false;
				}

			}
			return true;
		}

		return false;
	}


	@Override
	public boolean valid(Object value) throws Exception {
		if (value!= null && !value.equals("")) {
			Integer realValue = Integer.valueOf(value.toString());
			//BigDecimal minValue = new BigDecimal(min);

			if (this.min != null) {
				if (realValue<min) {
					message = "err.param.min." + min;
					return false;
				}
			}
			if (this.max != null) {

				if (realValue>max) {
					message = "数字应在范围" + min + "-" + max + "之内";
					return false;
				}

			}
			return true;
		}

		return false;
	}
}
