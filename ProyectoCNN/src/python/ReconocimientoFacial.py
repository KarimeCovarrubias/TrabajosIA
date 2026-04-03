import cv2
import torch
import numpy as np
import pickle
from facenet_pytorch import MTCNN, InceptionResnetV1
from tensorflow.keras.models import load_model

# cargar modelo entrenado
model = load_model("face_classifier.h5")
labels = pickle.load(open("labels.pkl", "rb"))
mtcnn = MTCNN(image_size=160)
facenet = InceptionResnetV1(pretrained='vggface2').eval()

cap = cv2.VideoCapture(0)

while True:
    ret, frame = cap.read()
    if not ret or frame is None:
        break

    if not ret:
        break
    img_rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
    boxes, _ = mtcnn.detect(img_rgb)

    if boxes is not None:
        for box in boxes:
            x1,y1,x2,y2 = map(int, box)
            rostro = img_rgb[y1:y2, x1:x2]
            
            # verificar que el recorte no esté vacío
            if rostro.size == 0:
                continue
            rostro = cv2.resize(rostro,(160,160))
            
            rostro = torch.tensor(rostro).permute(2,0,1).float()
            rostro = rostro.unsqueeze(0)
            embedding = facenet(rostro).detach().numpy()

            pred = model.predict(embedding)

            prob = np.max(pred)
            clase = np.argmax(pred)
            nombre = labels[clase]

            # evitar falsos positivos
            if prob < 0.75:
                nombre = "Desconocido"

            cv2.rectangle(frame,(x1,y1),(x2,y2),(0,255,0),2)

            cv2.putText(
                frame,
                f"{nombre} {prob:.2f}",
                (x1,y1-10),
                cv2.FONT_HERSHEY_SIMPLEX,
                0.8,
                (0,255,0),
                2
            )
            
        # Mostrar texto para salir
        cv2.putText(
            frame,
            "Presione 'Q', 'q' o ESC para salir",
            (30, 30),
            cv2.FONT_HERSHEY_SIMPLEX,
            0.9,
            (0, 255, 0),
            1,
        )

    cv2.imshow("Reconocimiento Facial", frame)

    key = cv2.waitKey(1) & 0xFF

    # ESC, 'Q' o 'q' para salir
    if key == 27 or key == ord('q') or key == ord('Q'):
        break

    # si la ventana se cierra manualmente
    if cv2.getWindowProperty("Reconocimiento Facial", cv2.WND_PROP_VISIBLE) < 1:
        break

cap.release()
cv2.destroyAllWindows()