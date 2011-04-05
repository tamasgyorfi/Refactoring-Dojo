package dojo.refactor.production.escaperule;

public class ExtendedCharacterEscapeRule extends CharacterEscaperRule{

	public String escapeInvalidCharactersInXml(String inputXml) {
		StringBuffer retval = new StringBuffer();
		char character;
		for (int i = 0; i < inputXml.length(); i++) {
			character = inputXml.charAt(i);
			if (character > 128) {
				retval.append(escapeCharacter((short)character));
			} else {
				retval.append((char) character);
			}
		}
		return retval.toString();
	}
}
