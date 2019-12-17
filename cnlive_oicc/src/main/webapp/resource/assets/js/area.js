/**
 * 
 */
var isEdit = true;
var isForgin = false;
$(function() {
	var oneLevel = $("#oneLevelArea");
	var secondLevel = $("#secondLevelArea");
	var threeLevel = $("#threeLevelArea");
	var fourLevel = $("#fourLevelArea");

	if (oneLevelArea != '' && secondLevelArea != '' && threeLevelArea != ''
			&& fourLevelArea != '') {

		loadArea({
			level : 1,
			pid : 0
		}, [ oneLevel ], oneLevelArea);

		loadArea({
			level : 2,
			pid : oneLevelArea
		}, [ secondLevel ], secondLevelArea);

		loadArea({
			level : 3,
			pid : secondLevelArea
		}, [ threeLevel ], threeLevelArea);

		loadArea({
			level : 4,
			pid : threeLevelArea
		}, [ fourLevel ], fourLevelArea);
		
		if(oneLevelArea !=0){
			secondLevel.css("display","none");
			threeLevel.css("display","none");  
		}else{
			secondLevel.css("display","");
			threeLevel.css("display","");  
		}

	} else if (oneLevelArea != '' && secondLevelArea != '') {
		loadArea({
			level : 1,
			pid : 0
		}, [ oneLevel ], oneLevelArea);
		loadArea({
			level : 2,
			pid : oneLevelArea
		}, [ secondLevel ], secondLevelArea);
		loadArea({
			level : 3,
			pid : secondLevelArea
		}, [ threeLevel ], threeLevelArea);
		
		if(oneLevelArea !=0){
			secondLevel.css("display","none");
			threeLevel.css("display","none");  
		}else{
			secondLevel.css("display","");
			threeLevel.css("display","");  
		}
		
	} else if (oneLevelArea != '') {
		loadArea({
			level : 1,
			pid : 0
		}, [ oneLevel ], oneLevelArea);
		loadArea({
			level : 2,
			pid : oneLevelArea
		}, [ secondLevel ], secondLevelArea);
	}

	if (!isExist) {
		loadArea({
			level : 1,
			pid : 0
		}, [ oneLevel, secondLevel, threeLevel, fourLevel ], '');
	}
	oneLevel.change(function() {

		if (parseInt($(this).val()) != 0) {
			isForgin = true;
			loadArea({
				level : 2,
				pid : $(this).val()
			}, [ secondLevel ], secondLevelArea);

			loadArea({
				level : 3,
				pid : parseInt($(this).val()) + 1
			}, [ threeLevel ], threeLevelArea);
			secondLevel.css("display","none");
			threeLevel.css("display","none");  
		} else {
			secondLevel.css("display","");
			threeLevel.css("display","");
			isForgin = false;
			loadArea({
				level : 2,
				pid : $(this).val()
			}, [ secondLevel, threeLevel, fourLevel ], '');
		}
	});

	secondLevel.change(function() {
		loadArea({
			level : 3,
			pid : $(this).val()
		}, [ threeLevel, fourLevel ], '');
	});

	threeLevel.change(function() {
		loadArea({
			level : 4,
			pid : $(this).val()
		}, [ fourLevel ], '');
	});

	fourLevel.change(function() {
		if ($(this).val() == '2') {
			secondLevel.val('');
			threeLevel.val('');
		}
	});

});

