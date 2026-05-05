// js/auth.js
const API_BASE_URL = 'http://localhost:8080/api/auth';

document.addEventListener('DOMContentLoaded', () => {
    
    // Handle Login
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const identifier = document.getElementById('identifier').value;
            const password = document.getElementById('password').value;
            const messageBox = document.getElementById('messageBox');

            try {
                const response = await fetch(`${API_BASE_URL}/login`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ identifier, password })
                });

                if (response.ok) {
                    const data = await response.json();
                    messageBox.innerHTML = '<span class="success-text">Login successful! Redirecting...</span>';
                    // Store user data in localStorage (for demo purposes)
                    localStorage.setItem('user', JSON.stringify(data));
                    
                    setTimeout(() => {
                        window.location.href = 'user-dashboard.html';
                    }, 1500);
                } else {
                    const errorData = await response.json();
                    messageBox.innerHTML = `<span class="error-text">${errorData.error || 'Login failed'}</span>`;
                }
            } catch (error) {
                messageBox.innerHTML = '<span class="error-text">Cannot connect to server.</span>';
            }
        });
    }

    // Handle Registration
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const messageBox = document.getElementById('messageBox');
            
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (password !== confirmPassword) {
                messageBox.innerHTML = '<span class="error-text">Passwords do not match.</span>';
                return;
            }

            const role = document.getElementById('role').value;
            const endpoint = role === 'ADMIN' ? `${API_BASE_URL}/register-admin` : `${API_BASE_URL}/register`;

            const payload = {
                firstName: document.getElementById('firstName').value,
                lastName: document.getElementById('lastName').value,
                username: document.getElementById('username').value,
                email: document.getElementById('email').value,
                password: password,
                phoneNumber: document.getElementById('phoneNumber').value,
                address: document.getElementById('address').value
            };

            try {
                const response = await fetch(endpoint, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });

                if (response.ok) {
                    messageBox.innerHTML = '<span class="success-text">Registration successful! Please login.</span>';
                    setTimeout(() => {
                        window.location.href = 'login.html';
                    }, 2000);
                } else {
                    const errorData = await response.json();
                    let errorMsg = errorData.error || 'Registration failed';
                    // Check for validation errors object
                    if(!errorData.error && Object.keys(errorData).length > 0) {
                        errorMsg = Object.values(errorData)[0];
                    }
                    messageBox.innerHTML = `<span class="error-text">${errorMsg}</span>`;
                }
            } catch (error) {
                messageBox.innerHTML = '<span class="error-text">Cannot connect to server.</span>';
            }
        });
    }
});
