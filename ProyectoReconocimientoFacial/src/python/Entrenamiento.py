import os
import numpy as np
import torch
import pickle

from PIL import Image

from facenet_pytorch import InceptionResnetV1

from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout
from tensorflow.keras.utils import to_categorical

from sklearn.preprocessing import LabelEncoder
from sklearn.model_selection import train_test_split

# Carpeta con las imagenes 
dataset_path = "dataset_aumentado"

facenet = InceptionResnetV1(pretrained='vggface2').eval()

X = []
y = []

print("Creando embeddings")

for persona in os.listdir(dataset_path):
    rutaPersona = os.path.join(dataset_path, persona)
    
    if not os.path.isdir(rutaPersona):
        continue
    
    for archivo in os.listdir(rutaPersona):
        rutaImagen = os.path.join(rutaPersona, archivo)
        # Abrir y redimensionar (FaceNet suele usar 160x160)
        img = Image.open(rutaImagen).convert('RGB')
        img = np.array(img)
        img = torch.tensor(img).permute(2,0,1).float()
        img = img.unsqueeze(0)
        embedding = facenet(img)
        
        X.append(embedding.detach().numpy()[0])
        y.append(persona)

X = np.array(X)
y = np.array(y)

print('Total de imágenes: ', len(X))

encoder = LabelEncoder()
y_num = encoder.fit_transform(y)

labels_dict = dict(zip(range(len(encoder.classes_)), encoder.classes_))

pickle.dump(labels_dict, open("labels.pkl", "wb"))

y_cat = to_categorical(y_num)

# dividir datos
X_train, X_test, y_train, y_test = train_test_split(
    X,
    y_cat,
    test_size=0.2,
    random_state=42
)

print("Entrenando modelo...")

model = Sequential()

model.add(Dense(256, activation='relu', input_shape=(512,)))
model.add(Dropout(0.4))

model.add(Dense(128, activation='relu'))
model.add(Dropout(0.3))

model.add(Dense(len(labels_dict), activation='softmax'))

model.compile(
    optimizer='adam',
    loss='categorical_crossentropy',
    metrics=['accuracy']
)

model.fit(
    X_train,
    y_train,
    epochs=15,
    batch_size=32,
    validation_data=(X_test, y_test)
)


model.save("face_classifier.h5")

print("Modelo listo")