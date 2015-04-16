// [KEIS01] sky.CONFIG=======================================================
if( !sky )var sky = {};

/**
 * C: sky Ajax 컨트롤 클래스
 * <pre>
 * var skyAjax = new sky.ajax("/ajaxCall.do", "POST", "json");
 * </pre>
 *
 * @param {String} url - ajax호출 URL
 * @param {String} type - 전송방식( "GET", "POST")
 * @param {String} dataType - 결과형태 ("xml", "json", "text")
 * @return {sky.ajax}
 * @classDescription Ajax를 컨트롤 하는 클래스로 Ajax요청, 파라미터 전송, 결과셋 포맷을 컨트롤 한다.
 *
 * @constructor
 *
 * */
sky.ajax = function (url, type, dataType) {

	this.url = url;
	this.type = type;
	this.dataType = dataType;
	this.param = "";
}

/**
 * F: ajax호출 URL에 원하는 파라미터 값을 setting한다.
 * @param {String} name - parameter 이름
 * @param {String} value  - parameter 값
 * @return {void}
 */
sky.ajax.prototype.addParam = function (name, value) {

	if(this.param.length == 0){
		// 20111217 modified by jjhan
		//this.param += name + "=" + value;
		this.param += name + "=" + encodeURIComponent(value);
	} else {
		// 20111217 modified by jjhan
		//this.param += "&" + name + "=" + value;
		this.param += "&" + name + "=" + encodeURIComponent(value);
	}
}


/**
 * F: Ajax를 수행한다.
 * @param {functino} callBack - Ajax결과셋을 처리할 function명
 * @return {void}
 */
sky.ajax.prototype.send = function (callBack){

	var oThis	= this;

	$.ajax({
		type: this.type,
		url: this.url,
		data: this.param,
		dataType: this.dataType,
		success: function(msg) {

			if(msg==null) return;

			try {
				return typeof callBack == 'function' ? callBack(msg, oThis) : eval(callBack+'(msg)');
			} catch (err) {

				alert("CallBack Method가  없습니다.");
			}

		}
	});
}

