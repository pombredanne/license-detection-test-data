/**
 * Test case for license.
 * Runs with mocha.
 */
'use strict'

const License = require('../lib/License.js')
const assert = require('assert')
const coz = require('coz')

describe('license', function () {
  this.timeout(3000)

  before(async () => {

  })

  after(async () => {

  })

  it('License', async () => {
    const bud = License({
      type: 'MIT',
      holder:'hoge'
    })
    bud.mkdirp = true
    bud.path = `${__dirname}/../tmp/licence/MIT.md`
    coz.render(bud)
  })
})

/* global describe, before, after, it */
