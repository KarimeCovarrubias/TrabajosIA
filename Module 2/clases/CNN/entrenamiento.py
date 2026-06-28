import tensorflow as tf
from tensorflow.keras import layers, models
from tensorflow.keras.preprocessing.image import ImageDataGenerator
import numpy as np
import os

# Config
IMG_SIZE = (160, 160)
BATCH_SIZE = 32

# -------------------------------------------------------------------------------------------
# Se agregó lo siguiente
# Obtiene la ruta de la carpeta donde está este script
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
# -------------------------------------------------------------------------------------------

# 1. Load Data with Augmentation
train_datagen = ImageDataGenerator(rescale=1./255, rotation_range=20, horizontal_flip=True)
val_datagen = ImageDataGenerator(rescale=1./255)

# Une la ruta base con la carpeta de datos
train_path = os.path.join(BASE_DIR, 'data', 'train')
val_path = os.path.join(BASE_DIR, 'data', 'val')

# ----------------------------------------------------------------------------------------------------------------------------------------
# Se modificó:
# train_gen = train_datagen.flow_from_directory('data/train', target_size=IMG_SIZE, batch_size=BATCH_SIZE, class_mode='sparse')
# val_gen = val_datagen.flow_from_directory('data/val', target_size=IMG_SIZE, batch_size=BATCH_SIZE, class_mode='sparse')

# Luego úsalas en el generador
train_gen = train_datagen.flow_from_directory(train_path, target_size=IMG_SIZE, batch_size=BATCH_SIZE, class_mode='sparse')
val_gen = val_datagen.flow_from_directory(val_path, target_size=IMG_SIZE, batch_size=BATCH_SIZE, class_mode='sparse')
# ----------------------------------------------------------------------------------------------------------------------------------------

# 2. Build Model (Transfer Learning)
base_model = tf.keras.applications.MobileNetV2(input_shape=(160, 160, 3), include_top=False, weights='imagenet')
base_model.trainable = False 

model = models.Sequential([
    base_model,
    layers.GlobalAveragePooling2D(),
    layers.Dense(len(train_gen.class_indices), activation='softmax')
])

model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])

# 3. Train and Save
model.fit(train_gen, validation_data=val_gen, epochs=5)
# Se modificó la ruta para guardar el archivo .h5 en la misma carpeta (CNN)
model_path = os.path.join(BASE_DIR, 'celebrity_model.h5')
model.save(model_path)

# Store class names for prediction
class_names = list(train_gen.class_indices.keys())
print("Classes identified:", class_names)