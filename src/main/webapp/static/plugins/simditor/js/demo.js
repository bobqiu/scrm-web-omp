/**
 * Created by Administrator on 2016/6/15.
 */
(function() {
    $(function() {
        var $preview, editor, toolbar;
        Simditor.locale = 'zh-CN';
        toolbar = ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', '|', 'link', 'image', 'hr', '|', 'indent', 'outdent', 'alignment'];
        editor = new Simditor({
            textarea: $('#txt-content'),
            placeholder: '这里输入文字...',
            toolbar: toolbar,
            pasteImage: true,
            upload: location.search === '?upload' ? {
                url: '/upload'
            } : false
        });
        $preview = $('#preview');
        if ($preview.length > 0) {
            return editor.on('valuechanged', function(e) {
                return $preview.html(editor.getValue());
            });
        }
    });
}).call(this);