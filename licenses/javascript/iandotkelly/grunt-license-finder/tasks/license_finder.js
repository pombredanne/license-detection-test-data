/*
 * grunt-license-finder
 * https://github.com/iandotkelly/grunt-license-finder
 *
 * Copyright (c) 2014 Ian Kelly
 * Licensed under the MIT license.
 */

'use strict';

var nlf = require('nlf');
var fs = require('fs');

module.exports = function(grunt) {

	grunt.registerMultiTask('license_finder', 'Finds licenses in a node project and its dependencies', function() {

		var options = this.options({
			production: false,			// whether to include production dependencies only
			directory: process.cwd(),	// the directory to scan - default to cwd
			out: './licenses.txt',		// the ouput filename
			csv: false,					// whether to output in csv format
			depth: undefined			// the maximum depth to go to
		});

		var done = this.async();

		nlf.find({
			directory: options.directory,
			production: options.production,
			depth: options.depth
		}, function (err, data) {

			if (err) {
				grunt.log.error('Failure to retrieve license information from nlf');
				grunt.log.error(err);
				return done(false);
			}

			grunt.log.writeln('Retrieved license information');

			// format the output
			var formatter = options.csv ? nlf.csvFormatter : nlf.standardFormatter;

			formatter.render(data, options, function (err, output) {
				if (err) {
					grunt.log.error('Failure to format license information');
					grunt.log.error(err);
					return done(false);
				}

				fs.writeFile(options.out, output, function (err) {
					if (err) {
						grunt.log.error('Failure to write to file: ' + options.out);
						grunt.log.error(err);
						return done(false);
					}

					grunt.log.writeln('License information written to: ' + options.out);
					done();
				});
			});
		});
	});
};
