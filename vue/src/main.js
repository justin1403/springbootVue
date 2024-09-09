import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI  from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/gloable.css';
import request from "@/utils/request";
import locale from 'element-ui/lib/locale/lang/zh-TW'
import store from "@/store";
// import VueI18n from 'vue-i18n'




Vue.config.productionTip = false
// 可加上 size 值可 mini 或 small
Vue.use(ElementUI,{size:"mini",locale});
// Vue.use(VueI18n)

Vue.prototype.request = request

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')

