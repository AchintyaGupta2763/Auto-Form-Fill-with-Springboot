import axios from 'axios';

const API_BASE_URL = "http://localhost:8080/api/auth";

export function signupUser(signupData) {
    // POST /api/auth/signup
    return axios.post(`${API_BASE_URL}/signup`, signupData)
        .then(response => response)
        .catch(error => {
            console.error("There was an error during signup:", error.response?.data);
            throw error; // rethrow to handle in the UI
        });
}

export function loginUser(loginData) {
    // POST /api/auth/login
    return axios.post(`${API_BASE_URL}/login`, loginData)
        .then(response => response.data)
        .catch(error => {
            console.error("There was an error during login:", error);
            throw error; // rethrow to handle in the UI
        });
}