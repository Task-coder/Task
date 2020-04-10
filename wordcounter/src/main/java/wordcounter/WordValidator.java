package wordcounter;

public class WordValidator implements Validator{
	@Override
	public boolean isValid(String word) {
		return word != null && !word.isEmpty() &&
				word.matches("[a-zA-Z]+");
	}
}
