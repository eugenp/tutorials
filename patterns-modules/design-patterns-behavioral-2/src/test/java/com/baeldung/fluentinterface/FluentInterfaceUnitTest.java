package com.baeldung.fluentinterface;

import com.baeldung.fluentinterface.components.*;
import com.baeldung.fluentinterface.components.HtmlHeader.Type;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.baeldung.fluentinterface.components.HtmlList.Type.ORDERED;
import static org.assertj.core.api.Assertions.assertThat;

class FluentInterfaceUnitTest {

	@Test
	void givenTenNumbers_thenStreamIsProcessedCorrectly() {
		Stream<Integer> numbers = Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Stream<String> processedNumbers = numbers.distinct()
		  .filter(nr -> nr % 2 == 0)
		  .skip(1)
		  .limit(4)
		  .map(nr -> "#" + nr)
		  .peek(nr -> System.out.println(nr));
		String result = processedNumbers.collect(Collectors.joining(", "));

		assertThat(result).isEqualTo("#2, #4, #6, #8");
	}

	@Test
	void givenUserBuilder_thenCreateUserCorrectly() {
		User.Builder userBuilder = User.builder();
		userBuilder = userBuilder
		  .firstName("John")
		  .lastName("Doe")
		  .email("jd@gmail.com")
		  .username("jd_2000")
		  .id(1234L);

		User user = userBuilder.build();

		assertThat(user.name()).isEqualTo("John Doe");
	}

	@Test
	void givenHtmlDocument_thenGenerateHtmlCorrectly() {
		HtmlDocument document = new HtmlDocument()
		  .header("Principles of O.O.P.")
		  .paragraph("OOP in Java.")
		  .horizontalLine()
		  .paragraph("The main pillars of OOP are:")
		  .orderedList("Encapsulation", "Inheritance", "Abstraction", "Polymorphism");
		String html = document.html();

		assertThat(html).isEqualToIgnoringWhitespace(
		  "<html>"
		  + "<h1>Principles of O.O.P.</h1>"
		  + "<p>OOP in Java.</p>"
		  + "<hr>"
		  + "<p>The main pillars of OOP are:</p>"
		  + "<ol>"
		  + "<li>Encapsulation</li>"
		  + "<li>Inheritance</li>"
		  + "<li>Abstraction</li>"
		  + "<li>Polymorphism</li>"
		  + "</ol>"
		  + "</html>"
		);
	}

	@Test
	void givenHtmlDocument_thenInstanceIsImmutable() {
		HtmlDocument document = new HtmlDocument()
		  .header("Principles of O.O.P.");
		HtmlDocument updatedDocument = document
		   .paragraph("OOP in Java.");

		assertThat(document).isNotEqualTo(updatedDocument);
	}


	@Test
	void givenLargeHtmlDocument_thenGenerateHtmlCorrectly() {
		String html = new LargeHtmlDocument()
		  .head(new HtmlHeader(Type.PRIMARY, "title"))
		  .body(new HtmlDiv()
			.append(new HtmlSpan()
			  .paragraph("learning OOP from John Doe")
			  .append(new HorizontalLine())
			  .paragraph("The pillars of OOP:")
			)
			.append(new HtmlList(ORDERED, "Encapsulation", "Inheritance", "Abstraction", "Polymorphism"))
		  )
		  .footer(new HtmlDiv()
			.paragraph("trademark John Doe")
		  )
		  .html();

		String expectedHtml = "<html> <head><h1>title</h1></head> <body><div><span>learning OOP from John Doe<hr>The pillars of OOP:</span><ol><li>Encapsulation</li><li>Inheritance</li><li>Abstraction</li><li>Polymorphism</li></ol></div></body> <footer><div>trademark John Doe</div></footer></html>";
		assertThat(html).isEqualToIgnoringWhitespace(expectedHtml);
	}
}