import matplotlib.pyplot as plt
from tensorflow.keras.preprocessing import image

# ---------------------------------------
# Se agregó lo siguiente
import numpy as np
import tensorflow as tf
import os

# Define las clases manualmente (deben estar en el mismo orden que las carpetas)
class_names = ['ben_afflek', 'elton_john', 'jerry_seinfeld', 'madonna', 'mindy_kaling']

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
# ---------------------------------------

def predict_celebrity(img_path, model_path=os.path.join(BASE_DIR, 'celebrity_model.h5')):
    # Load model and image
    model = tf.keras.models.load_model(model_path)
    img = image.load_img(img_path, target_size=(160, 160))
    
    # Pre-process
    img_array = image.img_to_array(img) / 255.0
    img_array = np.expand_dims(img_array, axis=0) # Create batch axis

    # Predict
    predictions = model.predict(img_array)
    score = np.max(predictions)
    class_idx = np.argmax(predictions)
    
    # Display
    plt.imshow(img)
    plt.title(f"Prediction: {class_names[class_idx]} ({100 * score:.2f}%)")
    plt.axis('off')
    plt.show()

# Usage:
predict_celebrity(os.path.join(BASE_DIR, 'data', 'val', 'madonna', 'madonna1.jpg'))