// @flow

const path = require('path');
const fs = require('fs');
const debug = require('debug')('embelish:license');
const formatter = require('formatter');
const { ContentGenerator } = require('./content');
const { Package } = require('./package');
const { isFilePresent, readFileContent } = require('./file-tools');

const REGEX_LICENSE = /^licen(c|s)e$/i;

async function insertLicense(ast, packageData /*: Package */, basePath /*: string */) {
  const licenseHeaderIndex = ast.findIndex((item) => {
    return item.type === 'heading' && REGEX_LICENSE.test(item.raw);
  });

  const licenseContent = await generateLicense(packageData, basePath);
  if (licenseContent) {
    debug(`license header index = ${licenseHeaderIndex}`);
    if (licenseHeaderIndex >= 0) {
      ast.splice(licenseHeaderIndex + 1, ast.length, ContentGenerator.paragraph(licenseContent));
    }

    // update the license file
    await updateLicenseFile(basePath, licenseContent);
  }
}

async function generateLicense(packageData /*: Package */, basePath /*: string */) {
  const embellishOverrides = packageData.embellish || {};
  const licenseHolder = embellishOverrides.licenseHolder || packageData.author;
  if (packageData.license && licenseHolder) {
    const licenseName = packageData.license.toLowerCase();
    const licenseTemplateFile = path.resolve(__dirname, '..', 'licenses', `${licenseName}.txt`);
    const haveLicenseTemplate = await isFilePresent(licenseTemplateFile);
    if (!haveLicenseTemplate) {
      throw new Error(`License template for ${packageData.license} not found.  Consider submitting an embellish-readme PR`);
    }

    const templateContent = haveLicenseTemplate ? await readFileContent(licenseTemplateFile) : '';
    const template = formatter(templateContent);

    return template({
      year: new Date().getFullYear(),
      holder: licenseHolder,
    });
  }
}

function updateLicenseFile(basePath /*: string */, content /*: string */) /*: Promise<void> */ {
  return new Promise((resolve, reject) => {
    fs.writeFile(path.resolve(basePath, 'LICENSE'), content, 'utf-8', (err) => {
      if (err) {
        return reject(err);
      }

      resolve();
    });
  });
}

module.exports = {
  insertLicense
};
