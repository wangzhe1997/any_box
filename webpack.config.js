//webpack.config.js

const {resolve} = require('path')

module.exports = {
  // ...
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  }
}