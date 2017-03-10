$(function(){
    var header ={
        init : function () {
            var data = g_menuList;
            /*
            * tab菜单
            * */
            var otherHtml = "";
            for(var i=0; i<data.length; i++){
                if(data[i].menuName == "系统管理"){//系统管理
                    var systemHtml = '<li class="system-manage s-manage"><a href="javascript:void(0);" parent_target="' + i + '" event-onclick class="s-dropdown-toggle">' + data[i].menuName + '</a></li>';
                    $(".s-navbar-right").append(systemHtml);
                }else{//其它菜单
                    if(data[i].menuName == '首页'){
                        otherHtml += '<li onclick="sessionStorage.clear();sessionStorage[\'topNav\']=\''+data[i].menuName+'\'"><a href="javascript:void(0);" class="s-active" parent_target="' + i + '">' + data[i].menuName + '</a></li>';
                    }else{
                        otherHtml += '<li onclick="sessionStorage.clear(); sessionStorage[\'topNav\']=\''+data[i].menuName+'\'"><a href="javascript:void(0);" parent_target="' + i + '">' + data[i].menuName + '</a></li>';
                    }
                    $(".s-nav-tab ul").html(otherHtml);
                }
            }

            /*
             * top导航栏切换tab
             * */
            $(".s-nav-tab a").on("click", function (e) {
                $(".s-init-body").addClass("hidden");
                $(".s-row").removeClass("hidden");
                $(".system-manage a").removeClass("s-system-active");
                $(this).addClass("s-active").parents("li").siblings().find("a").removeClass("s-active");
                var parent_target = parseInt($(this).attr("parent_target"));
                var add = "no";//判断是否添加
                for(var i=0; i<data.length; i++){
                    if(parent_target == i){
                        if(data[i].subMenu.length != 0){//如果有二级菜单
                            $("#mainTwoMenu").show();
                            $("#navTab").removeClass("pageContext");
                            for(var j=0; j<data[i].subMenu.length; j++){
                                if(data[i].subMenu[j].menuType == 1){//如果有三级菜单
                                    /*
                                     * 二级菜单
                                     * */
                                    var collapse = data[i].subMenu[j].menuType == 1 ? 'collapse' : '';
                                    var href = data[i].subMenu[j].menuType == 1 ? 'javscript:' : data[i].subMenu[j].menuUrl;
                                    var navTab = data[i].subMenu[j].menuType == 1 ? '' : 'data-target="navTab"';
                                    var secondOrder = '<div class="panel panel-default">'+
                                        '<div class="panel-heading" role="tab" id="heading">'+
                                        '<h4 class="panel-title" data-toggle="' + collapse + '" data-parent="#accordion" data-target="#mainmenu' + j + '" style="cursor:pointer;" aria-expanded="true">'+
                                        '<a onclick="if(href!=\'javscript:\'){sessionStorage[\'LeftNav\']=\''+href+'\'}" href="' + href + '" '+navTab+'>'+
                                        '<i class="glyphicon glyphicon-cog"></i>' + data[i].subMenu[j].menuName +
                                        '<i class="glyphicon pull-right glyphicon-triangle-bottom"></i>'+
                                        '</a>'+
                                        '</h4>'+
                                        '</div>'+
                                        '<div id="mainmenu' + j + '" class="panel-collapse collapse in" aria-expanded="true">'+
                                        '<ul class="list-group submenu' + data[i].subMenu[j].menuId + '">'+

                                        '</ul>'+
                                        '</div>'+
                                        '</div>';
                                    if(add == "no"){
                                        $(".panel-group").html(secondOrder);
                                        add = "yes";
                                    }else{
                                        $(".panel-group").append(secondOrder);
                                    }

                                    /*
                                     * 三级菜单
                                     * */
                                    var thirdOrder = "";
                                    for(var z=0; z<data[i].subMenu[j].subMenu.length; z++){
                                        thirdOrder += '<li class="list-group-item" onclick="sessionStorage[\'LeftNav\']=\''+data[i].subMenu[j].subMenu[z].menuUrl+'\'">'+
                                            '<a href="' + data[i].subMenu[j].subMenu[z].menuUrl + '" data-target="navTab">'+
                                            '<i class="glyphicon glyphicon-triangle-right"></i>'+ data[i].subMenu[j].subMenu[z].menuName +
                                            '</a>'+
                                            '</li>';
                                    }
                                    $(".submenu" + data[i].subMenu[j].menuId).html(thirdOrder);

                                    $("#navTab").load(data[i].subMenu[0].subMenu[0].menuUrl);//默认展示first菜单

                                }else{
                                    var secondOrder = '<div id="mainmenu" class="panel-collapse collapse in s-panel-collapse" aria-expanded="true">' +
                                        '<ul class="list-group submenu11">' +
                                        '<li class="list-group-item" onclick="sessionStorage[\'LeftNav\']=\''+data[i].subMenu[j].subMenu[z].menuUrl+'\'">' +
                                        '<a href="' + data[i].subMenu[j].menuUrl + '" data-target="navTab">' +
                                        '<i class="glyphicon glyphicon-triangle-right">' +
                                        '</i>' + data[i].subMenu[j].menuName +
                                        '</a>' +
                                        '</li>' +
                                        '</ul>' +
                                        '</div>';
                                    if(add == "no"){
                                        $(".panel-group").html(secondOrder);
                                        add = "yes";
                                    }else{
                                        $(".panel-group").append(secondOrder);
                                    }

                                    $("#navTab").load(data[i].subMenu[0].menuUrl);//默认展示first菜单

                                }
                            }
                        }else{
                            console.log("未添加菜单！");
                            // $(".s-row").addClass("hidden");
                            $("#mainTwoMenu").hide();
                            if(data[i].menuName == '首页'){
                                $("#navTab").load(basePath + "home/page").addClass("pageContext");
                            }
                            else{
                                $("#navTab").load(basePath + "notOpen").addClass("pageContext");
                            }
                        }
                    }
                }
            });

            /*
             * 系统管理
             * */
            $("[event-onclick]").on("click", function () {
                $(".s-init-body").addClass("hidden");
                $(".s-row").removeClass("hidden");
                $(".system-manage a").addClass("s-system-active");
                sessionStorage['sysNav']="s-system-active";
                $(".s-nav-tab a").removeClass("s-active");
                var parent_target = parseInt($(this).attr("parent_target"));
                var add = "no";//判断是否添加
                for(var i=0; i<data.length; i++){
                    if(parent_target == i){
                        if(data[i].subMenu.length != 0){//如果有二级菜单
                            $("#mainTwoMenu").show();
                            $("#navTab").removeClass("pageContext");
                            for(var j=0; j<data[i].subMenu.length; j++){
                                if(data[i].subMenu[j].menuType == 1){//如果有三级菜单
                                    /*
                                     * 二级菜单
                                     * */
                                    var collapse = data[i].subMenu[j].menuType == 1 ? 'collapse' : '';
                                    var href = data[i].subMenu[j].menuType == 1 ? 'javscript:' : data[i].subMenu[j].menuUrl;
                                    var navTab = data[i].subMenu[j].menuType == 1 ? '' : 'data-target="navTab"';
                                    var secondOrder = '<div class="panel panel-default">'+
                                        '<div class="panel-heading" role="tab" id="heading">'+
                                        '<h4 class="panel-title" data-toggle="' + collapse + '" data-parent="#accordion" data-target="#mainmenu' + j + '" style="cursor:pointer;" aria-expanded="true">'+
                                        '<a href="' + href + '" '+navTab+'>'+
                                        '<i class="glyphicon glyphicon-cog"></i>' + data[i].subMenu[j].menuName +
                                        '<i class="glyphicon pull-right glyphicon-triangle-bottom"></i>'+
                                        '</a>'+
                                        '</h4>'+
                                        '</div>'+
                                        '<div id="mainmenu' + j + '" class="panel-collapse collapse in" aria-expanded="true">'+
                                        '<ul class="list-group submenu' + data[i].subMenu[j].menuId + '">'+

                                        '</ul>'+
                                        '</div>'+
                                        '</div>';
                                    if(add == "no"){
                                        $(".panel-group").html(secondOrder);
                                        add = "yes";
                                    }else{
                                        $(".panel-group").append(secondOrder);
                                    }

                                    /*
                                     * 三级菜单
                                     * */
                                    var thirdOrder = "";
                                    for(var z=0; z<data[i].subMenu[j].subMenu.length; z++){
                                        thirdOrder += '<li class="list-group-item" onclick="sessionStorage[\'LeftNav\']=\''+data[i].subMenu[j].subMenu[z].menuUrl+'\'">'+
                                            '<a href="' + data[i].subMenu[j].subMenu[z].menuUrl + '" data-target="navTab">'+
                                            '<i class="glyphicon glyphicon-triangle-right"></i>'+ data[i].subMenu[j].subMenu[z].menuName +
                                            '</a>'+
                                            '</li>';
                                    }
                                    $(".submenu" + data[i].subMenu[j].menuId).html(thirdOrder);
                                    if (sessionStorage['LeftNav'] != undefined) {
                                        console.log(sessionStorage['LeftNav']);
                                        $("#navTab").load(sessionStorage['LeftNav']);
                                    }
                                    else {
                                        $("#navTab").load(data[i].subMenu[0].subMenu[0].menuUrl);//默认展示first菜单
                                    }


                                }else{
                                    var secondOrder = '<div id="mainmenu" class="panel-collapse collapse in s-panel-collapse" aria-expanded="true">' +
                                        '<ul class="list-group submenu11">' +
                                        '<li class="list-group-item" onclick="sessionStorage[\'LeftNav\']=\''+data[i].subMenu[j].menuUrl+'\'">' +
                                        '<a href="' + data[i].subMenu[j].menuUrl + '" data-target="navTab">' +
                                        '<i class="glyphicon glyphicon-triangle-right">' +
                                        '</i>' + data[i].subMenu[j].menuName +
                                        '</a>' +
                                        '</li>' +
                                        '</ul>' +
                                        '</div>';
                                    if(add == "no"){
                                        $(".panel-group").html(secondOrder);
                                        add = "yes";
                                    }else{
                                        $(".panel-group").append(secondOrder);
                                    }

                                    if (sessionStorage['LeftNav'] != undefined) {
                                        console.log(sessionStorage['LeftNav']);
                                        $("#navTab").load(sessionStorage['LeftNav']);
                                    }
                                    else {
                                        $("#navTab").load(data[i].subMenu[0].menuUrl);//默认展示first菜单
                                    }

                                }
                            }
                        }else{
                            console.log("未添加菜单！");
                            $(".s-row").addClass("hidden");
                        }
                    }
                }

            });
        }
    }
    header.init();
    
    /**
     * 判断本地点击在导航位置
     */
    //清空样式
    $(".s-nav-tab a").each(function(index,ele){
        $(ele).removeClass('s-active');
    });
    if(sessionStorage['sysNav']){
        $('.system-manage a').addClass(sessionStorage['sysNav']);
        $(".system-manage a").click();
    }else {
        //添加样式
        $(".s-nav-tab li").each(function (index, ele) {
            if (sessionStorage['topNav']) {
                if ($(ele).text() == sessionStorage['topNav']) {
                    $(ele).find('a').addClass('s-active');
                    var datas = g_menuList;
                    var parent_target = parseInt($(ele).find('a').attr("parent_target"));
                    var add = "no";//判断是否添加
                    for (var i = 0; i < datas.length; i++) {
                        if (parent_target == i) {
                            if (datas[i].subMenu.length != 0) {//如果有二级菜单
                                $("#mainTwoMenu").show();
                                $("#navTab").removeClass("pageContext");
                                for (var j = 0; j < datas[i].subMenu.length; j++) {
                                    if (datas[i].subMenu[j].menuType == 1) {//如果有三级菜单
                                        /*
                                         * 二级菜单
                                         * */
                                        var collapse = datas[i].subMenu[j].menuType == 1 ? 'collapse' : '';
                                        var href = datas[i].subMenu[j].menuType == 1 ? 'javscript:' : datas[i].subMenu[j].menuUrl;
                                        var navTab = datas[i].subMenu[j].menuType == 1 ? '' : 'data-target="navTab"';
                                        var secondOrder = '<div class="panel panel-default">' +
                                            '<div class="panel-heading" role="tab" id="heading">' +
                                            '<h4 class="panel-title" data-toggle="' + collapse + '" data-parent="#accordion" data-target="#mainmenu' + j + '" style="cursor:pointer;" aria-expanded="true">' +
                                            '<a href="' + href + '" ' + navTab + '>' +
                                            '<i class="glyphicon glyphicon-cog"></i>' + datas[i].subMenu[j].menuName +
                                            '<i class="glyphicon pull-right glyphicon-triangle-bottom"></i>' +
                                            '</a>' +
                                            '</h4>' +
                                            '</div>' +
                                            '<div id="mainmenu' + j + '" class="panel-collapse collapse in" aria-expanded="true">' +
                                            '<ul class="list-group submenu' + datas[i].subMenu[j].menuId + '">' +

                                            '</ul>' +
                                            '</div>' +
                                            '</div>';
                                        if (add == "no") {
                                            $(".panel-group").html(secondOrder);
                                            add = "yes";
                                        } else {
                                            $(".panel-group").append(secondOrder);
                                        }

                                        /*
                                         * 三级菜单
                                         * */
                                        var thirdOrder = "";
                                        for (var z = 0; z < datas[i].subMenu[j].subMenu.length; z++) {
                                            thirdOrder += '<li class="list-group-item" onclick="sessionStorage[\'LeftNav\']=\'' + datas[i].subMenu[j].subMenu[z].menuUrl + '\'">' +
                                                '<a href="' + datas[i].subMenu[j].subMenu[z].menuUrl + '" data-target="navTab">' +
                                                '<i class="glyphicon glyphicon-triangle-right"></i>' + datas[i].subMenu[j].subMenu[z].menuName +
                                                '</a>' +
                                                '</li>';
                                        }
                                        $(".submenu" + datas[i].subMenu[j].menuId).html(thirdOrder);


                                        if (sessionStorage['LeftNav'] != undefined) {
                                            console.log(sessionStorage['LeftNav']);
                                            $("#navTab").load(sessionStorage['LeftNav']);
                                        }
                                        else {
                                            $("#navTab").load(datas[i].subMenu[0].subMenu[0].menuUrl);//默认展示first菜单
                                        }
                                    } else {
                                        var secondOrder = '<div id="mainmenu" class="panel-collapse collapse in s-panel-collapse" aria-expanded="true">' +
                                            '<ul class="list-group submenu11">' +
                                            '<li class="list-group-item" onclick="sessionStorage[\'LeftNav\']=\'' + datas[i].subMenu[j].subMenu[z].menuUrl + '\'">' +
                                            '<a href="' + datas[i].subMenu[j].menuUrl + '" data-target="navTab">' +
                                            '<i class="glyphicon glyphicon-triangle-right">' +
                                            '</i>' + datas[i].subMenu[j].menuName +
                                            '</a>' +
                                            '</li>' +
                                            '</ul>' +
                                            '</div>';
                                        if (add == "no") {
                                            $(".panel-group").html(secondOrder);
                                            add = "yes";
                                        } else {
                                            $(".panel-group").append(secondOrder);
                                        }


                                        if (sessionStorage['LeftNav'] != undefined) {
                                            console.log(sessionStorage['LeftNav']);
                                            $("#navTab").load(sessionStorage['LeftNav']);
                                        }
                                        else {
                                            $("#navTab").load(datas[i].subMenu[0].menuUrl);//默认展示first菜单
                                        }


                                    }
                                }
                            } else {
                                console.log("未添加菜单！");
                                // $(".s-row").addClass("hidden");
                                $("#mainTwoMenu").hide();
                                if (datas[i].menuName == '首页') {
                                    $("#navTab").load(basePath + "home/page").addClass("pageContext");

                                }
                                else {
                                    $("#navTab").load(basePath + "notOpen").addClass("pageContext");
                                }
                            }
                        }
                    }
                }
            } else {
                $(".s-nav-tab a").eq(0).addClass('s-active');
                $("#mainTwoMenu").hide();
                $("#navTab").load(basePath + "home/page").addClass("pageContext");

            }
        });
    }
});