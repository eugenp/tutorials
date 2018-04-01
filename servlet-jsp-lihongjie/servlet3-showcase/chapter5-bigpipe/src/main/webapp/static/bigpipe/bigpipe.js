pl = {
    write : function(data) {
        if(data.css) {
            $(data.css).each(function(index, c) {
                try {
                    //1、加载css
                    document.write('<link type="text/css" rel="stylesheet" href="' + c + '">');
                } catch (e) {
                    //ignore
                }
            });

        }
        //2、写html
        $("#" + data.container).html(data.html);
        //3加载js
        if(data.js) {
            $(data.js).each(function(index, c) {
                try {
                    $.getScript(c);
                } catch (e) {
                    //ignore
                }
            });

        }
    }
};