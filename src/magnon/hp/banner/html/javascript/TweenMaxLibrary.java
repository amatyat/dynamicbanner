package magnon.hp.banner.html.javascript;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Script;

public class TweenMaxLibrary {

	public static void appendTweenMaxLibrary(Body body) {
		//append tweenmax javascript library
		Script scriptTagFirst = new Script("");
		scriptTagFirst.setAttribute("type", "text/javascript");
		scriptTagFirst.setAttribute("src", "https://cdnjs.cloudflare.com/ajax/libs/gsap/1.20.2/TweenMax.min.js");
		body.appendChild(scriptTagFirst);
		
		//append jQuery javascript library
		scriptTagFirst = new Script("");
		scriptTagFirst.setAttribute("type", "text/javascript");
		scriptTagFirst.setAttribute("src", "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js");
		body.appendChild(scriptTagFirst);
	}
}
