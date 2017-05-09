'use strict';

var notify = require("gulp-notify");
var argv = require('yargs').argv;

module.exports = function() {

    var args = Array.prototype.slice.call(arguments);
    var notification = argv.notification === undefined ? true : argv.notification;
    // Send error to notification center with gulp-notify
    if(notification) {
        notify.onError({
            title:    "JHipster Gulp Build",
            subtitle: "Failure!",
            message:  "Error: <%= error.message %>",
            sound:    "Beep"
        }).apply(this, args);
    }
    // Keep gulp from hanging on this task
    this.emit('end');

};
