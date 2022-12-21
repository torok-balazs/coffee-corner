package com.balazstorok.sre.domain;

public interface ProductType<T extends Product> {

	T create();
}
