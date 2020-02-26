package magnon.hp.banner.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import magnon.hp.banner.model.BannerModel;
import magnon.hp.banner.model.FrameModel;
import magnon.hp.banner.model.ImageModel;
import magnon.hp.banner.model.TextModel;

public class CreateBannerModelFromDB {

	private static Connection con = new JDBCConncetionProvider().connect();

	public static void main(String[] args) {

		CreateBannerModelFromDB createBannerModelFromDB = new CreateBannerModelFromDB();

		String fileName = "2020-01-14_1578987057877";
		
		try {
			//use this for banner model by creating object of this class
			//createBannerModelFromDB.getBannerModelFromDB(fileName)
			System.out.println(createBannerModelFromDB.getBannerModelFromDB(fileName));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//Get Banner Model from Database based on the filename
	public BannerModel getBannerModelFromDB(String fileName) throws SQLException {

		BannerModel bannerModel = new BannerModel();
		int bannerID = 0;
		Statement statement = null;
		try {
			statement = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {

			//Get Banner Information based on the fileName and populate BannerModel
			String queryString = "select * from banner where file_name='"+fileName+"'";
			System.out.println(queryString);
			ResultSet rs = statement.executeQuery(queryString);

			while (rs.next()) {

				bannerID = rs.getInt(1);

				bannerModel.setCanvas_height(rs.getString(2));
				bannerModel.setCanvas_width(rs.getString(3));
				bannerModel.setColorpicker(rs.getString(4));
				bannerModel.setHpl_link(rs.getString(5));
				bannerModel.setTarget(rs.getString(6));
				bannerModel.setAnimation_loop(rs.getString(7));
				bannerModel.setLoop_count(rs.getInt(8)+"");
				bannerModel.setPause_on_hover(rs.getString(9));

				bannerModel.setGradient_value(rs.getString(10));
				bannerModel.setReplay(rs.getString(11));
				bannerModel.setFoldername(rs.getString(12));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Retrieve Frame ID based on Banner ID
		String queryFrameString = "select * from frames where banner_id='"+bannerID+"'";

		ResultSet rsFrames = statement.executeQuery(queryFrameString);

		List<FrameModel> frameList = new ArrayList<>();
		List<Integer> frameIDList = new ArrayList<>();
		int frameIDinList = 0 ;
		while (rsFrames.next()) {
			frameIDinList = rsFrames.getInt(1);
			frameIDList.add(frameIDinList);
		}

		for(int frameID: frameIDList) {

			FrameModel frameModel = new FrameModel();

			//Retrieve Image Details based on Banner ID and Frame ID
			String queryImageString = "select * from image_details where banner_id='"+bannerID+"' and frame_id='"+frameID+"'";

			ResultSet rsImage = statement.executeQuery(queryImageString);

			List<ImageModel> imageList = new ArrayList<>();
			while(rsImage.next()) {
				ImageModel imageModel = new ImageModel();
				imageModel.setImagePath(rsImage.getString(4));
				imageModel.setOnTime(rsImage.getFloat(5));
				imageModel.setOffTime(rsImage.getFloat(6));
				imageModel.setEndOnTime(rsImage.getFloat(7));
				imageModel.setEndOffTime(rsImage.getFloat(8));
				imageModel.setStartCoordinateX(rsImage.getFloat(9));
				imageModel.setStartCoordinateY(rsImage.getFloat(10));
				imageModel.setStopCoordinateX(rsImage.getFloat(11));
				imageModel.setStopCoordinateY(rsImage.getFloat(12));
				imageModel.setEffect(rsImage.getString(13));
				imageModel.setEndEffect(rsImage.getString(14));
				imageModel.setWidth(rsImage.getString(15));
				imageModel.setHeight(rsImage.getString(16));
				imageModel.setElementNumber(rsImage.getInt(17));
				imageList.add(imageModel);

			}
			frameModel.setImageList(imageList);

			//Retrieve Text details based on Banner ID and Frame ID
			String queryTextString = "select * from text_details where banner_id='"+bannerID+"' and frame_id='"+frameID+"'";

			ResultSet rsText = statement.executeQuery(queryTextString);

			List<TextModel> textList = new ArrayList<>();
			while(rsText.next()) {
				TextModel textModel = new TextModel();
				textModel.setText(rsText.getString(4));
				textModel.setOnTime(rsText.getFloat(5));
				textModel.setOffTime(rsText.getFloat(6));
				textModel.setEndOnTime(rsText.getFloat(7));
				textModel.setEndOffTime(rsText.getFloat(8));
				textModel.setStartCoordinateX(rsText.getFloat(9));
				textModel.setStartCoordinateY(rsText.getFloat(10));
				textModel.setStopCoordinateX(rsText.getFloat(11));
				textModel.setStopCoordinateY(rsText.getFloat(12));
				textModel.setEffect(rsText.getString(13));
				textModel.setEndEffect(rsText.getString(14));
				textModel.setElementNumber(rsText.getInt(15));
				
				textList.add(textModel);
				

			}
			frameModel.setTextList(textList);

			frameList.add(frameModel);
		}
		bannerModel.setFrames(frameList);
		System.out.println(bannerID);

		return bannerModel;

	}
}
