<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="org.apache.commons.fileupload.FileItem"
import="org.apache.commons.fileupload.disk.DiskFileItemFactory"
import="org.apache.commons.fileupload.servlet.ServletFileUpload"
import="java.io.File"
import="java.io.IOException"
import="java.util.Iterator"
import="java.text.SimpleDateFormat"
import="java.util.Date"
import="java.util.UUID"
%><%
try
{
	request.setCharacterEncoding("UTF-8");
	String	path		= "richupload/uploadFile";
	String	urlbase		= "http://"+request.getServerName()+":"+request.getServerPort();
	int		maxFileSize	= 10*1024*1024;
	String	nmField		= "file";
	//String	savePath	= getServletContext().getRealPath("/")+path;

	String	savePath	="C:/upload";

	// 등록된 이미지정보를 리턴할 URL 을 설정한다.
	String	callbackURL	= request.getParameter("callbackURL");
//out.print("isFindFileItem:"+nmField+"<br/>");

	// Create a factory for disk-based file items
	DiskFileItemFactory factory = new DiskFileItemFactory();

	// Set factory constraints
	factory.setSizeThreshold(maxFileSize);
	factory.setRepository(new File(savePath));

	// Create a new file upload handler
	ServletFileUpload servletUpload = new ServletFileUpload(factory);

	// Set overall request size constraint
	servletUpload.setSizeMax(-1);
	Iterator<FileItem>	iterator	= servletUpload.parseRequest(request).iterator();
	FileItem	fileItem		= null;
	boolean		isFindFileItem	= false;

	// fileitem 선별
	while(iterator.hasNext())
	{
		fileItem	= iterator.next();
		if (fileItem.getFieldName().indexOf(nmField) > -1)
		{
			isFindFileItem	= true;
			break;
		}
	}

//out.print("isFindFileItem:"+isFindFileItem+"<br/>");
	if (!isFindFileItem) throw new IOException("'"+nmField+"'로 업로드된 파일이 없습니다.");
	else
	{
		String	uuid	= UUID.randomUUID().toString();

		// 응답할 xml 속성값 설정
		String		attFileName		= (new File(fileItem.getName())).getName();
		String[]	fileSplitedName	= attFileName.split("\\.");
		String		attFileType		= fileSplitedName[fileSplitedName.length-1].toLowerCase();
		long		attFileSize		= fileItem.getSize();
		String		saveFileName	= uuid+"."+attFileType;
		String		attFileSeq		= savePath+"/"+saveFileName;
		String		attURL			= urlbase+request.getContextPath()+"/"+path+"/"+saveFileName;

		// 파일 생성
		fileItem.write(new File(attFileSeq));

		// 결과 출력
%>
<file filename="<%=attFileName%>" filetype="<%=attFileType%>" filesize="<%=attFileSize%>" thumbnail="" fileseq="<%=saveFileName%>"><?=attURL?></file>
<%
	}
} catch (Exception e) {
	e.printStackTrace();
}
%>