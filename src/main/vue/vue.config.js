const path = require("path");
module.exports = {
  pages: {
    index: {
      entry: "src/main.js",
      title: "Intelli-Pack Application"
    }
  },
  transpileDependencies: [
    'vuetify',
  ],
  devServer: {
    port: 10000,
    proxy: {
      "/api": {
        target: "http://localhost:8080"
      },
      "/data": {
        target: "http://localhost:8080"
      },
      "/login": {
        target: "http://localhost:8080"
      },
      "/fe":{
        target: "http://localhost:8080"
      }
    }
  },
  outputDir: path.resolve(__dirname, "../resources/public"),
  indexPath: 'index.html'
};

