package space.davidboles.ht.tp;

public class MIMESwitcher {
	
	public static String toMIME(String fileName) {
		String lowName = fileName.toLowerCase();
		for(int i = 0; i < types.length; i++) {
			if(lowName.endsWith(types[i][0])) {
				return types[i][1];
			}
		}
		
		return null;
	}
	
	public static String toExtension(String mime) {
		String lowName = mime.toLowerCase();
		for(int i = 0; i < types.length; i++) {
			if(lowName.endsWith(types[i][1])) {
				return types[i][0];
			}
		}
		
		return null;
	}

	public static final String[][] types =  {
		{".png", "images/png"},
		{".bmp", "image/bmp"},
		{".gif", "image/gif"},
		{".ico", "image/x-icon"},
		{".jpg", "image/jpeg"},
		{".jpeg", "image/jpeg"},
		{".svg", "image/svg+xml"},
		{".tiff", "image/tiff"},
		{".css", "text/css"},
		{".csv", "text/csv"},
		{".html", "text/html"},
		{".rtf", "text/richtext"},
		{".tsv", "text/tab-separated-values"},
		{".txt", "text/plain"},
		{".avi", "video/x-msvideo"},
		{".f4v", "video/x-f4v"},
		{".flv", "video/x-fl"},
		{".h261", "video/h261"},
		{".h263", "video/h263"},
		{".h264", "video/h264"},
		{".mpeg", "video/mpeg"},
		{".mp4", "video/mp4"},
		//TODO add types
	};
}
