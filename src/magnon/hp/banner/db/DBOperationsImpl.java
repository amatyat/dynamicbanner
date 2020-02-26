package magnon.hp.banner.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import magnon.hp.banner.model.BannerModel;
import magnon.hp.banner.model.FrameModel;
import magnon.hp.banner.model.ImageModel;
import magnon.hp.banner.model.TextModel;

public class DBOperationsImpl implements DBOperations{
	private static Connection con = new JDBCConncetionProvider().connect();

	public void insert(BannerModel bannerModel) {

		String SQL_INSERT = "INSERT INTO banner_details (name, banner_name, created_date) VALUES (?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement
					(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			Date sqlDate = new Date(new java.util.Date().getTime());

			ps.setString(1, bannerModel.getUsername());
			ps.setString(2, bannerModel.getFoldername());
			ps.setDate(3, sqlDate);
			int i = ps.executeUpdate();

			ResultSet rs=ps.getGeneratedKeys();
			int bannerID = 0;
			if(rs.next()){
				bannerID=rs.getInt(1);
				
				insertBanner(bannerModel, bannerID);
				
				insertFrames(bannerModel, bannerID);
			}
			if(i>0) {
				System.out.println("Banner added");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void insertBanner(BannerModel bannerModel, int bannerID) {
		String SQL_INSERT = "INSERT INTO banner (banner_id, canvas_height, "
				+ "canvas_width, colorpicker, hpl_link, target,"
				+ " animation_loop, loop_count, pause_on_hover, "
				+ "gradient, replay, file_name, frame_count) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement
					(SQL_INSERT);

			ps.setInt(1, bannerID);
			ps.setString(2, bannerModel.getCanvas_height());
			ps.setString(3, bannerModel.getCanvas_width());
			ps.setString(4, bannerModel.getColorpicker());
			ps.setString(5, bannerModel.getHpl_link());
			
			ps.setString(6, bannerModel.getTarget());
			ps.setString(7, bannerModel.getAnimation_loop());
			ps.setInt(8, Integer.parseInt(bannerModel.getLoop_count()));
			ps.setString(9, bannerModel.getPause_on_hover());
			
			
			ps.setString(10, bannerModel.getGradient_value());
			ps.setString(11, bannerModel.getReplay());
			ps.setString(12, bannerModel.getFoldername());
			ps.setInt(13, bannerModel.getFrames().size());
			
			int i = ps.executeUpdate();

			if(i>0) {
				System.out.println("Banner added");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}


	private void insertFrames(BannerModel bannerModel, int bannerID) {

		for(FrameModel frameModel : bannerModel.getFrames()) {

			String SQL_INSERT = "INSERT INTO frames (banner_id, frame_element_count) VALUES (?,?)";
			try {
				PreparedStatement ps = con.prepareStatement
						(SQL_INSERT,PreparedStatement.RETURN_GENERATED_KEYS);

				ps.setInt(1, bannerID);
				ps.setInt(2, frameModel.getFrame_element_count());
				int i = ps.executeUpdate();
				ResultSet rs=ps.getGeneratedKeys();
				int frameID = 0;
				if(rs.next()){
					frameID=rs.getInt(1);
				}
				if(i>0) {
					System.out.println("Frame inserted");

					for(ImageModel imageModel : frameModel.getImageList()) {
						insertImage(imageModel, bannerID, frameID);
					}

					for(TextModel textModel : frameModel.getTextList()) {
						insertText(textModel, bannerID, frameID);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}


	private void insertImage(ImageModel imageModel, int bannerID, int frameID) {

		String SQL_INSERT = "INSERT INTO image_details (banner_id, frame_id, imagePath,"
				+ " onTime, offTime, endOnTime, endOffTime, "
				+ "startCoordinateX, startCoordinateY, stopCoordinateX, stopCoordinateY,"
				+ " effect, endeffect, "
				+ "width, height, elementNumber) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement
					(SQL_INSERT);

			ps.setInt(1, bannerID);
			ps.setInt(2, frameID);
			ps.setString(3, imageModel.getImagePath());
			ps.setFloat(4, imageModel.getOnTime());
			ps.setFloat(5, imageModel.getOffTime());
			ps.setFloat(6, imageModel.getEndOnTime());
			ps.setFloat(7, imageModel.getEndOffTime());
			
			ps.setFloat(8, imageModel.getStartCoordinateX());
			ps.setFloat(9, imageModel.getStartCoordinateY());
			ps.setFloat(10, imageModel.getStopCoordinateX());
			ps.setFloat(11, imageModel.getStopCoordinateY());
			
			ps.setString(12, imageModel.getEffect());
			ps.setString(13, imageModel.getEndEffect());
			ps.setString(14, imageModel.getWidth());
			ps.setString(15, imageModel.getHeight());
			
			ps.setInt(16, imageModel.getElementNumber());
			
			int i = ps.executeUpdate();
			if(i>0) {
				System.out.println("Image inserted");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}	

	}


	private void insertText(TextModel textModel, int bannerID, int frameID) {

		String SQL_INSERT = "INSERT INTO text_details (banner_id, frame_id, text, "
				+ "onTime, offTime, endOnTime, endOffTime, "
				+ "startCoordinateX, startCoordinateY, stopCoordinateX, stopCoordinateY,"
				+ " effect, endeffect, elementNumber) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement
					(SQL_INSERT);

			ps.setInt(1, bannerID);
			ps.setInt(2, frameID);
			ps.setString(3, textModel.getText());
			
			ps.setFloat(4, textModel.getOnTime());
			ps.setFloat(5, textModel.getOffTime());
			ps.setFloat(6, textModel.getEndOnTime());
			ps.setFloat(7, textModel.getEndOffTime());
			
			ps.setFloat(8, textModel.getStartCoordinateX());
			ps.setFloat(9, textModel.getStartCoordinateY());
			ps.setFloat(10, textModel.getStopCoordinateX());
			ps.setFloat(11, textModel.getStopCoordinateY());
			
			ps.setString(12, textModel.getEffect());
			ps.setString(13, textModel.getEndEffect());
			
			ps.setInt(14, textModel.getElementNumber());
			int i = ps.executeUpdate();
			if(i>0) {
				System.out.println("Text inserted");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}	

	}


	public String select(BannerModel bannerModel, String htmlRespone) {

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

		return htmlRespone;
	}
}
