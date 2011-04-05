package dojo.refactor.production.escaperule;

public abstract class CharacterEscaperRule {

	public abstract String escapeInvalidCharactersInXml(String string);
	
	public StringBuilder escapeCharacter(short character) {
		return new StringBuilder("&#").append(character).append(';');
	}

}
