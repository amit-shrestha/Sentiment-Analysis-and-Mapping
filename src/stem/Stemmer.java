package stem;

public class Stemmer {
	public static String main(String word) {
		char[] characters = word.toCharArray();
		char[] newCharacters1 = step1(characters);
		char[] newCharacters2 = step2(newCharacters1);
		char[] newCharacters3 = step3(newCharacters2);
		char[] newCharacters4 = step4(newCharacters3);
		char[] newCharacters5a = step5a(newCharacters4);
		char[] newCharacters5b = step5b(newCharacters5a);
		String stemmedWord = new String(newCharacters5b);
		return stemmedWord;
	}

	public static char[] sequenceCreator(char[] characters) {
		int i = 0;
		String seq = null;
		for (char x : characters) {
			boolean res = cons(x);
			if (i == 0) {
				if (res == true) {
					seq = "C";
				}
				if (res == false) {
					seq = "V";
				}
				i++;
			} else if (i != 0) {
				if (x == 'y') {
					if (seq.charAt((i - 1)) == 'C') {
						res = false;
					}
					if (seq.charAt((i - 1)) == 'V') {
						res = true;
					}
				}
				if (res == true) {
					if (seq.charAt((i - 1)) == 'V') {
						seq = seq.concat("C");
						i++;
					}
				}
				if (res == false) {
					if (seq.charAt((i - 1)) == 'C') {
						seq = seq.concat("V");
						i++;
					}
				}
			}
		}
		return seq.toCharArray();
	}

	public static int measure(char[] sequence) {
		int i = 0;
		int count = 0;
		int c = 0;
		int m = 0;
		for (i = 0; i < sequence.length; i++) {
			if (count == 0) {
				if (sequence[i] == 'C') {
				}
				if (sequence[i] == 'V') {
					c = 1;
				}
			}
			if (count > 0) {
				if (sequence[i] == 'C') {
					if (c == 1) {
						m += 1;
						c = 0;
					}
				}
				if (sequence[i] == 'V') {
					if (c == 1) {
					}
					if (c == 0) {
						c = 1;
					}
				}
			}
			count++;
		}
		return m;
	}

	public static boolean cons(char ch) {
		switch (ch) {
		case 'a':
			return false;
		case 'e':
			return false;
		case 'i':
			return false;
		case 'o':
			return false;
		case 'u':
			return false;
		default:
			return true;
		}
	}

