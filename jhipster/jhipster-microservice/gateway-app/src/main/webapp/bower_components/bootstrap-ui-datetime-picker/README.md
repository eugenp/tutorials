# Bootstrap-UI/datetime-picker

AngularJs directive to use a date and/or time picker as a dropdown from an input. 

[Demo](https://rawgit.com/Gillardo/bootstrap-ui-datetime-picker/master/example/index.html)

## Installation
To use the directive you must have the following angular-ui bootstrap directives included already
* DatePicker
* TimePicker

You should already have the ui.bootstrap dependancy included in your app.js file like below, You then need to add ui.bootstrap.datetimepicker, as so
```
angular.module('app', ['ui.bootstrap', 'ui.bootstrap.datetimepicker']);
```
Download the source from dist/datetime-picker.min.js file and include it in your project.

Or use Bower (thank you krico for setup)
```
bower install --save bootstrap-ui-datetime-picker
```
and link with `bower_components/bootstrap-ui-datetime-picker/dist/datetime-picker.min.js`

## Usage
You have the following properties available to use with the directive.  All are optional unless stated otherwise
* ngModel (required) - Your date object
* isOpen - (true/false)
* closeOnDateSelection (true/false)
* closeOnTimeNow (true/false)
* enableDate (true/false)
* enableTime (true/false)
* buttonBar (object)
* initialPicker ('date'/'time')
* reOpenDefault (false/'date'/'time') - NOTE: `true` not supported
* datepickerOptions (object)
* timepickerOptions (object)
* defaultTime (string)
* saveAs (boolean|function|'ISO'|'json'|'number')
* readAs (boolean|function)
 
##### isOpen
Whether the popup/dropdown is visible or not. Defaults to false
##### closeOnDateSelection
Close popup once a date has been chosen. TimePicker will stay open until user closes.
##### closeOnTimeNow
Close popup once a time has been chosen via now button.
##### enableDate
Whether you would like the user to be able to select a date. Defaults to true
##### enableTime
Whether you would like the user to be able to select a time. Defaults to true
##### buttonBar
To show or hide the button bar, or any of the buttons inside it. Defaults to the uiDatetimePickerConfig.
Only specify the elements that you want to override, as each button defaults to the uiDatetimePickerConfig setup, if it is not configured on scope of the datetimePicker
##### initialPicker
The initial picker to open when the control is first pressed
##### reOpenDefault
The picker to set as the picker to open once the control has already been opened at least once. Setting to `false` will default to the date picker if both date and time are enabled, or just the enabled control if only time or date is in use.
##### datepickerOptions
Object to configure settings for the datepicker (can be found on [angularUI site](https://angular-ui.github.io/bootstrap/#/datepicker))
##### timepickerOptions
Object to configure settings for the timepicker (can be found on [angularUI site](https://angular-ui.github.io/bootstrap/#/timepicker))
##### defaultTime
Initial time when a new date is selected (e.g. "14:00:00" or "2:00 pm")
##### whenClosed
An callback function to call when the picker dropdown is closed. See demo for more details.
##### saveAs
A boolean value to switch saving the Date to the model as a string, or a ngModel.$parsers function to take over the transformation from the Date object to a string.
Possible values:
* true
* false
* 'ISO' (Date.toISOString())
* 'json' (Date.toJSON())
* 'number' (Date.valueOf())
* a function accepting a value parameter and returning the converted value to save to the model.
Note: If using an html5 input type, the default parser will use Date.toLocaleString() to convert to a string.  To override this, provide a function with your desired formatted conversion.  Otherwise all other input types will use the supplied date format.
##### readAs
A boolean value to convert a string (or Date.valueOf()) value back to a Date object from the ngModel, or a ngModel.$formatters function to take over the transformation completely.

## uiDatetimePickerConfig
Now datetimePicker options are globally set by default.  If you do not state the values within the declaration, the config options are used instead.  Here are the default options

```
.constant('uiDatetimePickerConfig', {
        dateFormat: 'yyyy-MM-dd HH:mm',
        defaultTime: '00:00:00',
        html5Types: {
            date: 'yyyy-MM-dd',
            'datetime-local': 'yyyy-MM-ddTHH:mm:ss.sss',
            'month': 'yyyy-MM'
        },
        initialPicker: 'date',
        reOpenDefault: false,
        enableDate: true,
        enableTime: true,
        buttonBar: {
            show: true,
            now: {
                show: true,
                text: 'Now'
            },
            today: {
                show: true,
                text: 'Today'
            },
            clear: {
                show: true,
                text: 'Clear'
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
                text: 'Close'
            }
        },
        closeOnDateSelection: true,
        closeOnTimeNow: true,
        appendToBody: false,
        altInputFormats: [],
        ngModelOptions: { },
        saveAs: false,
        readAs: false,
    })
```

## Css
Personally i dont like the look of the angular-ui calendar itself, this is because the buttons are configured to use the btn-default style.  To get round this there are 3 css classes applied to the datetimepicker and depending on the picker that is being shown.  These classes surround the div element that contains the angular-ui datepicker and timepicker.  Using these classes you can change the style of the calendar.  The class are

```
.datetime-picker-dropdown
```

Applied to the dropdown that the pickers are contained within

```
.datetime-picker-dropdown > li.date-picker-menu
```
Applied when the date picker is visible

```
.datetime-picker-dropdown > li.time-picker-menu
```
Applied when the time picker is visible

###### EXAMPLE
For example, if i add this css code, you will see the difference to the calendar in the images below

```
.datetime-picker-dropdown > li.date-picker-menu div > table .btn-default {
    border: 0;
}
```
###### BEFORE
![alt tag](http://imageshack.com/a/img633/6894/9Dt0Le.gif)
###### AFTER
![alt tag](http://imageshack.com/a/img673/5236/to31hz.gif)


## Example
Here is an example to use the directive with a bootstrap input, displaying a calendar button

####HTML
```
<p class="input-group">
    <input type="text" class="form-control" datetime-picker="dd MMM yyyy HH:mm" ng-model="myDate" is-open="isOpen"  />
    <span class="input-group-btn">
        <button type="button" class="btn btn-default" ng-click="openCalendar($event, prop)"><i class="fa fa-calendar"></i></button>
    </span>
</p>
```
####JAVASCRIPT

```
app.controller('MyController', function() {
    var that = this;

    this.isOpen = false;

    this.openCalendar = function(e) {
        e.preventDefault();
        e.stopPropagation();

        that.isOpen = true;
    };
});
```

## Bug
If you do find a bug, can you please create a plunkr to replicate the error before raising an issue.  Attach the plunkr as a link to the issue so i can replicate the error and work out a solution. 

## Pull Requests
If you submit a PR, please test your changes against the demo page to make sure no functionality has been broken. 

## Support
This was developed using angular-ui bootstrap Version: 1.2.0 - 2016-04-07.  If you have a bug, please check what version of angular-ui you are using.  If you are using a version prior to this, then please upgrade if you can and try it. If the problem persists, please let me know.  I do have a day job but will try to get back to you asap.  If you can fix the bug, then let me know how, or even better, submit a pull request.
