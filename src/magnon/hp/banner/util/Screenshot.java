package magnon.hp.banner.util;

import java.awt.AWTException; 
import java.awt.Rectangle; 
import java.awt.Toolkit; 
import java.awt.Robot; 
import java.awt.image.BufferedImage; 
import java.io.IOException; 
import java.io.File; 
import javax.imageio.ImageIO; 
  
public class Screenshot { 
    public static final long serialVersionUID = 1L; 
    
    public static void main(String[] args) 
    { 
        try { 
            Thread.sleep(120); 
            Robot r = new Robot(); 
  
            // It saves screenshot to desired path 
            String path = "F:// Shot"; 
  
            // Used to get ScreenSize and capture image 
            Rectangle capture =  
            new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); 
           /* BufferedImage Image = r.createScreenCapture(capture); 
            ImageIO.write(Image, "jpg", new File(path)); */
            System.out.println("Screenshot saved"); 
            
            int count = 1;

    		while (true) {
    			 BufferedImage Image = r.createScreenCapture(capture); 
    	            ImageIO.write(Image, "jpg", new File(path+"_"+count+".png")); 
    			count++;
    			try {
    				Thread.sleep(5000);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
        } 
        catch (AWTException | IOException | InterruptedException ex) { 
            System.out.println(ex); 
        } 
    } 
} 