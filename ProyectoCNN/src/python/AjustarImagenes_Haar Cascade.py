import os
import cv2

dataset_original = "dataset"
dataset_procesado = "dataset_procesado"

face_cascade = cv2.CascadeClassifier(
    cv2.data.haarcascades + 'haarcascade_frontalface_default.xml'
)

tamano = 160

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

        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

        faces = face_cascade.detectMultiScale(gray, 1.3, 5)

        for (x, y, w, h) in faces:

            size = max(w, h)

            rostro = img[y:y+size, x:x+size]

            rostro = cv2.resize(rostro, (tamano, tamano))

            nombre_archivo = f"rostro_{contador}.jpg"

            cv2.imwrite(os.path.join(ruta_salida, nombre_archivo), rostro)

            contador += 1

    print("guardadas:", contador)

print("Dataset procesado listo")