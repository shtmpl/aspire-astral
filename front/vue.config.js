module.exports = {
  devServer: {
    port: 3142,
    proxy: {
      'api/*': {
        target: 'http://localhost:8080'
      }
    }
  },
  outputDir: 'target/dist' // Change build paths to make them Maven compatible
};
