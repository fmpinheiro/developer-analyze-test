package br.com.segware.repository;

import java.util.List;
import java.util.Set;

public interface Repository<T> {

	void insertAll(final Set<T> entities);
	
	List<T> findAll();

}
