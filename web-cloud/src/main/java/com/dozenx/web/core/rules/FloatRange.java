package com.dozenx.web.core.rules;

import java.math.BigDecimal;


public class FloatRange extends Rule {
	private Float min;
	private Float max;

	public Float getMin() {
		return min;
	}

	public void setMin(Float min) {
		this.min = min;
	}

	public Float getMax() {
		return max;
	}

	public void setMax(Float max) {
		this.max = max;
	}

	public FloatRange(Float max){

		this.max = max;
	}

	public FloatRange(Float min, Float max){
		this.min = min;
		this.max = max;
	}

	
	@Override
	public boolean valid() throws Exception {
		if (this.getValue() != null && !this.getValue().equals("")) {
			BigDecimal realValue = new BigDecimal(this.getValue().toString());
			//BigDecimal minValue = new BigDecimal(min);

			if (this.min != null) {
				if (realValue.compareTo(new BigDecimal(min)) == -1) {
					message = "err.param.min." + min;
					return false;
				}
			}
			if (this.max != null) {
				BigDecimal maxValue = new BigDecimal(max);
				if (realValue.compareTo(maxValue) == 1) {
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
		if (value != null && !value.equals("")) {
			BigDecimal realValue = new BigDecimal(value.toString());
			//BigDecimal minValue = new BigDecimal(min);

			if (this.min != null) {
				if (realValue.compareTo(new BigDecimal(min)) == -1) {
					message = "err.param.min." + min;
					return false;
				}
			}
			if (this.max != null) {
				BigDecimal maxValue = new BigDecimal(max);
				if (realValue.compareTo(maxValue) == 1) {
					message = "数字应在范围" + min + "-" + max + "之内";
					return false;
				}

			}
			return true;
		}

		return false;
	}
}
