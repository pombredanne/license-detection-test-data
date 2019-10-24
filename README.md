# license-detection-test-data
A set of license-related files to use as a test suite for license scanners.
See /licenses directory

This is based on pickled data found in these downloads provided by Github as part of https://github.com/github/CodeSearchNet :

 - https://s3.amazonaws.com/code-search-net/CodeSearchNet/v2/python.zip
 - https://s3.amazonaws.com/code-search-net/CodeSearchNet/v2/javascript.zip
 - https://s3.amazonaws.com/code-search-net/CodeSearchNet/v2/java.zip
 - https://s3.amazonaws.com/code-search-net/CodeSearchNet/v2/ruby.zip
 - https://s3.amazonaws.com/code-search-net/CodeSearchNet/v2/go.zip

https://github.com/github/CodeSearchNet/blob/e792e1caea20fbd4fba439565fe20c10d4798435/function_parser/function_parser/fetch_licenses.py is the upstream script that was used to collect these.

The script exlic.py was usd to recreate the files from the pickles. Some binary files were
removed as they had been damaged by the upstream process.
