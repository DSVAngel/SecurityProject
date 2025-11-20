import re
import pandas as pd
from fastapi import FastAPI
from pydantic import BaseModel
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
import os

# ============================
# ENTRENAMIENTO AL INICIAR API
# ============================

def limpiar_texto(texto):
    """Limpia el texto, lo convierte a minúsculas y elimina caracteres no alfabéticos."""
    texto = texto.lower()
    texto = re.sub(r'[^a-zA-Z\s]', '', texto)
    texto = re.sub(r'\s+', ' ', texto).strip()
    return texto

try:
    df = pd.read_csv("spam.csv", encoding="latin-1")
    df = df[['v1', 'v2']]
    df.columns = ['etiqueta', 'mensaje']
except FileNotFoundError:
    print("ERROR: No se encontró 'spam.csv'. La API no se inicializará correctamente.")
    df = pd.DataFrame({
        'etiqueta': ['ham', 'spam', 'ham', 'spam'],
        'mensaje': ['Hey, what\'s up?', 'WINNER! Text YES to 87021.', 'See you at 5.', 'Free entry to competition!']
    })
    
df['etiqueta'] = df['etiqueta'].map({'ham': 0, 'spam': 1})
df['mensaje_limpio'] = df['mensaje'].apply(limpiar_texto)

if len(df) > 0:
    vectorizador = TfidfVectorizer(stop_words='english', max_features=1000)
    X = vectorizador.fit_transform(df['mensaje_limpio'])
    y = df['etiqueta']

    modelo_nb = MultinomialNB()
    modelo_nb.fit(X, y)
    print("Modelo entrenado exitosamente.")
else:
    vectorizador = None
    modelo_nb = None
    print("ERROR: El modelo no se pudo entrenar debido a la falta de datos.")


# ============================
# API
# ============================

app = FastAPI()

class BodyMensaje(BaseModel):
    """Modelo Pydantic para la estructura de la solicitud POST."""
    texto: str

@app.post("/clasificar")
def clasificar(body: BodyMensaje):
    """
    Clasifica un mensaje de texto como SPAM o HAM y devuelve la confianza.
    """
    if modelo_nb is None or vectorizador is None:
         return {"error": "El modelo no está disponible. Falló el entrenamiento."}

    limpio = limpiar_texto(body.texto)
    vector = vectorizador.transform([limpio])
    
    probabilidades = modelo_nb.predict_proba(vector)[0]
    
    pred_index = modelo_nb.predict(vector)[0]
    
    etiqueta = "SPAM" if pred_index == 1 else "HAM"
    
    confianza = probabilidades[pred_index]
    
    confianza_redondeada = round(confianza, 4)
    
    return {
        "etiqueta": etiqueta,
        "confianza": confianza_redondeada
    }
