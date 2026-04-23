import tensorflow as tf
import numpy as np

# Datos de entrenamiento
celsius = np.array([-40, -10, 0, 8, 15, 22, 38], dtype=float)
fahrenheit = np.array([-40, 14, 32, 46, 59, 72, 100], dtype=float)

# Capa densa con 1 sola neurona
capa = tf.keras.layers.Dense(units=1, input_shape=[1])
modelo = tf.keras.models.Sequential([capa])

# Compilación
modelo.compile(optimizer=tf.keras.optimizers.Adam(0.1), loss='mean_squared_error')

# Entrenamiento
modelo.fit(celsius, fahrenheit, epochs=500, verbose=False)

# Obtener los valores internos de la capa
pesos = capa.get_weights()

# pesos[0] es la matriz de pesos (W)
# pesos[1] es el arreglo de sesgos (b)
print(f"Variables internas del modelo:")
print(f"Peso (Weight): {pesos[0][0][0]}")
print(f"Sesgo (Bias): {pesos[1][0]}")