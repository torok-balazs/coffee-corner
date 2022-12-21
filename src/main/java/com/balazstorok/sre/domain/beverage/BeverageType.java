package com.balazstorok.sre.domain.beverage;

import com.balazstorok.sre.domain.ProductType;
import java.math.BigDecimal;

public enum BeverageType implements ProductType<Beverage> {
	COFFEE_S {
		@Override
		public Beverage create() {
			return new Beverage("Small coffee", BigDecimal.valueOf(2.5));
		}
	},
	COFFEE_M {
		@Override
		public Beverage create() {
			return new Beverage("Medium coffee", BigDecimal.valueOf(3.0));
		}
	},
	COFFEE_L {
		@Override
		public Beverage create() {
			return new Beverage("Large coffee", BigDecimal.valueOf(3.5));
		}
	},
	ORANGE_JUICE {
		@Override
		public Beverage create() {
			return new Beverage("Freshly squeezed orange juice", BigDecimal.valueOf(3.95));
		}
	};

	public Beverage with(final ExtraType extraType) {
		final Beverage beverage = this.create();
		final Extra extra = extraType.create();
		beverage.addExtra(extra);
		return beverage;
	}
}
