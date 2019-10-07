package magnon.hp.banner.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import magnon.hp.banner.model.*;

/**
 * Servlet implementation class FormServlet
 */
@WebServlet("/FormServlet")
@MultipartConfig(maxFileSize = 16177215)  
public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String SAVE_DIR = "F:\\Banner\\BannerServlet\\WebContent\\outputBanner";

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
		HttpSession session = request.getSession();
		bannerModel.setUsername(session.getAttribute("UserName").toString());

		// gets absolute path of the web application
		String savePath =  SAVE_DIR+"\\"+bannerModel.getUsername();

		String canvas_width = request.getParameter("canvas_width");
		String canvas_height = request.getParameter("canvas_height");

		String colorpicker = request.getParameter("colorpicker");

		String hpl_link = request.getParameter("hpl_link");
		String target = request.getParameter("hpl_target");

		//Part filePart = request.getPart("file_name[0]"); // Retrieves <input type="file" name="file">
		//String image = filePart.getSubmittedFileName();


		//List<String banner_text = request.getParameter("banner_text");


		List<FrameModel> frameList = new ArrayList<>();




		bannerModel.setFrames(frameList);

		bannerModel.setCanvas_height(canvas_height);
		bannerModel.setCanvas_width(canvas_width);
		bannerModel.setColorpicker(colorpicker);
		bannerModel.setHpl_link(hpl_link);
		bannerModel.setTarget(target);


		// creates the save directory if it does not exists
		File fileSaveDir = new File(savePath); 
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir(); 
		}
		FrameModel frame = new FrameModel();
		List<ImageModel> imageList = new ArrayList<>();
		//String banner_text = "";
		int counter = 0;
		for (Part part : request.getParts()) {

			String fileName = extractFileName(part);
			// refines the fileName in case it is an absolute path
			fileName = new File(fileName.trim()).getName();
			if(!fileName.isEmpty()) {
				ImageModel imageModel = new ImageModel();
				File filesSaveDir = new File(savePath);
				System.out.println("save"+savePath);
				if (!filesSaveDir.exists()) {
					System.out.println("no exist"+savePath);
					filesSaveDir.mkdir(); 
				}
				File filesSaveImageDir = new File(savePath+"\\images");
				if (!filesSaveImageDir.exists()) {
					System.out.println("no exist 2"+filesSaveImageDir.getAbsolutePath());
					filesSaveImageDir.mkdir(); 
				}
				part.write(savePath+File.separator+"images" + File.separator + fileName);
				//System.out.println(counter+request.getParameter("image_stop_time[" + counter + "]"));
				float imageOnTime = Float.parseFloat(request.getParameter("imame_start_time[" + counter + "]")!= null ? request.getParameter("imame_start_time[" + counter + "]"):"0");
				float imageOffTime = Float.parseFloat(request.getParameter("image_stop_time[" + counter + "]")!=null?request.getParameter("image_stop_time[" + counter + "]"):"1");

				imageModel.setImagePath(fileName);
				imageModel.setOnTime(imageOnTime);
				imageModel.setOffTime(imageOffTime);
				imageList.add(imageModel);				
				counter++;

			}


		}

		frame.setImageList(imageList);
		String[] bannerTextArray;
		bannerTextArray = request.getParameterValues("banner_text[]");


		List<TextModel> bannerTextList = new ArrayList<>();
		for(counter = 0; counter < bannerTextArray.length; counter++) {
			TextModel bannerText = new TextModel();
			
			float textOnTime = Float.parseFloat(request.getParameter("text_start_time[" + counter + "]")!=null ? request.getParameter("text_start_time[" + counter + "]"):"0");
			float textOffTime = Float.parseFloat(request.getParameter("text_end_time[" + counter + "]")!=null?request.getParameter("text_end_time[" + counter + "]"):"1");
			
			bannerText.setText(bannerTextArray[counter]);
			bannerText.setOnTime(textOnTime);
			bannerText.setOffTime(textOffTime);
			bannerTextList.add(bannerText);
			counter++;
		}
		frame.setTextList(bannerTextList);

		request.setAttribute("message", "Upload has been done successfully!");

		frameList.add(frame);
		bannerModel.setFrames(frameList);
		System.out.println("Canvas Width is: " + canvas_width);
		System.out.println("Canvas Height is: " + canvas_height);
		
		PrintWriter writer = response.getWriter();

		String htmlRespone = "<html><h3>";
		htmlRespone += "user is: " + bannerModel.getUsername()+ "<br/>";	
		htmlRespone += "canvas_width is a: " + canvas_width + "<br/>";		
		htmlRespone += "canvas_height is: " + canvas_height + "<br/>";	
		//htmlRespone += "image is: " + image + "<br/>";
		htmlRespone += "Path: " + savePath + "<br/>";
		//htmlRespone += "banner_text is: " + banner_text + "<br/>";
		htmlRespone += "hpl_link is: " + frameList.get(0).getImageList().get(0).getImagePath() + "<br/>";
		htmlRespone += "target is: " + target + "<br/>";

		htmlRespone += "</h3></html>";

		htmlRespone+=BannerCreator.createHTML(bannerModel);

		// return response
		writer.println(htmlRespone);		
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length()-1);
			}
		}
		return "";
	}



}
