function openclose(obj) {
	var obj = document.getElementById(obj);
	if(obj.style.display == "none") obj.style.display = "block";
	else obj.style.display = "none";
}

latte.ui.onLoad(function () {
	var selected_menu = 0;
	var content_count = 0;

	var selected_class_name = "active";

	var menus = document.getElementById("guide").getElementsByTagName("h3");
	var dives = document.getElementsByTagName("iframe");
	var contents = new Array;

	for (var i=0; i<dives.length; i++) {
		var iframe = dives[i];

		if (iframe.className == "GuideContent") {

			// Javascript를 이용하여 CSS초기화
			if (selected_menu == content_count) {
				iframe.style.display = "block";
				menus[content_count].className += " " + selected_class_name;
			}
			else {
				iframe.style.display = "none";
				menus[content_count].className = menus[content_count].className.replace(selected_class_name, "");
			}
			// -->

			contents.push(iframe);
			content_count++;
		}
	}

	for (var i=0; i<menus.length; i++) {
		var quick_link = menus[i];
		quick_link.content_index = i;

		quick_link.onclick = quick_link.onkeypress = function(event) {

			contents[selected_menu].style.display = "none";

			menus[selected_menu].className = menus[selected_menu].className.replace(selected_class_name, "");

			contents[this.content_index].style.display = "block";
			this.className += " " + selected_class_name;

			selected_menu = this.content_index;

			return false;
		}
	}
});