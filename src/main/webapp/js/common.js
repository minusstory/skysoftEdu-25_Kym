/*날짜선택 달력의 사용자휴일 지정*/
var holiday	= {
	'solar'	: {
		'1.1'	: '설날(신정)',
		'3.1'	: '3.1절',
		'5.5'	: '어린이날',
		'6.6'	: '현충일',
		'8.15'	: '광복절',
		'10.3'	: '개천절',
		'12.25'	: '크리스마스'
	},
	'lunar'	: {
		'12.last'	: '설날 연휴(구정)',
		'1.1'		: '설날(구정)',
		'1.2'		: '설날 연휴(구정)',
		'4.8'		: '부처님 오신날',
		'8.14'		: '추석 연휴',
		'8.15'		: '추석',
		'8.16'		: '추석 연휴'
	}
};

/*날짜선택 달력*/
(window.dynLatteUI = function () {
	latte.ui.addCalendarManager({
		calNode		: '.calendar',
		tplURL		: '/js/latte.ui.form.Calendar/ui.html',
		fmtDate		: '#{YYYY}/#{MM}/#{DD}',
		holidayMap	: holiday
	});
})();

/*휴가신청서전용 달력*/
$(document).ready(function(){
	var cbfnc	= typeof cbSelectDate == 'undefined' ? new Function() : cbSelectDate;
	latte.ui.addCalendarManager({
		calNode		: '.calendar-custom',
		tplURL		: '/js/latte.ui.form.Calendar/ui.html',
		fmtDate		: '#{YYYY}/#{MM}/#{DD}',
		holidayMap	: holiday,
		callback	: {selAfter:cbfnc}
	});
});

/* 학력노드 관리*/
var eduNodeMgr	= latte.ui.addNodeManager({
	tplNode		: '.edu-tpl',
	wrpNode		: '#content',
	itmClassNm	: 'edu-itm',
	absNode		: '#content',
	msgUnder	: '학력정보는 한개이상 필요합니다.',
	staSeq		: 2,
	limUnder	: 1,
	callback	: {'addAfter':dynLatteUI}
});

/* 경력노드 관리*/
var carNodeMgr	= latte.ui.addNodeManager({
	tplNode		: '.car-tpl',
	wrpNode		: '#content',
	itmClassNm	: 'car-itm',
	absNode		: '#content',
	msgUnder	: '경력정보는 한개이상 필요합니다.',
	staSeq		: 2,
	limUnder	: 1,
	callback	: {'addAfter':dynLatteUI}
});

/* 자격노드 관리*/
var quaNodeMgr	= latte.ui.addNodeManager({
	tplNode		: '.qua-tpl',
	wrpNode		: '#content',
	itmClassNm	: 'qua-itm',
	absNode		: '#content',
	msgUnder	: '자격정보는 한개이상 필요합니다.',
	staSeq		: 2,
	limUnder	: 1,
	callback	: {'addAfter':dynLatteUI}
});

/*자산대여 노드 관리*/
var rentNodeMgr = latte.ui.addNodeManager({
	tplNode		: '.rent-tpl',
	wrpNode		: '#content',
	itmClassNm	: 'rent-itm',
	absNode		: '#content',
	msgUnder	: '대여할 자산은 한개이상이 존재하야여 합니다.',
	staSeq		: 2,
	limUnder	: 1
});


/*기본조직코드 선택시*/
var nextNode;
function orgDepth(obj , nextDepth){
	nextNode = nextDepth;
	var nextDepth = document.getElementById(nextDepth);
	if(obj.value == "") {
		clearSelectBox(nextDepth);

		nextDepth.options.add(new Option("전체", ""));
	}
	else{
		var skyAjax = new sky.ajax("/common/ajax/getChildOrgInfo.sky", "POST", "json");
		skyAjax.addParam("orgCd",obj.value);
		skyAjax.send(doOrgCodeAjax);
	}
}

