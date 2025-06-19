import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import CreatePetView from '../views/CreatePetView.vue'
import PetView from '../views/PetView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/crear-mascota',
      name: 'create-pet',
      component: CreatePetView,
    },
    {
      path: '/mascotas',
      name: 'get-pet',
      component: PetView,
    },
  ],
})

router.beforeEach((to, from, next) => {
  const user = localStorage.getItem('user')

  if (!user && to.path !== '/login') {
    next('/login')
  } else {
    next()
  }
})

export default router
