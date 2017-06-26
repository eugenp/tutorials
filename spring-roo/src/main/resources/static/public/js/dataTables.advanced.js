/*! Advanced Configuration for DataTables 1.0.0
 * 2017 DISID Corportation, S.L - www.disid.com
 */
/**
 * @summary     Advanced configuration and extra features for DataTables
 * @description A collection of API methods, events and buttons for DataTables
 *   that provides advanced features in a DataTable element. Allows developers
 *   to customize the common Datatables functions easily during the DataTables
 *   initialization.
 * @version     1.0.0
 * @file        dataTables.advanced.js
 * @author      DISID Corporation, S.L (www.disid.com)
 * @contact     info@disid.com
 * @copyright   Copyright 2017 DISID Corporation, S.L
 *
 * This source file is free software, available under the following license:
 *   Apache 2.0 License - https://www.apache.org/licenses/LICENSE-2.0
 *
 * This source file is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the license files for details.
 *
 * For details please refer to: https://www.apache.org/licenses/LICENSE-2.0
 */
(function(factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD
        define(['jquery', 'datatables.net'], function($) {
            return factory($, window, document);
        });
    } else if (typeof exports === 'object') {
        // CommonJS
        module.exports = function(root, $) {
            if (!root) {
                root = window;
            }

            if (!$ || !$.fn.dataTable) {
                $ = require('datatables.net')(root, $).$;
            }

            return factory($, root, root.document);
        };
    } else {
        // Browser
        factory(jQuery, window, document);
    }
}(function($, window, document, undefined) {
    'use strict';
    var DataTable = $.fn.dataTable;

    // Version information for debugger
    DataTable.advanced = {};

    DataTable.advanced.version = '1.0.0';

    DataTable.advanced.init = function(settings) {

        // Getting dt element
        var dt = new DataTable.Api(settings);

        // Getting jQueryTable
        var $jQueryTable = $(dt.table().node());
        var $jQueryTableBody = $(dt.table().body());
        var $jQueryTableContainer = $(dt.table().container());

        // Saving default datatables configuration
        var dtClasses = $.extend(true, {}, DataTable.ext.classes);
        var dtButtons = $.extend(true, {}, DataTable.ext.buttons);
        var dtDefaults = $.extend(true, {}, DataTable.defaults);

        // Getting the provided advanced configuration
        // and the default options
        var init = settings.oInit.advanced;
        var opts = $.extend(true, {}, DataTable.defaults.advanced);

        // If developer has customize some function or property,
        // override the default ones.
        if ($.isPlainObject(init)) {

            if (init.btnsContainerClass !== undefined) {
                opts.btnsContainerClass = init.btnsContainerClass;
            }

            if (init.buttons !== undefined && $.isPlainObject(init.buttons)) {
                $.extend(opts.buttons, init.buttons);

                // If button is provided as null, is necessary to register it
                // with the default functionality, but will not be included to be
                // displayed.
                var hiddenButtons = [];
                for (var i in opts.buttons) {
                  if (opts.buttons.hasOwnProperty(i)) {
                    var button = opts.buttons[i];
                    if (!button) {
                      opts.buttons[i] = DataTable.defaults.advanced.buttons[i];
                      hiddenButtons.push(i);
                    }
                  }
                }

            }

            if (init.classes !== undefined && $.isPlainObject(init.classes)) {
                $.extend(opts.classes, init.classes);
            }

            if (init.deferRender !== undefined) {
                opts.deferRender = init.deferRender;
            }

            if (init.dom !== undefined) {
                opts.dom = init.dom;
            }

            if (init.loadData !== undefined) {
                opts.loadData = init.loadData;
            }

            if (init.loadFromState !== undefined) {
                opts.loadFromState = init.loadFromState;
            }

            if (init.onInitComplete !== undefined) {
                opts.onInitComplete = init.onInitComplete;
            }

            if (init.processing !== undefined) {
                opts.processing = init.processing;
            }

            if (init.renderTools !== undefined) {
                opts.renderTools = init.renderTools;
            }

            if (init.responsive !== undefined) {
                opts.responsive = init.responsive;
            }

            if (init.retrieve !== undefined) {
                opts.retrieve = init.retrieve;
            }

            if (init.serverSide !== undefined) {
                opts.serverSide = init.serverSide;
            }

            if (init.stateSave !== undefined) {
                opts.stateSave = init.stateSave;
            }
            
            if (init.defaultButtons !== undefined) {
            	opts.defaultButtons = init.defaultButtons;
            }

        }

        // Configure this Datatables rendering
        $.extend(DataTable.ext.classes, opts.classes);

        // Initialize and register buttons
        $.extend(DataTable.ext.buttons, opts.buttons);

        // Configure the RenderTools
        $.extend(DataTable, {
            'renderTools': opts.renderTools
        });

        // Set datatables defaults
        $.extend(DataTable.defaults, {
            'ajax': opts.loadData,
            'buttons': {
                'dom': {
                    'container': {
                        'className': opts.btnsContainerClass
                    }
                },
                // Obtains a button list from the specified buttons
                'buttons': getButtonsList(opts.buttons, opts.defaultButtons, hiddenButtons)
            },
            'columnDefs': [{
                'targets': 'checkboxcol', // First column from the left
                'checkboxes': {
                    'selectRow': true,
                    'selectAllPages': true
                }
            }, {
                'targets': 'dttools', // First column from the right,
                'width': 100,
                'render': {
                    'display': DataTable.renderTools
                }
            }, {
                'targets': '_all',
                'render': {
                    'display': DataTable.render.text()
                }
            }],

            'deferRender': opts.deferRender,
            'dom': opts.dom,
            'fnInitComplete': opts.onInitComplete,
            'processing': opts.processing,
            'responsive': opts.responsive,
            'retrieve': opts.retrieve,
            'serverSide': opts.serverSide,
            'stateSave': opts.stateSave,
            'stateSaveParams': opts.loadFromState
        });

        // After extend this datatable functions, is necessary to reinitialize
        // the table to apply the new configuration, so is necessary to detroy it
        // and draw it again.
        // IMPORTANT: To prevent that some event registered by other plugin in the original
        // table could be called, is necessary to unregister events.
        $jQueryTableBody.off("click");
        // IMPORTANT: You must not specify again the advanced
        // attribute during the Datatables initialization to prevent that this process starts again
        delete settings.oInit.advanced;
        // Destroy the existing table
        $jQueryTable.DataTable().destroy();
        // Create the new table using the initial configuration
        var datatables = $jQueryTable.DataTable(settings.oInit);

        // Next tables should use the default configuration, so restore it.
        DataTable.ext.classes = dtClasses;
        DataTable.ext.buttons = dtButtons;
        DataTable.defaults = dtDefaults;

        // Finally, register events to this datatable.
        registerEvents(datatables);

    };


    /*
     * Default configuration for the DataTable Advanced
     * component.
     *
     * IMPORTANT: Don't modify this object manually. You should
     * configure your DataTables during the initialization. Ex:
     *
     * $('table').DataTable({
     *    advanced : {
     *      loadData: function(data, callback, settings) {
     *        // code goes here
     *      },
     *      buttons: {
     *        // Disable add button
     *        'add': null,
     *        // Customize the delete function
     *        'delete': myCustomDeleteFunction
     *      },
     *      stateSave: false
     *    }
     * });
     */
    DataTable.defaults.advanced = {
        "btnsContainerClass": 'dt-buttons btn-group col-sm-6',
        "buttons": {
            'add': createButton,
            'delete': deleteBatchButton,
            'csv': exportCsvButton,
            'excel': exportExcelButton,
            'pdf': exportPdfButton
        },
        "classes": {
            "sFilter": "dataTables_filter col-sm-6",
            "sInfo": "dataTables_info col-sm-6",
            "sPaging": "dataTables_paginate col-sm-6 paging_",
            "sProcessing": "dataTables_processing progress-bar progress-bar-striped active"
        },
        "defaultButtons": [{
            'extend': 'colvis',
            'className': 'btn-action',
        },
        {
            'extend': 'pageLength',
            'className': 'btn-action',
        }],
        "deferRender": true,
        "dom": 'Bfrtip',
        "loadData": loadData,
        "loadFromState": loadFromState,
        "onInitComplete": onInitComplete,
        "processing": true,
        "renderTools": renderTools,
        "responsive": true,
        "retrieve": true,
        "serverSide": true,
        "stateSave": true

    }

    //////////////////// Private functions ********************************


    /**
     * This function obtains a button list using the defined buttons
     * in the default options or in the custom values provided by the
     * developer.
     */
    function getButtonsList(buttons, defaultButtons, hiddenButtons) {
        // Create an empty array
        var buttonsArray = [];

        // Obtain buttons and exclude the hidden buttons
        for (var i in buttons) {
            if (hiddenButtons === undefined || hiddenButtons.indexOf(i) === -1) {
                buttonsArray.push(i);
            }
        }

        // Finally include default buttons
        buttonsArray.push(defaultButtons);

        return buttonsArray;

    }


    /**
     * Creates a new button in the buttons plugin toolbar to create a new row
     * using the value of the table tag attribute 'data-create-url-function' as
     * a function which returns the URL or, if it is not defined, the value of
     * the table tag attribute 'data-create-url' to be used as the URL.
     */
    function createButton(datatables, conf) {
        var dataCreateUrl = getDataCreateUrl(datatables);

        if (dataCreateUrl) {
            /* This code uses a modal dialog to show the creation
             * form. This will be the default option in a future
             * version.
             */
            /*
            if (hasParentTable(datatables)) {
              return {
                'action': function(e, datatables, node, config) {
                  if (getParentSelectedRowId(datatables)) {
                    $('#categoryProductsTableAdd').modal('show');
                  }
                },
                'className': 'btn-action add',
                'name': 'add',
                'text': datatables.i18n('buttons.add', 'Add')catego
              };

            } else {
            */
            return {
                'action': function(e, datatables, node, config) {
                    var createUrl = getCreateUrl(datatables);
                    if (createUrl) {
                        location.href = createUrl;
                    }
                },
                'className': 'btn-action add',
                'name': 'add',
                'text': datatables.i18n('buttons.add', 'Add')
            };
            //}
        }
    };

    /**
     * Creates a new button in the buttons plugin toolbar to delete rows selected
     * with the checkbox extension,
     * using the value of the table tag attribute 'data-delete-url-function' as
     * a function which returns the URL or, if it is not defined, the value of
     * the table tag attribute 'data-create-url' to be used as the URL.
     */
    function deleteBatchButton(datatables, conf) {
        var deleteBatchUrl = getDataDeleteBatchUrl(datatables);

        if (deleteBatchUrl) {
            return {
                'action': function(e, datatables, node, config) {
                    var tableId = getTableId(datatables);
                    var $deleteConfirm = $('#' + tableId + 'DeleteBatchConfirm');

                    // When the delete element modal is opened, copy the current
                    // element id to be deleted to the 'TABLE_ID + DeleteRowId'
                    // element
                    $deleteConfirm.on('show.bs.modal', function(e) {
                        // Get data-row-id attribute of the clicked element
                        var rows_selected = datatables.columns().checkboxes.selected();

                        // Populate the row-id data attribute in the modal
                        $('#' + tableId + 'DeleteBatchRowId').data('row-id', rows_selected.join(","));
                    });

                    $('#' + tableId + 'DeleteBatchButton').on('click', function() {
                        deleteBatchElement(datatables);
                    });

                    $deleteConfirm.modal();
                },
                'className': 'btn-action delete',
                'name': 'delete',
                'text': datatables.i18n('buttons.delete', 'Delete')
            };
        }
    };

    /**
     * Deletes the element whose id is the one in the datatables
     * row whose _delete_ button has been selected, and the
     * the opened modal confirmacion has been accepted
     * (see modal-confirm-delete.html)
     * @param datatables DataTable on which the calling should act upon
     */
    function deleteBatchElement(datatables) {
        var $token = $("meta[name='_csrf']");
        var $header = $("meta[name='_csrf_header']");

        var tableId = getTableId(datatables);
        var rowIds = $('#' + tableId + 'DeleteBatchRowId').data('row-id');

        var url = getDeleteBatchUrl(datatables, rowIds);

        $.ajax({
                url: url,
                type: 'DELETE',
                beforeSend: function(request) {
                    if ($token != null && $token.length > 0 && $header != null && $header.length > 0) {
                        request.setRequestHeader($header.attr("content"), $token.attr("content"));
                    }
                }
            })
            .done(function(result) {
                var $deleteSuccess = $('#' + tableId + 'DeleteBatchSuccess');
                $deleteSuccess.modal();
                datatables.columns().checkboxes.deselect();
                datatables.ajax.reload(); // Refresh Datatables
            })
            .fail(function(jqXHR, status) {
                var $deleteError = $('#' + tableId + 'DeleteBatchError');
                $deleteError.modal();
            });
    }

    /**
     * Creates a new button in the buttons plugin toolbar to export data in
     * CSV format using the value of the table tag attribute
     * 'data-export-csv-url' as a function which returns the value
     * to be used as the URL.
     */
    function exportCsvButton(datatables, conf) {
        var dataExportCsvUrl = getDataValue(datatables, 'export-csv-url');
        if (dataExportCsvUrl) {
            return getExportButton(datatables, dataExportCsvUrl, "CSV");
        }
    }

    /**
     * Creates a new button in the buttons plugin toolbar to export data in
     * XLS format using the value of the table tag attribute
     * 'data-export-xls-url' as a function which returns the value
     * to be used as the URL.
     */
    function exportExcelButton(datatables, conf) {
        var dataExportXlsUrl = getDataValue(datatables, 'export-xls-url');
        if (dataExportXlsUrl) {
            return getExportButton(datatables, dataExportXlsUrl, "XLS");
        }
    }


    /**
     * Creates a new button in the buttons plugin toolbar to export data in
     * PDF format using the value of the table tag attribute
     * 'data-export-pdf-url' as a function which returns the value
     * to be used as the URL.
     */
    function exportPdfButton(datatables, conf) {
        var dataExportPdfUrl = getDataValue(datatables, 'export-pdf-url');
        if (dataExportPdfUrl) {
            return getExportButton(datatables, dataExportPdfUrl, "PDF");
        }
    }

    /**
     * Generates a Datatables export button object using the provided URL
     * and the provided type.
     *
     * @param datatables DataTable on which the calling should act upon
     * @param url The url where export button will load
     * @param type The type of the export button. Will be used to set
     * 			the button name
     * @return a new export button with the provided configuration
     */
    function getExportButton(datatables, url, type) {
        return {
            'action': function(e, datatables, node, config) {
                // Check if current datatable has some records. If not,
                // show an error modal and prevent to continue
                if (datatables.context[0]._iRecordsDisplay === 0) {
                    var tableId = getTableId(datatables);
                    var $exportError = $('#' + tableId + 'ExportEmptyError');
                    $exportError.modal();
                    return;
                }

                // Process the URL to obtain an URL that includes the Datatables
                // parameters.
                var processedUrl = getUrlWithDatatablesParams(datatables, url);

                // Open the processed URL in a new window
                window.open(
                    processedUrl,
                    '_blank'
                );
            },
            'className': 'btn-action export-' + type.toLowerCase(),
            'name': type,
            'text': datatables.i18n('buttons.export.' + type.toLowerCase(), type)
        };
    }

    /**
     * Generates and executes an ajax request whose goal is to load data for a
     * DataTable element.
     *
     * @param data DataTable object data
     * @param callback Name of the function to call with the server data obtained
     *        once the ajax request has been completed
     * @param settings DataTable object options
     */
    function loadData(data, callback, settings) {
        var datatables = this.DataTable();
        var url = getLoadUrl(datatables);
        if (url) {
            loadDataFromUrl(datatables, data, callback, url);
        } else {
            callback(emptyData(data.draw));
        }
    }

    /**
     * Generates and executes an ajax request whose goal is to load data for a
     * DataTable element.
     *
     * @param datatables DataTable on which the calling should act upon
     * @param data DataTable object data
     * @param callback Name of the function to call with the server data obtained
     *       once the ajax request has been completed
     * @param url Url to use for ajax request
     */
    function loadDataFromUrl(datatables, data, callback, url) {
        var $token = $("meta[name='_csrf']");
        var $header = $("meta[name='_csrf_header']");

        var prefix = 'loadUrlParam';
        var dataAttrs = getAllDataValues(datatables);
        $.each(dataAttrs, function(property, value) {
            if (property.length > prefix.length && property.lastIndexOf(prefix, 0) === 0) {
                var param = toLowerCaseFirst(property.substring(prefix.length));
                data[param] = value;
            }
        });

        $.ajax({
                url: url,
                type: 'GET',
                data: data,
                dataType: 'json',
                headers: {
                    Accept: "application/vnd.datatables+json",
                },
                context: datatables,
                beforeSend: function(request) {
                    if ($token != null && $token.length > 0 && $header != null && $header.length > 0) {
                        request.setRequestHeader($header.attr("content"), $token.attr("content"));
                    }
                }
            })
            .done(function(result) {
                callback(result);
                if (datatables.state.loaded()) {
                    var rowSelectedId = datatables.state.loaded().rowSelectedId;
                    if (rowSelectedId) {
                        var rowSelected = datatables.row('#' + rowSelectedId);
                        if (rowSelected.length > 0) {
                            rowSelected.select();
                        }
                    }
                }
            })
            .fail(function(jqXHR, status) {
                if (jqXHR.responseJSON != null && jqXHR.responseJSON.status === 403) {
                    var settings = this.settings()[0];
                    settings.oLanguage.sEmptyTable = "<p>Your session has expired or you have insufficient permissions</p>" +
                        "<a class='btn btn-primary' onclick='javascript:location.reload();'><span>Refresh</span></a>";
                }
                callback(emptyData(data.draw));
            });
    }


    /**
     * Converts the first char of the given string to lower case.
     * @param str the string to convert
     * @returns the converted string
     */
    function toLowerCaseFirst(str) {
        if (str.length > 0) {
            var value = str.charAt(0).toLowerCase();
            if (str.length > 1) {
                value = value.concat(str.slice(1));
            }
            return value;
        }
    }

    /**
     * Returns the parent datatables whose id is given through
     * the 'data-parent-table' attribute.
     */
    function getParentDatatables(datatables) {
        var parentTableId = getParentTableId(datatables);
        // Validate if parent-table id has been specified and
        // if the parent Datatable has been initialized
        if (parentTableId && $.fn.dataTable.isDataTable($(parentTableId))) {
            return $(parentTableId).DataTable();
        }
    }

    /**
     * Returns the parent table id when this datatables is a detail.
     */
    function getParentTableId(datatables) {
        var parentTableId = getDataValue(datatables, 'parent-table');
        if (parentTableId) {
            return "#" + parentTableId;
        }
    }

    /**
     * Returns the id of the selected row in the parent table, if any.
     * @param datatables child datatables
     * @returns the id of the parent datatables selected row
     */
    function getParentSelectedRowId(datatables) {
        var parentDatatables = getParentDatatables(datatables);
        if (parentDatatables) {
            var selected = parentDatatables.row({
                selected: true
            });

            if (selected.any()) {
                return selected.data().id;
            }
        }
    }

    /**
     * Returns if the datatables has a related parent datatables
     * @param datatables to find if it has a parent datatables
     * @returns if there is a parent datatables
     */
    function hasParentTable(datatables) {
        var parentTableId = getParentTableId(datatables);
        if (parentTableId) {
            return true;
        }
        return false;
    }

    /**
     * Process the given Url to perform the following actions*
     * - If the given datatables is not related to a parent one, it
     *   returns the given url as is.
     * - If the url contains the '_PARENTID_' valuea and there is a related
     *   parent table, if the parent table has a selected row, its identifier
     *   is used to replace the '_PARENTID_' value in the given url.
     *   Otherwise no url is returned because it is considered as an invalid
     *   url.
     * If the processed url is valid, the given id value is used to replace
     * the '_ID_' parameter in the url
     * @param datatables DataTable on which the calling should act upon
     * @param url to process
     * @param id (optional) identifier of the datatables row to act upon
     * @returns the processed url
     */
    function processUrl(datatables, url, id) {
        var processedUrl = url;
        // If it is a detail table, we have to get the parent id from
        // the selected row in the parent table, and replace the
        // _PARENTID_ variable in the given URL.
        if (url && url.indexOf('_PARENTID_') > -1 && hasParentTable(datatables)) {
            var parentRowId = getParentSelectedRowId(datatables);
            if (parentRowId !== undefined) {
                processedUrl = url.replace('_PARENTID_', parentRowId);
            } else {
                processedUrl = undefined;
            }
        }

        if (id !== undefined && processedUrl) {
            processedUrl = processedUrl.replace('_ID_', id);
        }

        return processedUrl;
    }

    /**
     * Process the given Url and the Datatables configuration to build
     * an URL that contains the Datatables parameters.
     * This function is useful when is necessary to make a petition
     * to the server side without using AJAX.
     *
     * @param datatables DataTable on which the calling should act upon
     * @param url to process
     * @returns the processed url
     */
    function getUrlWithDatatablesParams(datatables, url) {

        // Remove existing parameters
        url = url.split("?")[0];

        // Getting data from Datatables
        var dtContext = datatables.context[0];
        var data = dtContext.oAjaxData;

        // Getting search value
        var searchValue = data.search.value;

        // Getting order
        var order = data.order;

        // Getting columns
        var columns = data.columns;

        var sortParams = "";
        for (var i = 0; i < order.length; i++) {
            if (order[i] !== null && order[i] !== undefined &&
                order[i].column !== null && order[i].column !== undefined) {
                var columnName = columns[order[i].column].data;
                var dir = order[i].dir;

                sortParams += "sort=" + columnName + "," + dir + "&";

            }
        }
        if (sortParams.length > 0) {
            sortParams = sortParams.substr(0, sortParams.length - 1);
        }

        var datatablesColumns = "";
        for (i = 0; i < columns.length; i++) {
            if (columns[i] !== null && columns[i] !== undefined &&
                columns[i].data !== null && columns[i].data !== undefined &&
                datatablesColumns.indexOf(columns[i].data) === -1) {
                datatablesColumns += columns[i].data + ",";
            }
        }
        if (datatablesColumns.length > 0) {
            datatablesColumns = datatablesColumns.substr(0, datatablesColumns.length - 1);
            datatablesColumns = "datatablesColumns=" + datatablesColumns;
        }


        // Build URL parameters
        var hasParameters = false;
        var params = "";
        if (searchValue != null && searchValue != "" && searchValue != undefined) {
            if (hasParameters) {
                params += "&";
            }
            params += "search[value]=" + searchValue;
            hasParameters = true;
        }

        if (sortParams != null && sortParams != "" && sortParams != undefined) {
            if (hasParameters) {
                params += "&";
            }
            params += sortParams;
            hasParameters = true;
        }

        if (datatablesColumns != null && datatablesColumns != "" && datatablesColumns != undefined) {
            if (hasParameters) {
                params += "&";
            }
            params += datatablesColumns;
        }

        return url + "?" + params;

    }

    /**
     * Deletes the element whose id is the one in the datatables
     * row whose _delete_ button has been selected, and the
     * the opened modal confirmacion has been accepted
     * (see modal-confirm-delete.html)
     * @param datatables DataTable on which the calling should act upon
     */
    function deleteElement(datatables) {
        var $token = $("meta[name='_csrf']");
        var $header = $("meta[name='_csrf_header']");

        var tableId = getTableId(datatables);
        var rowId = $('#' + tableId + 'DeleteRowId').data('row-id');
        var url = getDeleteUrl(datatables, rowId);

        $.ajax({
                url: url,
                type: 'DELETE',
                beforeSend: function(request) {
                    if ($token != null && $token.length > 0 && $header != null && $header.length > 0) {
                        request.setRequestHeader($header.attr("content"), $token.attr("content"));
                    }
                }
            })
            .done(function(result) {
                var $deleteSuccess = $('#' + tableId + 'DeleteSuccess');
                $deleteSuccess.modal();
                datatables.ajax.reload(); // Refresh Datatables
            })
            .fail(function(jqXHR, status) {
                var $deleteError = $('#' + tableId + 'DeleteError');
                $deleteError.modal();
            });
    }

    /**
     * Returns the URL to load the data for a Datatables. The value
     * is defined through a 'data-load-url' attribute in the
     * Datatables table tag.
     * @param datatables DataTable on which the calling should act upon
     */
    function getLoadUrl(datatables) {
        var url = getDataValue(datatables, 'load-url');
        return processUrl(datatables, url);
    }

    /**
     * Returns the URL to create a new element for the Datatables.
     * The URL is processed to replace any parameters.
     *
     * @param datatables DataTable on which the calling should act upon
     */
    function getCreateUrl(datatables) {
        var url = getDataCreateUrl(datatables);
        return processUrl(datatables, url);
    }

    /**
     * Returns the URL to create a new element for the Datatables
     * as defined in the table data attributes.
     * The value is defined in the Datatables table tag with a
     * 'data-create-url-function' as a function which returns the URL
     * or, if it is not defined, the value of the attribute
     * 'data-create-url' to be used as the URL.
     *
     * @param datatables DataTable on which the calling should act upon
     */
    function getDataCreateUrl(datatables) {
        var urlFunction = getDataValue(datatables, 'create-url-function');
        var url = urlFunction ? $[urlFunction]() : getDataValue(datatables, 'create-url');
        return url;
    }

    /**
     * Returns the URL to show the details of an element of the Datatables.
     * The value is defined in the Datatables table tag with a
     * 'data-show-url' as the URL to use.
     * The URL contains the text *_ID_* in the place where
     * the selected element Id has to be inserted.
     *
     * @param datatables DataTable on which the calling should act upon
     * @param id identifier of the element to edit
     */
    function getShowUrl(datatables, id) {
        if (id == null || id === "null") {
            return null;
        }
        var url = getDataValue(datatables, 'show-url');
        return processUrl(datatables, url, id);
    }

    /**
     * Returns the URL to edit an element of the Datatables.
     * The value is defined in the Datatables table tag with a
     * 'data-edit-url' as the URL to use.
     * The URL contains the text *_ID_* in the place where
     * the selected element Id has to be inserted.
     *
     * @param datatables DataTable on which the calling should act upon
     * @param id identifier of the element to edit
     */
    function getEditUrl(datatables, id) {
        var url = getDataValue(datatables, 'edit-url');
        return processUrl(datatables, url, id);
    }

    /**
     * Returns the URL to remove an element of the Datatables.
     * The value is defined in the Datatables table tag with a
     * 'data-delete-url' as the URL to use.
     * The URL contains the text *_ID_* in the place where
     * the selected element Id has to be inserted.
     *
     * @param datatables DataTable on which the calling should act upon
     */
    function getDataDeleteUrl(datatables) {
        var url = getDataValue(datatables, 'delete-url');
        return url;
    }

    /**
     * Returns the URL to remove an element of the Datatables.
     * The value is defined in the Datatables table tag with a
     * 'data-delete-url' as the URL to use.
     *
     * The URL contains the text *_ID_* in the place where
     * the selected element Id has to be inserted, AND it is
     * replaced with the provided id.
     *
     * @param datatables DataTable on which the calling should act upon
     * @param id identifier of the element to remove
     */
    function getDeleteUrl(datatables, id) {
        var url = getDataDeleteUrl(datatables);
        return processUrl(datatables, url, id);
    }

    /**
     * Returns the URL to remove a list of elements from the Datatables.
     * The value is defined in the Datatables table tag with a
     * 'data-delete-batch-url' as the URL to use.
     * The URL contains the text *_ID_* in the place where
     * the selected elements Ids have to be inserted.
     *
     * @param datatables DataTable on which the calling should act upon
     */
    function getDataDeleteBatchUrl(datatables) {
        var url = getDataValue(datatables, 'delete-batch-url');
        return url;
    }

    /**
     * Returns the URL to remove a list of elements from the Datatables.
     * The value is defined in the Datatables table tag with a
     * 'data-delete-batch-url' as the URL to use.
     * The URL contains the text *_ID_* in the place where
     * the selected elements Ids have to be inserted, and it is
     * replaced with the provided idlist.
     *
     * @param datatables DataTable on which the calling should act upon
     * @param idlist list of identifiers of the elements to remove
     */
    function getDeleteBatchUrl(datatables, idlist) {
        var url = getDataDeleteBatchUrl(datatables);
        return processUrl(datatables, url, idlist);
    }

    /**
     * Returns the 'data-name' attribute value of a datatables.
     * @param datatables DataTable on which the calling should act upon
     * @param name the name of the data attribute to return the value of
     */
    function getDataValue(datatables, name) {
        var $dt = jQueryTable(datatables);
        return $dt.data(name);
    }

    /**
     * Returns all the 'data-*' attributes of a datatables.
     * @param datatables DataTable on which the calling should act upon
     */
    function getAllDataValues(datatables) {
        var $dt = jQueryTable(datatables);
        return $dt.data();
    }

    /**
     * Returns the jQuery object for the given datatables element.
     */
    function jQueryTable(datatables) {
        return $(datatables.table().node());
    }

    /**
     * Returns the table id attribute for the given datatables element.
     */
    function getTableId(datatables) {
        var $jQueryTable = jQueryTable(datatables);
        return $jQueryTable.attr('id');
    }

    /**
     * Generates a JSON object with the necessary data for indicating a
     * DataTables object that 0 elements have been found.
     * This is used for details related tables, when a parent table
     * row is not selected.
     *
     * @param draw DataTables request counter
     * @returns {json} JSON object with empty data
     */
    function emptyData(draw) {
        return {
            'data': [],
            'draw': draw,
            'error': null,
            'recordsFiltered': '0',
            'recordsTotal': '0'
        };
    }

    /**
     * This function will be called when DataTables has been fully
     * initialised and data loaded.
     */
    function onInitComplete(oSettings, json) {
        var datatables = this.DataTable();
        // Save the selected row to state
        saveSelectedRowToState(datatables, oSettings, json);
        // Register checkboxes
        registerCheckBoxesEvents(datatables);
    }

    /**
     * If a row is selected, store it in the persisted table state
     * so if the user goes to another page and returns, the current
     * selected row is still selected.
     * @param datatables the Datatables element
     * @param oSettings DataTable object options
     * @param json
     */
    function saveSelectedRowToState(datatables, oSettings, json) {
        var state = datatables.state;
        datatables.on('select', function(e, dt, type, indexes) {
            if (type === 'row') {
                var rowSelectedId = datatables.rows(indexes).ids()[0];
                state.loaded().rowSelectedId = rowSelectedId;
                state.save();
            }
        });
        datatables.on('deselect', function(e, dt, type, indexes) {
            if (type === 'row') {
                state.loaded().rowSelectedId = undefined;
                state.save();
            }
        });
        if (!state.loaded()) {
            oSettings.oLoadedState = datatables.state();
        }
    }

    /**
     * Loads a previously persisted datatables state.
     * @param settings DataTable object options
     * @param data DataTable object data
     */
    function loadFromState(settings, data) {
        var datatables = this.DataTable();
        loadSelectedRowFromState(datatables, data);
    }

    /**
     * Loads a previously selected row id from the persisted state.
     * @param settings DataTable object options
     * @param data DataTable object data
     */
    function loadSelectedRowFromState(datatables, data) {
        var state = datatables.state;
        if (state.loaded()) {
            var rowSelectedId = state.loaded().rowSelectedId;
            if (rowSelectedId) {
                data.rowSelectedId = rowSelectedId;
            }
        }
    }

    /**
     * Registers events for the given datatables
     */
    function registerEvents(datatables) {
        registerDeleteModalEvents(datatables);
        registerAddModalEvents(datatables);
        registerToParentEvents(datatables);
        registerOnDrawFinishesEvents(datatables);
    }

    /**
     * This function registers all the necessary actions to execute
     * when the provided datatables is completly drawed.
     */
    function registerOnDrawFinishesEvents(datatables) {
        // When this datatable is re-drawed, the
        // following actions will be executed
        datatables.on('draw.dt', function() {
            // Register events to the new included checkboxes
            registerCheckBoxesEvents(datatables);
        });
    }

    /**
     * Registers events related to the checkboxes of the given
     * datatables.
     */
    function registerCheckBoxesEvents(datatables) {
        // Getting the table id
        var tableId = getTableId(datatables);
        // Obtain all checkboxes for this table
        var checkBoxes = jQuery("#" + tableId + " input:checkbox");
        // Register change event for every checkbox. Every time that some checkbox
        // changes, validates if the delete batch button should be enabled or not
        jQuery.each(checkBoxes, function(item) {
            jQuery(this).change(function() {
                var rows_selected = datatables.columns().checkboxes.selected();
                if (rows_selected.join(",") === "") {
                    datatables.button('delete:name').disable();
                } else {
                    datatables.button('delete:name').enable();
                }
            });
        });

        // Re-initialize the delete batch button
        var rows_selected = datatables.columns().checkboxes.selected();
        if (rows_selected.join(",") === "") {
            datatables.button('delete:name').disable();
        } else {
            datatables.button('delete:name').enable();
        }

    }

    /**
     * Registers the events related to the delete modals, so the
     * modal knows the id of the row to delete.
     */
    function registerDeleteModalEvents(datatables) {
        var tableId = getTableId(datatables);
        var $deleteConfirm = $('#' + tableId + 'DeleteConfirm');

        // When the delete element modal is opened, copy the current
        // element id to be deleted to the 'TABLE_ID + DeleteRowId'
        // element
        $deleteConfirm.on('show.bs.modal', function(e) {
            // Get data-row-id attribute of the clicked element
            var rowId = jQuery(e.relatedTarget).data('row-id');
            // Populate the row-id data attribute in the modal
            $('#' + tableId + 'DeleteRowId').data('row-id', rowId)
        });

        $('#' + tableId + 'DeleteButton').on('click', function() {
            deleteElement(datatables);
        });
    }

    /**
     * When a table is linked to parent table, for a master detail list
     * for example, it registers the row selection events in the parent
     * table to update the data in the child table.
     */
    function registerToParentEvents(datatables) {
        var parentDatatables = getParentDatatables(datatables);

        if (parentDatatables) {
            // Register to de/select events
            parentDatatables.on('select', function() {
                datatables.button('add:name').enable();
                datatables.ajax.reload();
            });

            parentDatatables.on('deselect', function() {
                datatables.button('add:name').disable();
                datatables.ajax.reload();
            });

            // Register to reload finished event, needed when the selected row has
            // been deleted in the parent table or any other change
            parentDatatables.on('xhr.dt', function() {
                datatables.ajax.reload();
            });

            datatables.button('add:name').disable();
        }
    }

    /**
     * Registers the events related to the delete modals, so the
     * modal knows the id of the row to delete.
     */
    function registerAddModalEvents(datatables) {
        var parentDatatables = getParentDatatables(datatables);

        // The add modal dialog is only used in child datatables
        if (parentDatatables) {
            var tableId = getTableId(datatables);

            $('#' + tableId + 'AddButton').on('click', function() {
                var url = getCreateUrl(datatables);
                $addForm = $('#' + tableId + 'AddForm');
                var params = $addForm.serialize();
                $.ajax({
                    type: $addForm.attr('method'),
                    url: url,
                    data: params,
                    success: function(data) {
                        datatables.ajax.reload();
                    }
                });
            });
        }
    }

    /**
     * Renders the tools column, with the buttons to perform operations
     * on the table rows.
     */
    function renderTools(data, type, full, meta) {
        var datatables = new $.fn.dataTable.Api(meta.settings);
        var tableId = getTableId(datatables);
        var rowId = data;
        var buttons = '<div class="btn-group" role="group">';

        var showUrl = getShowUrl(datatables, rowId);
        // Check if the show will be inline
        var showInline = getDataValue(datatables, 'show-inline');
        if (showUrl && !showInline) {
            buttons = buttons.concat('<a class="btn btn-action btn-sm" href="')
                .concat(showUrl).concat('" ><span class="glyphicon glyphicon-eye-open"></span></a>');
        }else if(showUrl && showInline){
        	buttons = buttons.concat('<a aria-expanded="false" class="btn btn-action btn-sm" href="#" onclick="event.preventDefault();jQuery(\'#').concat(tableId).concat('\').DataTable().advanced.showInline(this, jQuery(\'#').concat(tableId).concat('\').DataTable(),\'').concat(showUrl).concat('\')" role="button"><span class="glyphicon glyphicon-eye-open"></span></a>');
        }

        var editUrl = getEditUrl(datatables, rowId);
        if (editUrl) {
            buttons = buttons.concat('<a class="btn btn-action btn-sm" href="')
                .concat(editUrl).concat('"><span class="glyphicon glyphicon-pencil"></span></a>');
        }

        var deleteUrl = getDeleteUrl(datatables, rowId);
        if (deleteUrl) {
            buttons = buttons.concat('<a role="button" class="btn btn-action btn-sm" data-toggle="modal" data-target="#')
                .concat(tableId).concat('DeleteConfirm" data-row-id="')
                .concat(data).concat('"><span class="glyphicon glyphicon-trash"></span></a>');
        }

        buttons = buttons.concat('</div>');
        return buttons;
    }
    
    
    /**
     * This method tries to display the show view of the selected record
     * expanding the selected row.
     */
    function showInline(showButton, datatables, showUrl){
    	var tr = showButton.closest('tr');
        var row = datatables.row( tr );
        if ( row.child.isShown() ) {
            // This row is already open - close it
            $(showButton).attr("aria-expanded", "false");
            row.child.hide();
        }
        else {
           $(showButton).attr("aria-expanded", "true");
           $.ajax({
    		      url: showUrl + "/inline",
    		      dataType: 'html'
    		    }).done(function(data) {
    			    // Open this row
    			    row.child(data).show();
    		    }).fail(function(data){
    			    // Show error in new row
    			    row.child("<div class='alert alert-danger'>ERROR: An error occurred while trying to obtain more info.</div>").show();
    		    });
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * DataTables Advanced Extension API
     *
     * For complete documentation, please refer to the docs/api directory or the
     * DataTables site
     */

    // Local variables to improve compression
    var apiRegister = DataTable.Api.register;

    apiRegister('advanced()', function() {
        return this.iterator('table', function(settings) {
            DataTable.advanced.init(settings);
        });
    });

    apiRegister('advanced.getTableId()', getTableId);
    apiRegister('advanced.getCreateUrl()', getCreateUrl);
    apiRegister('advanced.getEditUrl()', getEditUrl)
    apiRegister('advanced.getDeleteUrl()', getDeleteUrl);
    apiRegister('advanced.getDeleteBatchUrl()', getDeleteBatchUrl);
    apiRegister('advanced.getShowUrl()', getShowUrl);
    apiRegister('advanced.getDataValue()', getDataValue);
    apiRegister('advanced.processUrl()', processUrl);

    apiRegister('advanced.getCreateButton()', createButton);
    apiRegister('advanced.getDeleteBatchButton()', deleteBatchButton);
    apiRegister('advanced.getExportCsvButton()', exportCsvButton);
    apiRegister('advanced.getExportExcelButton()', exportExcelButton);
    apiRegister('advanced.getExportPdfButton()', exportPdfButton);
    
    apiRegister('advanced.showInline()', showInline);



    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Initialization
     */

    // DataTables creation - check if advanced has been defined in the options.
    $(document).on('preInit.dt.dtadvanced', function(e, settings) {
        if (e.namespace !== 'dt') {
            return;
        }

        // If the 'advanced' option has been specified,
        // initialize the advanced configuration for this
        // DataTable.
        if (settings.oInit.advanced !== undefined) {
            DataTable.advanced.init(settings);
        }

    });

    return DataTable.advanced;
}));
