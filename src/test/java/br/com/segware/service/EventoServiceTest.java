package br.com.segware.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.segware.enums.Tipo;
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

	@Test
	public void shouldListClientEvents() {
		final Set<Evento> events = createAnyEvents();
		service.insertAll(events);
		
	}

	private Set<Evento> createAnyEvents() {
		final Set<Evento> events = new HashSet<>();
		final Evento event = new Evento();
		event.setCodigoCliente("E100");
		event.setTipo(Tipo.ARME);
		events.add(event);

		final Evento event2 = new Evento();
		event2.setCodigoCliente("E101");
		event2.setTipo(Tipo.ARME);
		events.add(event2);

		final Evento event3 = new Evento();
		event3.setCodigoCliente("E102");
		event3.setTipo(Tipo.DESARME);
		events.add(event3);

		final Evento event4 = new Evento();
		event4.setCodigoCliente("E103");
		event4.setTipo(Tipo.TESTE);
		events.add(event4);

		return events;
	}

}
