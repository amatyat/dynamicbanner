package magnon.hp.banner.html.stylesheet;

import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Style;
import com.hp.gagawa.java.elements.Text;

public class StyleSheetCreator {

	
	
	public static void appendStyle(String styleString, Html html) {
		Style s = new Style("");
		s.appendChild(new Text(styleString));
		
		html.appendChild(s);
	}
	
	
}