function loadArea(data, targetElement, value) {
	$.getJSON(base + "/region/getAreaByLevel", data, function(rs) {
		if (rs.code == 0) {
			for (var i = 0; i < targetElement.length; i++) {
				targetElement[i].empty();
				var defaultOption = $("<option>").val("").text("请选择");
				targetElement[i].append(defaultOption);

				/*
				 * if (targetElement[i].attr("id") == 'fourLevelArea' && isLocal !=
				 * '1') { defaultOption = $("<option>").val("2").text("官方组委会");
				 * targetElement[i].append(defaultOption); if (isEdit) { if ("2" ==
				 * fourLevelArea) targetElement[i].val("2"); isEdit = false; } }
				 */

			}
			var areas = rs.data.areas;
			for (var i = 0; i < areas.length; i++) {
				var option = $("<option>");
				option.val(areas[i].id);
				option.text(areas[i].name);
				targetElement[0].append(option);
			}

			if (targetElement[0].attr("id") != 'oneLevelArea' && !isExist
					&& isForgin) {
				targetElement[0].val(targetElement[0].find("option").last()
						.attr("value"));
				
			} else {
				targetElement[0].val(value);
			}

			if (!isExist && targetElement[0].attr("id") == 'oneLevelArea') {
				// if (isLocal == '1') {
				// $("#oneLevelArea").val(500);
				// $("#oneLevelArea").prop("disabled", true);
				// loadArea({
				// level : 2,
				// pid : 500
				// }, [ $("#secondLevelArea") ], '');
				//
				// } else {
				$("#oneLevelArea").val(0);
				// $("#oneLevelArea").prop("disabled", true);
				loadArea({
					level : 2,
					pid : 0
				}, [ $("#secondLevelArea") ], '');
				// }
			}
		} else {
			alert("获取地区列表失败");
		}
	});

}
$(function() {
	try {
		if (isLogin()) {

			var userName = getUserName();
			var facePath = getCookieSmallFacepath();

			if ('1' == isLocal) {
				$("#username")
						.html(
								"<a href='http://bm.fc.cnlive.com/edit' style='display:inline'>"
										+ userName
										+ "</a> | <a href='javascript:logout(\"http://fc.cnlive.com/hwIndex.html?spId=004_002\")' style='display:inline'>退出</a>");

			} else {
				$("#username")
						.html(
								"<a href='http://bm.fc.cnlive.com/edit' style='display:inline'>"
										+ userName
										+ "</a> | <a href='javascript:logout(\"http://fc.cnlive.com/?spId=004_002\")' style='display:inline'>退出</a>");
			}
			$("#username").css("font-size", "20px");
			$("#username").css("margin-top", "10px");
			$("#username").css("color", "#ffffff");

			$("#zhuche").css("display", "none");
		}
	} catch (e) {
	}

});

function zhuceAccount(url) {
	location.href = "http://www2.cnlive.com/web/reg/register.jsp?service=http://z.cnlive.com/user/login.action?forward="
			+ url;
}

function globalzhuceAccount(url) {
	location.href = "http://www2.cnlive.com/web/reg/register2.jsp?service=http://z.cnlive.com/user/login.action?forward="
			+ url;
}

function globalLogin(url) {
	location.href = "https://user.cnlive.com/sso/login?from=global&service=http://z.cnlive.com/user/login.action?forward="
			+ url;
}

function localLogin(url) {
	location.href = "https://user.cnlive.com/sso/login?service=http://z.cnlive.com/user/login.action?forward="
			+ url;
}

function createUrl(url) {

	if (url.indexOf("?") != -1) {
		if (url.indexOf("spId=004_002") == -1) {
			url += "&spId=004_002";
		}
	} else {
		url += "?spId=004_002";
	}
	return encodeURIComponent(url);
}

var initFormValues = {};
$(function() {
	if (isExist) {
		initFormValues = getFormData();
		initFormValues['participant.country_id'] = oneLevelArea;
		initFormValues['participant.province_id'] = secondLevelArea;
		initFormValues['participant.city_id'] = threeLevelArea;
		initFormValues['participant.regionId'] = fourLevelArea;
		if ('1' == isLocal) {
			var timer = null;
			timer = setInterval(function() {
				initFormValues['idYear'] = year;
				initFormValues['idMonth'] = month;
				initFormValues['idDay'] = day;
				if (year != '' && month != '' && day != '')
					clearInterval(timer);
			}, 10);

		}
		// console.log(initFormValues);
	}
});

function getFormData() {
	var jsonObj = {};
	var name = "";
	var value = "";
	$("input[type='text']").each(function(i, element) {
		name = $(this).attr("name");
		jsonObj[name] = value;
	});

	$("select").each(function(i, element) {
		name = $(this).attr("name");
		jsonObj[name] = value;
	});

	$("input[type='checkBox']").each(function(i, element) {
		name = $(this).attr("name");
		jsonObj[name] = value;
	});

	$("input[type='radio']").each(function(i, element) {
		name = $(this).attr("name");
		jsonObj[name] = value;
	});

	// 赋值
	$("select option:selected").each(function(i, element) {
		name = $(this).parent().attr("name");
		value = $(this).val();
		jsonObj[name] = value;

	});

	$("input[type='checkBox']:checked").each(function(i, element) {
		name = $(this).attr("name");
		value = $(this).val();
		jsonObj[name] = value;
	});

	$("input[type='radio']:checked").each(function(i, element) {
		name = $(this).attr("name");
		value = $(this).val();
		jsonObj[name] = value;
	});

	$("input[type='text']").each(function(i, element) {
		name = $(this).attr("name");
		value = $(this).val();
		jsonObj[name] = value;
	});
	return jsonObj;
}

function isChange() {
	if (!isExist)
		return true;
	var formData = getFormData();
	console.log(formData);
	for ( var key in initFormValues) {
		if (initFormValues[key] != formData[key]) {
			return true;
		}
	}
	return false;
}
