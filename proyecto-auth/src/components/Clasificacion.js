import React, { useState } from 'react';
import axios from 'axios';

function Clasificacion() {
    const [texto, setTexto] = useState('');
    const [loading, setLoading] = useState(false);
    const [result, setResult] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!texto.trim()) {
            console.warn('Por favor ingresa un texto antes de clasificar.');
            return;
        }

        setLoading(true);
        setResult(null);

        try {
            await new Promise((resolve) => setTimeout(resolve, 2500));

            const response = await axios.post('http://localhost:8080/api/spam/clasificar', { texto });
            
            setResult(response.data);
        } catch (error) {
            console.error('Error al clasificar:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={styles.container}>
            <style>{`
                @keyframes load {
                    /* Movimiento de la barra de izquierda a derecha */
                    0% { transform: translateX(-100%); }
                    100% { transform: translateX(100%); }
                }
            `}</style>

            <div style={styles.card}>
                <h2 className="text-2xl font-semibold mb-4 text-gray-700">Clasificación de Texto</h2>

                <form onSubmit={handleSubmit} style={styles.form}>
                    <textarea
                        value={texto}
                        onChange={(e) => setTexto(e.target.value)}
                        placeholder="Escribe aquí el texto a clasificar..."
                        rows="6"
                        style={styles.textarea}
                    />

                    <button type="submit" style={styles.button} disabled={loading}>
                        {loading ? 'Clasificando...' : 'Clasificar'}
                    </button>

                    {loading && (
                        <div style={styles.progressContainer}>
                            {/* La animación 'load' definida arriba ahora funciona aquí */}
                            <div style={styles.progressBar}></div>
                        </div>
                    )}
                </form>

                {result && (
                    <div style={styles.result}>
                        <h3>Resultado</h3>
                        <p><strong>Categoría:</strong> {result.categoria}</p>
                        <p><strong>Confianza:</strong> {result.confianza}</p>
                    </div>
                )}
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
        maxWidth: '600px',
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
        gap: '15px',
    },
    textarea: {
        border: '1px solid #ddd',
        padding: '12px',
        borderRadius: '4px',
        resize: 'none',
        fontSize: '15px',
    },
    button: {
        padding: '12px',
        fontSize: '16px',
        backgroundColor: '#007bff',
        color: 'white',
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
        transition: 'background-color 0.3s',
        opacity: 0.8,
    },
    progressContainer: {
        width: '100%',
        height: '8px',
        background: '#eee',
        borderRadius: '4px',
        overflow: 'hidden',
    },
    progressBar: {
        width: '100%',
        height: '100%',
        background: '#007bff',
        animation: 'load 2s linear infinite',
        transform: 'translateX(-100%)', 
    },
    result: {
        marginTop: '20px',
        background: '#f8f9fa',
        padding: '15px',
        borderRadius: '6px',
    },
};

export default Clasificacion;