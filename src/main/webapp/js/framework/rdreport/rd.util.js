/*
 * rd.uti.js
 *
 * description : 웹접근성을 준수하기 위하여  keyboard로 접근하였을 경우 event keycode를 감지하기 위한 util
 *
 *
 */
function SaveAsDialog(keycode){

	if(keycode == 0 || keycode == 13){
		var Rdviewer = document.getElementById('Rdviewer');
		Rdviewer.SaveAsDialog();
    }
}
function PrintDialog(keycode){

	if(keycode == 0 || keycode == 13){
		var Rdviewer = document.getElementById('Rdviewer');
		Rdviewer.PrintDialog();
    }
}
function FirstPage(keycode){

	if(keycode == 0 || keycode == 13){
		var Rdviewer = document.getElementById('Rdviewer');
		Rdviewer.FirstPage();
    }
}
function PrevPage(keycode){

	if(keycode == 0 || keycode == 13){
		var Rdviewer = document.getElementById('Rdviewer');
		Rdviewer.PrevPage();
    }
}
function NextPage(keycode){

	if(keycode == 0 || keycode == 13){
		var Rdviewer = document.getElementById('Rdviewer');
		Rdviewer.NextPage();
    }
}
function LastPage(keycode){

	if(keycode == 0 || keycode == 13){
		var Rdviewer = document.getElementById('Rdviewer');
		Rdviewer.LastPage();
    }
}
function ZoomIn(keycode){

	if(keycode == 0 || keycode == 13){
		var Rdviewer = document.getElementById('Rdviewer');
		Rdviewer.ZoomIn();
    }
}
function ZoomOut(keycode){

	if(keycode == 0 || keycode == 13){
		var Rdviewer = document.getElementById('Rdviewer');
		Rdviewer.ZoomOut();
    }
}
function msout(num) {
	document.images[num].src =first_img[num].src;
}
function msover(num) {
	document.images[num].src = over_img[num].src;
}

img_cnt = 7;
first_img = new Array(img_cnt + 1);
for (var i = 0; i<=img_cnt; i++) {first_img[i] = new Image(); }
first_img[0].src = "/design/imgs/report/save_1.gif"   ;
first_img[1].src = "/design/imgs/report/print_1.gif"  ;
first_img[2].src = "/design/imgs/report/first_1.gif"  ;
first_img[3].src = "/design/imgs/report/back_1.gif"   ;
first_img[4].src = "/design/imgs/report/next_1.gif"   ;
first_img[5].src = "/design/imgs/report/last_1.gif"   ;
first_img[6].src = "/design/imgs/report/zoomin_1.gif" ;
first_img[7].src = "/design/imgs/report/zoomout_1.gif";


over_img = new Array(img_cnt + 1);
for (var i = 0; i<=img_cnt; i++) { over_img[i] = new Image(); }
over_img[0].src = "/design/imgs/report/save_2.gif"   ;
over_img[1].src = "/design/imgs/report/print_2.gif"  ;
over_img[2].src = "/design/imgs/report/first_2.gif"  ;
over_img[3].src = "/design/imgs/report/back_2.gif"   ;
over_img[4].src = "/design/imgs/report/next_2.gif"   ;
over_img[5].src = "/design/imgs/report/last_2.gif"   ;
over_img[6].src = "/design/imgs/report/zoomin_2.gif" ;
over_img[7].src = "/design/imgs/report/zoomout_2.gif";