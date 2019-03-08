import Vue from 'vue'

import store from './store/index'
import App from './component/App.vue'

Vue.config.productionTip = false

new Vue({
  store,
  render: h => h(App)
}).$mount('#root')
