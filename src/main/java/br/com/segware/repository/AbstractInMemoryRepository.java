package br.com.segware.repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractInMemoryRepository<T> {
	
	private Set<T> entities = new HashSet<>();

	public void insertAll(final Set<T> entities) {
		this.entities.addAll(entities);
	}
	
	public Set<T> findAll() {
		return Collections.unmodifiableSet(entities);
	}
	
}
