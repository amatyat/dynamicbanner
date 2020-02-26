package magnon.hp.banner.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import magnon.hp.banner.util.ZipUtil;

@WebServlet("/UploadDownloadFileServlet")
public class UploadDownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServletFileUpload uploader = null;
	@Override
	public void init() throws ServletException{
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
		fileFactory.setRepository(filesDir);
		this.uploader = new ServletFileUpload(fileFactory);
	}

	private static final String SAVE_DIR = "outputBanner";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		String folderName = request.getParameter("folder");
		String user = request.getParameter("user");
		
		File serverDirectory = new File(request.getServletContext().getRealPath("/"));
		File parentDirectory = serverDirectory.getParentFile().getParentFile();

		File rootDirectory = new File(parentDirectory.getAbsolutePath()+File.separator+"webapps/ROOT");
		
		// gets absolute path of the web application
		String folder =  rootDirectory.getAbsolutePath()+File.separator+SAVE_DIR+File.separator+user+File.separator+folderName;

		if(fileName == null || fileName.equals("")){
			throw new ServletException("File Name can't be null or empty");
		}
		
		File zipfile = new File(folder+File.separator+folderName+".zip");
		File dir = new File(folder);
		
		if(!zipfile.exists()){
			  new ZipUtil().zipDirectory(dir, zipfile.getAbsolutePath());
		}
		System.out.println("File location on server::"+zipfile.getAbsolutePath());
		ServletContext ctx = getServletContext();
		InputStream fis = new FileInputStream(zipfile);
		String mimeType = ctx.getMimeType(zipfile.getAbsolutePath());
		response.setContentType(mimeType != null? mimeType:"application/octet-stream");
		response.setContentLength((int) zipfile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + folderName + ".zip\"");
		
		ServletOutputStream os       = response.getOutputStream();
		byte[] bufferData = new byte[1024];
		int read=0;
		while((read = fis.read(bufferData))!= -1){
			os.write(bufferData, 0, read);
		}
		os.flush();
		os.close();
		fis.close();
		System.out.println("File downloaded at client successfully");
	}
	
	//TBD Change/Remove
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!ServletFileUpload.isMultipartContent(request)){
			throw new ServletException("Content type is not multipart/form-data");
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");
		try {
			List<FileItem> fileItemsList = uploader.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			while(fileItemsIterator.hasNext()){
				FileItem fileItem = fileItemsIterator.next();
				System.out.println("FieldName="+fileItem.getFieldName());
				System.out.println("FileName="+fileItem.getName());
				System.out.println("ContentType="+fileItem.getContentType());
				System.out.println("Size in bytes="+fileItem.getSize());
				System.out.println("Absolute Path="+request.getServletContext().getAttribute("FILES_DIR"));
				//TBD change
				File file = new File("F:\\Anju"+File.separator+fileItem.getName());
				System.out.println("Absolute Path at server="+file.getAbsolutePath());
				fileItem.write(file);
				out.write("File "+fileItem.getName()+ " uploaded successfully.");
				out.write("<br>");
				out.write("<a href=\"UploadDownloadFileServlet?fileName="+fileItem.getName()+"\">Download "+fileItem.getName()+"</a>");
			}
		} catch (FileUploadException e) {
			out.write("Exception in uploading file.");
		} catch (Exception e) {
			out.write("Exception in uploading file.");
		}
		out.write("</body></html>");
	}

}
