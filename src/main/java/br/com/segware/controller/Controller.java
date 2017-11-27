package br.com.segware.controller;

import java.util.Set;

public interface Controller<T> {

	void loadCsv();
	
	Set<T> findAll();
	
}
