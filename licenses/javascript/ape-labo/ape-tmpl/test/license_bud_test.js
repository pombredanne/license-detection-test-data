/**
 * Test case for licenseBud.
 * Runs with nodeunit.
 */
'use strict'

/* global describe, before, after, it */

const licenseBud = require('../lib/license_bud.js')
const path = require('path')
const coz = require('coz')

const assert = require('assert')
const mkdirp = require('mkdirp')

describe('license_bud', () => {
  let basedir = path.resolve(__dirname, '..')
  let tmpDir = path.resolve(basedir, 'tmp/readme_md_bud_test/pkg-foo')

  before(async () => {
    mkdirp.sync(tmpDir)
  })

  it('License bud', async () => {
    const bud = licenseBud({
      type: 'MIT',
      holder: 'me'
    })
    assert.ok(bud)
    bud.path = tmpDir + '/LICENSE'
    await coz.render(bud, {
      cwd: tmpDir
    })
  })
})
