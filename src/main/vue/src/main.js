import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'
import store from './store'
import './plugins'
import VueLayers from 'vuelayers'
import 'vuelayers/lib/style.css' // needs css-loader
import '@mdi/font/css/materialdesignicons.css' // Ensure you are using css-loader
import Vuetify from 'vuetify/lib'
import VueFlashMessage from '@smartweb/vue-flash-message';
import axios from 'axios'
import VueAxios from 'vue-axios'
import { LMap, LTileLayer, LMarker } from 'vue2-leaflet';
import 'leaflet/dist/leaflet.css';
import { Icon } from 'leaflet';

delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

Vue.component('l-map', LMap);
Vue.component('l-tile-layer', LTileLayer);
Vue.component('l-marker', LMarker);

Vue.config.productionTip = false;
Vue.prototype.$axios = axios;

Vue.use(Vuetify)
Vue.use(VueFlashMessage);
Vue.use(VueAxios, axios)

export default new Vuetify({
  icons: {
    iconfont: 'mdi', // default - only for display purposes
  },
})
Vue.use(VueLayers)


Vue.config.productionTip = false

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App),
}).$mount('#app')
