<script setup>
import { ref, onMounted } from 'vue'

const userType = ref('')
const nombre = ref('')

onMounted(() => {
  const user = JSON.parse(localStorage.getItem('user'))
  userType.value = user?.name || ''
})
</script>

<template>
  <div class="home-container">
    <header>
      <div class="welcome-box">
        <h1>
          Bienvenido <span v-if="userType">({{ userType }})</span>
        </h1>
        <p v-if="nombre">Nombre: {{ nombre }}</p>
      </div>
    </header>
    <main>
      <div class="button-group">
        <button v-if="userType === 'ADMIN'" @click="$router.push('/crear-mascota')">
          Crear mascota
        </button>
        <button v-if="userType === 'CLIENT' || userType === 'ADMIN'" @click="$router.push('/mascotas')">
          Ver mascotas
        </button>
      </div>
    </main>
  </div>
</template>

<style scoped>
.home-container {
  min-height: 100vh;
  background: transparent;
  display: flex;
  flex-direction: column;
  align-items: center;
}

header {
  margin-top: 2rem;
  text-align: center;
  color: #222;
}

h1,
p,
span {
  color: #222;
}

main {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.button-group {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.button-group button {
  padding: 1rem 2rem;
  font-size: 1.2rem;
  border: none;
  border-radius: 8px;
  background: #4caf50;
  color: white;
  cursor: pointer;
  transition: background 0.2s;
}

.button-group button:hover {
  background: #388e3c;
}

.welcome-box {
  background: #fff;
  border: 1px solid #ccc;
  border-radius: 12px;
  padding: 2rem 3rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.07);
  display: inline-block;
  margin-bottom: 2rem;
}
</style>
