package br.com.segware.repository;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.segware.model.Evento;

public class EventoInMemoryRepositoryTest {

	private Repository<Evento> repository;

	@Before
	public void setUp() throws Exception {
		repository = new EventoInMemoryRepository();
	}
	
	@Test
	public void shouldInsertAllEvents() {
		final Evento event = new Evento();
		final Evento anotherEvent = new Evento();
		
		final Set<Evento> events = new HashSet<>();
		events.add(event);
		events.add(anotherEvent);
		
		repository.insertAll(events);
		
		Assert.assertEquals(repository.findAll().size(), 2);
	}

}
