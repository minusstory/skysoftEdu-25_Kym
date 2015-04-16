/*
* ----------------------------------------------------------------------------------------------------------------------
* [v2.1 - latte v0.5] 2011/02/24 <ragnarok>
*
* 1. Context Path 를 참조하도록 수정함
*
* ----------------------------------------------------------------------------------------------------------------------
* [v2.1 - latte v2.0b7] 2011/02/23 <ragnarok>
*
* 1. Flash Player 10,1,x,x 보안 오류 해결
*   - 10.2 이상의 버전을 기본으로 사용하도록 수정함
*
* ----------------------------------------------------------------------------------------------------------------------
* [v2.0b1 - latte v2.0b7] 2011/01/07 <ragnarok>
*
* 1. 최초 베타 패키지
*
*/
if (typeof(window.richapp) == 'undefined') window.richapp = {};
if (typeof(window.richupload) == 'undefined') window.richupload = {};

richapp.RICHUPLOAD	= 'richupload';
richapp[richapp.RICHUPLOAD]		= {
	nUploads : {},
	nDownloads : {}
};

richupload.VERSION				= '2.1';
richupload.E_UPLOAD_CLASS_NM	= 'richupload';
richupload.E_DOWNLOAD_CLASS_NM	= 'richdownload';
richupload.E_FILE_ITEM_CLASS_NM	= 'richfileitem';
richupload.E_RICHFILE_CLASS_NM	= 'richfile';
richupload.E_NOSCRIPT_CLASS_NM	= 'noscript';

richupload.E_ENGINE_NM			= 'engine';
richupload.E_SWF_URL_NM			= 'swfurl';
richupload.E_SWF_VERSION_NM		= 'swfver';
richupload.E_CONF_URL_NM		= 'confurl';
richupload.E_CONF_CHARSET_NM	= 'confchar';
richupload.E_LOCALE_NM			= 'locale';
richupload.E_ADDFILE_NM			= 'addfile';
richupload.E_DELFILE_NM			= 'delfile';
richupload.E_SUBMIT_NM			= 'submit';

richupload.E_FITEM_NAME_NM		= 'filename';
richupload.E_FITEM_TYPE_NM		= 'filetype';
richupload.E_FITEM_SIZE_NM		= 'filesize';
richupload.E_FITEM_SEQ_NM		= 'fileseq';
richupload.E_FITEM_URL_NM		= 'fileurl';
richupload.E_FITEM_THUMBNAIL_NM	= 'thumbnail';


richupload.DEF_SWF_URL			= '/richupload/js/richupload2.swf';	// richfilter를 사용하는 경우에만 context path 를 제외한다. 일반적인 경우 context path 를 포함한 url을 사용한다
richupload.DEF_SWF_VERSION		= '10.2.152.26';
richupload.DEF_CONF_URL			= '/richupload/js/config.xml';		// richfilter를 사용하는 경우에만 context path 를 제외한다. 일반적인 경우 context path 를 포함한 url을 사용한다
richupload.DEF_CONF_CHARSET		= 'UTF-8';
richupload.DEF_LOCALE			= 'ko_KR';
richupload.DEF_SIZE				= {};
richupload.DEF_SIZE[richupload.E_UPLOAD_CLASS_NM]	= {
	width	: 600,
	height	: 185
};
richupload.DEF_SIZE[richupload.E_DOWNLOAD_CLASS_NM]	= {
	width	: 600,
	height	: 165
};

richupload.DEF_TIT_UPLOAD_NONE		= '파일 업로드';
richupload.DEF_TIT_DOWNLOAD_NONE	= '파일 다운로드';

