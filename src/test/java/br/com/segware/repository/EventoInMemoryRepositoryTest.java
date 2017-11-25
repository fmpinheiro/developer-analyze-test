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
	public void deveInserirEventos() {
		final Evento evento = criarEvento(1L);
		final Evento outroEvento = criarEvento(2L);
		
		final Set<Evento> eventos = new HashSet<>();
		eventos.add(evento);
		eventos.add(outroEvento);
		
		repository.insertAll(eventos);
		
		Assert.assertEquals(repository.findAll().size(), 2);
	}
	
	private Evento criarEvento(final Long codigoEvento) {
		final Evento evento = new Evento();
		evento.setCodigoEvento(codigoEvento);
		return evento;
	}

}
