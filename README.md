# Factory Emission Prediction

An end-to-end Machine Learning and Deep Learning pipeline designed to predict pollution emission levels from factories in Saudi Arabia. This project provides a robust workflow starting from raw data processing to deploying an optimized AI model ready for edge devices (Android).

## Project Overview

The objective of this project is to classify and predict factory emission levels using various features such as emission control technologies and CO2 emission tons. The workflow involves extensive data preprocessing, comparative analysis of classical machine learning algorithms, and the training of a Deep Neural Network. The final model is converted and optimized for mobile deployment.

## Features & Workflow

- **Data Preprocessing & Cleaning:**
  - Handled missing values using a probabilistic distribution approach to maintain statistical integrity.
  - Mitigated the effect of outliers in numerical features using Interquartile Range (IQR) clipping.
  - Performed statistical correlation analysis (ANOVA and Chi-Square) to evaluate feature importance against the target variable.

- **Model Training & Evaluation:**
  - Evaluated classical machine learning models including **Random Forest**, **Logistic Regression**, and **Decision Trees**.
  - Developed a custom **Deep Learning (Multi-Layer Perceptron)** model using TensorFlow and Keras, incorporating Dropout and Batch Normalization for improved generalization.
  - Implemented callbacks like Early Stopping to prevent over-fitting.

- **Edge Deployment Readiness:**
  - Converted the final Keras neural network to a **TensorFlow Lite (`.tflite`)** model, optimizing it for fast and lightweight inference on mobile devices.
  - Automatically exports crucial preprocessing metadata (Feature Order, Scaler Parameters, Categorical Mappings, Target Labels) as JSON files to ensure seamless integration into Android applications.

## Technologies Used

- **Programming & Analysis:** Python, Pandas, NumPy, SciPy
- **Visualization:** Matplotlib, Seaborn
- **Machine Learning:** Scikit-Learn (Random Forest, Logistic Regression, Decision Tree, StandardScaler, LabelEncoder)
- **Deep Learning:** TensorFlow, Keras
- **Deployment:** TensorFlow Lite (TFLite)
