package magnon.hp.banner.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import magnon.hp.banner.model.BannerModel;

public class DBOperations {
	private static Connection con = new JDBCConncetionProvider().connect();

	public static void insert(BannerModel bannerModel) {

		String SQL_INSERT = "INSERT INTO banner_details (name, banner_name, created_date) VALUES (?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement
					(SQL_INSERT);
			Date sqlDate = new Date(new java.util.Date().getTime());

			ps.setString(1, bannerModel.getUsername());
			ps.setString(2, bannerModel.getFoldername());
			ps.setDate(3, sqlDate);
			int i = ps.executeUpdate();
			if(i>0) {
				System.out.println("Folder Created");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	public static String select(BannerModel bannerModel, String htmlRespone) {
		
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
