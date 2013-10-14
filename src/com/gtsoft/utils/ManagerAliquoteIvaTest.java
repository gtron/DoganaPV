package com.gtsoft.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ManagerAliquoteIvaTest {

	protected ManagerAliquoteIva manager = null;

	@Before
	public void init() {
		manager = ManagerAliquoteIva.getInstance();
	}

	@Test
	public void testCalcolaAliquotaIvaDelGiorno() {

		assertEquals( 0.22 , calcolaAliquota("2013-10-10") , 0.0001);

		initAliquote("0.21:2013-10-01,0.22");

		assertEquals( 0.22 , calcolaAliquota("2013-10-10") , 0.0001);

		String config = "0.20:2012-10-01,0.22:2014-09-01,0.21:2013-10-01,0.22:2014-09-01,0.25";

		initAliquote(config);

		assertEquals( 0.22 , calcolaAliquota("2013-10-10") , 0.0001);

		assertEquals( 0.20 , calcolaAliquota("2012-02-10") , 0.0001);

		assertEquals( 0.21 , calcolaAliquota("2013-09-30") , 0.0001);
		assertEquals( 0.22 , calcolaAliquota("2013-10-01") , 0.0001);

		assertEquals( 0.25 , calcolaAliquota("2015-10-10") , 0.0001);


		//		System.out.println("Aliquota del giorno " + giorno + " : " + result);

	}

	@Test
	public void testInitAliquote() {


		String config = "0.21:2013-09-01,0.20:2012-10-01,0.25";

		initAliquote(config);

		assertEquals(manager.getLastLimit(), "2013-09-01" );
		assertEquals(manager.getAliquotaCorrente(), "0.25" );

		config = "0.21";

		initAliquote(config);

		assertEquals(manager.getLastLimit(), "0" );
		assertEquals(manager.getAliquotaCorrente(), "0.21" );

	}

	protected double calcolaAliquota(String giorno) {
		return manager.calcolaAliquota(giorno);
	}

	protected void initAliquote(String config) {
		manager.initAliquote(config);
	}

}
