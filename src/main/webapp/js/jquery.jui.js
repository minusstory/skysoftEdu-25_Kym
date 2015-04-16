// jsdoc for jquery >> http://jtemplates.tpython.com/doc/symbols/jQuery.html
// front developer >> https://github.com/Songhun/Front-end-Developer-Interview-Questions/blob/master/Korean/README_KR.md

(function ($) {

// JUI의 namespace를 지정
var _STATIC	= {
		'version'		: '0.1.0',
		'slice'			: Array.prototype.slice,
		'CONST_OBJ'		: {}.construct,
		'USER_AGENT'	: navigator.userAgent.toLowerCase(),
		'KEYCODE'	: {
			'F1'		: 112,
			'F2'		: 113,
			'F3'		: 114,
			'F4'		: 115,
			'F5'		: 116,
			'F6'		: 117,
			'F7'		: 118,
			'F8'		: 119,
			'F9'		: 120,
			'F10'		: 121,
			'F11'		: 122,
			'F12'		: 123,
			'BACK'		: 8,
			'TAB'		: 9,
			'ENTER'		: 13,
			'ESC'		: 27,
			'PAGEUP'	: 33,
			'PAGEDOWN'	: 34,
			'END'		: 35,
			'HOME'		: 36,
			'LEFT'		: 37,
			'UP'		: 38,
			'RIGHT'		: 39,
			'DOWN'		: 40,
			'INSERT'	: 45,
			'DELETE'	: 46,
			'['			: 219,
			']'			: 221
		}
	},
	ns	= $.fn.nsjui = 'jui';



// 객체정의 시작
var jui	= $.fn[ns] = $.extend(

// 객체의 생성자 정의
function (method) {
	var	uiItem	= jui[method];

	if (uiItem) {
		return uiItem.apply(this, jui.slice.call(arguments, 1));
	}
	else if (typeof method === 'object' || !method) {
		return methods.init.apply(this, arguments);
	}
	else {
		$.error('Method "'+method+'" does not exist on jQuery.'+ns);
	}
},

// 객체의 static private member 연결
_STATIC,

// 객체의 private member 정의
{
	'init'	: function (settings) {
		return this;
	},

	'isObject'	: function (obj) {
		if (!obj) return false;
		return obj.construct === jui.CONST_OBJ;
	},

	'isWindowLoaded'	: function (win) {
		return (win||this).document.readyState === 'complete';
	},

	'getOptionFromEvent'	: function (event, dataKey) {
		var settings;

		if (event) {
			settings	= event.data ? event.data : $(event.target).data(dataKey);
		}
		else {
			settings	= this;
		}

		return settings.node ? settings : null;
	},

	'getFocusNodes'	: function (node) {
		var node	= $(node || this);
		return node
			.find('a, button:not(:disabled,[readonly]), input:not(:disabled,:hidden,[readonly]), select:not(:disabled,[readonly]), textarea:not(:disabled,[readonly])')
			.filter(jui.filterDisplayNode);
	},

	'filterDisplayNode'	: function () {
		var _this	= $(this);
		if (_this.css('display') == 'none' || _this.css('visibility') == 'hidden') {
			return null;
		}
		return this;
	},

	'getWindow' : function (node) {
		var node	= node || this;
		var doc	= node.context;

		if (doc) return arguments.callee.call(doc);
		if (doc = node.ownerDocument) return doc.parentWindow || doc.defaultView;
		if (doc = node.document && doc.body) return node;

		return node.parentWindow || node.defaultView;
	},

	'stopPropagation' : function (event) {
		if (event && event.stopPropagation) event.stopPropagation();
	},

	'preventDefault' : function (event) {
		if (event && event.preventDefault) event.preventDefault();
	},

	// 임계치 사이값을 가져온다.
	'getBetween'	: function (val, min, max) {
		if (val < min) return min;
		if (val > max) return max;
		return val;
	},

	// 양력>음력 변환
	'transLunar' : {
		'lunarMonthTable'	: [
			[2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2],
			[1, 2, 1, 1, 2, 1, 2, 5, 2, 2, 1, 2],
			[1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1],   /* 1901 */
			[2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2],
			[1, 2, 1, 2, 3, 2, 1, 1, 2, 2, 1, 2],
			[2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1],
			[2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2],
			[1, 2, 2, 4, 1, 2, 1, 2, 1, 2, 1, 2],
			[1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1],
			[2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2],
			[1, 5, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2],
			[1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1],
			[2, 1, 2, 1, 1, 5, 1, 2, 2, 1, 2, 2],   /* 1911 */
			[2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2],
			[2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2],
			[2, 2, 1, 2, 5, 1, 2, 1, 2, 1, 1, 2],
			[2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2],
			[1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1],
			[2, 3, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1],
			[2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2],
			[1, 2, 1, 1, 2, 1, 5, 2, 2, 1, 2, 2],
			[1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2],
			[2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2],   /* 1921 */
			[2, 1, 2, 2, 3, 2, 1, 1, 2, 1, 2, 2],
			[1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2],
			[2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1],
			[2, 1, 2, 5, 2, 1, 2, 2, 1, 2, 1, 2],
			[1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1],
			[2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2],
			[1, 5, 1, 2, 1, 1, 2, 2, 1, 2, 2, 2],
			[1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2],
			[1, 2, 2, 1, 1, 5, 1, 2, 1, 2, 2, 1],
			[2, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1],   /* 1931 */
			[2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2],
			[1, 2, 2, 1, 6, 1, 2, 1, 2, 1, 1, 2],
			[1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2],
			[1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1],
			[2, 1, 4, 1, 2, 1, 2, 1, 2, 2, 2, 1],
			[2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1],
			[2, 2, 1, 1, 2, 1, 4, 1, 2, 2, 1, 2],
			[2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2],
			[2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1],
			[2, 2, 1, 2, 2, 4, 1, 1, 2, 1, 2, 1],   /* 1941 */
			[2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 2],
			[1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2],
			[1, 1, 2, 4, 1, 2, 1, 2, 2, 1, 2, 2],
			[1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2],
			[2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2],
			[2, 5, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2],
			[2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2],
			[2, 2, 1, 2, 1, 2, 3, 2, 1, 2, 1, 2],
			[2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1],
			[2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2],   /* 1951 */
			[1, 2, 1, 2, 4, 2, 1, 2, 1, 2, 1, 2],
			[1, 2, 1, 1, 2, 2, 1, 2, 2, 1, 2, 2],
			[1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2],
			[2, 1, 4, 1, 1, 2, 1, 2, 1, 2, 2, 2],
			[1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2],
			[2, 1, 2, 1, 2, 1, 1, 5, 2, 1, 2, 2],
			[1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2],
			[1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1],
			[2, 1, 2, 1, 2, 5, 2, 1, 2, 1, 2, 1],
			[2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2],   /* 1961 */
			[1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1],
			[2, 1, 2, 3, 2, 1, 2, 1, 2, 2, 2, 1],
			[2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2],
			[1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2],
			[1, 2, 5, 2, 1, 1, 2, 1, 1, 2, 2, 1],
			[2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2],
			[1, 2, 2, 1, 2, 1, 5, 2, 1, 2, 1, 2],
			[1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1],
			[2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2],
			[1, 2, 1, 1, 5, 2, 1, 2, 2, 2, 1, 2],   /* 1971 */
			[1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1],
			[2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 1],
			[2, 2, 1, 5, 1, 2, 1, 1, 2, 2, 1, 2],
			[2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2],
			[2, 2, 1, 2, 1, 2, 1, 5, 2, 1, 1, 2],
			[2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1],
			[2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1],
			[2, 1, 1, 2, 1, 6, 1, 2, 2, 1, 2, 1],
			[2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2],
			[1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2],   /* 1981 */
			[2, 1, 2, 3, 2, 1, 1, 2, 2, 1, 2, 2],
			[2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2],
			[2, 1, 2, 2, 1, 1, 2, 1, 1, 5, 2, 2],
			[1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2],
			[1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1],
			[2, 1, 2, 2, 1, 5, 2, 2, 1, 2, 1, 2],
			[1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1],
			[2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2],
			[1, 2, 1, 1, 5, 1, 2, 1, 2, 2, 2, 2],
			[1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2],   /* 1991 */
			[1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2],
			[1, 2, 5, 2, 1, 2, 1, 1, 2, 1, 2, 1],
			[2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2],
			[1, 2, 2, 1, 2, 2, 1, 5, 2, 1, 1, 2],
			[1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2],
			[1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1],
			[2, 1, 1, 2, 3, 2, 2, 1, 2, 2, 2, 1],
			[2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1],
			[2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1],
			[2, 2, 2, 3, 2, 1, 1, 2, 1, 2, 1, 2],   /* 2001 */
			[2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1],
			[2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2],
			[1, 5, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2],
			[1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 1],
			[2, 1, 2, 1, 2, 1, 5, 2, 2, 1, 2, 2],
			[1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2],
			[2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2],
			[2, 2, 1, 1, 5, 1, 2, 1, 2, 1, 2, 2],
			[2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2],
			[2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1],   /* 2011 */
			[2, 1, 6, 2, 1, 2, 1, 1, 2, 1, 2, 1], //29
			[2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2], //30
			[1, 2, 1, 2, 1, 2, 1, 2, 5, 2, 1, 2], //30
			[1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 1], //29
			[2, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2], //30
			[2, 1, 1, 2, 3, 2, 1, 2, 1, 2, 2, 2],
			[1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2],
			[2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2],
			[2, 1, 2, 5, 2, 1, 1, 2, 1, 2, 1, 2],
			[1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1],   /* 2021 */
			[2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2],
			[1, 5, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2],
			[1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1],
			[2, 1, 2, 1, 1, 5, 2, 1, 2, 2, 2, 1],
			[2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2],
			[1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2],
			[1, 2, 2, 1, 5, 1, 2, 1, 1, 2, 2, 1],
			[2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2],
			[1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1],
			[2, 1, 5, 2, 1, 2, 2, 1, 2, 1, 2, 1],   /* 2031 */
			[2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2],
			[1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 5, 2],
			[1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1],
			[2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2],
			[2, 2, 1, 2, 1, 4, 1, 1, 2, 2, 1, 2],
			[2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2],
			[2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1],
			[2, 2, 1, 2, 5, 2, 1, 2, 1, 2, 1, 1],
			[2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1],
			[2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2],   /* 2041 */
			[1, 5, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2],
			[1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2]
		],

		'lunarCalc' : function (year, month, day, type, leapmonth) {
			var lunarMonthTable	= this.lunarMonthTable;
			var solYear, solMonth, solDay;
			var lunYear, lunMonth, lunDay;
			var lunLeapMonth, lunMonthDay;
			var i, lunIndex;
			var solMonthDay = [31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
			/* range check */
			if (year < 1900 || year > 2040)
			{
				alert('1900년부터 2040년까지만 지원합니다');
				return;
			}
			/* 속도 개선을 위해 기준 일자를 여러개로 한다 */
			if (year >= 2000)
			{
				/* 기준일자 양력 2000년 1월 1일 (음력 1999년 11월 25일) */
				solYear = 2000;
				solMonth = 1;
				solDay = 1;
				lunYear = 1999;
				lunMonth = 11;
				lunDay = 25;
				lunLeapMonth = 0;
				solMonthDay[1] = 29;    /* 2000 년 2월 28일 */
				lunMonthDay = 30;   /* 1999년 11월 */
			}
			else if (year >= 1970)
			{
				/* 기준일자 양력 1970년 1월 1일 (음력 1969년 11월 24일) */
				solYear = 1970;
				solMonth = 1;
				solDay = 1;
				lunYear = 1969;
				lunMonth = 11;
				lunDay = 24;
				lunLeapMonth = 0;
				solMonthDay[1] = 28;    /* 1970 년 2월 28일 */
				lunMonthDay = 30;   /* 1969년 11월 */
			}
			else if (year >= 1940)
			{
				/* 기준일자 양력 1940년 1월 1일 (음력 1939년 11월 22일) */
				solYear = 1940;
				solMonth = 1;
				solDay = 1;
				lunYear = 1939;
				lunMonth = 11;
				lunDay = 22;
				lunLeapMonth = 0;
				solMonthDay[1] = 29;    /* 1940 년 2월 28일 */
				lunMonthDay = 29;   /* 1939년 11월 */
			}
			else
			{
				/* 기준일자 양력 1900년 1월 1일 (음력 1899년 12월 1일) */
				solYear = 1900;
				solMonth = 1;
				solDay = 1;
				lunYear = 1899;
				lunMonth = 12;
				lunDay = 1;
				lunLeapMonth = 0;
				solMonthDay[1] = 28;    /* 1900 년 2월 28일 */
				lunMonthDay = 30;   /* 1899년 12월 */
			}
			lunIndex = lunYear - 1899;
			while (true)
			{
				if (type == 1 &&
					year == solYear &&
					month == solMonth &&
					day == solDay)
				{
					return {
						'year'		: lunYear,
						'month'		: lunMonth,
						'day'		: lunDay,
						'leapMonth'	: lunLeapMonth
					};
				}
				else if (type == 2 &&
						year == lunYear &&
						month == lunMonth &&
						day == lunDay &&
						leapmonth == lunLeapMonth)
				{
					return {
						'year'		: solYear,
						'month'		: solMonth,
						'day'		: solDay,
						'leapMonth'	: 0
					};
				}
				/* add a day of solar calendar */
				if (solMonth == 12 && solDay == 31)
				{
					solYear++;
					solMonth = 1;
					solDay = 1;
					/* set monthDay of Feb */
					if (solYear % 400 == 0)
						solMonthDay[1] = 29;
					else if (solYear % 100 == 0)
						solMonthDay[1] = 28;
					else if (solYear % 4 == 0)
						solMonthDay[1] = 29;
					else
						solMonthDay[1] = 28;
				}
				else if (solMonthDay[solMonth - 1] == solDay)
				{
					solMonth++;
					solDay = 1;
				}
				else
					solDay++;
				/* add a day of lunar calendar */
				if (lunMonth == 12 &&
					((lunarMonthTable[lunIndex][lunMonth - 1] == 1 && lunDay == 29) ||
					(lunarMonthTable[lunIndex][lunMonth - 1] == 2 && lunDay == 30)))
				{
					lunYear++;
					lunMonth = 1;
					lunDay = 1;
					if (lunYear > 2043) {
						alert("입력하신 달은 없습니다.");
						break;
					}
					lunIndex = lunYear - 1899;
					if (lunarMonthTable[lunIndex][lunMonth - 1] == 1)
						lunMonthDay = 29;
					else if (lunarMonthTable[lunIndex][lunMonth - 1] == 2)
						lunMonthDay = 30;
				}
				else if (lunDay == lunMonthDay)
				{
					if (lunarMonthTable[lunIndex][lunMonth - 1] >= 3
						&& lunLeapMonth == 0)
					{
						lunDay = 1;
						lunLeapMonth = 1;
					}
					else
					{
						lunMonth++;
						lunDay = 1;
						lunLeapMonth = 0;
					}
					if (lunarMonthTable[lunIndex][lunMonth - 1] == 1)
						lunMonthDay = 29;
					else if (lunarMonthTable[lunIndex][lunMonth - 1] == 2)
						lunMonthDay = 30;
					else if (lunarMonthTable[lunIndex][lunMonth - 1] == 3)
						lunMonthDay = 29;
					else if (lunarMonthTable[lunIndex][lunMonth - 1] == 4 &&
							lunLeapMonth == 0)
						lunMonthDay = 29;
					else if (lunarMonthTable[lunIndex][lunMonth - 1] == 4 &&
							lunLeapMonth == 1)
						lunMonthDay = 30;
					else if (lunarMonthTable[lunIndex][lunMonth - 1] == 5 &&
							lunLeapMonth == 0)
						lunMonthDay = 30;
					else if (lunarMonthTable[lunIndex][lunMonth - 1] == 5 &&
							lunLeapMonth == 1)
							lunMonthDay = 29;
					else if (lunarMonthTable[lunIndex][lunMonth - 1] == 6)
						lunMonthDay = 30;
				}
				else
					lunDay++;
			}
		},

		'toLunar' : function (startYear,startMonth,startDay) {
			if ( !startYear || startYear == 0 ||
				 !startMonth || startMonth == 0 ||
				 !startDay || startDay == 0 )
			{
				alert('날짜를 입력해주세요');
				return;
			}
			var solMonthDay = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
			if (startYear % 400 == 0 || ( startYear % 4 == 0 && startYear % 100 != 0 )) solMonthDay[1] += 1;

			if ( startMonth < 1 || startMonth > 12 ||
				 startDay < 1 || startDay > solMonthDay[startMonth-1] ) {
				if ( solMonthDay[1] == 28 && startMonth == 2 && startDay > 28 )
					alert("윤년이 아닙니다. 다시 입력해주세요");
				else
					alert("날짜 범위를 벗어났습니다. 다시 입력해주세요");
				return;
			}
			var startDate = new Date(startYear, startMonth - 1, startDay);
			/* 양력/음력 변환 */
			var date = this.lunarCalc(startYear, startMonth, startDay, 1);
			return [date.year, date.month, date.day, date.leapMonth];
		}
	}
}

);
// 객체정의 끝



})(jQuery);