import axios from 'axios'

const API_URL = 'http://localhost:8080/pets'

class PetService {
  async createPet(pet) {
    const user = JSON.parse(localStorage.getItem('user'))
    const token = user?.token || ''
    return axios.post(API_URL, pet, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
  }

  async getPet(id) {
    const user = JSON.parse(localStorage.getItem('user'))
    const token = user?.token || ''
    const response = await axios.get(`${API_URL}/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    return response.data
  }

  async getAllPets() {
    const user = JSON.parse(localStorage.getItem('user'))
    const token = user?.token || ''
    const response = await axios.get(API_URL, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    return response.data
  }

  async updatePet(id, newStatus) {
    console.log('id: ' + id + ' newStatus: ' + newStatus)
    const updatePet = { id: id, new_status: newStatus }
    console.log('updatePet: ' + updatePet)
    const user = JSON.parse(localStorage.getItem('user'))
    const token = user?.token || ''
    return await axios.put(`${API_URL}/status`, updatePet, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
  }

  async deletePet(id) {
    const user = JSON.parse(localStorage.getItem('user'))
    const token = user?.token || ''
    return await axios.delete(`${API_URL}/delete/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
  }
}

export default new PetService()
