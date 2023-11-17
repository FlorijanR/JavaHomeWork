package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class QueryParserTest {

	@Test
	void testQueryParser() {
		QueryParser qp1 = new QueryParser(" firstName>\"A\" and lastName LIKE \"B*ć\"");
		QueryParser qp2 = new QueryParser(" firstName>\"A\" and firstName<=\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		//query firstName>"A" and lastName LIKE "B*ć"
		//query firstName>"A" and firstName<="C" and lastName LIKE "B*ć" and jmbag>"0000000002"
		
		List<ConditionalExpression> expressions1 = qp1.getQuery();
		List<ConditionalExpression> expressions2 = qp2.getQuery();

		assertEquals(FieldValueGetters.FIRST_NAME, expressions1.get(0).getFieldGetter());
		assertEquals("A", expressions1.get(0).getStringLiteral());
		assertEquals(ComparisonOperators.GREATER, expressions1.get(0).getComparisonOperator());
		assertEquals(FieldValueGetters.LAST_NAME, expressions1.get(1).getFieldGetter());
		assertEquals("B*ć", expressions1.get(1).getStringLiteral());
		assertEquals(ComparisonOperators.LIKE, expressions1.get(1).getComparisonOperator());
		
		assertEquals(FieldValueGetters.FIRST_NAME, expressions2.get(0).getFieldGetter());
		assertEquals("A", expressions2.get(0).getStringLiteral());
		assertEquals(ComparisonOperators.GREATER, expressions2.get(0).getComparisonOperator());
		assertEquals(FieldValueGetters.FIRST_NAME, expressions2.get(1).getFieldGetter());
		assertEquals("C", expressions2.get(1).getStringLiteral());
		assertEquals(ComparisonOperators.LESS_OR_EQUALS, expressions2.get(1).getComparisonOperator());
		assertEquals(FieldValueGetters.LAST_NAME, expressions2.get(2).getFieldGetter());
		assertEquals("B*ć", expressions2.get(2).getStringLiteral());
		assertEquals(ComparisonOperators.LIKE, expressions2.get(2).getComparisonOperator());
		assertEquals(FieldValueGetters.JMBAG, expressions2.get(3).getFieldGetter());
		assertEquals("0000000002", expressions2.get(3).getStringLiteral());
		assertEquals(ComparisonOperators.GREATER, expressions2.get(3).getComparisonOperator());		
		
		assertEquals(false, qp1.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp1.getQueriedJMBAG());
		
		assertEquals(false, qp2.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp2.getQueriedJMBAG());
	}

	@Test
	void testIsDirectQuery() {
		QueryParser qp1 = new QueryParser(" jmbag       =\"0123456789\"    ");
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		
//		List<ConditionalExpression> expressions1 = qp1.getQuery();
//		assertEquals(FieldValueGetters.JMBAG, expressions1.get(0).getFieldGetter());
//		assertEquals("0123456789", expressions1.get(0).getStringLiteral());
//		assertEquals(ComparisonOperators.EQUALS, expressions1.get(0).getComparisonOperator());
		
		assertEquals(true, qp1.isDirectQuery());
		assertEquals(false, qp2.isDirectQuery());
	}

	@Test
	void testGetQueriedJMBAG() {
		QueryParser qp1 = new QueryParser(" jmbag       =\"0123456789\"    ");
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");

		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertThrows(IllegalStateException.class, () -> qp2.getQueriedJMBAG());
	}

	@Test
	void testGetQuery() {
		QueryParser qp1 = new QueryParser(" jmbag       =\"0123456789\"    ");
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		
		assertEquals(1, qp1.getQuery().size());
		assertEquals(2, qp2.getQuery().size());
	}

}
