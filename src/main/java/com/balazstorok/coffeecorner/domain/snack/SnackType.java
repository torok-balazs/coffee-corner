package com.balazstorok.coffeecorner.domain.snack;

import com.balazstorok.coffeecorner.domain.ProductType;
import java.math.BigDecimal;

public enum SnackType implements ProductType<Snack> {
	BACON_ROLL {
		@Override
		public Snack create() {
			return new Snack("Bacon Roll", BigDecimal.valueOf(4.5));
		}
	},
}