if(typeof window.latte=="undefined")window.latte={};if(typeof window.richupload=="undefined")window.richupload={};latte.Class({$name:"richupload.engine.Initializer",$const:function(){},eLst:null,oSWF:null,init:function(){this.oSWF=richupload.engine.FlashLauncher.getInstance()},deploy:function(){var b=this;latte.observer.add(window,"load",function(){var a=(latte.cookie.getCookie("context.path")||"").replace(/"/g,"");richupload.DEF_SWF_URL=a+richupload.DEF_SWF_URL;richupload.DEF_CONF_URL=a+richupload.DEF_CONF_URL;b.eLst=latte.query("."+richupload.E_UPLOAD_CLASS_NM+",."+richupload.E_DOWNLOAD_CLASS_NM,document.body);b.run()})},run:function(){this.launchFlash()},createAttributeInfo:function(b){var a=latte.css.getRealSize(b);return{ver:b.getAttribute(richupload.E_SWF_VERSION_NM)||richupload.DEF_SWF_VERSION,path:b.getAttribute(richupload.E_SWF_URL_NM)||richupload.DEF_SWF_URL,locale:b.getAttribute(richupload.E_LOCALE_NM)||richupload.DEF_LOCALE,engineMode:b.className,engineNm:b.getAttribute(richupload.E_ENGINE_NM),confURL:b.getAttribute(richupload.E_CONF_URL_NM)||richupload.DEF_CONF_URL,confChar:b.getAttribute(richupload.E_CONF_CHARSET_NM)||richupload.DEF_CONF_CHARSET,width:a.width,height:a.height,id:b.getAttribute("id"),addfileNm:b.getAttribute(richupload.E_ADDFILE_NM),delfileNm:b.getAttribute(richupload.E_DELFILE_NM),submitNm:b.getAttribute(richupload.E_SUBMIT_NM)}},createSWFInfo:function(b,a){return{ver:a.ver,path:a.path,oAttrs:"",locale:encodeURIComponent(a.locale),engineID:encodeURIComponent(a.id),engineMode:encodeURIComponent(a.engineMode),engineNm:encodeURIComponent(a.engineNm),confURL:encodeURIComponent(a.confURL),confChar:encodeURIComponent(a.confChar),width:a.width,height:a.height,nFile:b.className==richupload.E_UPLOAD_CLASS_NM?'<input class="'+richupload.E_RICHFILE_CLASS_NM+'" type="file" name="'+a.id+'" id="'+a.id+'"/>':"",fileLst:b.innerHTML,nPos:b}},launchFlash:function(){var b=this,a=null;latte.iter(this.eLst,function(d){var c=b.createAttributeInfo(d);a=b.oSWF.launch(b.createSWFInfo(d,c));a._oCtrl={oAdd:new richupload.control.FileManage(a,c.addfileNm),oDel:new richupload.control.FileManage(a,c.delfileNm),nNoScript:d,submit:latte.isFNC(window[c.submitNm])?window[c.submitNm]:null};a._oAttr=c;a._isUploaded=!1;switch(c.engineMode){case richupload.E_UPLOAD_CLASS_NM:richapp[richapp.RICHUPLOAD].nUploads[c.id]=a;break;case richupload.E_DOWNLOAD_CLASS_NM:richapp[richapp.RICHUPLOAD].nDownloads[c.id]=a}})}});latte.Class.singletone(richupload.engine.Initializer);
if(typeof window.latte=="undefined")window.latte={};if(typeof window.richupload=="undefined")window.richupload={};latte.Class({$name:"richupload.engine.FlashLauncher",$const:function(){this.tpl="";latte.isMSIE?(this.tpl+='<object id="#{engineID}SWF" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=#{ver}" width="#{width}" height="#{height}" #{attrs}>',this.tpl+='<param name="movie" value="#{path}" />'):this.tpl+='<object id="#{engineID}SWF" type="application/x-shockwave-flash" title="" data="#{path}" width="#{width}" height="#{height}" #{attrs}>';this.tpl+='<param name="wmode" value="window" />';this.tpl+='<param name="flashvars" value="locale=#{locale}&engineID=#{engineID}&engineMode=#{engineMode}&engineName=#{engineNm}&configURL=#{confURL}&configCharset=#{confChar}" />';this.tpl+='<param name="allowScriptAccess" value="always" />';this.tpl+='<div class="noscript"><p>You can also use the content in  <a href="http://www.adobe.com/kr/products/flashplayer/">Flash Player(Freeware)</a></p>#{nFile}#{fileList}</div>';this.tpl+="</object>"},tpl:"",launch:function(a){a.ver=latte.isUND(a.ver)?richupload.DEF_SWF_VERSION:a.ver;a.width=latte.isUND(a.width)?richupload.DEF_SIZE[a.engineMode].width:a.width;a.height=latte.isUND(a.height)?richupload.DEF_SIZE[a.engineMode].height:a.height;var b;b=latte.dom.ele.create("div",a.nPos.ownerDocument);b.innerHTML=latte.tpl.eval(this.tpl,a);b=b.firstChild;latte.dom.appendNode.before(a.nPos,b);a.nPos.parentNode.removeChild(a.nPos);return b}});latte.Class.singletone(richupload.engine.FlashLauncher);
if(typeof window.latte=="undefined")window.latte={};if(typeof window.richupload=="undefined")window.richupload={};latte.Class({$name:"richupload.control.CallbackHandler",$const:function(){},uploadType:null,oUploadHdl:null,onLoad:function(){},onAddFile:function(a){richapp[richapp.RICHUPLOAD].nUploads[a]._isUploaded=!1},onDelFile:function(a,b){var c=richapp[richapp.RICHUPLOAD].nUploads[a];c._isUploaded=!1;b&&c._oCtrl.oDel.pushFileKey(b)},onUploadCreate:function(a){var a=richapp[richapp.RICHUPLOAD].nUploads[a],b=latte.query("."+richupload.E_FILE_ITEM_CLASS_NM,a._oCtrl.nNoScript);a._oCtrl.oAdd.pushByNodes(b)},onUploadUnitFile:function(a,b){richapp[richapp.RICHUPLOAD].nUploads[a]._oCtrl.oAdd.pushFileKey(b)},onUploadComplete:function(a){var b=richapp[richapp.RICHUPLOAD].nUploads[a],c=richupload.control.UploadHandler.getInstance();b._isUploaded=!0;latte.isFNC(b._oCtrl.submit)||c.completeUpload(a);b=b._oCtrl.submit(b,b.getLocaleString("MSG_FILE_UPLOAD_COMPLETE"));c.completeUpload(a,b===!1?!0:!1)},onUploadCancel:function(a){alert(richapp[richapp.RICHUPLOAD].nUploads[a].getLocaleString("MSG_FILE_UPLOAD_CANCEL"))},onDownloadCreate:function(a){var a=richapp[richapp.RICHUPLOAD].nDownloads[a],b=latte.query("."+richupload.E_FILE_ITEM_CLASS_NM,a._oCtrl.nNoScript);a._oCtrl.oAdd.pushByNodes(b)},onDownloadFile:function(){}});latte.Class.singletone(richupload.control.CallbackHandler);
if(typeof window.latte=="undefined")window.latte={};if(typeof window.richupload=="undefined")window.richupload={};latte.Class({$name:"richupload.control.UploadHandler",$const:function(){},uploadIDLst:null,oResult:null,startUpload:function(b,d,a){var c=richupload.control.UploadHandler.getInstance();c.uploadType=d;c.fncCallback=b;c.uploadIDLst=a||[];c.oResult={};latte.iter(c.uploadIDLst,function(a){c.oResult[a]=null});if(!a.length)return null;switch(d){case "sequential":c.nextUpload();break;case "multiple":latte.iter(a,function(a){c.uploadFile(richapp[richapp.RICHUPLOAD].nUploads[a])})}},nextUpload:function(){var b=richupload.control.UploadHandler.getInstance(),d=b.uploadIDLst.shift();b.uploadFile(richapp[richapp.RICHUPLOAD].nUploads[d])},completeUpload:function(b,d){var a=richupload.control.UploadHandler.getInstance(),c=!0;if(b&&!latte.isUND(a.oResult[b]))a.oResult[b]=richapp[richapp.RICHUPLOAD].nUploads[b]._isUploaded;latte.iter(a.oResult,function(a){if(a==null)return c=!1,null});c||d&&a.uploadType=="sequential"?(a.uploadType=null,a.uploadIDLst=null,latte.isFNC(a.fncCallback)&&a.fncCallback(a.oResult)):a.uploadType=="sequential"&&a.nextUpload()},uploadFile:function(b){if(!b||b._oAttr.engineMode!=richupload.E_UPLOAD_CLASS_NM||!latte.isFNC(b.upload))return!1;b.upload();return!0},upload:function(b,d,a){var d=(d||"sequential").toLowerCase(),c=richapp[richapp.RICHUPLOAD].nUploads,e=[],d=d.split(/-/),f=richupload.control.UploadHandler.getInstance();switch(d[1]){default:latte.iter(c,function(a){e.push(a._oAttr.id)});break;case "start":latte.iter(c,function(b){(e.length||a==b._oAttr.id)&&e.push(b._oAttr.id)});break;case "end":latte.iter(c,function(b){e.push(b._oAttr.id);if(a==b._oAttr.id)return null});break;case "fix":e=latte.array.merge([],a)}f.startUpload(b,d[0],e)}});latte.Class.singletone(richupload.control.UploadHandler);
if(typeof window.latte=="undefined")window.latte={};if(typeof window.richupload=="undefined")window.richupload={};latte.Class({$name:"richupload.control.FileManage",$const:function(a,b){if(a)this.nSWF=a;if(b)this.nFileLst=latte.dom.ele.getById(b,a.ownerDocument);this.oFileLst=[]},nSWF:null,nFileLst:null,oFileLst:null,getFileInfoByNode:function(a){return{name:a.getAttribute(richupload.E_FITEM_NAME_NM),type:a.getAttribute(richupload.E_FITEM_TYPE_NM),size:a.getAttribute(richupload.E_FITEM_SIZE_NM),seq:a.getAttribute(richupload.E_FITEM_SEQ_NM),url:a.getAttribute(richupload.E_FITEM_URL_NM)||"",purl:a.getAttribute(richupload.E_FITEM_THUMBNAIL_NM)}},pushByNode:function(a){a=this.getFileInfoByNode(a);this.nSWF.addRemoteFile(a);return a},pushByNodes:function(a){var b=this,c=[];latte.iter(a,function(a){c.push(b.pushByNode(a))});return c},pushFileKey:function(a){this.oFileLst.push(a);if(this.nFileLst)this.nFileLst.value=this.oFileLst.join(",")}});

// richupload startup sandbox
(function () {
	try {
		richupload.oCallBackHdl	= richupload.control.CallbackHandler.getInstance();
		richupload.upload		= richupload.control.UploadHandler.getInstance().upload;

		var oInit	= richupload.engine.Initializer.getInstance();
		oInit.init();
		oInit.deploy();
	}
	catch (ex) {}
})();