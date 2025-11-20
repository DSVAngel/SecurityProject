import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Historial() {
    const [historial, setHistorial] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchHistorial = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/clasificaciones');
                setHistorial(response.data);
            } catch (error) {
                console.error('Error al cargar historial:', error);
            }
        };
        fetchHistorial();
    }, []);

    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <div style={styles.header}>
                    <h2 style={styles.title}>Historial de Clasificaciones</h2>
                    <button
                        style={styles.addButton}
                        onClick={() => navigate('/clasificacion')}
                    >
                        + Nueva Clasificación
                    </button>
                </div>

                <div style={styles.tableWrapper}>
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                <th style={styles.th}>ID</th>
                                <th style={styles.th}>Fecha</th>
                                <th style={styles.th}>Categoría</th>
                                <th style={styles.th}>Confianza</th>
                            </tr>
                        </thead>
                        <tbody>
                            {historial.map((h, index) => (
                                <tr
                                    key={h.id}
                                    style={{
                                        backgroundColor: index % 2 === 0 ? '#fafafa' : '#ffffff',
                                        transition: 'background-color 0.2s',
                                        cursor: 'pointer',
                                    }}
                                    onMouseEnter={(e) =>
                                        (e.currentTarget.style.backgroundColor = '#eaf3ff')
                                    }
                                    onMouseLeave={(e) =>
                                        (e.currentTarget.style.backgroundColor =
                                            index % 2 === 0 ? '#fafafa' : '#ffffff')
                                    }
                                >
                                    <td style={styles.td}>{h.id}</td>
                                    <td style={styles.td}>{h.fecha}</td>
                                    <td style={{ ...styles.td, ...styles.badge(h.categoria) }}>
                                        {h.categoria}
                                    </td>
                                    <td style={styles.td}>{h.confianza}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}
const styles = {
    container: {
        padding: '50px',
        background: '#f5f5f5',
        minHeight: '100vh',
        display: 'flex',
        justifyContent: 'center',
    },
    card: {
        background: 'white',
        padding: '30px',
        borderRadius: '12px',
        boxShadow: '0 4px 20px rgba(0,0,0,0.08)',
        width: '100%',
        maxWidth: '900px',
    },
    header: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: '25px',
    },
    title: {
        color: '#333',
        margin: 0,
    },
    addButton: {
        backgroundColor: '#007bff',
        color: 'white',
        border: 'none',
        padding: '10px 18px',
        borderRadius: '6px',
        cursor: 'pointer',
        fontSize: '15px',
        fontWeight: '500',
        transition: 'background-color 0.2s',
    },
    addButtonHover: {
        backgroundColor: '#0056b3',
    },
    tableWrapper: {
        overflowX: 'auto',
        borderRadius: '8px',
    },
    table: {
        width: '100%',
        borderCollapse: 'collapse',
        fontSize: '15px',
    },
    th: {
        backgroundColor: '#007bff',
        color: 'white',
        textAlign: 'left',
        padding: '12px',
        fontWeight: '600',
    },
    td: {
        padding: '12px',
        borderBottom: '1px solid #ddd',
    },
    badge: (categoria) => {
        let color = '#999';
        if (categoria === 'Positivo') color = '#28a745';
        if (categoria === 'Negativo') color = '#dc3545';
        if (categoria === 'Neutro') color = '#ffc107';
        return {
            color,
            fontWeight: 'bold',
        };
    },
};

export default Historial;
