# frozen_string_literal: true

# Copyright (C) 2017-2018 Dimitri Arrigoni <dimitri@arrigoni.me>
# License GPLv3+: GNU GPL version 3 or later
# <http://www.gnu.org/licenses/gpl.html>.
# This is free software: you are free to change and redistribute it.
# There is NO WARRANTY, to the extent permitted by law.

writer = tools.fetch(:gemspec_writer)

desc 'Apply license on (ruby) source files'
task 'sources:license', [:output] => [writer.to_s] do |task, args|
  output  = args[:output] ? Pathname.new(args[:output]) : nil
  builder = tools.fetch(:gemspec_builder)

  begin
    tools.fetch(:licenser).process do |process|
      process.output  = output if output
      process.license = project.version.license_header
      process.files   = builder.source_files.select do |file|
        # @todo use a better ruby files recognition
        # @todo implement better exclusion
        if file.extname.gsub(/^\./, '') == 'rb'
          file.basename.to_s != 'gems.rb'
        else
          false
        end
      end
    end
  rescue SystemExit, Interrupt
    exit(Errno::ECANCELED::Errno)
  rescue Errno::EPIPE => e
    exit(e.class.const_get(:Errno))
  end
end
