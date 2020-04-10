package wordcounter;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple application that holds number of occurrences of given words.
 * 
 * Same words written in different languages are treated as same word.
 * 
 * This class is not thread safe. It has been assumed that it will be used 
 * only on single threaded environment.
 * 
 * It does not check upper/lower case of given words. It is assumed that case checking is 
 * handled by translator.
 */
public class WordCounter {
	private Map<String, Integer> words;
	private Translator translator;
	private Validator validator;
	
	public WordCounter(Translator translator, Validator validator) {
		words = new HashMap<>();
		this.translator = translator;
		this.validator = validator;
	}
	
	/**
	 * Adds given word to the counter
	 * Treats same words written in different languages as same word
	 * 
	 * @param word
	 * 
	 * @return Returns true if given word is valid and added
	 */
	public boolean addWord(String word) {
		boolean isValid = validator.isValid(word);
		
		if (isValid) {
			word = translateToEnglish(word);
			int count = countWord(word);
			
			words.put(word, ++count);
		}
		
		return isValid;
	}
	
	/**
	 * Returns number of occurrences of given word.
	 * Returns zero if given word doesn't exist
	 * 
	 * @return word
	 */
	public int countWord(String word) {
		word = translateToEnglish(word);
					
		return words.getOrDefault(word, 0);
	}
	
	private String translateToEnglish(String word) {
		return translator.translate(word);
	}
}
