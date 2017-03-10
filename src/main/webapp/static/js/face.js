var face=new Array("微笑","撇嘴","色","发呆","得意","流泪","害羞","闭嘴","睡","大哭","尴尬","发怒","调皮","呲牙","惊讶","难过","酷","囧","抓狂","吐","偷笑","愉快","白眼","傲慢","饥饿","困","惊恐","流汗","憨笑","悠闲","奋斗","咒骂","疑问","嘘","晕","衰","骷髅","敲打","再见","擦汗","抠鼻","鼓掌","糗大了","坏笑","左哼哼","右哼哼","哈欠","鄙视","委屈","快哭了","阴险","亲亲","可怜","菜刀","西瓜","啤酒","篮球","乒乓","咖啡","饭","猪头","玫瑰","凋谢","嘴唇","爱心","心碎","蛋糕 ","闪电","炸弹","便便","月亮","太阳","拥抱","强","弱","握手","胜利","抱拳","勾引","拳头","OK","跳跳","发抖","怄火","转圈","嘿哈","捂脸","奸笑","机智","皱眉","耶");
(function($){
    /*表情图片添加*/
    $.fn.qqFace = function(options){
        var defaults = {
            id : 'facebox',
            path : 'face/',
            assign : 'content',
            top : '0px',
            left : '0px'
        };
        var option = $.extend(defaults, options);
        var assign = $('#'+option.assign);
        var id = option.id;
        var path = option.path;


        if(assign.length<=0){
            alert('缺少表情赋值对象。');
            return false;
        }

        $(this).click(function(e){
            var strFace, labFace;
            if($('#'+id).length<=0){
                strFace = '<div style="left:'+option.left+';top:'+option.top+'" id="'+id+'" class="tool-tips face-div"><span class="bdnuarrow"><em></em><i></i></span>';
                for(var i =0;i<face.length;i++){
                    labFace = '['+face[i]+']';
                    strFace += '<img title="'+face[i]+'" src="'+path+i+'.png" onclick="$(\'#'+option.assign+'\').setCaret();$(\'#'+option.assign+'\').insertAtCaret(\'' + labFace + '\');"/>';
                }
                strFace += '</div>';
                $(this).parent().append(strFace);

                $('#'+id).show();
                e.stopPropagation();

            }else{
                $('#'+id).hide();
                $('#'+id).remove();
            }

        });

        $(document).click(function(){
            $('#'+id).hide();
            $('#'+id).remove();
        });
    };



})(jQuery);

jQuery.extend({
    unselectContents: function(){
        if(window.getSelection)
            window.getSelection().removeAllRanges();
        else if(document.selection)
            document.selection.empty();
    }
});
jQuery.fn.extend({
    selectContents: function(){
        $(this).each(function(i){
            var node = this;
            var selection, range, doc, win;
            if ((doc = node.ownerDocument) && (win = doc.defaultView) && typeof win.getSelection != 'undefined' && typeof doc.createRange != 'undefined' && (selection = window.getSelection()) && typeof selection.removeAllRanges != 'undefined'){
                range = doc.createRange();
                range.selectNode(node);
                if(i == 0){
                    selection.removeAllRanges();
                }
                selection.addRange(range);
            } else if (document.body && typeof document.body.createTextRange != 'undefined' && (range = document.body.createTextRange())){
                range.moveToElementText(node);
                range.select();
            }
        });
    },

    setCaret: function(){
        if(!$.browser.msie) return;
        var initSetCaret = function(){
            var textObj = $(this).get(0);
            textObj.caretPos = document.selection.createRange().duplicate();
        };
        $(this).click(initSetCaret).select(initSetCaret).keyup(initSetCaret);
    },

    insertAtCaret: function(textFeildValue){
        var textObj = $(this).get(0);
        if(document.all && textObj.createTextRange && textObj.caretPos){
            var caretPos=textObj.caretPos;
            caretPos.text = caretPos.text.charAt(caretPos.text.length-1) == '' ?
            textFeildValue+'' : textFeildValue;
        } else if(textObj.setSelectionRange){
            var rangeStart=textObj.selectionStart;
            var rangeEnd=textObj.selectionEnd;
            var tempStr1=textObj.value.substring(0,rangeStart);
            var tempStr2=textObj.value.substring(rangeEnd);
            var objectValue = tempStr1+textFeildValue+tempStr2;
            if(objectValue.length > 1500){
                return;
            }
            textObj.value=objectValue;
            textObj.focus();
            var len=textFeildValue.length;
            textObj.setSelectionRange(rangeStart+len,rangeStart+len);
            textObj.blur();
            FontNum("changeContent", "limit-text", 1500);
        }else{
            textObj.value+=textFeildValue;
        }
    }
});

$(function () {

});