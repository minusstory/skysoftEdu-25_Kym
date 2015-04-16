/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.framework.crypto.exception;

/**
 * 기능 지원 안되는 메소드 호출시 발생
 * @author 개발프레임웍크 실행환경 개발팀 김종호
 * @since 2009. 03.10
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일      수정자              수정내용
 *  ---------   ---------   -------------------------------
 *  2009.03.10    김종호        최초생성
 * 
 * </pre>
 */
public class UnsupportedException extends Exception {
/**
  * UnsupportedException Class 생성자
  * @param msg - Exception 메시지
  * @return null;
   */
	public UnsupportedException(String msg)
	{
		super(msg);
	}
}
