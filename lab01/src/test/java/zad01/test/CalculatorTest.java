package zad01.test;

import static org.junit.Assert.*;

import org.junit.Test;

import zad01.Calculator;


public class CalculatorTest {
	
	Calculator calc = new Calculator();
	
	@Test
	public void checkAdd() {
		assertEquals(4, calc.add(2, 2));
	}
	
	@Test
	public void checkSub() {
		assertEquals(5, calc.sub(10, 5));
	}
	
	@Test
	public void checkMulti() {
		assertEquals(16, calc.multi(4, 4));
	}
	
	@Test
	public void checkDiv() {
		assertEquals(5, calc.div(10, 2));
	}
	
	@Test
	public void checkGreather() {
		assertEquals(true, calc.greather(5, 1));
	}
	
//	@Test(expected = ArithmeticException.class) 
//	public void checkArithExeption() {
//		int testCase = 2 / 0; 
//	}
	
}
