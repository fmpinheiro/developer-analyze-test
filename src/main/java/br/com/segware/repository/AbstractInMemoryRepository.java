package br.com.segware.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class AbstractInMemoryRepository<T> {
	
	private List<T> entities = new ArrayList<>();

	public void insertAll(final Set<T> entities) {
		this.entities.addAll(entities);
	}
	
	public List<T> findAll() {
		return Collections.unmodifiableList(entities);
	}
	
}
