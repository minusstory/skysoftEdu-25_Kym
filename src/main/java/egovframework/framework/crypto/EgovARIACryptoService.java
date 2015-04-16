package egovframework.framework.crypto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

public interface EgovARIACryptoService {

    /**
     * 파일처리시 사용되는 blockSize 지정.
     * 
     * @param blockSize
     */
    public void setBlockSize(int blockSize);
    
    /**
     * 암호화 처리.
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public byte[] encrypt(byte[] data, String password);
    
    /**
     * BigDecimal 암호화 처리.
     * 
     * @param number
     * @return
     * @throws Exception
     */
    public BigDecimal encrypt(BigDecimal number, String password);
    
    /**
     * 파일 암호화 처리.
     * 
     * @param srcFile
     * @param trgtFile
     * @param password
     * @throws Exception
     */
    public void encrypt(File srcFile, String password, File trgtFile) throws FileNotFoundException, IOException;
    
    /**
     * 복호화 처리.
     * 
     * @param encryptedData
     * @param password
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] encryptedData, String password);
    
    /**
     * BigDecimal 복호화 처리.
     * 
     * @param encryptedNumber
     * @param password
     * @return
     * @throws Exception
     */
    public BigDecimal decrypt(BigDecimal encryptedNumber, String password);
    
    /**
     * 파일 복호화 처리.
     * 
     * @param encryptedFile
     * @param password
     * @param trgtFile
     * @throws Exception
     */
    public void decrypt(File encryptedFile, String password, File trgtFile) throws FileNotFoundException, IOException;
    
	/**
	 * String 을 암호화 하여, String 으로 리턴
	 * key는 시스템설정의 값을 읽어서 사용
	 * 
	 * @param data
	 * @return
	 */
	public String encrypt(String data);
	
	/**
	 * String 을 암호화 하여, String 으로 리턴
	 * 
	 * @param data 암호화 대상 문자열
	 * @param password 암호화키
	 * @return
	 */
	public String encrypt(String data, String password);
	
	/**
	 * String 을 암호화 하여, String 으로 리턴
	 * URL로 사용하기 위해 URL Encoding 처리
	 * key는 시스템설정의 값을 읽어서 사용
	 * 
	 * @param data
	 * @return
	 */
	public String encryptURL(String data) throws UnsupportedEncodingException;
	
	/**
	 * String 을 복호화 하여, String 으로 리턴
	 * key는 시스템설정의 값을 읽어서 사용
	 * 
	 * @param encryptedData
	 * @return
	 */
	public String decrypt(String encryptedData);
	
	/**
	 * String 을 복호화 하여, String 으로 리턴
	 * 
	 * @param encryptedData
	 * @param password 암호화키
	 * @return
	 */
	public String decrypt(String encryptedData, String password);
	
    /**
     * 파일 암호화 처리.
     * key는 시스템설정의 값을 읽어서 사용
     * 
     * @param srcFile
     * @param trgtFile
     * @throws Exception
     */
    public void encrypt(File srcFile, File trgtFile) throws FileNotFoundException, IOException;	
    
    /**
     * 파일 복호화 처리.
     * key는 시스템설정의 값을 읽어서 사용
     * 
     * @param encryptedFile
     * @param trgtFile
     * @throws Exception
     */
    public void decrypt(File encryptedFile, File trgtFile) throws FileNotFoundException, IOException;
}
