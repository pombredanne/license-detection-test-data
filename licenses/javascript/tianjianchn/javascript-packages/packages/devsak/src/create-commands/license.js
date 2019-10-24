

const path = require('path');
const fs = require('fs');
const util = require('../util');
const readlineSync = require('readline-sync');

const pkgPath = path.resolve(process.cwd(), 'package.json');
const licensePath = path.resolve(process.cwd(), 'LICENSE');
const defaultLicences = {
  MIT: path.resolve(__dirname, '../../resources/default-MIT-LICENSE'),
};

exports.command = 'license';
exports.describe = 'Create license file';

exports.builder = function builder(yargs) {
  return yargs
    .options({
      type: {
        alias: 't',
        demand: true,
        default: 'MIT',
        choices: ['MIT'],
        type: 'string',
      },
    });
};

exports.handler = function handler(argv) {
  const { type } = argv;

  const exists = util.fileExists(licensePath);
  if (exists) {
    if (!readlineSync.keyInYN('license exists. Overwrite?')) return;
  }

  let content = fs.readFileSync(defaultLicences[type], { encoding: 'utf8' });
  content = content.replace('[YEAR]', new Date().getFullYear());

  const pkgInfo = require(pkgPath);// eslint-disable-line import/no-dynamic-require

  let email,
    author;
  if (!pkgInfo.author) {
    while (!(author = readlineSync.question('Your name (Author)?')));// eslint-disable-line no-cond-assign
    email = readlineSync.questionEMail('Your email?');
  } else {
    author = pkgInfo.author.name;
    email = pkgInfo.author.email;
  }
  content = content.replace('[AUTHOR]', author);
  content = content.replace('[EMAIL]', email);

  fs.writeFileSync(licensePath, content);
  console.log('Create license successfully!');
};