	public static boolean vowelinstem(char[] characters) {
		for (int i = 0; i < characters.length; i++) {
			if (!cons(characters[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean doublecons(char[] characters) {
		int clength = characters.length;
		if (cons(characters[clength - 1]) && (characters[clength - 1] == characters[clength - 2])) {
			return true;
		}
		return false;
	}

	public static boolean lsz(char[] characters) {
		if (endswith(characters, "l") || endswith(characters, "s") || endswith(characters, "z")) {
			return true;
		}
		return false;
	}

	public static boolean cvc(char[] characters) {
		char[] sequence = sequenceCreator(characters);
		int seqlength = sequence.length;
		if (seqlength < 3) {
			return false;
		}
		if (sequence[seqlength - 1] == 'C' && sequence[seqlength - 2] == 'V' && sequence[seqlength - 3] == 'C') {
			if (!endswith(characters, "w") || !endswith(characters, "x") || !endswith(characters, "y")) {
				return true;
			}
		}
		return false;
	}

	public static boolean endswith(char[] characters, String suff) {
		int clength = characters.length;
		char[] suffix = suff.toCharArray();
		int slength = suffix.length;
		int elength = clength - slength;
		if (elength < 0) {
			return false;
		}
		for (int i = 0; i < slength; i++) {
			if (characters[elength + i] != suffix[i]) {
				return false;
			}
		}
		return true;
	}

	public static char[] setto(char[] characters, String suff) {

		String str = new String(characters);
		String newStr = str.concat(suff);
		char[] newCharacters = newStr.toCharArray();
		return newCharacters;
	}

	public static char[] remove(char[] characters, String suff) {
		int clength = characters.length;
		char[] suffix = suff.toCharArray();
		int slength = suffix.length;
		if (clength == slength) {
			return characters;
		}
		char[] newCharacters = new char[(clength - slength)];
		for (int i = 0; i < (clength - slength); i++) {
			newCharacters[i] = characters[i];
		}
		return newCharacters;
	}

	public static char[] step1(char[] characters) {
		if (endswith(characters, "sses")) {
			char[] newCharacters = remove(characters, "es");
			return newCharacters;
		} else if (endswith(characters, "ies")) {
			char[] newCharacters = remove(characters, "ies");
			char[] newCharacters1 = setto(newCharacters, "i");
			return newCharacters1;
		} else if (endswith(characters, "s") && !endswith(characters, "ss")) {
			char[] newCharacters = remove(characters, "s");
			return newCharacters;
		} else if (endswith(characters, "eed")) {
			char[] newCharacters = remove(characters, "d");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m != 0) {
				return newCharacters;
			}
		} else if (endswith(characters, "ed")) {
			char[] newCharacters = remove(characters, "ed");
			if (vowelinstem(newCharacters) && (endswith(newCharacters, "at") || endswith(newCharacters, "bl")
					|| endswith(newCharacters, "iz"))) {
				char[] newCharacters1 = setto(newCharacters, "e");
				return newCharacters1;
			} else if (vowelinstem(newCharacters) && doublecons(newCharacters) && lsz(newCharacters)) {
				return newCharacters;
			} else if (vowelinstem(newCharacters) && doublecons(newCharacters) && !lsz(newCharacters)) {
				int nclength = newCharacters.length;
				char[] newCharacters2 = new char[(nclength - 1)];
				for (int i = 0; i < (nclength - 1); i++) {
					newCharacters2[i] = newCharacters[i];
				}
				return newCharacters2;
			} else if (!vowelinstem(newCharacters)) {
				return characters;
			}
			return newCharacters;
		} else if (endswith(characters, "ing")) {
			char[] newCharacters = remove(characters, "ing");
			if (vowelinstem(newCharacters) && doublecons(newCharacters) && !lsz(newCharacters)) {
				int nclength = newCharacters.length;
				char[] newCharacters1 = new char[(nclength - 1)];
				for (int i = 0; i < (nclength - 1); i++) {
					newCharacters1[i] = newCharacters[i];
				}
				return newCharacters1;
			}
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m == 1 && cvc(newCharacters) && !doublecons(newCharacters)) {
				char[] newCharacters2 = setto(newCharacters, "e");
				return newCharacters2;
			}
			if (!vowelinstem(newCharacters)) {
				return characters;
			}
			return newCharacters;
		} else if (endswith(characters, "y") && vowelinstem(characters)) {
			char[] newCharacters = remove(characters, "y");
			char[] newCharacters1 = setto(newCharacters, "i");
			return newCharacters1;
		}
		return characters;
	}

	public static char[] step2(char[] characters) {
		if (endswith(characters, "ational")) {
			char[] newCharacters = remove(characters, "ational");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ate");
				return newCharacters1;
			}
		} else if (endswith(characters, "tional")) {
			char[] newCharacters = remove(characters, "tional");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "tion");
				return newCharacters1;
			}
		} else if (endswith(characters, "enci")) {
			char[] newCharacters = remove(characters, "enci");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ence");
				return newCharacters1;
			}
		} else if (endswith(characters, "anci")) {
			char[] newCharacters = remove(characters, "anci");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ance");
				return newCharacters1;
			}
		} else if (endswith(characters, "izer")) {
			char[] newCharacters = remove(characters, "izer");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ize");
				return newCharacters1;
			}
		} else if (endswith(characters, "abli")) {
			char[] newCharacters = remove(characters, "abli");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "able");
				return newCharacters1;
			}
		} else if (endswith(characters, "alli")) {
			char[] newCharacters = remove(characters, "alli");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "al");
				return newCharacters1;
			}
		} else if (endswith(characters, "entli")) {
			char[] newCharacters = remove(characters, "entli");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ent");
				return newCharacters1;
			}
		} else if (endswith(characters, "eli")) {
			char[] newCharacters = remove(characters, "eli");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "e");
				return newCharacters1;
			}
		} else if (endswith(characters, "ousli")) {
			char[] newCharacters = remove(characters, "ousli");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ous");
				return newCharacters1;
			}
		} else if (endswith(characters, "ization")) {
			char[] newCharacters = remove(characters, "ization");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ize");
				return newCharacters1;
			}
		} else if (endswith(characters, "isation")) {
			char[] newCharacters = remove(characters, "isation");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ise");
				return newCharacters1;
			}
		} else if (endswith(characters, "ation")) {
			char[] newCharacters = remove(characters, "ation");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ate");
				return newCharacters1;
			}
		} else if (endswith(characters, "ator")) {
			char[] newCharacters = remove(characters, "ator");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ate");
				return newCharacters1;
			}
		} else if (endswith(characters, "alism")) {
			char[] newCharacters = remove(characters, "alism");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "al");
				return newCharacters1;
			}
		} else if (endswith(characters, "iveness")) {
			char[] newCharacters = remove(characters, "iveness");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ive");
				return newCharacters1;
			}
		} else if (endswith(characters, "fulness")) {
			char[] newCharacters = remove(characters, "fulness");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ful");
				return newCharacters1;
			}
		} else if (endswith(characters, "ousness")) {
			char[] newCharacters = remove(characters, "ousness");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ous");
				return newCharacters1;
			}
		} else if (endswith(characters, "aliti")) {
			char[] newCharacters = remove(characters, "aliti");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "al");
				return newCharacters1;
			}
		} else if (endswith(characters, "iviti")) {
			char[] newCharacters = remove(characters, "iviti");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ive");
				return newCharacters1;
			}
		} else if (endswith(characters, "biliti")) {
			char[] newCharacters = remove(characters, "biliti");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ble");
				return newCharacters1;
			}
		}
		return characters;
	}

	public static char[] step3(char[] characters) {
		if (endswith(characters, "icate")) {
			char[] newCharacters = remove(characters, "icate");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ic");
				return newCharacters1;
			}
		} else if (endswith(characters, "ative")) {
			char[] newCharacters = remove(characters, "icate");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				return newCharacters;
			}
		}
		if (endswith(characters, "alize")) {
			char[] newCharacters = remove(characters, "alize");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "al");
				return newCharacters1;
			}
		}
		if (endswith(characters, "iciti")) {
			char[] newCharacters = remove(characters, "iciti");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ic");
				return newCharacters1;
			}
		}
		if (endswith(characters, "ical")) {
			char[] newCharacters = remove(characters, "ical");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				char[] newCharacters1 = setto(newCharacters, "ical");
				return newCharacters1;
			}
		} else if (endswith(characters, "ful")) {
			char[] newCharacters = remove(characters, "ful");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				return newCharacters;
			}
		} else if (endswith(characters, "ness")) {
			char[] newCharacters = remove(characters, "ness");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 0) {
				return newCharacters;
			}
		}
		return characters;
	}

	public static char[] step4(char[] characters) {
		if (endswith(characters, "al")) {
			char[] newCharacters = remove(characters, "al");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ance")) {
			char[] newCharacters = remove(characters, "ance");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ence")) {
			char[] newCharacters = remove(characters, "ence");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "er")) {
			char[] newCharacters = remove(characters, "er");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ic")) {
			char[] newCharacters = remove(characters, "ic");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "able")) {
			char[] newCharacters = remove(characters, "able");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ible")) {
			char[] newCharacters = remove(characters, "ible");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ant")) {
			char[] newCharacters = remove(characters, "ant");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ment")) {
			char[] newCharacters = remove(characters, "ment");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ement")) {
			char[] newCharacters = remove(characters, "ement");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ent")) {
			char[] newCharacters = remove(characters, "ent");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ion")) {
			char[] newCharacters = remove(characters, "ion");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1 && (!endswith(newCharacters, "s") || !endswith(newCharacters, "t"))) {
				return newCharacters;
			}
		} else if (endswith(characters, "ou")) {
			char[] newCharacters = remove(characters, "ou");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ism")) {
			char[] newCharacters = remove(characters, "ism");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ate")) {
			char[] newCharacters = remove(characters, "ate");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "iti")) {
			char[] newCharacters = remove(characters, "iti");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ous")) {
			char[] newCharacters = remove(characters, "ous");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ive")) {
			char[] newCharacters = remove(characters, "ive");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		} else if (endswith(characters, "ize")) {
			char[] newCharacters = remove(characters, "ize");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			}
		}
		return characters;
	}

	public static char[] step5a(char[] characters) {
		if (endswith(characters, "e")) {
			char[] newCharacters = remove(characters, "e");
			char[] sequence = sequenceCreator(newCharacters);
			int m = measure(sequence);
			if (m > 1) {
				return newCharacters;
			} else if (m == 1 && !cvc(characters)) {
				return newCharacters;
			}
			return characters;
		}
		return characters;
	}

	public static char[] step5b(char[] characters) {
		char[] sequence = sequenceCreator(characters);
		int m = measure(sequence);
		if (m > 1 && doublecons(characters) && endswith(characters, "l")) {
			char[] newCharacters = remove(characters, "l");
			return newCharacters;
		}
		return characters;
	}
}