/*기본조직코드 선택후 callback function */
function doOrgCodeAjax(res){
	var form = document.getElementById("mainFrm");
	var srchOrgCd2 = document.getElementById(nextNode);
	try {
		if("" != res){
			clearSelectBox(srchOrgCd2);
			srchOrgCd2.options.add(new Option("전체", ""));
			for(var i = 0 ; i < res.length ; i++){
				srchOrgCd2.options.add(new Option(res[i].NAME, res[i].CODE));
			}
		} else {
			clearSelectBox(srchOrgCd2);
			srchOrgCd2.options.add(new Option("전체", ""));
		}

	} catch (e) {
		alert(e + '데이터 형식이 잘못되었습니다. 관리자에게 문의 바랍니다.');
	}
	nextNode="";
}

/*SELECTBOX CLEAR*/
function clearSelectBox(obj){
	var length = obj.length;
	for(var i = 0 ; i < length ; i ++){
		obj.options.remove(0);
	}
}

/*사원사진보기 팝업*/
function openEmpPic(src) {
	latte.ui.open('/popup/pop_empPic.jsp?'+src, 'empPic', [163, 253], {posRel:[0, -300], useMove:true, sizMax : [600,600]});
}

/*우편번호찾기 팝업*/
function goFindPostNo(callBackFuncNm){
	var goUrl = "/common/popup/postNoFind.sky?callBackFuncNm="+callBackFuncNm+"&srchPostalTpCd=000001";
	latte.ui.open(goUrl, 'findPost', [470, 292], {posRel:[0, 0], useMove:true, sizMax : [600,600]});
}

/* QR코드 이미지 팝업 */
function goQrCodeImage(empNo,empNm){
	var goUrl = "/common/popup/qrcodeImagePop.sky?empNo="+empNo+"&empNm="+empNm;
	latte.ui.open(goUrl, 'QRCODE', [120, 118], {posRel:[0, 0], useMove:true, sizMax : [600,600]});
}

/* 사원증 이미지 팝업 */
function goEmpCardImage(empNo){
	var goUrl = "/common/popup/empCardImagePop.sky?empNo="+empNo;
	latte.ui.open(goUrl, 'EmpCard', [605, 510], {posRel:[-300, -700], useMove:true, sizMax : [800,600]});
}

/* 증명사진보기 팝업*/
function goPhoto(empNo){
	var goUrl = "/common/popup/empInfoPhoto.sky?empNo="+empNo;
	latte.ui.open(goUrl, 'PHOTO', [150, 225], {posRel:[0, 0], useMove:true, sizMax : [600,600]});
}

/* 결재라인팝업*/
function goSmtLinePop(callBackFuncNm, drftTpCd, revEmpNo ,smtEmpNo , refEmpNo){
	var paramRevEmpNo = "";	//검토자 parameter
	var paramSmtEmpNo = "";	//결재자 parameter
	var paramRefEmpNo = "";	//참조자 parameter

	//검토자 parameter setting
	l = revEmpNo.length;
	if(l != undefined){
		for(var i = 0;i < l;i++){
			paramRevEmpNo += "&revEmpNoArr=" +revEmpNo[i].value;
		}
	}else paramRevEmpNo += "&revEmpNoArr=" +revEmpNo.value;

	//결재자 parameter setting
	l = smtEmpNo.length;
	if(l != undefined){
		for(var i = 0;i < l;i++){
			paramSmtEmpNo += "&smtEmpNoArr=" +smtEmpNo[i].value;
		}
	}else paramSmtEmpNo += "&smtEmpNoArr=" +smtEmpNo.value;

	//참조자 parameter setting
	if(refEmpNo != undefined){
		l = refEmpNo.length;
		if(l != undefined){
			for(var i = 0;i < l;i++){
				paramRefEmpNo += "&refEmpNoArr=" +refEmpNo[i].value;
			}
		}else paramRefEmpNo += "&refEmpNoArr=" +refEmpNo.value;
	}

	//URL setting
	var param = "?callBackFuncNm="+callBackFuncNm+"&drftTpCd="+drftTpCd + paramRevEmpNo + paramSmtEmpNo + paramRefEmpNo;
	var goUrl = "/common/popup/drftSmtLinePop.sky"+param;

	latte.ui.open(goUrl, 'smtLine', [827, 485], {posRel:[-450, -150], useMove:true, sizMax : [1000,900]});
}
