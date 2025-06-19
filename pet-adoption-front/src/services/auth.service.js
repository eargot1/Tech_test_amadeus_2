import axios from 'axios'

const API_URL = 'http://localhost:8080/auth/login'

class AuthService {
  async login(email, password) {
    const response = await axios.post(API_URL, {
      email: email,
      password: password,
    })
    if (response.data.token) {
      localStorage.setItem('user', JSON.stringify(response.data))
    }
    return response.data
  }
}

export default new AuthService()
