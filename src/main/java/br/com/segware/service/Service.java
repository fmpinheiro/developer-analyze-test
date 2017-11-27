package br.com.segware.service;

import java.util.List;
import java.util.Set;

public interface Service<T> {

	void insertAll(final Set<T> entities);

	List<T> findAll();
	
}
