const {spawn} = require('child_process')

exports.install = function add (licenseType, {name, cwd, operation} = {}) {
  return new Promise((resolve, reject) => {
    const args = ['install', licenseType]
    const cliPath = operation.pathToNodeBinFile('license-generator')

    if (name) {
      args.push('-n')
      args.push(name)
    }

    const license = spawn(cliPath, args, {cwd})

    license.stdout.on('data', (data) => {
      const message = data.toString()

      if (message.includes('Error')) {
        reject(new Error(`Error generating ${licenseType} license > ${message}`))
      }
    })

    license.on('error', (error) => {
      reject(error)
    })

    license.on('close', (code) => {
      if (code === 1) {
        process.exit(1)
      }
      else {
        resolve()
      }
    })
  })
}
