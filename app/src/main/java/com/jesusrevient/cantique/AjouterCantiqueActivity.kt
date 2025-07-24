<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Image d’arrière-plan -->
    <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/background_spirituel"
        android:scaleType="centerCrop"
        android:alpha="0.3" />

    <!-- Bannière fixe -->
    <LinearLayout
        android:id="@+id/headerBanner"
        android:layout_width="match_parent"
        android:layout_height="56dp"
        android:orientation="horizontal"
        android:background="#FFD700"
        android:gravity="center_vertical"
        android:paddingStart="8dp"
        android:paddingEnd="8dp"
        android:elevation="4dp"
        android:layout_gravity="top">

        <ImageButton
            android:id="@+id/btnBack"
            android:layout_width="40dp"
            android:layout_height="40dp"
            android:background="@android:color/transparent"
            android:src="@drawable/ic_arrow_back"
            android:contentDescription="Retour" />

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Ajouter un cantique"
            android:textSize="20sp"
            android:textStyle="bold"
            android:textColor="#000000"
            android:gravity="center" />
    </LinearLayout>

    <!-- ScrollView contenant le contenu -->
    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="56dp"
        android:padding="16dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <EditText
                android:id="@+id/editTitre"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Titre"
                android:inputType="text" />

            <EditText
                android:id="@+id/editAuteur"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Auteur"
                android:inputType="text" />

            <EditText
                android:id="@+id/editCategorie"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Catégorie"
                android:inputType="text" />

            <EditText
                android:id="@+id/editNumero"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Numéro"
                android:inputType="number" />

            <EditText
                android:id="@+id/editParoles"
                android:layout_width="match_parent"
                android:layout_height="150dp"
                android:hint="Paroles"
                android:inputType="textMultiLine"
                android:gravity="top"
                android:scrollbars="vertical" />

            <Button
                android:id="@+id/buttonChooseAudio"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Choisir un fichier audio" />

            <Button
                android:id="@+id/buttonChoosePdf"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Choisir une partition PDF" />

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Choisir la collection"
                android:textSize="16sp"
                android:layout_marginTop="16dp"
                android:textStyle="bold"
                android:textColor="#000000" />

            <Spinner
                android:id="@+id/spinnerCollection"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="8dp"
                android:background="@drawable/spinner_background" />

            <Button
                android:id="@+id/buttonAjouter"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Ajouter le cantique"
                android:layout_marginTop="16dp"
                android:backgroundTint="#A67C52"
                android:textColor="#FFFFFF" />
        </LinearLayout>
    </ScrollView>
</FrameLayout>
    
