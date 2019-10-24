#!/usr/bin/env node

const {promisify} = require('util');
const fs = require('fs');
const path = require('path');
const glob = promisify(require('glob'));

const readFile = promisify(fs.readFile);
const writeFile = promisify(fs.writeFile);
const extension = process.argv[3];
const root = path.join(__dirname, '..');
const patterns = process.argv.slice(4).map(folder => `${folder}/**/*.${extension}`);
const copyrightRegex = /^\/\*\*\n \* Copyright([^/]|[^*]\/)*?\*\//;
const copyright =`
/**
 * Copyright (c) 2017-present A. Matías Quezada <amatiasq@gmail.com>
 */
`.trim()

console.log(`Checking patterns ${patterns.join('\n')}`);
main()


async function main() {
  const nested = await Promise.all(patterns.map(pattern => glob(pattern)));
  const files = [].concat(...nested);

  return await Promise.all(files.map(checkFile));
}


async function checkFile(file) {
  const buffer = await readFile(file);
  const content = buffer.toString();
  const header = content.match(copyrightRegex);

  if (header && header[0] === copyright)
    return;

  const newContent = header ?
    content.replace(copyrightRegex, copyright) :
    `${copyright}\n${content}`

  console.log(`Updating ${file}`);
  return await writeFile(file, newContent);
}