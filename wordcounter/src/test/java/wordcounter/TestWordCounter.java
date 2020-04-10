package wordcounter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

public class TestWordCounter {
	@Test
	public void testAddInvalidWord() {
		Translator translator = getDummyTranslator();
		Validator validator = new WordValidator();
		WordCounter wordCounter = new WordCounter(translator, validator);
		
		boolean isValid = wordCounter.addWord("word123");
		int count = wordCounter.countWord("word123");
		
		assertFalse(isValid);
		assertEquals("Do not add Invalid words", 0, count);
		
		isValid = wordCounter.addWord("word has spaces");		
		count = wordCounter.countWord("word has spaces");
		
		assertFalse(isValid);
		assertEquals("Do not add Invalid words", 0, count);
		
		isValid = wordCounter.addWord("-%$word%^%&^");		
		count = wordCounter.countWord("-%$word%^%&^");
		
		assertFalse(isValid);
		assertEquals("Do not add Invalid words", 0, count);
	}
	
	@Test
	public void testAddWord() {
		Translator translator = getDummyTranslator();
		Validator validator = new WordValidator();
		WordCounter wordCounter = new WordCounter(translator, validator);
		String word1 = "flower";
		String word2 = "apple";
		String word3 = "banana";
		
		int count = wordCounter.countWord(word1);		
		assertEquals("Initially count should be zero", 0, count);
		
		boolean isValid = wordCounter.addWord(word1);		
		assertTrue(isValid);
		
		count = wordCounter.countWord(word1);
		assertEquals("Count should be incremented", 1, count);
		
		wordCounter.addWord(word1);
		wordCounter.addWord(word1);
		wordCounter.addWord(word1);
		wordCounter.addWord(word2);
		wordCounter.addWord(word2);
		wordCounter.addWord(word1);
		wordCounter.addWord(word3);
		wordCounter.addWord(word3);
		wordCounter.addWord(word2);
		wordCounter.addWord(word1);
		wordCounter.addWord(word3);
		wordCounter.addWord(word2);
		wordCounter.addWord(word2);
		wordCounter.addWord(word3);
		
		count = wordCounter.countWord(word1);
		assertEquals(6, count);
		
		count = wordCounter.countWord(word2);
		assertEquals(5, count);
		
		count = wordCounter.countWord(word3);
		assertEquals(4, count);
		
		count = wordCounter.countWord("flowe");
		assertEquals(0, count);
		count = wordCounter.countWord("flowers");
		assertEquals(0, count);
	}
	
	@Test
	public void testAddForeignWord() {
		Translator translator = getEnglishTranslator();
		Validator validator = new WordValidator();
		WordCounter wordCounter = new WordCounter(translator, validator);
				
		String english = "flower";
		String spanish = "flor";
		String german = "blume";
				
		wordCounter.addWord(spanish);
		wordCounter.addWord(german);
		wordCounter.addWord(english);
		
		int count = wordCounter.countWord(english);
		
		assertEquals("Treat same words written in different languages as same word", 3, count);
		
		count = wordCounter.countWord(spanish);
		
		assertEquals("Treat same words written in different languages as same word", 3, count);
		
		count = wordCounter.countWord(german);
		
		assertEquals("Treat same words written in different languages as same word", 3, count);
		
		
		wordCounter.addWord(german);
		wordCounter.addWord(german);
		wordCounter.addWord(german);
		wordCounter.addWord(german);
		
		count = wordCounter.countWord(english);
		
		assertEquals("Treat same words written in different languages as same word", 7, count);
	}

	private Translator getDummyTranslator() {
		Translator translator = mock(Translator.class);
		
		// just return given word
		when(translator.translate(anyString())).thenAnswer(i -> i.getArguments()[0]);
		
		return translator;
	}
	
	private Translator getEnglishTranslator() {
		Translator translator = mock(Translator.class);
		
		// just return flower
		when(translator.translate(anyString())).thenReturn("flower");
		
		return translator;
	}
}
