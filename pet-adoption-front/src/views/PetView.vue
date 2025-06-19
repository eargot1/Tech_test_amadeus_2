<template>
  <div class="pet-view">
    <div class="pet-list">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Especie</th>
            <th>Acción</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="pet in pets" :key="pet.id">
            <td>{{ pet.id }}</td>
            <td>{{ pet.name }}</td>
            <td>{{ pet.species }}</td>
            <td>
              <button @click="selectPet(pet.id)">Ver</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="pet-detail" v-if="selectedPet">
      <div class="pet-box">
        <h2>{{ selectedPet.name }}</h2>
        <p><strong>ID:</strong> {{ selectedPet.id }}</p>
        <p><strong>Nombre:</strong> {{ selectedPet.name }}</p>
        <p><strong>Edad:</strong> {{ selectedPet.age }}</p>
        <p><strong>Especie:</strong> {{ selectedPet.species }}</p>
        <p><strong>Estado:</strong> {{ selectedPet.status }}</p>
        <p><strong>Fecha de Ingreso:</strong> {{ selectedPet.entryDate }}</p>

        <div>
          <div v-if="userType === 'ADMIN'">
            <button @click="deletePet(selectedPet.id)">Eliminar</button>
            <div style="margin-top: 1rem">
              <select v-model="newStatus">
                <option value="IN_SHELTER">En refugio</option>
                <option value="IN_ADOPTION_PROCESS">En proceso de adopción</option>
                <option value="ADOPTED">Adoptado</option>
              </select>
              <button @click="updateStatus(selectedPet.id)">Actualizar estado</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import PetService from '@/services/pet.service.js'

const pets = ref([])
const selectedPet = ref(null)
const userType = ref('')
const newStatus = ref('IN_SHELTER')

onMounted(() => {
  const user = JSON.parse(localStorage.getItem('user'))
  userType.value = user?.name || ''
  console.log('userType cargado:', userType.value)
})

onMounted(async () => {
  pets.value = await PetService.getAllPets()
})

const selectPet = async (id) => {
  console.log('el id es: ' + id)
  selectedPet.value = await PetService.getPet(id)
  console.log('el selectedPet es: ' + selectedPet)
}

const deletePet = async (id) => {
  const confirmed = window.confirm('¿Estás seguro de que deseas eliminar esta mascota?')
  if (!confirmed) return

  await PetService.deletePet(id)
  alert('Mascota eliminada')
}

const updateStatus = async (id) => {
  console.log('Nuevo estado:', newStatus.value)
  await PetService.updatePet(id, newStatus.value)
  alert(`Estado actualizado a ${newStatus.value}`)
}
</script>

<style scoped>
.pet-view {
  display: flex;
  gap: 2rem;
  min-height: 80vh;
  padding: 2rem;
  background: #f5f5f5;
}

.pet-list {
  flex: 1;
}

.pet-detail {
  flex: 1;
  display: flex;
  align-items: flex-start;
  justify-content: center;
}

.pet-box {
  background: #fff;
  border: 1px solid #ccc;
  border-radius: 12px;
  padding: 2rem 3rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.07);
  min-width: 300px;
}

table {
  width: 100%;
  border-collapse: collapse;
  background: #fff;
}

th,
td {
  border: 1px solid #ddd;
  padding: 0.75rem;
  text-align: left;
}

th {
  background: #e0e0e0;
}

button {
  background: #4caf50;
  color: #fff;
  border: none;
  border-radius: 6px;
  padding: 0.5rem 1rem;
  cursor: pointer;
}

button:hover {
  background: #388e3c;
}
</style>
