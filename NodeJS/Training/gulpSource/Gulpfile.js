/**
 * Created by pthiru on 2/9/2016.
 */


var gulp = require('gulp');

var nodemon = require('gulp-nodemon');

gulp.task('default', function () {
    nodemon({
        script: 'app.js',
        ext: 'js',
        ignore: ['./node_modules/**']
    }).on('restart', function () {
        console.log('Restarting..');
    })
});


/*var gulp = require('gulp');
 var sass = require('gulp-sass');
 var sourcemaps = require('gulp-sourcemaps');

 gulp.task('sass', function () {
 return gulp.src('./sass/!**!/!*.scss')
 .pipe(sourcemaps.init())
 .pipe(sass().on('error', sass.logError))
 .pipe(sourcemaps.write('./maps'))
 .pipe(gulp.dest('./css'));
 });

 gulp.task('watch', function () {
 gulp.watch('./sass/!**!/!*.scss', ['sass']);
 });

 gulp.task('default', ['sass', 'watch']);*/
