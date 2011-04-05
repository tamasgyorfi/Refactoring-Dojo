package dojo.refactor.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dojo.refactor.production.XmlCharactersEscaper;

public class XmlCharactersEscaperTest {

	XmlCharactersEscaper xmlCharactersEscaper = new XmlCharactersEscaper();

	@Test
	public void shouldReturnEmptyXmlForEmptyXml() {
		assertEquals(
				"<xml><SvcRsData></SvcRsData></xml> ",
				xmlCharactersEscaper
						.escapeInvalidCharactersInXml("<xml><SvcRsData></SvcRsData></xml> "));
	}

	@Test
	public void shouldNotEscapeAndSignNotWithinCdata() {
		assertEquals(
				"<xml><SvcRsData>&</SvcRsData></xml> ",
				xmlCharactersEscaper
						.escapeInvalidCharactersInXml("<xml><SvcRsData>&</SvcRsData></xml> "));
	}

	@Test
	public void shouldEscapeAndSignWithinCdataAndRemoveCdataBlock() {
		assertEquals(
				"<xml><SvcRsData>tag &#38;</SvcRsData></xml> ",
				xmlCharactersEscaper
						.escapeInvalidCharactersInXml("<xml><SvcRsData>tag<![CDATA[ &]]></SvcRsData></xml> "));
	}

	@Test
	public void shouldRemoveCdataBlockAndLeaveLettersUnchanged() {
		assertEquals(
				"<xml><SvcRsData>tag John Smith</SvcRsData></xml> ",
				xmlCharactersEscaper
						.escapeInvalidCharactersInXml("<xml><SvcRsData>tag<![CDATA[ John Smith]]></SvcRsData></xml> "));
	}

	@Test
	public void shouldRemoveCdataBlockAndEscapeNonLetterCharactersWithinIt() {
		assertEquals(
				"<xml><SvcRsData>data John &#38; Smith</SvcRsData></xml> ",
				xmlCharactersEscaper
						.escapeInvalidCharactersInXml("<xml><SvcRsData>data<![CDATA[ John & Smith]]></SvcRsData></xml> "));
	}

	@Test
	public void shouldEscapeAllNonSafeCharactersWithinACdataBlock() {
		assertEquals(
				"<xml><SvcRsData>data &#60;sender&#62;John &#38; Smith&#60;/sender&#62;</SvcRsData></xml> ",
				xmlCharactersEscaper
						.escapeInvalidCharactersInXml("<xml><SvcRsData>data<![CDATA[ <sender>John & Smith</sender>]]></SvcRsData></xml> "));
	}
}