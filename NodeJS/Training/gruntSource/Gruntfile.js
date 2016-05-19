/**
 * Created by pthiru on 2/9/2016.
 */

module.exports = function (grunt) {
    grunt.initConfig({
            uglify: {
                dist: {
                    src: ['FileOne.js', 'FileTwo.js'],
                    dest: 'Minified/Output.min.js'
                }
            }
        }
    );
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.registerTask('default', ['uglify']);

    /*grunt.initConfig({
     sass: {
     dist: {
     files: [{
     cwd: 'app/styles',
     src: '**!/!*.scss',
     dest: '../.tmp/styles',
     expand: true,
     ext: '.css'
     }]
     }
     },
     autoprefixer: {
     options: ['last 1 version'],
     dist: {
     files: [{
     expand: true,
     cwd: '.tmp/styles',
     src: '{,*!/}*.css',
     dest: 'dist/styles'
     }]
     }
     },
     watch: {
     styles: {
     files: ['app/styles/{,*!/}*.scss'],
     tasks: ['sass:dist', 'autoprefixer:dist']
     }
     }
     });
     grunt.loadNpmTasks('grunt-contrib-watch');
     grunt.registerTask('default', ['watch']);*/
};