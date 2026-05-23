# %% In [4]:

import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score

# %% In [5]:

iris = sns.load_dataset('iris') #Load Data
X = iris.iloc[:,:-1] #Set our training data
y = iris.iloc[:,-1] #Set training labels
X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,    # 20% for testing, 80% for training
    random_state=42,  # Ensures reproducibility
    shuffle=True      # Mixes the data before splitting
)

# %% In [6]:

skmodel = KNeighborsClassifier(n_neighbors=7)
skmodel.fit(X_train, y_train)

# %% Out[6]:

KNeighborsClassifier(n_neighbors=7)

# %% In [7]:

sk_predictions = skmodel.predict(X_test)
sk_accuracy = accuracy_score(y_test, sk_predictions)
print(f" sklearn-model got accuracy score of : {sk_accuracy}")