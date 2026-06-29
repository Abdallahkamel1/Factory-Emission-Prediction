# Factory Emission Prediction: AI Pipeline & Edge Deployment

This project demonstrates a complete, end-to-end AI engineering pipeline designed to predict factory emission levels. From robust data handling and statistical feature selection to designing a Deep Learning architecture and deploying it on edge devices (Android) via TensorFlow Lite, this repository highlights a comprehensive skillset in Machine Learning Engineering.

## 🧠 Core AI Engineering Skills Demonstrated

### 1. Data Engineering & Preprocessing
*   **Probabilistic Imputation:** Addressed missing categorical data not with simple modes, but by sampling from the underlying probability distributions to preserve data variance.
*   **Outlier Handling:** Implemented interquartile range (IQR) boundary capping to prevent extreme values from artificially inflating the loss function and skewing weight updates.
*   **Feature Scaling & Encoding:** Applied `StandardScaler` for continuous variables and `LabelEncoder` for categorical strings, maintaining state mappings for consistent production inference.

### 2. Statistical Analysis & Feature Selection
*   **Correlation Testing:** Programmatically applied **ANOVA** (for numeric vs. categorical) and **Chi-Square / Cramér's V** (for categorical vs. categorical) statistical tests to rigorously validate feature relevance against the target emission levels.
*   **Dimensionality Awareness:** Filtered out uninformative identifiers (e.g., Facility IDs) based on cardinality and domain context prior to model training.

### 3. Model Development & Evaluation
*   **Traditional ML Baselines:** Established performance benchmarks using Random Forest, Logistic Regression, and Decision Tree classifiers, evaluating them systematically using F1-scores and classification reports.
*   **Custom Deep Learning Architecture:** Designed a Sequential Artificial Neural Network (ANN) optimized for the classification task:
    *   Implemented `BatchNormalization` to accelerate convergence and stabilize the learning process.
    *   Utilized `Dropout` regularization to mitigate overfitting.
    *   Incorporated `EarlyStopping` dynamically monitoring validation accuracy to restore the best model weights.

### 4. Edge AI & Model Deployment (MLOps)
*   **TensorFlow Lite Integration:** Successfully bridged the gap between model training and mobile deployment by converting the heavy Keras model into a compressed `.tflite` format.
*   **State Extraction for Edge Inference:** Automated the extraction of training states into JSON configuration files (`scaler_params.json`, `categorical_mappings.json`, `target_label_mapping.json`, `feature_columns.json`). This ensures the Android mobile client can perfectly replicate the Python preprocessing pipeline on the edge device without discrepancies.

## 📁 Repository Structure

```
├── ml_model/                  # Core AI pipeline and model generation 
│   ├── pollution_detection.py # Full ML pipeline (Preprocessing -> ANN -> TFLite)
│   └── requirements.txt       # Python environment dependencies
└── android_app/               # Native edge deployment integrating the TFLite model
```

## 🚀 Running the ML Pipeline

To reproduce the AI training and export process:

1. Navigate to the `ml_model` directory and install the necessary dependencies:
   ```bash
   cd ml_model
   pip install -r requirements.txt
   ```
2. Execute the training script:
   ```bash
   python pollution_detection.py
   ```
3. **Artifacts Generated:** The script will automatically output the `emission_level_model.tflite` model and the accompanying JSON preprocessing mappings required by the Android application for real-time edge inference.
