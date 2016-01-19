package space.davidboles.ht.ml;

public class HTMLEscape {
	private static final String[][] escapes = {
			{"\'", "&apos;"},
			{"\"", "&quot;"},
			{"&", "&amp;"},
			{"<", "&lt;"},
			{">", "&gt;"}
	};
	
	public static String escape(String unescaped) {
		for (int i = 0; i < escapes.length; i++) {
			unescaped = unescaped.replaceAll(escapes[i][0], escapes[i][1]);
		}
		return unescaped;
	}
	
	public static String unescape(String escaped) {
		for (int i = 0; i < escapes.length; i++) {
			escaped = escaped.replaceAll(escapes[i][1], escapes[i][0]);
		}
		return escaped;
	}
}
