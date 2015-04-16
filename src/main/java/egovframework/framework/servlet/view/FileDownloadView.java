package egovframework.framework.servlet.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * <pre>
 * LFileDownloadView
 * </pre>
 *
 * @author DevOn OSF, LG CNS,Inc., devon@lgcns.com
 * @version DevOn OSF 0.5.0
 * @since DevOn OSF 0.5.0
 */
public class FileDownloadView extends AbstractView {

	protected Log logger = LogFactory.getLog(this.getClass());

	private final String DEFAULT_ALIAS_NAME="alias";
	private final String DEFAULT_MODEL_NAME="downloadFile";
	private final String DEFAULT_ENCODING="utf-8";

	/** DownloadView 모델 명. */
	private String modelName = DEFAULT_MODEL_NAME;
	private String alias = DEFAULT_ALIAS_NAME;
	private String encoding=DEFAULT_ENCODING;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public FileDownloadView(){
		setContentType("application/octet-stream");
	}

	@Override
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Object obj = model.get(modelName);
		response.setContentType(getContentType());

		if(obj != null && obj.getClass().isArray()) {
			// TODO 한꺼번에 여러 파일을 압축해서 다운로드
//			File [] files = (File [])obj;
//			setResponseHeader(request, response, "all.zip", -1);

		}else{
			File file = (File)obj;
			setResponseHeader(request, response, model.get(alias).toString(), (int)file.length());
			OutputStream out = response.getOutputStream();

			FileInputStream fin = new FileInputStream(file);
			try {
				FileCopyUtils.copy(fin, out);

			} catch (Exception e) {
				logger.debug("파일 다운로드 취소");
			}
			finally {
				fin.close();
			}
			out.flush();
		}
	}

	/**
	 * HttpServletResponse 헤더 정보를 세팅한다.
	 * @param request 요청 객체.
	 * @param response 응답 객체.
	 * @param fileName 파일 명
	 * @param contentLength 파일 사이즈.
	 * @throws UnsupportedEncodingException 인코딩 예외 발생 처리 객체.
	 */
	private void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName, int contentLength) throws UnsupportedEncodingException {
		String sUserAgent = request.getHeader("USER-AGENT");

		if(contentLength != -1)
			response.setContentLength(contentLength);

		if (sUserAgent.indexOf("MSIE 5.5") != -1) {
            response.setHeader("Content-Disposition", "filename=\"" + encoding + "\""
                    + encodeURLEncoding(fileName) + "\";");
        } else {

            boolean isFirefox = (sUserAgent.toLowerCase().indexOf("firefox") != -1) ? true : false;

            if (isFirefox) {
                response.setHeader("Content-Disposition", "attachment; filename=" + "\"=?" + this.encoding + "?Q?"
                        + encodeQuotedPrintable(fileName) + "?=\";");
            } else {
                response.setHeader("Content-Disposition", "attachment; filename=\""
                        + encodeURLEncoding(fileName).replaceAll("\\+", " ") + "\"");
            }

        }

        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
	}

    private String encodeURLEncoding(String p_sStr) throws UnsupportedEncodingException {
        String filename = p_sStr;
        try {
            filename = java.net.URLEncoder.encode(filename, "utf-8");//this.encoding
        	filename = filename.replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
        	logger.error("Exception Occured while file name encoding : " + e);
        }
        return filename;
    }

    private String encodeQuotedPrintable(String p_sStr) throws UnsupportedEncodingException {
        String sUrlEncodingStr = URLEncoder.encode(p_sStr, this.encoding);
        sUrlEncodingStr = sUrlEncodingStr.replaceAll("\\+", "_");
        sUrlEncodingStr = sUrlEncodingStr.replaceAll("%", "=");

        return sUrlEncodingStr;
    }
}