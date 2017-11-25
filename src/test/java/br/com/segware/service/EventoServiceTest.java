package br.com.segware.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.segware.model.Evento;

public class EventoServiceTest {

	private Service<Evento> service;
	
	@Before
	public void setUp() throws Exception {
		service = new EventoService();
	}
	
	@Test
	public void shouldInsertAllEvents() {
		final Set<Evento> events = createAnyEvents();
		
		service.insertAll(events);
		
		Assert.assertTrue(!events.isEmpty());
		Assert.assertEquals(events.size(), 4);
	}
	
	private Set<Evento> createAnyEvents() {
		final Set<Evento> events = new HashSet<>();
		events.add(new Evento());
		events.add(new Evento());
		events.add(new Evento());
		events.add(new Evento());
		return events;
	}
	
}
