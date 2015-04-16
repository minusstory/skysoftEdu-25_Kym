/*  -----------------------------------------------------------------------
 * 		RD Report.js
 *  -----------------------------------------------------------------------
 *	Description : RD Reprot Designer를 출력하기위한 Javascript Object (Sky Tag와 연동  가능함
 *  @author 조상철
 *  @Date 2013. 3. 11
 *
 */

if( !sky ) var sky = {};

sky.report = {
	VIEWER_URL : 		"/framework/report/rdview.sky",
	FILE_PATH : 		"filePath",
	PARAM_TYPE : 		"paramType",
	ENGINE : 			"engine",
	CRYPTO : 			"crypto",
	REPORT_WIDTH : 		"reportWidth",
	REPORT_HEIGHT : 	"reportHeight"
};

sky.report.popup = {
	PRINT_DEFAULT_WIDTH : 			900,
	PRINT_DEFAULT_HEIGHT : 			650,
	DOWN_DEFAULT_WIDTH : 			100,
	DOWN_DEFAULT_HEIGHT : 			100
}

var SkyRDReport = function(name, config, param){

	this.name = name;
	this.config = config;
	this.param = param;

	if (typeof window.report == 'undefined') window.report = {};
	window.report[name]	= this;

};


SkyRDReport.prototype = {
	onLoad : function () {
		var reportForm = this.createReportForm();
		var reportPopup = this.createReportPopup();

		reportForm.setAttribute("target", this.name + "ReportPopup");
		reportForm.submit();
	},


	getParamData : function (){

		var paramType = this.getConfigValue(sky.report.PARAM_TYPE);
		var paramString = "/" + paramType;
		//파라 미터 맵핑
		for(var paramKey in this.param){
			if(paramType == "rp") paramString += " [" + this.getParamValue(paramKey) + "]";
			else if(paramType == "rv") paramString += " " + paramKey + "[" + this.getParamValue(paramKey) + "]";
		}

		return paramString;
	},


	getConfigValue : function(key){
		return eval("this.config." + key);
	},

	createReportPopup : function(){

		var reportWidth;
		var reportHeight;

		var engine = this.getConfigValue(sky.report.ENGINE);
		//팝업 높이 설정
		if(engine == 'print'){
			reportWidth = this.getConfigValue(sky.report.REPORT_WIDTH) ? this.getConfigValue(sky.report.REPORT_WIDTH) : sky.report.popup.PRINT_DEFAULT_WIDTH;
			reportHeight = this.getConfigValue(sky.report.REPORT_HEIGHT) ? this.getConfigValue(sky.report.REPORT_HEIGHT) : sky.report.popup.PRINT_DEFAULT_HEIGHT;
		}
		else if(engine == 'down'){
			reportWidth = this.getConfigValue(sky.report.REPORT_WIDTH) ? this.getConfigValue(sky.report.REPORT_WIDTH) : sky.report.popup.DOWN_DEFAULT_WIDTH;
			reportHeight = this.getConfigValue(sky.report.REPORT_HEIGHT) ? this.getConfigValue(sky.report.REPORT_HEIGHT) : sky.report.popup.DOWN_DEFAULT_HEIGHT;
		}

		//팝업 옵션
		var feature = "width=" + reportWidth + ",height=" + reportHeight + ",resizable=yes,status=no,menubar=no,toolbar=no,z-lock=yes,alwaysRaised=yes";
		var reportPopup = window.open('', this.name + "ReportPopup", feature);

		return reportPopup;
	},


	createReportForm : function(){
		var reportForm = eval("document." + this.name + "ReportForm");

		if(!reportForm) reportForm = document.createElement("form");

		reportForm.setAttribute("name", this.name + "ReportForm");
		reportForm.setAttribute("method", "post");
		reportForm.setAttribute("action", sky.report.VIEWER_URL);
		document.body.appendChild(reportForm);

		for(var conf in this.config){
			this.createHidenElement(reportForm, conf, this.getConfigValue(conf));
		}

		this.createHidenElement(reportForm, "param", encodeURI(this.getParamData()));

		return reportForm;
	},


	createHidenElement: function (form, elementName, elementValue){

		var element = document.createElement("input");
		element.setAttribute("name", elementName);
		element.setAttribute("value", elementValue);
		form.appendChild(element);
	},


	setParam : function(param){
		this.param = param;
	},

	getParam : function(){
		return this.param;
	},

	getParamValue : function(key){
		return this.param[key];
	},

	setParamAdd : function(key, value){
		if(this.param[key]) return;
		this.param[key] = value;
	},

	setParamDel : function(key){
		if(!this.param[key]) return;
		delete this.param[key];
	},

	hasParamKey : function(key){
		this.param[key] ? true : false;
	}
}



