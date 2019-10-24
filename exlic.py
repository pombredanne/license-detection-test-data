# -*- coding: utf-8 -*-
#
# Copyright (c)  nexB Inc. and others. All rights reserved.

# You may not use this software except in compliance with the License.
# You may obtain a copy of the License at: http://apache.org/licenses/LICENSE-2.0
# Unless required by applicable law or agreed to in writing, software distributed
# under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
# CONDITIONS OF ANY KIND, either express or implied. See the License for the
# specific language governing permissions and limitations under the License.
#
#  ScanCode is a free software code scanning tool from nexB Inc. and others.
#  Visit https://github.com/nexB/scancode-toolkit/ for support and download.

from __future__ import absolute_import
from __future__ import unicode_literals
from __future__ import print_function

import hashlib
import json
import pickle
from os import path

import click
click.disable_unicode_literals_warning = True

from commoncode import fileutils


@click.command()

@click.argument('license_pickles', metavar='FILE', nargs=-1,
    # ensure that the input path is bytes on Linux, unicode elsewhere
    type=click.Path(exists=True, readable=True))

@click.option('--output-dir',
    type=str, default='licenses',
    metavar='DIR',
    help='Set the output directory')

@click.help_option('-h', '--help')

def cli(license_pickles, output_dir='licenses'):
    """
    Extract unique GH data license pickles to plain files.
    """
    seen = set()

    tot_lic_saved = 0
    tot_lic_skipped = 0

    for license_pickle in license_pickles:
        _, _, language = license_pickle.rpartition('/')
        language, _, _, = language.partition('_')
        language_dir = path.join(output_dir, language)

        with open(license_pickle, 'rb') as lp:
            licenses_by_repo = pickle.load(lp)
        licenses_by_repo = json.dumps(licenses_by_repo, ensure_ascii=True)
        licenses_by_repo = json.loads(licenses_by_repo)

        lic_saved = 0
        lic_skipped = 0
        for repo_licenses in licenses_by_repo.values():
            for repo_path, text in repo_licenses:
                try:
                    encoded = text.encode('utf-8', 'surrogateescape')
                    checksum = hashlib.sha1(encoded).hexdigest()
                    if checksum in seen:
                        # print('skipping:', language, repo_path)
                        lic_skipped += 1
                        continue
                    seen.add(checksum)
                    fdir, _, fname = repo_path.rpartition('/')
                    fdir = path.join(language_dir, fdir)
                    fileutils.create_dir(fdir)
                    floc = path.join(fdir, fname)
                    # print('saving:', floc)
                    # assert not path.exists(floc), floc
                    with open(floc, 'wb') as o:
                        o.write(encoded)
                    lic_saved += 1

                except:
                    print(type(text))
                    raise
        print('For:', language, 'saved:', lic_saved, 'skipped:', lic_skipped)
        tot_lic_saved += lic_saved
        tot_lic_skipped += lic_skipped

    print('Total:', 'saved:', tot_lic_saved, 'skipped:', tot_lic_skipped)


if __name__ == '__main__':
    cli()
