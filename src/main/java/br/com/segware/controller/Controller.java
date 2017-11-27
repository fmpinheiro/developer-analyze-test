package br.com.segware.controller;

import java.util.List;

public interface Controller<T> {

	void loadCsv();
	
	List<T> findAll();
	
}
