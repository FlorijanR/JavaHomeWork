package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	@Test
	void testConditionalExpression() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StudentDatabase sdb = new StudentDatabase(lines);
		StudentRecord record = sdb.forJMBAG("0000000060"); //lastName = Vignjević
		
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE); // lastName LIKE "Bos*"

		assertEquals(false, expr.getComparisonOperator()
				.satisfied(expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Bos*"
		));
		
		expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Vig*",
				ComparisonOperators.LIKE); // lastName LIKE "Vig*"

		assertEquals(true, expr.getComparisonOperator()
				.satisfied(expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Vig*"
		));
		
//		List<StudentRecord> filteredList = sdb.filter(r -> {
//				ConditionalExpression ce = new ConditionalExpression(FieldValueGetters.LAST_NAME, "V*",
//						ComparisonOperators.LIKE);
//				return ce.getComparisonOperator()
//							.satisfied(ce.getFieldGetter().get(r),
//							ce.getStringLiteral());
//		});
//		filteredList.forEach(x -> System.out.println(x.getLastName()));
//		System.out.println(filteredList.size());
	}

}




