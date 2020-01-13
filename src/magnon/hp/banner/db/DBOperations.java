package magnon.hp.banner.db;

import magnon.hp.banner.model.BannerModel;

public interface DBOperations {
	
	public void insert(BannerModel bannerModel);
	
	public String select(BannerModel bannerModel, String htmlRespone);

}
