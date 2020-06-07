import Vue from 'vue'
import Router from 'vue-router'
import index from '@/components/index'
import aa from '@/components/aa'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      component: index
    },
    {
      path: '/aa',
      name: 'aa',
      component: aa
    }
  ]
})
