var app = angular.module('app', ['ui.bootstrap', 'ui.bootstrap.datetimepicker']);

app.controller('MyController', ['$scope', function($scope) {

    var that = this;

    // date picker
    this.picker1 = {
        date: new Date('2015-03-01T00:00:00Z'),
        datepickerOptions: {
            showWeeks: false,
            startingDay: 1,
            dateDisabled: function(data) {
                return (data.mode === 'day' && (new Date().toDateString() == data.date.toDateString()));
            }
        }
    };

    // time picker
    this.picker2 = {
        date: new Date('2015-03-01T12:30:00Z'),
        timepickerOptions: {
            readonlyInput: false,
            showMeridian: false
        }
    };

    // date and time picker
    this.picker3 = {
        date: new Date()
    };

    // min date picker
    this.picker4 = {
        date: new Date(),
        datepickerOptions: {
            maxDate: null
        }
    };

    // max date picker
    this.picker5 = {
        date: new Date(),
        datepickerOptions: {
            minDate: null
        }
    };

    // set date for max picker, 10 days in future
    this.picker5.date.setDate(this.picker5.date.getDate() + 10);

    // global config picker
    this.picker6 = {
        date: new Date()
    };

    // dropdown up picker
    this.picker7 = {
        date: new Date()
    };

    // view mode picker
    this.picker8 = {
        date: new Date(),
        datepickerOptions: {
            mode: 'year',
            minMode: 'year',
            maxMode: 'year'
        }
    };

    // dropdown up picker
    this.picker9 = {
        date: null
    };

    // min time picker
    this.picker10 = {
        date: new Date('2016-03-01T09:00:00Z'),
        timepickerOptions: {
            max: null
        }
    };

    // max time picker
    this.picker11 = {
        date: new Date('2016-03-01T10:00:00Z'),
        timepickerOptions: {
            min: null
        }
    };

    // button bar
    this.picker12 = {
        date: new Date(),
        buttonBar: {
            show: true,
            now: {
                show: true,
                text: 'Now!'
            },
            today: {
                show: true,
                text: 'Today!'
            },
            clear: {
                show: false,
                text: 'Wipe'
            },
            date: {
                show: true,
                text: 'Date'
            },
            time: {
                show: true,
                text: 'Time'
            },
            close: {
                show: true,
                text: 'Shut'
            }
        }
    };

    // when closed picker
    this.picker13 = {
        date: new Date(),
        closed: function(args) {
            that.closedArgs = args;
        }
    };

    // saveAs - ISO
    this.picker14 = {
        date: new Date().toISOString()
    }

    this.openCalendar = function(e, picker) {
        that[picker].open = true;
    };

    // watch min and max dates to calculate difference
    var unwatchMinMaxValues = $scope.$watch(function() {
        return [that.picker4, that.picker5, that.picker10, that.picker11];
    }, function() {
        // min max dates
        that.picker4.datepickerOptions.maxDate = that.picker5.date;
        that.picker5.datepickerOptions.minDate = that.picker4.date;

        if (that.picker4.date && that.picker5.date) {
            var diff = that.picker4.date.getTime() - that.picker5.date.getTime();
            that.dayRange = Math.round(Math.abs(diff/(1000*60*60*24)))
        } else {
            that.dayRange = 'n/a';
        }

        // min max times
        that.picker10.timepickerOptions.max = that.picker11.date;
        that.picker11.timepickerOptions.min = that.picker10.date;
    }, true);


    // destroy watcher
    $scope.$on('$destroy', function() {
        unwatchMinMaxValues();
    });

}]);