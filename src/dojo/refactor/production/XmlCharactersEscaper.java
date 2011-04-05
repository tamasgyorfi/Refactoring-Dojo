package dojo.refactor.production;

import dojo.refactor.production.escaperule.ExtendedCharacterEscapeRule;
import dojo.refactor.production.escaperule.SimpleCharacterEscapeRule;

public class XmlCharactersEscaper {

	private static final String XML_END_TAG = "</SvcRsData>";
	private static final String XML_START_TAG = "<SvcRsData>";
	private static final String CDATA_END_TAG = "]]>";
	private static final String CDATA_START_TAG = "<![CDATA[";
	
	private SimpleCharacterEscapeRule simpleEscaper = new SimpleCharacterEscapeRule();
	private ExtendedCharacterEscapeRule extendedEscaper = new ExtendedCharacterEscapeRule();

	public XmlCharactersEscaper() {
	}

	public String escapeInvalidCharactersInXml(final String inputXml) {
		int indexOfParentTag = inputXml.indexOf(XML_START_TAG);
		if (indexOfParentTag >= 0) {
			
			int indexOfXmlBody = indexOfParentTag + XML_START_TAG.length();
			int indexOfXmlBodyEnd = inputXml.indexOf(XML_END_TAG);
			
			String charactersBeforeXmlStartTag = inputXml.substring(0, indexOfXmlBody);
			String charactersAfterXmlEndTag = inputXml.substring(indexOfXmlBodyEnd);

			String xmlBodyWithoutParentTag = inputXml.substring(indexOfXmlBody, indexOfXmlBodyEnd);
			
			return extendedEscaper.escapeInvalidCharactersInXml(new StringBuilder(charactersBeforeXmlStartTag).append(
					escapeCharactersInCdataBlock(xmlBodyWithoutParentTag))
					.append(charactersAfterXmlEndTag).toString());
		}

		return extendedEscaper.escapeInvalidCharactersInXml(inputXml);
	}

	private String escapeCharactersInCdataBlock(final String xml) {
		String inputXml = xml;
		
		StringBuilder escapedXml = new StringBuilder();
		int cDataStartTagIndex = inputXml.indexOf(CDATA_START_TAG);
		while (cDataStartTagIndex >= 0) {
			
			int cDataStartTagEndIndex = cDataStartTagIndex + CDATA_START_TAG.length();
			int cDataEndTagIndex = inputXml.indexOf(CDATA_END_TAG);

			String cDataBody = inputXml.substring(cDataStartTagEndIndex, cDataEndTagIndex);
			
			String escapedCBody = simpleEscaper.escapeInvalidCharactersInXml(cDataBody);
			String xmlBodyBeforeCData = inputXml.substring(0, cDataStartTagIndex);
			
			escapedXml.append(xmlBodyBeforeCData).append(escapedCBody);

			inputXml = inputXml.substring(cDataEndTagIndex + CDATA_END_TAG.length());
			cDataStartTagIndex = inputXml.indexOf(CDATA_START_TAG);
		}
		return escapedXml.append(inputXml).toString();
	}

}
