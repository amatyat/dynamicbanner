package magnon.hp.banner.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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

	private static final String SAVE_DIR = "outputBanner";

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
	
		File serverDirectory = new File(request.getServletContext().getRealPath("/"));
		File parentDirectory = serverDirectory.getParentFile().getParentFile();

		File rootDirectory = new File(parentDirectory.getAbsolutePath()+File.separator+"webapps/ROOT");
		System.out.println("Context path"+request.getServletContext().getRealPath("/") + " :::Path info "+ rootDirectory.getAbsolutePath());
		
		String imageUrl = request.getRequestURL().toString().replace("/BannerServlet/FormServlet", "") + "/" + SAVE_DIR + "/" + bannerModel.getUsername() + "/" + bannerModel.getFoldername() + "/" + "images/";
		
		// gets absolute path of the web application
		String savePath =  rootDirectory.getAbsolutePath()+File.separator+SAVE_DIR+File.separator+bannerModel.getUsername();

		File saveDir = new File(savePath);

		if(!saveDir.exists()) {
			saveDir.mkdir();
		}
		
		//set banner file directory path
		bannerModel.setBanner_file_path(savePath + File.separator + bannerModel.getFoldername());
		
		File folderDir = new File(savePath + File.separator +bannerModel.getFoldername());

		if(!folderDir.exists()) {
			folderDir.mkdir();
		}

		File imagefolderDir = new File(folderDir.getPath()+File.separator+"images");
		
		System.out.println("Image Folder:"+imagefolderDir.getAbsolutePath());

		if(!imagefolderDir.exists()) {
			imagefolderDir.mkdir();
		}

		FrameModel frame = new FrameModel();
		List<ImageModel> imageList = new ArrayList<>();
		List<TextModel> bannerTextList = new ArrayList<>();

		//	bannerModel.setFrames(frameList);

		String canvas_width = null, canvas_height = null, colorpicker, hpl_link, target = null, animation_loop = null, 
				loop_count = null, pause_on_hover = null, gradient = null, gradient_value = null, replay = null, elementCountStr = null;

		//total frames
		int totalFrameCount = 0;
		int elementCount = 0;



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
					if((fileItem.getFieldName()).equals("max_stop_time")) {
						bannerModel.setMax_stop_time(fileItem.getString());
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
					if((fileItem.getFieldName()).equals("gradient")) {
						gradient = fileItem.getString();
						bannerModel.setIs_gradient(gradient);
					}
					if((fileItem.getFieldName()).equals("gradient_value")) {
						gradient_value = fileItem.getString();
						bannerModel.setGradient_value(gradient_value);
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
						if(loop_count.equals(""))
							loop_count = "0";
						bannerModel.setLoop_count(loop_count);
					}
					if((fileItem.getFieldName()).equals("pause")) {
						pause_on_hover = fileItem.getString();
						bannerModel.setPause_on_hover(pause_on_hover);
					}
					if((fileItem.getFieldName()).equals("replay")) {
						replay = fileItem.getString();
						bannerModel.setReplay(replay);
					}
					if((fileItem.getFieldName()).equals("frame_start[]")) {
						frameCount = Integer.parseInt(fileItem.getString());
						frame = new FrameModel();

						imageList = new ArrayList<ImageModel>();
						bannerTextList = new ArrayList<TextModel>();
						System.out.println("start");
					}
					if((fileItem.getFieldName()).equals("frame_end[]")) {

						//set image list to frame
						frame.setImageList(imageList);

						//set text list to frame
						frame.setTextList(bannerTextList);

						frameList.add(frame);
						System.out.println("end"+imageList.get(0).getImagePath());
					}

					
					if((fileItem.getFieldName()).equals("frame_element_count[frame_" + frameCount + "][]")) {
						elementCountStr = fileItem.getString();
						
						elementCount = Integer.parseInt(elementCountStr);
						
						System.out.println(bannerText+"Element Numner : " + elementCount);
						
						bannerText = new TextModel();
						imageModel = new ImageModel();
						bannerText.setElementNumber(elementCount);
						imageModel.setElementNumber(elementCount);

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
					if((fileItem.getFieldName()).equals("image_end_start_time[frame_" + frameCount + "][]")) {
						onTime = fileItem.getString();

						if(onTime.trim().length() > 0) {
							imageOnTime = Float.parseFloat(onTime);
						}
						imageModel.setEndOnTime(imageOnTime);
					}
					if((fileItem.getFieldName()).equals("image_end_stop_time[frame_" + frameCount + "][]")) {
						offTime = fileItem.getString();

						if(offTime.trim().length() > 0) {
							imageOffTime = Float.parseFloat(offTime);
						}
						imageModel.setEndOffTime(imageOffTime);
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
						imageModel.setStartCoordinateX(startCoordinateX);
						imageModel.setStartCoordinateY(startCoordinateY);
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
					if((fileItem.getFieldName()).equals("image_end_effect_xy[frame_" + frameCount + "][]")) {
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
						imageModel.setEndCoordinateX(stopCoordinateX);
						imageModel.setEndCoordinateY(stopCoordinateY);
					}
					
					if((fileItem.getFieldName()).equals("image_width[frame_" + frameCount + "][]")) {
						imageModel.setWidth((fileItem.getString()));
					}
					
					if((fileItem.getFieldName()).equals("image_height[frame_" + frameCount + "][]")) {
						imageModel.setHeight((fileItem.getString()));
					}
					
					if((fileItem.getFieldName()).equals("image_effect[frame_" + frameCount + "][]")) {
						imageModel.setEffect(fileItem.getString());
						
						//imageList.add(imageModel);
					}
					
					if((fileItem.getFieldName()).equals("image_end_effect[frame_" + frameCount + "][]")) {
						imageModel.setEndEffect(fileItem.getString());
						
						imageList.add(imageModel);
					}

					if((fileItem.getFieldName()).equals("banner_text[frame_" + frameCount + "][]")) {

						bannerText.setText(fileItem.getString());
					}
					
					if((fileItem.getFieldName()).equals("banner_text_svg[frame_" + frameCount + "][]")) {

						bannerText.setBannerTextSvg(fileItem.getString());
					}
								
					if((fileItem.getFieldName()).equals("banner_text_color[frame_" + frameCount + "][]")) {

						bannerText.setBannerTextColor(fileItem.getString());
					}
					
					if((fileItem.getFieldName()).equals("text_font_size[frame_" + frameCount + "][]")) {

						bannerText.setTextFontSize(fileItem.getString());
					}
					
					if((fileItem.getFieldName()).equals("text_font[frame_" + frameCount + "][]")) {

						bannerText.setTextFont(fileItem.getString());
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
					if((fileItem.getFieldName()).equals("text_end_start_time[frame_" + frameCount + "][]")) {
						onTime = fileItem.getString();

						if(onTime.trim().length() > 0) {
							textOnTime = Float.parseFloat(onTime);
						}
						bannerText.setEndOnTime(textOnTime);

					}
					if((fileItem.getFieldName()).equals("text_end_stop_time[frame_" + frameCount + "][]")) {
						offTime = fileItem.getString();

						if(offTime.trim().length() > 0) {
							textOffTime = Float.parseFloat(offTime);
						}

						bannerText.setEndOffTime(textOffTime);
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
						bannerText.setStartCoordinateX(startCoordinateX);
						bannerText.setStartCoordinateY(startCoordinateY);
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
					if((fileItem.getFieldName()).equals("text_end_effect_xy[frame_" + frameCount + "][]")) {
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
						bannerText.setEndCoordinateX(stopCoordinateX);
						bannerText.setEndCoordinateY(stopCoordinateY);
					}
					if((fileItem.getFieldName()).equals("text_effect[frame_" + frameCount + "][]")) {
						bannerText.setEffect(fileItem.getString());
					}
					if((fileItem.getFieldName()).equals("text_end_effect[frame_" + frameCount + "][]")) {
						bannerText.setEndEffect(fileItem.getString());
						
						bannerTextList.add(bannerText);
					}


				} else {

					//file upload code starts
					String fileName = new File(fileItem.getName()).getName();

					String filePath = imagefolderDir.getPath()+File.separator+ fileName;
					File storeFile = new File(filePath);
					
					// saves the file on disk
					fileItem.write(storeFile);
					//file upload code ends

					imageModel.setImagePath(fileName);

				}

			}

			bannerModel.setFrames(frameList);
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();
			
			String htmlRespone = "";
			
			htmlRespone += BannerCreator.createBanner(bannerModel);

			htmlRespone += "Download Banners from below: <br/>";
			
			htmlRespone +="<br/><a href=\"UploadDownloadFileServlet?folder="+bannerModel.getFoldername()+"&user="+bannerModel.getUsername()+"&fileName="+"banner.html"+"\">"+bannerModel.getFoldername()+"</a>";

			/*//Gets folder details from database 
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
			}*/
			
			//String imageFolderPath = "file://"+imagefolderDir.getAbsolutePath().replace("\\", "/");
			
			//String dadaUrl = "data:image/jpeg;base64,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";
			
			//System.out.println(imageFolderPath);
			// return response
			writer.println(htmlRespone.replace("images/", imageUrl));

		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
