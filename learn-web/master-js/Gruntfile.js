var curChapter = '.';

module.exports = function(grunt){
    grunt.initConfig({
        jshint:{
            all:[curChapter+'/src/**/*.js'],
            options:{
                globals:{
                    _:false,
                    $:false,
                    jasmine: false,
                    it:false,
                    expect:false,
                    beforeEach:false,
                    afterEach:false,
                    sinon:false
                },
                browser:true,
                devel:true
            }
        },
        testem:{
            unit:{
                options:{
                    framework:'jasmine2',
                    launch_in_dev:['PhantomJS'],
                    //before_tests:'grunt jshint',
                    serve_files:[
                        curChapter+'/src/**/*.js',
                        curChapter+'/test/**/*.js',
                        'node_modules/lodash/index.js',
                        'node_modules/jquery/dist/jquery.js',
                        'node_modules/sinon/pkg/sinon.js'
                    ],
                    watch_files:[
                        curChapter+'/src/**/*.js',
                        curChapter+'/test/**/*.js'
                    ]
                }
            }
        }
    });

    //grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-testem');

    grunt.registerTask('default',['testem:run:unit']);
};