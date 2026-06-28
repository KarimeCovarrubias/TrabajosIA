# PROGRAMA PARA AJUSTAR TODAS LAS IMAGENES DEL DATASET A 160x160

import os
import cv2
import numpy as np
from facenet_pytorch import MTCNN

print("Librerías cargadas correctamente. Iniciando búsqueda de carpetas...")

dataset_original = "dataset_original"
dataset_procesado = "dataset_procesado"

os.makedirs(dataset_procesado, exist_ok=True)

mtcnn = MTCNN(image_size=160, margin=20, post_process=False)

for persona in os.listdir(dataset_original):
    
    ruta_persona = os.path.join(dataset_original, persona)
    ruta_salida = os.path.join(dataset_procesado, persona)

    if not os.path.isdir(ruta_persona):
        continue

    os.makedirs(ruta_salida, exist_ok=True)

    print("Procesando:", persona)

    contador = 0

    for archivo in os.listdir(ruta_persona):

        ruta_imagen = os.path.join(ruta_persona, archivo)

        img = cv2.imread(ruta_imagen)

        if img is None:
            continue

        img_rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

        rostro = mtcnn(img_rgb)

        if rostro is not None:

            rostro = rostro.permute(1, 2, 0).numpy()

            # asegurar rango correcto
            rostro = np.clip(rostro, 0, 255).astype("uint8")

            nombre_archivo = f"rostro_{contador}.jpg"

            cv2.imwrite(
                os.path.join(ruta_salida, nombre_archivo),
                cv2.cvtColor(rostro, cv2.COLOR_RGB2BGR)
            )

            contador += 1

    print("guardadas:", contador)

print("Dataset procesado listo")