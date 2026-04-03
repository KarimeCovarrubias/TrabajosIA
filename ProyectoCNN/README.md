# Instala las dependencias
```sh
pip install -r requisitos.txt
```

## Para poblar el dataset, el nombre de la persona se cambia en el código, también cuántas imágenes se quieren capturar
```sh
python3 src/rostro.py
```

# Para ajustar (procesar) las imágenes del dataset original
```sh
python3 src/AjustarImagenes.py
```

# Para aumentar las imágenes del dataset procesado
```sh
python3 src/DatasetAumentado.py
```

## Entrenar el modelo
```sh
python3 src/Entrenamiento.py
```

# Guardar requisitos
```sh
pip freeze > requisitos.txt
```

# Ejecutar el proyecto
```sh
pip src/ReconocimientoFacial.py
```

# Modelo entrenado
face_classifier.h5
labels.pkl
# face_classifier.h5 es la red neuronal que aprendió a distinguir las caras
# labels.pkl guarda los nombre de las personas (trabaja con números)

# Ver integrantes del equipo
integrantes.txt