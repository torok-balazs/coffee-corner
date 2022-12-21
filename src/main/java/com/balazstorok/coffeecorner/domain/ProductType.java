package com.balazstorok.coffeecorner.domain;

public interface ProductType<T extends Product> {

	T create();
}
