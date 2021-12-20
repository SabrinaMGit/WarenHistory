module.exports = {
  preset: '@vue/cli-plugin-unit-jest',

  setupFiles: [
    '<rootDir>/tests/index.js',
  ],
  moduleFileExtensions: [
    "js",
    "json",
    // tell Jest to handle `*.vue` files
    "vue"
  ],
  transform: {
    // process `*.vue` files with `vue-jest`
    ".*\\.(vue)$": "vue-jest"
  }
}
