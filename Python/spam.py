import pandas as pd
import matplotlib.pyplot as plt
from collections import Counter
import numpy as np
from sklearn.model_selection import train_test_split


data_path = "./spam.csv"
df = pd.read_csv(
    "spam.csv",
    encoding="latin-1"
)

df = df[['v1', 'v2']]
df.columns = ['etiqueta', 'mensaje']
df.head()
df.info()

print("////////////////////////////////")

conteo_etiquetas = df['etiqueta'].value_counts()
print(conteo_etiquetas)

print("////////////////////////////////")
conteo_etiquetas.plot(kind='bar',color=['green','red'])
plt.title("Conteo de mensajes por tipo")
plt.xlabel("Etiquetas")
plt.ylabel("Numero de mensajes")
plt.show()
plt.savefig("grafica.png")

print(df.describe())

print("////////////////////////////////")

df['etiqueta'] = df['etiqueta'].map({'ham': 0, 'spam': 1})
print(df['etiqueta'].value_counts())


print("////////////////////////////////")

import re

def limpiar_texto(texto):
    texto = texto.lower()                         
    texto = re.sub(r'[^a-zA-Z\s]', '', texto)     
    texto = re.sub(r'\s+', ' ', texto).strip()    
    return texto


df['mensaje_limpio'] = df['mensaje'].apply(limpiar_texto)
df.head()

print("////////////////////////////////")


df['longitud'] = df['mensaje_limpio'].apply(lambda x: len(x.split()))

print("=== Longitud de los mensajes (primeros 10) ===")
print(df[['mensaje_limpio', 'longitud']].head(10))

print("////////////////////////////////")

ham = df[df['etiqueta'] == 0]['mensaje_limpio']
spam = df[df['etiqueta'] == 1]['mensaje_limpio']


print("////////////////////////////////")

def palabras_mas_comunes(serie_mensajes, n=20):
    todas = " ".join(serie_mensajes).split()
    contador = Counter(todas)
    return contador.most_common(n)


top_ham = palabras_mas_comunes(ham, 20)
top_spam = palabras_mas_comunes(spam, 20)

print("\n=== Palabras más comunes en HAM ===")
print(pd.DataFrame(top_ham, columns=["palabra", "frecuencia"]))

print("\n=== Palabras más comunes en SPAM ===")
print(pd.DataFrame(top_spam, columns=["palabra", "frecuencia"]))


from sklearn.feature_extraction.text import CountVectorizer, TfidfVectorizer
vectorizador = TfidfVectorizer(stop_words='english', max_features=1000)

X_texto = vectorizador.fit_transform(df['mensaje_limpio'])

print("Matriz de texto (TF-IDF)", X_texto.shape)


X_longitud = df[['longitud']].values.reshape(-1, 1)

from scipy.sparse import hstack

X = hstack([X_texto, X_longitud])

y= df['etiqueta']


from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

print("Tamaño de X_train:", X_train.shape)
print("Tamaño de X_test:", X_test.shape)



from sklearn.naive_bayes  import MultinomialNB
from sklearn.linear_model import LogisticRegression
from sklearn.svm import LinearSVC

from sklearn.metrics import accuracy_score, classification_report, confusion_matrix


modelo_nb = MultinomialNB()
modelo_nb.fit(X_train, y_train)
y_pred_nb = modelo_nb.predict(X_test)

print("\n=== Resultados Naive Bayes ===")
print("Accuracy:", accuracy_score(y_test, y_pred_nb))
print("Classification Report:\n", classification_report(y_test, y_pred_nb))

modelo_lr = LogisticRegression(max_iter=1000)
modelo_lr.fit(X_train, y_train)
X_pred_lr = modelo_lr.predict(X_test)

print("\n=== Resultados Logistic Regression ===")
print("Accuracy:", accuracy_score(y_test, X_pred_lr))
print("Classification Report:\n", classification_report(y_test, X_pred_lr))


modelo_svm = LinearSVC()
modelo_svm.fit(X_train, y_train)
y_pred_svm = modelo_svm.predict(X_test)

print("\n=== Resultados SVM ===")
print("Accuracy:", accuracy_score(y_test, y_pred_svm))
print("Classification Report:\n", classification_report(y_test, y_pred_svm))

resultados = pd.DataFrame({
    'Modelos': ['Naive Bayes', 'Logistic Regression', 'SVM'],
    'Accuracy': [ 
        accuracy_score(y_test, y_pred_nb), 
        accuracy_score(y_test, X_pred_lr), 
        accuracy_score(y_test, y_pred_svm)
    ]
})

plt.figure(figsize=(8, 5))
plt.bar(resultados['Modelos'], resultados['Accuracy'], color=['skyblue', 'lightgreen', 'salmon'])
plt.title("Comparación de Modelos de Clasificación")
plt.xlabel("Modelos")
plt.ylabel("Accuracy")
plt.ylim(0.9, 1)

# IMPORTANTE: guardar ANTES del show
plt.savefig("comparacion_modelos.png", dpi=300, bbox_inches="tight")
plt.show()

###### Modulo de inicio, Clasificacion, Historial de clasificaciones
## Clasificacion formulario para poder hacer la clasificacion
## Barra de carga o barra de analisis
## Tabla de las clasificaciones y un boton 
