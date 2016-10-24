'use strict';

var gulp = require('gulp'),
    rev = require('gulp-rev'),
    plumber = require('gulp-plumber'),
    es = require('event-stream'),
    flatten = require('gulp-flatten'),
    replace = require('gulp-replace'),
    bowerFiles = require('main-bower-files'),
    changed = require('gulp-changed');

var handleErrors = require('./handle-errors');
var config = require('./config');

module.exports = {
    fonts: fonts,
    common: common,
    swagger: swagger,
    images: images
}

function fonts() {
    return es.merge(gulp.src(config.bower + 'bootstrap/fonts/*.*')
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(changed(config.dist + 'content/fonts/'))
        .pipe(rev())
        .pipe(gulp.dest(config.dist + 'content/fonts/'))
        .pipe(rev.manifest(config.revManifest, {
            base: config.dist,
            merge: true
        }))
        .pipe(gulp.dest(config.dist)),
        gulp.src(config.app + 'content/**/*.{woff,woff2,svg,ttf,eot,otf}')
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(changed(config.dist + 'content/fonts/'))
        .pipe(flatten())
        .pipe(rev())
        .pipe(gulp.dest(config.dist + 'content/fonts/'))
        .pipe(rev.manifest(config.revManifest, {
            base: config.dist,
            merge: true
        }))
        .pipe(gulp.dest(config.dist))
    );
}

function common() {
    return gulp.src([config.app + 'robots.txt', config.app + 'favicon.ico', config.app + '.htaccess'], { dot: true })
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(changed(config.dist))
        .pipe(gulp.dest(config.dist));
}

function swagger() {
    return es.merge(
        gulp.src([config.bower + 'swagger-ui/dist/**',
             '!' + config.bower + 'swagger-ui/dist/index.html',
             '!' + config.bower + 'swagger-ui/dist/swagger-ui.min.js',
             '!' + config.bower + 'swagger-ui/dist/swagger-ui.js'])
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(changed(config.swaggerDist))
            .pipe(gulp.dest(config.swaggerDist)),
        gulp.src(config.app + 'swagger-ui/index.html')
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(changed(config.swaggerDist))
            .pipe(replace('../bower_components/swagger-ui/dist/', ''))
            .pipe(replace('swagger-ui.js', 'lib/swagger-ui.min.js'))
            .pipe(gulp.dest(config.swaggerDist)),
        gulp.src(config.bower  + 'swagger-ui/dist/swagger-ui.min.js')
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(changed(config.swaggerDist + 'lib/'))
            .pipe(gulp.dest(config.swaggerDist + 'lib/'))
    );
}

function images() {
    return gulp.src(bowerFiles({filter: ['**/*.{gif,jpg,png}']}), { base: config.bower })
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(changed(config.dist +  'bower_components'))
        .pipe(gulp.dest(config.dist +  'bower_components'));
}
