import React from 'react';
import { useNavigate } from 'react-router-dom';

function Home() {
    const navigate = useNavigate();

    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <h2>Bienvenido </h2>
                <p>Selecciona una acción para continuar:</p>

                <div style={styles.buttons}>
                    <button onClick={() => navigate('/clasificacion')} style={styles.button}>
                        Nueva Clasificación
                    </button>
                    <button onClick={() => navigate('/historial')} style={styles.button}>
                        Ver Historial
                    </button>
                    <button
                        onClick={() => {
                            localStorage.removeItem('user');
                            navigate('/login');
                        }}
                        style={styles.logout}
                    >
                        Cerrar Sesión
                    </button>
                </div>
            </div>
        </div>
    );
}

const styles = {
    container: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        height: '100vh',
        background: '#f0f2f5',
    },
    card: {
        background: 'white',
        padding: '40px',
        borderRadius: '8px',
        boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
        textAlign: 'center',
    },
    buttons: {
        display: 'flex',
        flexDirection: 'column',
        gap: '10px',
        marginTop: '20px',
    },
    button: {
        background: '#007bff',
        color: 'white',
        border: 'none',
        padding: '12px',
        borderRadius: '6px',
        cursor: 'pointer',
    },
    logout: {
        background: '#dc3545',
        color: 'white',
        border: 'none',
        padding: '12px',
        borderRadius: '6px',
        cursor: 'pointer',
        marginTop: '10px',
    },
};

export default Home;
