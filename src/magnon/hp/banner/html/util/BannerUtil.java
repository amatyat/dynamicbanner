package magnon.hp.banner.html.util;

import java.net.URL;

import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Text;
import com.hp.gagawa.java.elements.Title;

import magnon.hp.banner.model.BannerModel;

public class BannerUtil {


	public static Div addHyperlinkToBannerWrapperContainer(BannerModel bannerModel, Div wrapperDiv) {
		String hpl_link = bannerModel.getHpl_link();
		String target = bannerModel.getTarget();
		if(isValid(hpl_link)) {
			wrapperDiv.setAttribute("onclick", "window.location='"+ hpl_link +"'");
			wrapperDiv.setAttribute("target", target);
		}
		
		return wrapperDiv;
	}
	
	/* Returns true if url is valid */
    public static boolean isValid(String url) 
    { 
        /* Try creating a valid URL */
        try { 
            new URL(url).toURI(); 
            return true; 
        }
        catch (Exception e) { 
            return false; 
        } 
    } 

	
	public static Title setPageTitle(String pageTitle) {
		Title title = new Title();
	    title.appendChild(new Text("HTML 5 Banner"));
	    
	    return title;
	}
}
