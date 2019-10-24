#! /usr/bin/env python

"""Prints copyright text of program

Copyright:
    CopyRight.py  print copyright text from variable
    Copyright (C) 2017  Alex Hyer

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
"""

import argparse

__author__ = 'Alex Hyer'
__email__ = 'theonehyer@gmail.com'
__license__ = 'GPLv3'
__maintainer__ = 'Alex Hyer'
__status__ = 'Production/Stable'
__version__ = '1.0.3'


class CopyRight(argparse.Action):
    """Argparse Action that prints a program copyright and exits program

    Note: It does not escape my notice that this action prints arbitrary text
    without any sort of "copyright-specific" attributes or mangling. This
    function is only called this for readability in code.

    Example:
        .. code-block:: Python

            >>> parser = argparse.ArgumentParser()
            >>> parser.add_argument('test',
            ...                     action=CopyRight,
            ...                     copyright_text='test')
            'test'
    """

    def __init__(self, option_strings, dest, copyright_text=None, nargs=None,
                 **kwargs):
        """Initialize class and spawn self as Base Class w/o nargs

        Args:
            option_strings (list): list of str giving command line flags that
                                   call this action

            dest (str): namespace reference to value

            copyright_text (str): str to print

            nargs (str): number of args as special char or int

            **kwargs (various): optional arguments to pass to super call
        """

        # Only accept a single value to analyze
        if nargs is not None:
            raise ValueError('nargs not allowed for CopyRight')

        self.copyright = copyright_text

        # Call self again but without nargs
        super(CopyRight, self).__init__(option_strings, dest, nargs=0,
                                        **kwargs)

    def __call__(self, parser, namespace, value, option_string=None):
        """Prints the given text stripped of excess whitespace and exits

        Args:
            parser (ArgumentParser): parser used to generate values

            namespace (Namespace): namespace to set values for

            value (str): actual value specified by user

            option_string (str): argument flag used to call this function

        Raises:
            TypeError: if value is not a str

            ValueError: if value cannot, for any reason, be parsed
                        by commas
        """

        print(self.copyright.strip())
        parser.exit()
