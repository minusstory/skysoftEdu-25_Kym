(function ($) {
var _STATIC	= {
		OPTION_NM			: 'calendar.settings',
		DATE_NM				: 'calendar.date',
		DATE_INDEX_NM		: 'calendar.date-index',
		MOVE_MONTH_NM		: 'calendar.moveMonth',
		CALLBACK_NM			: 'calendar.callback',
		EVENT_MOVEMONTH		: 1,
		EVENT_ACTIVEDATE	: 2,
		EVENT_TODAY			: 3,
		EVENT_SELECTDATE	: 4
	},
	ns	= $.fn.nsjui,
	jui	= $.fn[ns];



// 객체정의 시작
var calendar = jui.calendar = $.fn[ns+'Calendar'] = $.extend(

// 객체의 생성자 정의
function (settings) {
	if (!this.size()) return this;

	var jui				= $.fn[ns],
		calendar		= jui.calendar,
		blankFnc		= $.noop,
		settings		= settings || {},
		daysTelmpate	= settings.daysTelmpate,
		days			= daysTelmpate ? daysTelmpate : $('.date-matrix', this),
		row				= days.children().first().eq(0),
		rowChildren		= row.children(),
		tplCols			= [];

		row		= row.clone().html('');
		rowChildren.each(function (i, n) {
			tplCols.push($.template(null, row.clone().append($(n).clone())));
		});
		days.html('').show();

	settings	= $.extend({
		'paintDate'			: {},
		'selectedDate'		: {},
		'activeDate'		: null,
		'dateData'			: {},
		'useKeboard'		: true,

		'year'				: $('.year', this),
		'month'				: $('.month', this),
		'prevYearButton'	: $('.prev-year', this),
		'nextYearButton'	: $('.next-year', this),
		'prevMonthButton'	: $('.prev-month', this),
		'nextMonthButton'	: $('.next-month', this),
		'todayButton'		: $('.today', this),

		'load'				: blankFnc,
		'repaint'			: blankFnc,
		'repaintDate'		: blankFnc,
		'select'			: blankFnc,
		'active'			: blankFnc,

		'dayClass'			: ['sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat'],
		'monthClass'		: ['prev', '', 'next'],
		'todayClass'		: 'today',
		'selectedClass'		: 'selected',
		'activeClass'		: 'active',
		'dateButtonClass'	: 'date',

		'node'				: this,
		'days'				: days,
		'row'				: row,
		'tplCols'			: tplCols
	}, settings);

	this.data(calendar.OPTION_NM, settings);

	return calendar._init.call(this);
},

// 객체의 static private member 연결
_STATIC,

// 객체의 private member 정의
{
	'_init'	: function () {
		var settings	= this.data(calendar.OPTION_NM);

		calendar._setDate.call(settings);

		this.delegate('*', 'click', settings, calendar._clickDelegater);
		settings.days.delegate('*', 'keydown', settings, calendar._keydownDelegater);
		settings.days.delegate('*', 'focus', settings, calendar._focusDelegater);
		calendar._setCallbackHandler.call(settings);

		settings.load.call(settings.node, settings);

		return this;
	},

	'_setCallbackHandler'	: function () {
		var settings	= this;

		settings.prevYearButton.data(calendar.MOVE_MONTH_NM, -12);
		settings.nextYearButton.data(calendar.MOVE_MONTH_NM, 12);
		settings.prevMonthButton.data(calendar.MOVE_MONTH_NM, -1);
		settings.nextMonthButton.data(calendar.MOVE_MONTH_NM, 1);
		settings.todayButton.data(calendar.CALLBACK_NM, calendar.today);
		settings.days.data(calendar.CALLBACK_NM, calendar.select);

		return calendar;
	},

	'_getDateNodeFromEvent'	: function (event) {
		var settings	= this,
			dateNm		= calendar.DATE_NM,
			tarNode		= $(event.target),
			parNode		= tarNode;

		while (!parNode.data(dateNm) && parNode.parent().size) {
			parNode	= parNode.parent();
			if (!parNode.size()) break;
		}

		return parNode.data(dateNm) ? parNode : null;
	},

	'_getDateNodeFromDate'	: function (date) {
		var settings		= this,
			selectedClass	= 'date-'+date.year+'-'+date.month+'-'+date.date,
			dateNode		= settings.days.find('.'+selectedClass);

		return dateNode.data(calendar.DATE_NM) ? dateNode : null;
	},

	'_requestSwitchDateMatrix'	: function (eventFlags) {
		var settings	= this,
			days		= settings.days;

		days.queue(function () {
			days.clearQueue();
			calendar._setDate.call(settings, eventFlags);
			days.dequeue();
		});

		return calendar;
	},

	'_setDate'	: function (eventFlags) {
		var settings		= this,
			paintDate		= settings.paintDate,
			activeDate		= settings.activeDate,
			oDate			= new Date(),
			yearNode		= settings.year,
			monthNode		= settings.month,
			today, dspDate, actNode;

		today	= {
			'year'	: oDate.getFullYear(),
			'month'	: oDate.getMonth()+1,
			'date'	: oDate.getDate()
		};

		if (typeof paintDate.year == 'undefined') paintDate.year = today.year;
		if (typeof paintDate.month == 'undefined') paintDate.month = today.month;
		if (typeof paintDate.date == 'undefined') paintDate.date = 1;

		calendar._correctDate(paintDate);

		if (activeDate) {
			calendar._correctDate(activeDate);

			if (!calendar._getDateNodeFromDate.call(settings, activeDate)) {
				paintDate	= settings.paintDate = $.extend({}, activeDate);
			}
		}

		paintDate.today	= today;
		dspDate	= paintDate;

		if (monthNode.val() != dspDate.month || yearNode.val() != dspDate.year || !settings.days.children().size()) {
			yearNode.val(dspDate.year);
			monthNode.val(dspDate.month);
			calendar._paintDays.call(settings, eventFlags);
		}
		else {
			calendar._selectDate.call(settings, eventFlags);
			calendar._activeDate.call(settings, eventFlags);
		}

		return calendar;
	},

	'_correctDate'	: function (date) {
		var oDate	= new Date(date.year, date.month-1, date.date);

		date.year	= oDate.getFullYear();
		date.month	= oDate.getMonth()+1;
		date.date	= oDate.getDate();

		if (isNaN(date.year) || isNaN(date.month) || date.year+(date.month*0.01-0.01) < 1900) {
			date.year	= 1900;
			date.month	= 1;
			date.date	= 1;
		}
	},

	'_paintDays'	: function (eventFlags) {
		var settings	= this,
			dateMatrix	= calendar._getDateMatrix.call(settings),
			activeDate	= settings.activeDate,
			row			= settings.row,
			frgNode		= settings.node.get(0).ownerDocument.createDocumentFragment(),
			tplCols		= settings.tplCols,
			dayClass	= settings.dayClass,
			monthClass	= settings.monthClass,
			todayClass	= settings.todayClass;

		settings.repaint.call(settings.node, settings);

		for (var i=0, dateNode, day, oYMD, rowNode, monCnt=0, style, data; oYMD=dateMatrix[i]; i++) {
			if (!(day = i%7)) rowNode = row.clone().appendTo(frgNode);

			style	= dayClass[day];
			style	+= ' date-'+oYMD.year+'-'+oYMD.month+'-'+oYMD.date;
			style	+= ' date-idx-'+i;
			style	+= ' '+monthClass[oYMD.date === 1 ? ++monCnt : monCnt];
			style	+= ' '+(oYMD.today ? todayClass : '');

			data		= calendar._getDateData.call(settings, oYMD);
			data.style	= (style+' '+data.style).replace(/ +/g, ' ').replace(/ $/, '');

			if (activeDate && activeDate.index == i) settings.activeDate = $.extend({}, oYMD);

			settings.repaintDate.call(settings.node, settings, data);

			var a = $.tmpl(tplCols[day], data)
				.appendTo(rowNode)
				.data(calendar.DATE_NM, oYMD)
				.data(calendar.DATE_INDEX_NM, i);
		}

		settings.days.html('').append(frgNode);
		calendar._selectDate.call(settings, eventFlags);
		calendar._activeDate.call(settings, eventFlags);

		return calendar;
	},

	'_getDateData'	: function (date) {
		var settings	= this,
			transLunar	= jui.transLunar,
			dateData	= settings.dateData,
			symd		= [date.year, date.month, date.date],
			lymd		= transLunar.toLunar.apply(transLunar, symd),
			isSolarLast	= (new Date(symd[0], symd[1]-1, symd[2]+1)).getMonth() != symd[1]-1,
			isLunarLast	= transLunar.lunarMonthTable[lymd[0]-1899][lymd[1]-1]+28 == lymd[2],
			ptnSolar	= 'solar\\.(\\*|'+symd[0]+')\\.(\\*|'+symd[1]+')\\.(\\*|'+symd[2]+(isSolarLast?'|last':'')+')',
			ptnLunar	= 'lunar\\.(\\*|'+lymd[0]+')\\.(\\*|'+lymd[1]+')\\.(\\*|'+lymd[2]+(isLunarLast?'|last':'')+')',
			ptn			= new RegExp('^('+ptnSolar+'|'+ptnLunar+')$'),
			dataList	= [],
			data		= {
				'date'	: symd[2],
				'symd'	: symd.join('.'),
				'lymd'	: lymd.slice(0,3).join('.'),
				'lmd'	: lymd.slice(1,3).join('.')
			},
			item;

		$.each(dateData, function (k, v) {
			if (ptn.test(k)) dataList = dataList.concat(v);
		});

		while (item = dataList.shift()) {
			$.each(item, function (k, v) {
				data[k]	= data[k] ? (data[k]+','+v) : v;
			});
		}

		data.style	= data.style ? $.unique(data.style.split(/,/)).join(' ') : '';

		return data;
	},

	'_getChangedDateNode'	: function (date, className) {
		if (!date || !date.year || !date.month || !date.date) return null;

		var settings	= this,
			dateNode	= calendar._getDateNodeFromDate.call(settings, date);

		if (!dateNode) return null;

		settings.days.find('.'+className).removeClass(className);
		
		return dateNode.addClass(className);
	},

	'_selectDate'	: function (eventFlags) {
		var settings	= this,
			selectedNode= calendar._getChangedDateNode.call(settings, settings.selectedDate, settings.selectedClass);

		if (eventFlags & calendar.EVENT_SELECTDATE && selectedNode) {
			settings.select.call(settings.node, settings, selectedNode);
		}

		return calendar;
	},

	'_activeDate'	: function (eventFlags) {
		var settings	= this,
			activeNode	= calendar._getChangedDateNode.call(settings, settings.activeDate, settings.activeClass);

		if (activeNode && eventFlags & calendar.EVENT_ACTIVEDATE) {
			jui.getFocusNodes(activeNode).get(0).focus();
			settings.active.call(settings.node, settings, activeNode);
		}

		delete settings.activeDate;

		return calendar;
	},

	'_getDateMatrix'	: function () {
		var settings	= this,
			paintDate	= settings.paintDate,
			today		= paintDate.today,
			oDate		= new Date(paintDate.year, paintDate.month-1, 1),
			matrix		= [];

		matrix.year		= oDate.getFullYear();
		matrix.month	= oDate.getMonth()+1;
		matrix.date		= oDate.getDate();

		oDate.setDate(0);
		oDate.setDate(oDate.getDate()-oDate.getDay());

		matrix.prevMonth = oDate.getMonth()+1;

		for (var i=6; i--;) {

			for (var j=7, y, m, d, isToday; j--;) {
				y		= oDate.getFullYear();
				m		= oDate.getMonth()+1;
				d		= oDate.getDate();
				isToday	= today.year == y && today.month == m && today.date == d;

				matrix.push({
					'year'	: y,
					'month'	: m,
					'date'	: d,
					'today'	: isToday
				});

				oDate.setDate(++d);
			}
		}

		matrix.nextMonth = oDate.getMonth()+1;

		return matrix;
	},

	'_clickDelegater'	: function (event) {
		var settings	= jui.getOptionFromEvent.call(this, event, calendar.OPTION_NM),
			tarNode		= $(event.target),
			callback	= tarNode.data(calendar.CALLBACK_NM),
			moveMonth	= tarNode.data(calendar.MOVE_MONTH_NM);

		if (!tarNode.hasClass(settings.dateButtonClass) && !$.isFunction(callback) && !moveMonth) return calendar;

		jui.stopPropagation(event);

		if (callback) callback.call(event.target, event);
		else if (moveMonth) calendar.moveMonth.call(tarNode, event);
		else calendar.selectDate.call(tarNode, event);

		jui.preventDefault(event);

		return calendar;
	},

	'_keydownDelegater'	: function (event) {
		var settings	= jui.getOptionFromEvent.call(this, event, calendar.OPTION_NM);

		if (event.ctrlKey || event.shiftKey || event.altKey) return calendar;

		jui.stopPropagation(event);

		calendar.activeDate.call(event.target, event);

		return calendar;
	},

	'_focusDelegater'	: function (event) {
		var settings	= jui.getOptionFromEvent.call(this, event, calendar.OPTION_NM),
			activeClass	= settings.activeClass,
			actNode		= calendar._getDateNodeFromEvent.call(settings, event);

		if (!actNode) return calendar;

		jui.stopPropagation(event);

		settings.days.find('.'+activeClass).removeClass(activeClass);
		actNode.addClass(activeClass);
		delete settings.activeDate;

		return calendar;
	}
},

// 객체의 public member 정의
{
	'moveMonth'	: function (event) {
		var settings	= jui.getOptionFromEvent.call(this, event, calendar.OPTION_NM),
			moveMonth	= moveMonth || $(event.target).data(calendar.MOVE_MONTH_NM);

		settings.paintDate.month	+= moveMonth;

		calendar._requestSwitchDateMatrix.call(settings, calendar.EVENT_MOVEMONTH);
	},

	'activeDate'	: function (event) {
		var settings	= jui.getOptionFromEvent.call(this, event, calendar.OPTION_NM),
			dateNode	= calendar._getDateNodeFromEvent.call(settings, event);

		if (!dateNode || !settings.useKeboard) return;

		var keyCode		= jui.KEYCODE,
			activeDate	= settings.activeDate = $.extend({}, dateNode.data(calendar.DATE_NM)),
			dateIndex	= dateNode.data(calendar.DATE_INDEX_NM);

		switch (event.keyCode)
		{
		default : return; break;
		case keyCode.PAGEUP :
			activeDate.month	= settings.paintDate.month-1;
			activeDate.date		= 1;
			activeDate.index	= dateIndex;
		break;
		case keyCode.PAGEDOWN :
			activeDate.month	= settings.paintDate.month+2;
			activeDate.date		= 0;
			activeDate.index	= dateIndex;
		break;
		case keyCode.END :
			activeDate.date	+= 6-dateIndex%7;
		break;
		case keyCode.HOME :
			activeDate.date	-= dateIndex%7;
		break;
		case keyCode.LEFT :
			activeDate.date	-= 1;
		break;
		case keyCode.UP :
			activeDate.date	-= 7;
		break;
		case keyCode.RIGHT :
			activeDate.date	+= 1;
		break;
		case keyCode.DOWN :
			activeDate.date	+= 7;
		break;
		}

		jui.preventDefault(event);

		calendar._requestSwitchDateMatrix.call(settings, calendar.EVENT_ACTIVEDATE);
	},

	'today'	: function (event) {
		var settings		= jui.getOptionFromEvent.call(this, event, calendar.OPTION_NM),
			oDate			= new Date(),
			selectedDate	= {
				'year'	: oDate.getFullYear(),
				'month'	: oDate.getMonth()+1,
				'date'	: oDate.getDate()
			};

		settings.selectedDate	= $.extend({}, selectedDate)
		settings.paintDate		= $.extend({}, selectedDate);
		settings.activeDate		= $.extend({}, selectedDate);

		calendar._requestSwitchDateMatrix.call(settings, calendar.EVENT_TODAY);
	},

	'selectDate'	: function (event) {
		var settings	= jui.getOptionFromEvent.call(this, event, calendar.OPTION_NM),
			dateNode	= calendar._getDateNodeFromEvent.call(settings, event);

		if (!dateNode) return;

		var selectedDate	= dateNode.data(calendar.DATE_NM);

		settings.selectedDate	= $.extend({}, selectedDate)
		settings.paintDate		= $.extend({}, selectedDate);
		settings.activeDate		= $.extend({}, selectedDate);

		calendar._requestSwitchDateMatrix.call(settings, calendar.EVENT_SELECTDATE);
	}
}

);
// 객체정의 끝



})(jQuery);