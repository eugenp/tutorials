(function($) {
    $.fn.tableData = function(datas, opts) {
        opts = jQuery.extend({
            delete_callback: function() { return false; }, //删除 调用事件
        }, opts || {});
        datas = eval(datas);        //格式化JSON
        var thisTable = $(this);
        var row = $(thisTable).find(" tr:eq(1)").clone();
        var len_col = $(row).find("td").length;
        var tempIds = "";
        for (var c = 0; c < len_col; c++) {
            var id = $(row).find("td:eq(" + c + ")").attr("title");
            if (id != "" && id != undefined) {
                tempIds += id + "|";
            }
        }
        var column_ids = tempIds.split("|");
        len_col = column_ids.length - 1;
        
        $(thisTable).find(" tr:eq(1)").css("display", "none");
        $(thisTable).parent().parent().find(" tr[name=ready]").remove(); //移除现有数据行
        $(thisTable).parent().parent().find("form").each(function() {
        	$(this).css("display", "none");
        });
        $(thisTable).parent().css("display", "block");
        $.each(datas, function(i, data) {
            var row = $(thisTable).find(" tr:eq(1)").clone();
            for (var c = 0; c < len_col; c++) {
                var key = column_ids[c];
                var value = data[column_ids[c]];
                $(row).find("td[title='"+ key + "']").find("input").val(value).attr("type", "text");
            }
            //设置checkbox
            /*var checkbox = $(row).find("input");
            $(checkbox).val(data['id']);
            if(data['checked']=='1'){
            	$(checkbox).prop("checked", true);
            }*/
            row.attr("name", "ready");
            $(thisTable).append(row);             //追加新数据行
        })
        $(thisTable).find(" tr[name=ready]").show();  //显示新增的行
    }
})(jQuery);