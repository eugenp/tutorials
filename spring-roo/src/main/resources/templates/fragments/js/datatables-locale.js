// IIFE - Immediately Invoked Function Expression
(function(translateDatatables) {

  // The global jQuery object is passed as a parameter
  translateDatatables(window.jQuery, window, document);

}(function($, window, document) {

  // The $ is now locally scoped, it won't collide with other libraries 

  // Listen for the jQuery ready event on the document
  // READY EVENT BEGIN
  $(function() {
   
    // The DOM is ready!
    //console.log('The DOM is ready');
       
    // Set datatables defaults
    $.extend($.fn.dataTable.defaults, {
      // multilingual texts definitions, adding some to those already provided
      // default by Datatables
      'language': {
        'buttons': {
          'add': /*[[#{label_datatables_add}]]*/ 'Add',
          'delete': /*[[#{label_datatables_delete}]]*/ 'Delete',
          'colvis': /*[[#{label_datatables_columns}]]*/ 'Columns',
          'pageLength': /*[[#{label_datatables_showRows}]]*/ 'Show %d rows'
        },
        'select': {
          'rows': {
            _: /*[[#{label_datatables_selectedRows}]]*/ '%d selected rows',
            0: "",
            1: /*[[#{label_datatables_selectedRow}]]*/ '1 selected row'
          }
        },
        'decimal': /*[[#{label_datatables_decimal}]]*/ '.',
        'emptyTable': /*[[#{label_datatables_emptyTable}]]*/ 'No data available in table',
        'info': /*[[#{label_datatables_info}]]*/ 'Showing _START_ to _END_ of _TOTAL_ entries',
        'infoEmpty': /*[[#{label_datatables_infoEmpty}]]*/ 'Showing 0 to 0 of 0 entries',
        'infoFiltered': /*[[#{label_datatables_infoFiltered}]]*/ '(filtered from _MAX_ total entries)',
        'infoPostFix': /*[[#{label_datatables_infoPostFix}]]*/ '',
        'thousands': /*[[#{label_datatables_thousands}]]*/ '',
        'lengthMenu': /*[[#{label_datatables_lengthMenu}]]*/ 'Show _MENU_ entries',
        'loadingRecords': /*[[#{label_datatables_loadingRecords}]]*/ 'Loading...',
        'processing': /*[[#{label_datatables_processing}]]*/ 'Processing...',
        'search': /*[[#{label_datatables_search}]]*/ 'Search:',
        'zeroRecords': /*[[#{label_datatables_zeroRecords}]]*/ ' No matching records found',
        'paginate': {
          'first': /*[[#{label_datatables_first}]]*/ 'First',
          'last': /*[[#{label_datatables_last}]]*/ 'Last',
          'next': /*[[#{label_datatables_next}]]*/ 'Next',
          'previous': /*[[#{label_datatables_previous}]]*/ 'Previous'
        },
        'aria': {
          'sortAscending': /*[[#{label_datatables_sortAscending}]]*/ ': activate to sort column ascending',
          'sortDescending': /*[[#{label_datatables_sortDescending}]]*/ ': activate to sort column descending'
        }
      }  
    });
   
  });
   
  // READY EVENT END
  //console.log('The DOM may not be ready');
   
  // The rest of code goes here!
}));
