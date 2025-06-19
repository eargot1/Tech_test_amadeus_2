<template>
  <div class="login-container">
    <form @submit.prevent="handleLogin" class="login-form">
      <h2>Iniciar Sesión</h2>
      <div class="form-group">
        <label for="email">Email:</label>
        <input
          type="text"
          id="email"
          v-model="email"
          required
          placeholder="Ingrese su email"
        />
      </div>
      <div class="form-group">
        <label for="password">Contraseña:</label>
        <input
          type="text"
          id="password"
          v-model="password"
          required
          placeholder="Ingrese su contraseña"
        />
      </div>
      <button type="submit" class="login-button">Iniciar Sesión</button>
      <p v-if="error" class="error-message">{{ error }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import AuthService from '@/services/auth.service'

const email = ref('')
const password = ref('')
const error = ref('')

const handleLogin = async () => {
  error.value = ''
  try {
    await AuthService.login(email.value, password.value)
    window.location.href = '/'
  } catch (err) {
    error.value = err.response?.data?.message || 'Error al iniciar sesión'
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: transparent;
}

.login-form {
  width: 100%;
  max-width: 400px;
  min-width: 300px;
  box-sizing: border-box;
  background: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: #333;
}

input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.login-button {
  width: 100%;
  padding: 0.75rem;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 1rem;
}

.login-button:hover {
  background-color: #45a049;
}

.error-message {
  color: #45a049;
  font-size: 1rem;
  margin-top: 1rem;
}
</style>


