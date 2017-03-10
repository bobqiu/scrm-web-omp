/*
* 苏昶宇
* */
var PublicUtil = PublicUtil ? PublicUtil : {};
PublicUtil.localStorage = {
    init : function(){
        if(window.localStorage){
            PublicUtil.localStorage.obj = window.localStorage;
        }else{
            PublicUtil.localStorage.obj = null;
        }
        PublicUtil.localStorage.localtype = true;
    },
    add : function(name,value){
        if(PublicUtil.localStorage.obj){
            try{
                if(typeof(value) == "object"){
                    value = JSON.stringify(value);
                }
                PublicUtil.localStorage.obj.setItem(name,value);
                PublicUtil.cookieStorage.setCookie(name,value);
            }catch(e){
                PublicUtil.localStorage.remove(name);
                PublicUtil.cookieStorage.clearCookie(name);
            }

        }else{
            PublicUtil.cookieStorage.setCookie(name,value);
        }

    },
    get : function(name){
        if(PublicUtil.localStorage.obj){
            var obj = PublicUtil.localStorage.obj.getItem(name);
            if(!obj||obj=="null"){
                obj = PublicUtil.cookieStorage.getCookie(name);
            }
            return obj;

        }else{
            return PublicUtil.cookieStorage.getCookie(name);
        }
    },
    remove : function(name){
        if(PublicUtil.localStorage.obj){
            PublicUtil.localStorage.obj.removeItem(name);
            PublicUtil.cookieStorage.clearCookie(name);
        }else{
            PublicUtil.cookieStorage.clearCookie(name);
        }
    }


};

PublicUtil.cookieStorage ={
    getCookie:function(name){
        var start = document.cookie.indexOf(name + "=");
        var len = start + name.length + 1;
        if ((!start) && (name != document.cookie.substring(0, name.length))) {
            return "";
        }
        if (start == - 1) {
            return "";
        }
        var end = document.cookie.indexOf(';', len);
        if (end == - 1) {
            end = document.cookie.length;
        }
        return unescape(document.cookie.substring(len, end));
    },
    setCookie:function(name, value, expires){
        var exp = new Date();
        expires = expires ? expires : 90;
        exp.setTime(exp.getTime() + 60 * 1000 * expires);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
        //document.cookie = name + "=" + escape(value);
    },
    clearCookie:function(name){
        var date = new Date();
        date.setTime(date.getTime() - 10000);
        document.cookie = name + "=a; expires=" + date.toGMTString();
    }
};

(function(){
    //@autor liulj 2016-12-8 9:19:39 添加过滤密码框只能输入英文班数字
    $(document).on("keypress","input[type=password]",function (e) {
        var exclude = String.fromCharCode(e.keyCode) == '%';
        if(exclude || !/^[a-zA-Z0-9]+$/.test(String.fromCharCode(e.keyCode))) {
            e.preventDefault();
        }
    })
})(jQuery)