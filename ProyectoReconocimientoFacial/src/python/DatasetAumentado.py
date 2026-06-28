import os
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.preprocessing.image import load_img
from tensorflow.keras.preprocessing.image import img_to_array
from tensorflow.keras.preprocessing.image import save_img

dataset_path = "dataset_procesado"
output_path = "dataset_aumentado"

datagen = ImageDataGenerator(
    rotation_range=35,
    brightness_range=(0.7,1.3),
    horizontal_flip=True,
    zoom_range=0.15,
    width_shift_range=0.05,
    height_shift_range=0.05
)

os.makedirs(output_path, exist_ok=True)

for persona in os.listdir(dataset_path):
    ruta_persona = os.path.join(dataset_path, persona)
    ruta_salida = os.path.join(output_path, persona)
    os.makedirs(ruta_salida, exist_ok=True)

    contador = 0

    for archivo in os.listdir(ruta_persona):
        ruta_imagen = os.path.join(ruta_persona, archivo)
        img = load_img(ruta_imagen, target_size=(160,160))

        x = img_to_array(img)
        x = x.reshape((1,) + x.shape)

        i = 0

        for batch in datagen.flow(x, batch_size=1):
            nombre = f"aum_{contador}.jpg"
            save_img(os.path.join(ruta_salida, nombre), batch[0])

            i += 1
            contador += 1

            if i >= 3:  # crea 3 versiones nuevas por imagen
                break

print("Dataset aumentado listo")