package egovframework.framework.crypto.impl;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.framework.crypto.EgovARIACryptoService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * @Class EgovARIACryptoServiceImpl.java
 * @Description  ARIA 알고리즘을 사용한 대칭키 암/복호화 처리 서비스
 * @author
 * @since 2011. 11. 21.
 * @version 1.0
 */
public class EgovARIACryptoServiceImpl implements EgovARIACryptoService {

    /** EgovPropertyService */
    @Resource(name = "sysPropService")
    protected EgovPropertyService sysPropService;

    private final Base64 base64 = new Base64();

    private Logger logger = Logger.getLogger(this.getClass());	// Logger 처리

    private int blockSize = 1024;	// default

    public void setBlockSize(int blockSize) {
		if (blockSize % 16 != 0) {
		    blockSize += (16 - blockSize % 16);
		}
		this.blockSize = blockSize;
    }

    public BigDecimal encrypt(BigDecimal number, String password) {
    	throw new UnsupportedOperationException("Unsupported method.. (ARIA Cryptography service doesn't support BigDecimal en/decryption)");
    }

    public String encryptURL(String data) throws UnsupportedEncodingException
    {
    	return URLEncoder.encode( encrypt(data), "UTF-8" );
    }

    public String encrypt(String data) {
    	String password = sysPropService.getString("sys.crypto.default.password.enc");
    	return encrypt(data, password);
    }

    public String encrypt(String data, String password)
    {
    	return new String( base64.encode( encrypt( data.getBytes(), password) ) );
    }

    public byte[] encrypt(byte[] data, String password) {
	    ARIACipher cipher = new ARIACipher();
	    cipher.setPassword(password);
	    return cipher.encrypt(data);
    }

    public void encrypt(File srcFile, File trgtFile) throws FileNotFoundException, IOException {
    	String password = sysPropService.getString("sys.crypto.default.password.enc");
    	encrypt(srcFile, password, trgtFile);
    }

    public void encrypt(File srcFile, String password, File trgtFile) throws FileNotFoundException, IOException {
		FileInputStream fis = null;
		FileWriter fw = null;
		BufferedInputStream bis = null;
		BufferedWriter bw = null;

		byte[] buffer = null;

	    ARIACipher cipher = new ARIACipher();

	    cipher.setPassword(password);

	    buffer = new byte[blockSize];

	    logger.debug("blockSize = " + blockSize);

	    try {
    		fis = new FileInputStream(srcFile);
    		bis = new BufferedInputStream(fis);

    		fw = new FileWriter(trgtFile);
    		bw = new BufferedWriter(fw);

    		byte[] encrypted = null;
    		int length = 0;
    		long size = 0L;
    		while ((length = bis.read(buffer)) >= 0) {
    			if (length < blockSize) {
    				byte[] tmp = new byte[length];
    				System.arraycopy(buffer, 0, tmp, 0, length);
    				encrypted = cipher.encrypt(tmp);
    			} else {
    				encrypted = cipher.encrypt(buffer);
    			}
    			String line;
    			line = new String(base64.encode(encrypted), "US-ASCII");
    			bw.write(line);
    			bw.newLine();
    			size += length;
    		}
    		bw.flush();
    		logger.debug("processed bytes = " + size);
	    } catch (FileNotFoundException fnfe) {
	    	throw fnfe;
	    } catch (IOException ioe) {
	    	throw ioe;
	    } finally {
	    	if (fw != null) {
	    		try {
	    			fw.close();
	    		} catch (IOException ignore) {
	    			// no-op
	    		}
	    	}
	    	if (bw != null) {
	    		try {
	    			bw.close();
	    		} catch (IOException ignore) {
	    			// no-op
	    		}
	    	}
	    	if (fis != null) {
	    		try {
	    			fis.close();
	    		} catch (IOException ignore) {
	    			// no-op
	    		}
	    	}
	    	if (bis != null) {
	    		try {
	    			bis.close();
	    		} catch (IOException ignore) {
	    			// no-op
	    		}
	    	}
	    }

    }

    public BigDecimal decrypt(BigDecimal encryptedNumber, String password) {
    	throw new UnsupportedOperationException("Unsupported method.. (ARIA Cryptography service doesn't support BigDecimal en/decryption)");
    }

    public String decrypt(String encryptedData){
    	String password = sysPropService.getString("sys.crypto.default.password.enc");
    	return decrypt(encryptedData, password);
    }

    public String decrypt(String encryptedData, String password)
    {
    	return new String( decrypt( base64.decode( encryptedData.getBytes() ), password) );
    }

    public byte[] decrypt(byte[] encryptedData, String password) {
	    ARIACipher cipher = new ARIACipher();
	    cipher.setPassword(password);
	    return cipher.decrypt(encryptedData);
    }

    public void decrypt(File srcFile, File trgtFile) throws FileNotFoundException, IOException {
    	String password = sysPropService.getString("sys.crypto.default.password.enc");
    	decrypt(srcFile, password, trgtFile);
    }

    public void decrypt(File encryptedFile, String password, File trgtFile) throws FileNotFoundException, IOException {
		FileReader fr = null;
		FileOutputStream fos = null;
		BufferedReader br = null;
		BufferedOutputStream bos = null;

	    ARIACipher cipher = new ARIACipher();

	    cipher.setPassword(password);

	    try {
	    	fr = new FileReader(encryptedFile);
	    	br = new BufferedReader(fr);

			fos = new FileOutputStream(trgtFile);
			bos = new BufferedOutputStream(fos);

			byte[] encrypted = null;
			byte[] decrypted = null;
			String line = null;

			while ((line = br.readLine()) != null) {
			    encrypted = base64.decode(line.getBytes("US-ASCII"));

			    decrypted = cipher.decrypt(encrypted);

			    bos.write(decrypted);
			}
			bos.flush();
	    } catch (FileNotFoundException fnfe) {
	    	throw fnfe;
	    } catch (IOException ioe) {
	    	throw ioe;
	    } finally {
			if (fos != null) {
			    try {
			    	fos.close();
			    } catch (IOException ignore) {
			    	// no-op
			    }
			}
			if (bos != null) {
			    try {
			    	bos.close();
			    } catch (IOException ignore) {
			    	// no-op
			    }
			}
			if (fr != null) {
			    try {
			    	fr.close();
			    } catch (IOException ignore) {
			    	// no-op
			    }
			}
			if (br != null) {
			    try {
			    	br.close();
			    } catch (IOException ignore) {
			    	// no-op
			    }
			}
	    }

    }
}
