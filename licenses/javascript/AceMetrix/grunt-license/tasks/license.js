/*
 * grunt-license
 * https://github.com/badunk/grunt-license
 *
 * Copyright (c) 2013 Duncan Wong <baduncaduncan@gmail.com>
 * Licensed under the MIT license.
 */

'use strict';
var treeify = require('treeify');
var checker = require('license-checker');
var fs = require('fs');
var colors = require('colors');

module.exports = function(grunt) {

    // Please see the Grunt documentation for more information regarding task
    // creation: http://gruntjs.com/creating-tasks

    grunt.registerMultiTask('license', 'Generates list of licenses for your project', function() {
        var done = this.async();
        var defaults = {
            start: '.',
            unknown: false,
            depth: 1,
            include: 'all',
            output: 'LICENSES'
        };
        var options = grunt.util._.extend(defaults, this.data);
        checker.init(options, function(data){
            if (options.output) {
                fs.writeFileSync(options.output, JSON.stringify(data, null, 4));
                console.log('Successfully written '.green + options.output.grey);
            }else{
                console.log(treeify.asTree(data, true));   
            }
            done();
        });
    });

};
