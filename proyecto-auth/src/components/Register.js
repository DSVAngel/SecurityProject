import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Register() {
  const [formData, setFormData] = useState({
    name: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: ''
});

  const navigate = useNavigate();

  const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      alert('Las contraseñas no coinciden');
      return;
    }

    try {
      // Llamada al backend Spring Boot
      const response = await axios.post(`${API_URL}/auth/register`, {
        name: formData.name,
        lastName: formData.lastName,
        email: formData.email,
        password: formData.password,
      });

      console.log('Usuario registrado:', response.data);

      alert('Registro exitoso. Se ha enviado un código OTP a tu correo.');
      navigate('/otp');
    } catch (error) {
      console.error('Error en el registro:', error);
      alert(
        error.response?.data?.message ||
        'Error al registrar el usuario. Intenta nuevamente.'
      );
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2>Registro</h2>
        <form onSubmit={handleSubmit} style={styles.form}>
          <input
            type="text"
            name="name"
            placeholder="Nombre(s)"
            value={formData.nombre}
            onChange={handleChange}
            style={styles.input}
            required
          />
          <input
            type="text"
            name="lastName"
            placeholder="Apellidos"
            value={formData.apellidos}
            onChange={handleChange}
            style={styles.input}
            required
          />
          <input
            type="email"
            name="email"
            placeholder="Correo electrónico"
            value={formData.correo}
            onChange={handleChange}
            style={styles.input}
            required
          />
          <input
            type="password"
            name="password"
            placeholder="Contraseña"
            value={formData.password}
            onChange={handleChange}
            style={styles.input}
            required
          />
          <input
            type="password"
            name="confirmPassword"
            placeholder="Confirmar contraseña"
            value={formData.confirmPassword}
            onChange={handleChange}
            style={styles.input}
            required
          />
          <button type="submit" style={styles.button}>
            Registrarse
          </button>
        </form>
        <p style={styles.link}>
          ¿Ya tienes cuenta?{' '}
          <span onClick={() => navigate('/login')} style={styles.linkText}>
            Inicia sesión
          </span>
        </p>
      </div>
    </div>
  );
}

const styles = {
  container: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    minHeight: '100vh',
    backgroundColor: '#f5f5f5',
  },
  card: {
    backgroundColor: 'white',
    padding: '40px',
    borderRadius: '8px',
    boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
    width: '100%',
    maxWidth: '400px',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '15px',
  },
  input: {
    padding: '12px',
    fontSize: '16px',
    borderRadius: '4px',
    border: '1px solid #ddd',
  },
  button: {
    padding: '12px',
    fontSize: '16px',
    backgroundColor: '#28a745',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  },
  link: {
    marginTop: '20px',
    textAlign: 'center',
  },
  linkText: {
    color: '#007bff',
    cursor: 'pointer',
    textDecoration: 'underline',
  },
};

export default Register;
