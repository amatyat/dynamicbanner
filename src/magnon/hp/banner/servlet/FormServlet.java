package magnon.hp.banner.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import magnon.hp.banner.db.JDBCConncetionProvider;
import magnon.hp.banner.model.BannerModel;
import magnon.hp.banner.model.FrameModel;
import magnon.hp.banner.model.ImageModel;
import magnon.hp.banner.model.TextModel;

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
		Date bannerDate = new Date(new java.util.Date().getTime());

		long bannerTime = new java.util.Date().getTime();

		bannerModel.setFoldername(bannerDate+"_"+bannerTime);
		// gets absolute path of the web application
		String savePath =  SAVE_DIR+"\\"+bannerModel.getUsername();

		String canvas_width = request.getParameter("canvas_width");
		String canvas_height = request.getParameter("canvas_height");

		String colorpicker = request.getParameter("colorpicker");

		String hpl_link = request.getParameter("hpl_link");
		String target = request.getParameter("hpl_target");

		List<FrameModel> frameList = new ArrayList<>();
		

		//frame image start and stop time in sec
		String[] imageStartTimings = request.getParameterValues("image_start_time[]");
		String[] imageStopTimings = request.getParameterValues("image_stop_time[]");
		
		//frame image start and stop coordinates
		String[] imageStartCoordinates = request.getParameterValues("image_start_xy[]");
		String[] imageStopCoordinates = request.getParameterValues("image_stop_xy[]");
		
		//frame text array
		String[] bannerTextArray;
		bannerTextArray = request.getParameterValues("banner_text[]");
		
		
		//frame text start and stop time in sec
		String[] textStartTimings = request.getParameterValues("text_start_time[]");
		String[] textStopTimings = request.getParameterValues("text_end_time[]");
		
		//frame image start and stop coordinates
		String[] textStartCoordinates = request.getParameterValues("text_start_xy[]");
		String[] textStopCoordinates = request.getParameterValues("text_stop_xy[]");


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
				File userSaveDir = new File(savePath+"\\"+bannerModel.getFoldername());
				System.out.println("save"+userSaveDir);
				if (!userSaveDir.exists()) {
					System.out.println("no exist"+userSaveDir);
					userSaveDir.mkdir(); 
				}
			
				File filesSaveImageDir = new File(savePath+"\\"+bannerModel.getFoldername()+"\\images");
				if (!filesSaveImageDir.exists()) {
					System.out.println("no exist 2"+filesSaveImageDir.getAbsolutePath());
					filesSaveImageDir.mkdir(); 
				}
				part.write(savePath+"\\"+bannerModel.getFoldername()+File.separator+"images" + File.separator + fileName);
				
				String onTime = imageStartTimings[counter];
				String offTime = imageStopTimings[counter];
				float imageOnTime = 0, imageOffTime = 0;
				if(onTime.trim().length() > 0) {
					imageOnTime = Float.parseFloat(onTime);
				}
				if(offTime.trim().length() > 0) {
					imageOffTime = Float.parseFloat(offTime);
				}
				
				String startCoordinates = imageStartCoordinates[counter];
				String stopCoordinates = imageStopCoordinates[counter];
				String[] startCoordinatesArray, stopCoordinatesArray;
				float startCoordinateX = 0, startCoordinateY = 0, stopCoordinateX = 0, stopCoordinateY = 0;
				//check if start coordinate string is not empty and must contains a "," 
				//if((startCoordinates.trim().length() > 0) && (StringUtils.countMatches(startCoordinates, ",") == 1)) {
				if((startCoordinates.trim().length() > 0)) {
					startCoordinatesArray = startCoordinates.split(",");
					if(startCoordinatesArray[0].length() > 0) {
						startCoordinateX = Float.parseFloat(startCoordinatesArray[0]);
					}
					if(startCoordinatesArray[1].length() > 0) {
						startCoordinateY = Float.parseFloat(startCoordinatesArray[1]);
					}
				}
				//check if start coordinate string is not empty and must contains a "," 
				//if((stopCoordinates.trim().length() > 0) && (StringUtils.countMatches(stopCoordinates, ",") == 1)) {
				if((stopCoordinates.trim().length() > 0)) {
					stopCoordinatesArray = stopCoordinates.split(",");
					if(stopCoordinatesArray[0].length() > 0) {
						stopCoordinateX = Float.parseFloat(stopCoordinatesArray[0]);
					}
					if(stopCoordinatesArray[1].length() > 0) {
						stopCoordinateY = Float.parseFloat(stopCoordinatesArray[1]);
					}
				}
				
				imageModel.setImagePath(fileName);
				imageModel.setOnTime(imageOnTime);
				imageModel.setOffTime(imageOffTime);
				imageModel.setSartCoordinateX(startCoordinateX);
				imageModel.setSartCoordinateY(startCoordinateY);
				imageModel.setStopCoordinateX(stopCoordinateX);
				imageModel.setStopCoordinateY(stopCoordinateY);
				imageList.add(imageModel);				
				counter++;

			}

		}

		frame.setImageList(imageList);

		System.out.print("Banner Array Size : " + bannerTextArray.length);


		List<TextModel> bannerTextList = new ArrayList<>();
		for(counter = 0; counter < bannerTextArray.length; counter++) {
			TextModel bannerText = new TextModel();

			String onTime = textStartTimings[counter];
			String offTime = textStopTimings[counter];
			float textOnTime = 0, textOffTime = 0;
			if(onTime.trim().length() > 0) {
				textOnTime = Float.parseFloat(onTime);
			}
			if(offTime.trim().length() > 0) {
				textOffTime = Float.parseFloat(offTime);
			}
			
			String startCoordinates = textStartCoordinates[counter];
			String stopCoordinates = textStopCoordinates[counter];
			String[] startCoordinatesArray, stopCoordinatesArray;
			float startCoordinateX = 0, startCoordinateY = 0, stopCoordinateX = 0, stopCoordinateY = 0;
			//check if start coordinate string is not empty and must contains a "," 
			//if((startCoordinates.trim().length() > 0) && (StringUtils.countMatches(startCoordinates, ",") == 1)) {
			if((startCoordinates.trim().length() > 0)) {
				startCoordinatesArray = startCoordinates.split(",");
				if(startCoordinatesArray[0].length() > 0) {
					startCoordinateX = Float.parseFloat(startCoordinatesArray[0]);
				}
				if(startCoordinatesArray[1].length() > 0) {
					startCoordinateY = Float.parseFloat(startCoordinatesArray[1]);
				}
			}
			//check if start coordinate string is not empty and must contains a "," 
			//if((stopCoordinates.trim().length() > 0) && (StringUtils.countMatches(stopCoordinates, ",") == 1)) {
			if((stopCoordinates.trim().length() > 0)) {
				stopCoordinatesArray = stopCoordinates.split(",");
				if(stopCoordinatesArray[0].length() > 0) {
					stopCoordinateX = Float.parseFloat(stopCoordinatesArray[0]);
				}
				if(stopCoordinatesArray[1].length() > 0) {
					stopCoordinateY = Float.parseFloat(stopCoordinatesArray[1]);
				}
			}
			bannerText.setText(bannerTextArray[counter]);
			bannerText.setOnTime(textOnTime);
			bannerText.setOffTime(textOffTime);
			bannerText.setSartCoordinateX(startCoordinateX);
			bannerText.setSartCoordinateY(startCoordinateY);
			bannerText.setStopCoordinateX(stopCoordinateX);
			bannerText.setStopCoordinateY(stopCoordinateY);
			bannerTextList.add(bannerText);
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
		htmlRespone += "Path: " + savePath + "<br/>";
		htmlRespone += "hpl_link is: " + frameList.get(0).getImageList().get(0).getImagePath() + "<br/>";
		htmlRespone += "target is: " + target + "<br/>";


		htmlRespone += "Download Banners from below: <br/>";
		System.out.println(bannerModel.getUsername());
		htmlRespone +="<br/><a href=\"UploadDownloadFileServlet?folder="+bannerModel.getFoldername()+"&user="+bannerModel.getUsername()+"&fileName="+"banner.html"+"\">"+bannerModel.getFoldername()+"</a>";

		JDBCConncetionProvider jConncetionProvider = new JDBCConncetionProvider();
		Connection con = jConncetionProvider.connect();
		try {

			Statement statement = con.createStatement();
			String queryString = "select * from banner_details where name='"+bannerModel.getUsername()+"'";
			System.out.println(queryString);
			ResultSet rs = statement.executeQuery(queryString);
			while (rs.next()) {
				System.out.print("Folder: "+rs.getString(3));

				htmlRespone +="<br/><a href=\"UploadDownloadFileServlet?folder="+rs.getString(3)+"&user="+bannerModel.getUsername()+"&fileName="+"banner.html"+"\">"+rs.getString(3)+"</a>";

			}

		} catch (Exception e) {
			e.printStackTrace();
		}


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
