package br.com.segware;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class RepositoryEventosCSVTeste {

    RepositoryEventosCSV repository;

    @Before
    public void before() throws IOException {
        repository = new RepositoryEventosCSV();
    }
    
    @Test
    public void consultaFileCsvObterTamanhoListagem() {
    	try {
    		assertEquals(20, repository.getEventosAll().size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    

}