import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function OTP() {
  const [otp, setOtp] = useState(['', '', '', '', '', '']);
  const navigate = useNavigate();

  const handleChange = (element, index) => {
    if (isNaN(element.value)) return;

    setOtp([...otp.map((d, idx) => (idx === index ? element.value : d))]);

    // Mover al siguiente input
    if (element.value !== '' && element.nextSibling) {
      element.nextSibling.focus();
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const otpCode = otp.join('');
    console.log('OTP:', otpCode);
    // Aquí irá tu lógica de verificación OTP
    alert('OTP verificado: ' + otpCode);
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2>Verificación OTP</h2>
        <p style={styles.subtitle}>
          Ingresa el código de 6 dígitos enviado a tu correo
        </p>
        <form onSubmit={handleSubmit} style={styles.form}>
          <div style={styles.otpContainer}>
            {otp.map((data, index) => {
              return (
                <input
                  key={index}
                  type="text"
                  maxLength="1"
                  value={data}
                  onChange={(e) => handleChange(e.target, index)}
                  onFocus={(e) => e.target.select()}
                  style={styles.otpInput}
                />
              );
            })}
          </div>
          <button type="submit" style={styles.button}>
            Verificar
          </button>
        </form>
        <p style={styles.link}>
          <span style={styles.linkText}>Reenviar código</span>
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
  subtitle: {
    textAlign: 'center',
    color: '#666',
    marginBottom: '30px',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '20px',
  },
  otpContainer: {
    display: 'flex',
    justifyContent: 'space-between',
    gap: '10px',
  },
  otpInput: {
    width: '50px',
    height: '50px',
    fontSize: '24px',
    textAlign: 'center',
    borderRadius: '4px',
    border: '2px solid #ddd',
  },
  button: {
    padding: '12px',
    fontSize: '16px',
    backgroundColor: '#007bff',
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

export default OTP;