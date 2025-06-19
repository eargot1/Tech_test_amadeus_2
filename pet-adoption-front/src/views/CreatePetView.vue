<template>
  <div class="create-pet-container">
    <form @submit.prevent="handleSubmit" class="create-pet-form">
      <h2>Crear Mascota</h2>
      <div class="form-group">
        <label for="nombre">Nombre:</label>
        <input id="nombre" v-model="nombre" type="text" required />
      </div>
      <div class="form-group">
        <label for="edad">Edad:</label>
        <select id="edad" v-model="edad" required>
          <option disabled value="">Seleccione edad</option>
          <option v-for="n in 20" :key="n" :value="n">{{ n }} año(s)</option>
        </select>
      </div>
      <div class="form-group">
        <label for="especie">Especie:</label>
        <select id="especie" v-model="especie" required>
          <option disabled value="">Seleccione especie</option>
          <option value="DOG">Perro</option>
          <option value="CAT">Gato</option>
        </select>
      </div>
      <button type="submit">Crear</button>
    </form>
    <p v-if="error" class="error-message">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import PetService from '@/services/pet.service.js'

const nombre = ref('')
const edad = ref('')
const especie = ref('')

const handleSubmit = async () => {
  const pet = {
    name: nombre.value,
    age: edad.value,
    species: especie.value,
  }
  try {
    await PetService.createPet(pet)
    alert('Mascota creada con éxito')
     nombre.value = ''
     edad.value = ''
     especie.value = ''
  } catch (error) {
    alert('Error al crear la mascota')
  }
}
</script>

<style scoped>
.crear-mascota-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
}

.crear-mascota-form {
  background: #fff;
  padding: 2rem 3rem;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.07);
  display: flex;
  flex-direction: column;
  gap: 1rem;
  min-width: 320px;
}

.form-group label {
  color: #222;
  margin-bottom: 0.3rem;
  display: block;
}

input,
select {
  width: 100%;
  padding: 0.5rem;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 1rem;
}

button[type=\"submit\"] {
  background: #4caf50;
  color: #fff;
  border: none;
  border-radius: 6px;
  padding: 0.75rem;
  font-size: 1.1rem;
  cursor: pointer;
  margin-top: 1rem;
}

button[type=\"submit\"]:hover {
  background: #388e3c;
}
</style>
