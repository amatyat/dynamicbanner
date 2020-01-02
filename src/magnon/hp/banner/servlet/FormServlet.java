package magnon.hp.banner.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import magnon.hp.banner.db.JDBCConncetionProvider;
import magnon.hp.banner.html.BannerCreator;
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
		List<FrameModel> frameList = new ArrayList<>();
		HttpSession session = request.getSession();
		bannerModel.setUsername(session.getAttribute("UserName").toString());
		Date bannerDate = new Date(new java.util.Date().getTime());
		long bannerTime = new java.util.Date().getTime();

		bannerModel.setFoldername(bannerDate+"_"+bannerTime);
		// gets absolute path of the web application
		String savePath =  SAVE_DIR+File.separator+bannerModel.getUsername();

		File saveDir = new File(savePath);

		if(!saveDir.exists()) {
			saveDir.mkdir();
		}

		File folderDir = new File(savePath + File.separator +bannerModel.getFoldername());

		if(!folderDir.exists()) {
			folderDir.mkdir();
		}

		File imagefolderDir = new File(folderDir.getPath()+File.separator+"images");

		if(!imagefolderDir.exists()) {
			imagefolderDir.mkdir();
		}

		FrameModel frame = new FrameModel();
		List<ImageModel> imageList = new ArrayList<>();
		List<TextModel> bannerTextList = new ArrayList<>();

		//	bannerModel.setFrames(frameList);

		String canvas_width = null, canvas_height = null, colorpicker, hpl_link, target = null, animation_loop = null, loop_count = null, pause_on_hover = null;

		//total frames
		int totalFrameCount = 0;



		//frame text array
		ArrayList<String[]> bannerTextArray = new ArrayList<String[]>();
		for(int frameCount = 1; frameCount <= totalFrameCount; frameCount++) {
			bannerTextArray.add(request.getParameterValues("banner_text[frame_" + frameCount + "][]"));
		}



		//frame text start and stop time in sec
		ArrayList<String[]> textStartTimings = new ArrayList<String[]>();
		ArrayList<String[]> textStopTimings = new ArrayList<String[]>();
		for(int frameCount = 1; frameCount <= totalFrameCount; frameCount++) {
			textStartTimings.add(request.getParameterValues("text_start_time[frame_" + frameCount + "][]"));
		}
		for(int frameCount = 1; frameCount <= totalFrameCount; frameCount++) {
			textStopTimings.add(request.getParameterValues("text_end_time[frame_" + frameCount + "][]"));
		}

		//frame image start and stop coordinates
		ArrayList<String[]> textStartCoordinates = new ArrayList<String[]>();
		ArrayList<String[]> textStopCoordinates = new ArrayList<String[]>();
		for(int frameCount = 1; frameCount <= totalFrameCount; frameCount++) {
			textStartCoordinates.add(request.getParameterValues("text_start_xy[frame_" + frameCount + "][]"));
		}
		for(int frameCount = 1; frameCount <= totalFrameCount; frameCount++) {
			textStopCoordinates.add(request.getParameterValues("text_stop_xy[frame_" + frameCount + "][]"));
		}

		/////////////////////////

		response.setContentType("text/html");

		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			//out.println("You are not trying to upload<br/>");
			return;
		}
		//out.println("You are trying to upload<br/>");

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> fields = upload.parseRequest(request);
			//out.println("Number of fields: " + fields.size() + "<br/><br/>");
			Iterator<FileItem> it = fields.iterator();
			if (!it.hasNext()) {
				System.out.println("No fields found");
				return;
			}
			//out.println("<table border=\"1\">");


			ImageModel imageModel = null;
			TextModel bannerText = null;
			int frameCount = 1;
			while (it.hasNext()) {
				String onTime = null, offTime = null;
				String startCoordinates = null, stopCoordinates = null;
				float imageOnTime = 0;
				float imageOffTime = 0;
				float textOnTime = 0;
				float textOffTime = 0;

				FileItem fileItem = it.next();
				boolean isFormField = fileItem.isFormField();
				if (isFormField) {

					if((fileItem.getFieldName()).equals("total_frame_count")) {
						totalFrameCount = Integer.parseInt(fileItem.getString());
					}
					if((fileItem.getFieldName()).equals("canvas_width")) {
						canvas_width = fileItem.getString();
						bannerModel.setCanvas_width(canvas_width);
					}
					if((fileItem.getFieldName()).equals("canvas_height")) {
						canvas_height = fileItem.getString();
						bannerModel.setCanvas_height(canvas_height);
					}
					if((fileItem.getFieldName()).equals("colorpicker")) {
						colorpicker = fileItem.getString();
						bannerModel.setColorpicker(colorpicker);
					}
					if((fileItem.getFieldName()).equals("hpl_link")) {
						hpl_link = fileItem.getString();
						bannerModel.setHpl_link(hpl_link);
					}
					if((fileItem.getFieldName()).equals("hpl_target")) {
						target = fileItem.getString();
						bannerModel.setTarget(target);
					}
					if((fileItem.getFieldName()).equals("loop")) {
						animation_loop = fileItem.getString();
						bannerModel.setAnimation_loop(animation_loop);
					}
					if((fileItem.getFieldName()).equals("no_of_time")) {
						loop_count = fileItem.getString();
						bannerModel.setLoop_count(loop_count);
					}
					if((fileItem.getFieldName()).equals("pause")) {
						pause_on_hover = fileItem.getString();
						bannerModel.setPause_on_hover(pause_on_hover);
					}
					if((fileItem.getFieldName()).equals("frame_start[]")) {
						frameCount = Integer.parseInt(fileItem.getString());
						frame = new FrameModel();

						imageList = new ArrayList<ImageModel>();
						bannerTextList = new ArrayList<TextModel>();
						System.out.println("start");
					}
					if((fileItem.getFieldName()).equals("frame_end[]")) {


						/*	out.println(imageList.size()+"<br/>"+ frameCount +"*************");
						for(int i=0; i<imageList.size();i++) {
							out.println(imageList.get(i).getImagePath() + "&&&&&&&&&&& "+imageList.get(i).getOffTime());
						}

						out.println(bannerTextList.size()+"<br/>");
						for(int i=0; i<bannerTextList.size();i++) {
							out.println(bannerTextList.get(i).getText() + "&&&&$$$$$$$$$$$$$$$$$$$$&&&& "+bannerTextList.get(i).getOffTime());
						}*/
						//set image list to frame
						frame.setImageList(imageList);

						//set text list to frame
						frame.setTextList(bannerTextList);

						frameList.add(frame);
						System.out.println("end"+imageList.get(0).getImagePath());
					}


					if((fileItem.getFieldName()).equals("image_start_time[frame_" + frameCount + "][]")) {
						onTime = fileItem.getString();

						if(onTime.trim().length() > 0) {
							imageOnTime = Float.parseFloat(onTime);
						}
						imageModel.setOnTime(imageOnTime);
					}
					if((fileItem.getFieldName()).equals("image_stop_time[frame_" + frameCount + "][]")) {
						offTime = fileItem.getString();

						if(offTime.trim().length() > 0) {
							imageOffTime = Float.parseFloat(offTime);
						}
						imageModel.setOffTime(imageOffTime);
					}
					if((fileItem.getFieldName()).equals("image_start_xy[frame_" + frameCount + "][]")) {
						startCoordinates = fileItem.getString();

						String[] startCoordinatesArray;
						float startCoordinateX = 0, startCoordinateY = 0;
						//check if start coordinate string is not empty and must contains a ","
						if((startCoordinates.trim().length() > 0)) {
							startCoordinatesArray = startCoordinates.split(",");
							if(startCoordinatesArray[0].length() > 0) {
								startCoordinateX = Float.parseFloat(startCoordinatesArray[0]);
							}
							if(startCoordinatesArray[1].length() > 0) {
								startCoordinateY = Float.parseFloat(startCoordinatesArray[1]);
							}
						}
						imageModel.setSartCoordinateX(startCoordinateX);
						imageModel.setSartCoordinateY(startCoordinateY);
					}
					if((fileItem.getFieldName()).equals("image_stop_xy[frame_" + frameCount + "][]")) {
						stopCoordinates = fileItem.getString();

						String[] stopCoordinatesArray;
						float stopCoordinateX = 0, stopCoordinateY = 0;
						//check if start coordinate string is not empty and must contains a ","
						if((stopCoordinates.trim().length() > 0)) {
							stopCoordinatesArray = stopCoordinates.split(",");
							if(stopCoordinatesArray[0].length() > 0) {
								stopCoordinateX = Float.parseFloat(stopCoordinatesArray[0]);
							}
							if(stopCoordinatesArray[1].length() > 0) {
								stopCoordinateY = Float.parseFloat(stopCoordinatesArray[1]);
							}
						}
						imageModel.setStopCoordinateX(stopCoordinateX);
						imageModel.setStopCoordinateY(stopCoordinateY);
					}
					if((fileItem.getFieldName()).equals("image_effect[frame_" + frameCount + "][]")) {
						imageModel.setEffect(fileItem.getString());
						
						imageList.add(imageModel);
					}

					if((fileItem.getFieldName()).equals("banner_text[frame_" + frameCount + "][]")) {

						bannerText = new TextModel();
						bannerText.setText(fileItem.getString());
					}
					if((fileItem.getFieldName()).equals("text_start_time[frame_" + frameCount + "][]")) {
						onTime = fileItem.getString();

						if(onTime.trim().length() > 0) {
							textOnTime = Float.parseFloat(onTime);
						}
						bannerText.setOnTime(textOnTime);

					}
					if((fileItem.getFieldName()).equals("text_end_time[frame_" + frameCount + "][]")) {
						offTime = fileItem.getString();

						if(offTime.trim().length() > 0) {
							textOffTime = Float.parseFloat(offTime);
						}

						bannerText.setOffTime(textOffTime);
					}
					if((fileItem.getFieldName()).equals("text_start_xy[frame_" + frameCount + "][]")) {
						startCoordinates = fileItem.getString();

						String[] startCoordinatesArray;
						float startCoordinateX = 0, startCoordinateY = 0;
						//check if start coordinate string is not empty and must contains a ","
						if((startCoordinates.trim().length() > 0)) {
							startCoordinatesArray = startCoordinates.split(",");
							if(startCoordinatesArray[0].length() > 0) {
								startCoordinateX = Float.parseFloat(startCoordinatesArray[0]);
							}
							if(startCoordinatesArray[1].length() > 0) {
								startCoordinateY = Float.parseFloat(startCoordinatesArray[1]);
							}
						}
						bannerText.setSartCoordinateX(startCoordinateX);
						bannerText.setSartCoordinateY(startCoordinateY);
					}
					if((fileItem.getFieldName()).equals("text_stop_xy[frame_" + frameCount + "][]")) {
						stopCoordinates = fileItem.getString();

						String[] stopCoordinatesArray;
						float stopCoordinateX = 0, stopCoordinateY = 0;
						//check if start coordinate string is not empty and must contains a "," 
						if((stopCoordinates.trim().length() > 0)) {
							stopCoordinatesArray = stopCoordinates.split(",");
							if(stopCoordinatesArray[0].length() > 0) {
								stopCoordinateX = Float.parseFloat(stopCoordinatesArray[0]);
							}
							if(stopCoordinatesArray[1].length() > 0) {
								stopCoordinateY = Float.parseFloat(stopCoordinatesArray[1]);
							}
						}
						bannerText.setStopCoordinateX(stopCoordinateX);
						bannerText.setStopCoordinateY(stopCoordinateY);
					}
					if((fileItem.getFieldName()).equals("text_effect[frame_" + frameCount + "][]")) {
						bannerText.setEffect(fileItem.getString());
						
						bannerTextList.add(bannerText);
					}


				} else {

					//file upload code starts
					String fileName = new File(fileItem.getName()).getName();

					String filePath = imagefolderDir.getPath()+File.separator+ fileName;
					File storeFile = new File(filePath);
					System.out.println(fileName+"filePath :::::"+filePath);
					// saves the file on disk
					fileItem.write(storeFile);
					//file upload code ends

					imageModel = new ImageModel();
					imageModel.setImagePath(fileName);

				}

			}

			bannerModel.setFrames(frameList);

			PrintWriter writer = response.getWriter();

			String htmlRespone = "";
			htmlRespone += BannerCreator.createBanner(bannerModel);

			htmlRespone += "Download Banners from below: <br/>";

			//htmlRespone +="<br/><a href=\"UploadDownloadFileServlet?folder="+bannerModel.getFoldername()+"&user="+bannerModel.getUsername()+"&fileName="+"banner.html"+"\">"+bannerModel.getFoldername()+"</a>";

			//Gets folder details from database 
			//htmlRespone = DBOperations.select(bannerModel, htmlRespone);
			Connection con = new JDBCConncetionProvider().connect();
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
			// return response
			writer.println(htmlRespone);	

		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
