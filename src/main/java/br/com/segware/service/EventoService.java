package br.com.segware.service;

import java.util.Set;

import br.com.segware.model.Evento;
import br.com.segware.repository.EventoInMemoryRepository;
import br.com.segware.repository.Repository;

public class EventoService implements Service<Evento> {

	private final Repository<Evento> repository = new EventoInMemoryRepository();
	
	@Override
	public void insertAll(final Set<Evento> entities) {
		repository.insertAll(entities);
	}
	
	@Override
	public Set<Evento> findAll() {
		return repository.findAll();
	}
	
}
