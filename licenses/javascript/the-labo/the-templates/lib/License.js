/**
 * Define a bud for LICENSE
 * @memberof module:ape-tmpl/lib
 * @function licenseBud
 * @param {object} config - Configuration.
 * @param {string} config.type - Type of license.
 * @param {number} config.year - Copy right year.
 * @param {string} config.holder - License holder name.
 * @returns {object} - Bud object.
 */

'use strict'

const assert = require('assert')
const _tmpl = require('./_tmpl')

/** @lends licenseBud */
function licenseBud (config) {
  assert.ok(config.type, 'config.type is required.')
  assert.ok(config.holder, 'config.holder is required.')
  return {
    force: true,
    mode: '444',
    path: 'LICENSE',
    tmpl: _tmplForType(String(config.type).trim()),
    data: {
      holder: config.holder,
      year: config.year || new Date().getFullYear()
    }
  }
}

function _tmplForType (type) {
  switch (type) {
    case 'mit':
    case 'MIT':
      return _tmpl('LICENSE_MIT.hbs')
    case 'Apache-2.0':
    case 'Apache2':
    case 'APACHE-2.0':
    case 'APACHE2':
      return _tmpl('LICENSE_Apache-2.0.hbs')
    default:
      throw new Error(`Unknown license type: ${type}`)
  }
}

module.exports = licenseBud
