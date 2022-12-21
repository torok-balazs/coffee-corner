package com.balazstorok.coffeecorner.domain.beverage;

import com.balazstorok.coffeecorner.domain.ProductType;
import java.math.BigDecimal;

public enum ExtraType implements ProductType<Extra> {
	EXTRA_MILK {
		@Override
		public Extra create() {
			return new Extra("Extra milk", BigDecimal.valueOf(0.3));
		}
	},
	FOAMED_MILK {
		@Override
		public Extra create() {
			return new Extra("Foamed milk", BigDecimal.valueOf(0.5));
		}
	},
	SPECIAL_ROAST_COFFEE {
		@Override
		public Extra create() {
			return new Extra("Special roast coffee", BigDecimal.valueOf(0.9));
		}
	}
}
