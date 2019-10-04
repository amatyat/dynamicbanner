package magnon.hp.banner.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import magnon.hp.banner.model.BannerModel;
import magnon.hp.banner.model.FrameModel;

/**
 * Servlet implementation class FormServlet
 */
@WebServlet("/FormServlet")
@MultipartConfig(maxFileSize = 16177215)  
public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		BannerModel bannerModel = new BannerModel();
		
		String canvas_width = request.getParameter("canvas_width");
		String canvas_height = request.getParameter("canvas_height");
		
		String colorpicker = request.getParameter("colorpicker");
		
		String hpl_link = request.getParameter("hpl_link");
		String target = request.getParameter("hpl_target");
		
		Part filePart = request.getPart("file_name[1]"); // Retrieves <input type="file" name="file">
	    String image = filePart.getSubmittedFileName();
	    
		
		String banner_text = request.getParameter("banner_text[1]");
		
		List<FrameModel> frameList = new ArrayList<>();
		
		FrameModel frame = new FrameModel();
		frame.setImage(image);
		frame.setText(banner_text);
		frameList.add(frame);
		
		bannerModel.setFrames(frameList);
		
		bannerModel.setCanvas_height(canvas_height);
		bannerModel.setCanvas_width(canvas_width);
		bannerModel.setColorpicker(colorpicker);
		bannerModel.setHpl_link(hpl_link);
		bannerModel.setTarget(target);
		
		System.out.println("Canvas Width is: " + canvas_width);
		System.out.println("Canvas Height is: " + canvas_height);
		
		PrintWriter writer = response.getWriter();
		
		String htmlRespone = "<html><h3>";
		htmlRespone += "canvas_width is: " + canvas_width + "<br/>";		
		htmlRespone += "canvas_height is: " + canvas_height + "<br/>";	
		htmlRespone += "image is: " + image + "<br/>";
		htmlRespone += "banner_text is: " + banner_text + "<br/>";
		htmlRespone += "hpl_link is: " + hpl_link + "<br/>";
		htmlRespone += "target is: " + target + "<br/>";
		
			
		htmlRespone += "</h3></html>";
		
		htmlRespone+=BannerCreator.createHTML(bannerModel);
		
		// return response
		writer.println(htmlRespone);		
	}
	
	

}
